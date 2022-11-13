apply {
    from("$rootDir/library-build.gradle")
}

plugins {
    id(SqlDelight.plugin)
    kotlin(KotlinPlugins.serialization) version Kotlin.version

}

dependencies {
    "implementation"(project(Modules.heroDomain))

    "implementation"(Ktor.core)
    "implementation"(Ktor.clientSerialization)
    "implementation"(Ktor.android)

    "implementation"(SqlDelight.runtime)
}

sqldelight {
    database("HeroDatabase") {
        packageName = "com.sonu.hero_datasource.cache"
        sourceFolders = listOf("sqldelight")
    }
}