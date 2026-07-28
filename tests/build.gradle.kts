plugins {
    alias(libs.plugins.paperweight.userdev)
}

group = "dev.wyck.tests"

dependencies {
    val libs = rootProject.libs
    implementation(project(":commons")) // should be bundle
    implementation(project(":decoders"))
    paperweight.paperDevBundle(libs.versions.minecraft.v26.m2)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.google.guava)
    testImplementation(libs.mockito.core)
    testRuntimeOnly(libs.junit.platform.launcher)
}

// cleanup
val testServerDir: File = layout.buildDirectory.dir("test-server").get().asFile

val deleteTestWorlds by tasks.registering(Delete::class) {
    delete(provider {
        testServerDir.listFiles { file -> file.isDirectory && file.name.startsWith("universe-") }
            ?.toList() ?: emptyList<File>()
    })
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    jvmArgs(
        "-XX:+EnableDynamicAgentLoading",
        "--enable-native-access=ALL-UNNAMED",
        "--sun-misc-unsafe-memory-access=allow",
        "-Xshare:off",
    )


    systemProperty("repo.root", rootProject.projectDir.absolutePath)

    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showStandardStreams = false
    }
}

tasks.test {
    forkEvery = 1

    workingDir = testServerDir
    doFirst { testServerDir.mkdirs() }

    if (project.hasProperty("acceptEula")) {
        systemProperty("com.mojang.eula.agree", "true")
    }

    finalizedBy(deleteTestWorlds)
}

val wireProviderTest by tasks.registering(Test::class) {
    description = "Checks wire providers are correctly resolved typed."
    group = "verification"

    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    filter { includeTestsMatching("*WireProviderResolutionTest") }
}

tasks.jar {
    enabled = false
}
