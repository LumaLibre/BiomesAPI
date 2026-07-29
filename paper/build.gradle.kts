import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import net.minecrell.pluginyml.paper.PaperPluginDescription
import org.gradle.kotlin.dsl.project

plugins {
    alias(libs.plugins.plugin.yml.paper)
    alias(libs.plugins.paperweight.userdev)
    alias(libs.plugins.modrinth.minotaur)
}

val supported = listOf("26.2", "26.1.2", "1.21.11")
val minecraft = ":minecraft"
val minecraftProjects = project(minecraft)
    .subprojects
    .map { it.name }

group = "dev.wyck.paper"

dependencies {
    paperweight.paperDevBundle(libs.versions.minecraft.v1.m21.r11)
    compileOnly(libs.spongepowered.configurate.yaml)
    implementation(libs.faststats.metrics)
    implementation(project(":commons"))
    implementation(project(":decoders"))

    for (project in minecraftProjects) {
        implementation(project(path = "${minecraft}:${project}"))
    }
}

tasks {
    shadowJar {
        relocate("dev.faststats", "dev.wyck.metrics")
        exclude("com/google/**")

        minimize {
            exclude(project(":api"))
            exclude(project(":commons"))
            exclude(project(":decoders"))
            for (project in minecraftProjects) {
                exclude(project("${minecraft}:${project}"))
            }
            exclude("META-INF/maven/**")
            exclude("META-INF/proguard/**")
        }
        archiveBaseName.set("Wyck")
        archiveClassifier.set("")
    }

    jar {
        enabled = false
    }

    build {
        dependsOn(shadowJar)
    }
}

paper {
    name = "Wyck"
    main = "dev.wyck.paper.WyckPlugin"
    bootstrapper = "dev.wyck.paper.WyckPluginBootstrap"
    version = project.version.toString()
    authors = listOf("Jsinco", "Outspending")
    apiVersion = "1.21"
    description = "Standalone Wyck plugin for Paper servers."
    website = "https://wyck.dev"
    hasOpenClassloader = true
    foliaSupported = true
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP

    serverDependencies {
        register("ProtocolLib") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
            required = false
            joinClasspath = true
        }
        register("PacketEvents") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
            required = false
            joinClasspath = true
        }
    }
}

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN"))
    projectId.set("wyck")

    val version = project.version.toString()

    versionNumber.set(version)
    versionType.set(if (version.contains("-")) "beta" else "release")
    uploadFile.set(tasks.shadowJar)
    gameVersions.addAll(supported)
    loaders.addAll("paper", "purpur", "folia")
}


tasks.named("modrinth") {
    dependsOn(":tests:test") // no live for rn
}