plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "Wyck"

include(":api")
include(":commons")
include(":decoders")
include(":bundle")
include(":codegen")
include(":paper")
include(":tests")
include(":test-plugin")
include(":minecraft:1_21_11")
include(":minecraft:26_1")
include(":minecraft:26_2")
