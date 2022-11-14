apply {
    from("$rootDir/android-library-build.gradle")
}
dependencies {
    "implementation"(project(Modules.resources_drawables))
    "implementation"(Coil.coil)

}