apply {
    from("$rootDir/library-build.gradle")
}
dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.heroDomain))
    "implementation"(project(Modules.heroDataSource))
    "implementation"(project(Modules.heroDataSourceTest))

    "implementation"(Kotlinx.coroutinesCore)
    "implementation"(Junit.junit4)

}