plugins {
    alias(libs.plugins.paperweight.userdev)
}

dependencies {
    val libs = rootProject.libs
    api(project(":api"))
    api(project(":commons"))

    paperweight.paperDevBundle(libs.versions.minecraft.v26.m2)
}
