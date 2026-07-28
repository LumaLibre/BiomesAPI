plugins {
    alias(libs.plugins.run.paper)
    alias(libs.plugins.paperweight.userdev)
}

dependencies {
    implementation(project(":bundle")) {
        exclude(group = "org.bukkit")
    }
    paperweight.paperDevBundle(libs.versions.minecraft.v26.m2)
}

tasks {
    shadowJar {
        dependencies {
        }
        archiveClassifier.set("")
    }

    jar {
        enabled = false
    }

    build {
        dependsOn(shadowJar)
    }

    runServer {
        minecraftVersion("26.2")
        downloadPlugins {
            //modrinth("gBIw3Gvy", "4.2.2")
            //modrinth("gBIw3Gvy", "3.12.4")
        }
        //args("--safeMode")
        //commandLine.add("--safeMode")
    }
}