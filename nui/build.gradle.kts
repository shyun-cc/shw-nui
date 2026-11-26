plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("maven-publish")
}

val generated = layout.buildDirectory.dir("generated/shared-theme")
val generateTokens by tasks.registering(Exec::class) {
    inputs.files(rootProject.files("scripts/generate.mjs", "pnpm-lock.yaml", "node_modules/@shwkj/shw-ui-tokens/dist/tokens.json"))
    outputs.dir(generated)
    workingDir(rootDir)
    commandLine("node", "scripts/generate.mjs", generated.get().asFile.absolutePath)
}
android {
    namespace = "top.shw.nui"
    compileSdk = 35
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
    defaultConfig { minSdk = 26 }
    buildFeatures { compose = true }
    sourceSets["main"].java.srcDir(generated.map { it.dir("kotlin") })
    sourceSets["main"].res.srcDir(generated.map { it.dir("res") })
    publishing { singleVariant("release") { withSourcesJar() } }
}
tasks.named("preBuild") { dependsOn(generateTokens) }
tasks.matching { it.name.endsWith("SourcesJar") }.configureEach { dependsOn(generateTokens) }
dependencies {
    api(platform("androidx.compose:compose-bom:2025.04.01"))
    api("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.10.1")
}
afterEvaluate {
    publishing.publications.create<MavenPublication>("release") {
        groupId = "top.shw"; artifactId = "nui"; version = "0.1.0-SNAPSHOT"
        from(components["release"])
    }
}
