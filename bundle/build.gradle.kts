import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val bundledSourceProjects = listOf(":api")
val minecraft = ":minecraft"
val minecraftProjects = project(minecraft)
    .subprojects
    .map { it.name }
val minecraftArtifactIds = mapOf(
    "1_21_11" to "wyck-1.21.11",
    "26_1" to "wyck-26.1",
    "26_2" to "wyck-26.2",
)
check(minecraftProjects.toSet() == minecraftArtifactIds.keys) {
    "Every Minecraft module must have an artifact ID: ${minecraftProjects.toSet() - minecraftArtifactIds.keys}"
}

data class ModulePublication(
    val name: String,
    val artifactId: String,
    val projectPath: String,
    val dependencies: List<String> = emptyList(),
)

val modulePublications = listOf(
    ModulePublication("wyckApi", "wyck-api", ":api"),
    ModulePublication("wyckCommons", "wyck-commons", ":commons", listOf("wyck-api")),
) + minecraftProjects.map { name ->
    ModulePublication(
        "wyckMinecraft${name.replaceFirstChar(Char::uppercaseChar)}",
        minecraftArtifactIds.getValue(name),
        "${minecraft}:${name}",
        listOf("wyck-commons"),
    )
}
val wyckBasicProjects = listOf(":api", ":commons") + minecraftProjects.map { "${minecraft}:${it}" }

val wyckBasic by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

dependencies {
    val libs = rootProject.libs
    api(project(":api"))
    api(project(":commons"))
    api(project(":decoders"))

    // NMS Implementations
    for (project in minecraftProjects) {
        api(project(path = "${minecraft}:${project}"))
    }

    wyckBasicProjects.forEach { path ->
        add(wyckBasic.name, project(path))
    }
}

java {
    withSourcesJar()
}

tasks.named<Jar>("sourcesJar") {
    bundledSourceProjects.forEach { path ->
        val proj = project(path)
        val main = proj.extensions
            .getByType<SourceSetContainer>()
            .getByName("main")
        from(main.allSource)
    }
    dependsOn(bundledSourceProjects.map { project(it).tasks.named("classes") })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

val moduleSourcesJars = modulePublications.associate { publication ->
    publication.name to tasks.register<Jar>("${publication.name}SourcesJar") {
        archiveBaseName.set(publication.artifactId)
        archiveClassifier.set("sources")
        val sourceProject = project(publication.projectPath)
        val main = sourceProject.extensions
            .getByType<SourceSetContainer>()
            .getByName("main")
        from(main.allSource)
        dependsOn(sourceProject.tasks.named("classes"))
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

val wyckBasicSourcesJar by tasks.registering(Jar::class) {
    archiveBaseName.set("wyck-basic")
    archiveClassifier.set("sources")
    wyckBasicProjects.forEach { path ->
        val sourceProject = project(path)
        val main = sourceProject.extensions
            .getByType<SourceSetContainer>()
            .getByName("main")
        from(main.allSource)
    }
    dependsOn(wyckBasicProjects.map { project(it).tasks.named("classes") })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.named<Jar>("jar") {
    enabled = false
}

tasks.withType<PublishToMavenRepository>().configureEach {
    dependsOn(":tests:wireProviderTest")
}

configurations {
    apiElements { outgoing.artifacts.clear(); outgoing.artifact(tasks.shadowJar) }
    runtimeElements { outgoing.artifacts.clear(); outgoing.artifact(tasks.shadowJar) }
}

tasks.shadowJar {
    exclude("com/google/**")
    minimize {
        exclude(project(":api"))
        exclude(project(":commons"))
        exclude(project(":decoders"))
        for (project in minecraftProjects) {
            exclude(project("${minecraft}:${project}"))
        }
        exclude("META-INF/**")
    }
}

val wyckBasicJar by tasks.registering(ShadowJar::class) {
    archiveBaseName.set("wyck-basic")
    archiveClassifier.set("")
    configurations = listOf(wyckBasic)
    exclude("com/google/**")
    minimize {
        wyckBasicProjects.forEach { path ->
            exclude(project(path))
        }
        exclude("META-INF/**")
    }
}

tasks.build {
    dependsOn(wyckBasicJar, wyckBasicSourcesJar, moduleSourcesJars.values)
}

publishing {
    val repo: String? = System.getenv("REPO_URL")
    val user: String? = System.getenv("REPO_USERNAME")
    val pass: String? = System.getenv("REPO_PASSWORD")


    repositories {
        if (repo == null || user == null || pass == null) return@repositories

        maven {
            url = uri(repo)
            credentials(PasswordCredentials::class) {
                username = user
                password = pass
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "wyck"
            version = project.version.toString()

            artifact(tasks.shadowJar.get().archiveFile) {
                builtBy(tasks.shadowJar)
            }

            artifact(tasks.named("sourcesJar").get())
        }

        // TODO: Remove before 3.3.0
        create<MavenPublication>("wyckUppercase") {
            groupId = project.group.toString()
            artifactId = "Wyck"
            version = project.version.toString()

            artifact(tasks.shadowJar.get().archiveFile) {
                builtBy(tasks.shadowJar)
            }

            artifact(tasks.named("sourcesJar").get())
        }

        modulePublications.forEach { module ->
            create<MavenPublication>(module.name) {
                groupId = project.group.toString()
                artifactId = module.artifactId
                version = project.version.toString()

                artifact(project(module.projectPath).tasks.named<Jar>("jar"))
                artifact(moduleSourcesJars.getValue(module.name))

                if (module.dependencies.isNotEmpty()) {
                    pom.withXml {
                        val dependencies = asNode().appendNode("dependencies")
                        module.dependencies.forEach { dependencyId ->
                            val dependency = dependencies.appendNode("dependency")
                            dependency.appendNode("groupId", project.group.toString())
                            dependency.appendNode("artifactId", dependencyId)
                            dependency.appendNode("version", project.version.toString())
                            dependency.appendNode("scope", "compile")
                        }
                    }
                }
            }
        }

        create<MavenPublication>("wyckVanilla") {
            groupId = project.group.toString()
            artifactId = "wyck-vanilla"
            version = project.version.toString()

            artifact(wyckBasicJar.get().archiveFile) {
                builtBy(wyckBasicJar)
            }

            artifact(wyckBasicSourcesJar.get())
        }
    }
}
