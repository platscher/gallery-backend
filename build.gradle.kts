import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.sant1g"
version = "1.0"

plugins {
  jacoco

  id("org.jetbrains.kotlin.jvm") version "1.5.10"
  id("org.gradle.application")
  id("com.github.johnrengelman.shadow") version "5.2.0"
  id("io.gitlab.arturbosch.detekt") version "1.19.0"
}

repositories {
  mavenLocal()
  mavenCentral()
  gradlePluginPortal()
}

val ktorVersion: String by project.extra { "1.5.4" }
val detektVersion: String by project.extra { "1.19.0" }
val logbackVersion: String by project.extra { "1.2.3" }
val logbackEncoderVersion: String by project.extra { "6.3" }
val janinoVersion: String by project.extra { "3.1.2" }
val koinVersion: String by project.extra { "2.1.6" }
val jUnitVersion: String by project.extra { "5.6.2" }
val micrometerVersion: String by project.extra { "1.4.2" }

dependencies {
  implementation("io.ktor:ktor-client-core:$ktorVersion")
  implementation("io.ktor:ktor-client-apache:$ktorVersion")
  implementation("io.ktor:ktor-client-logging-jvm:$ktorVersion")
  implementation("io.ktor:ktor-client-json:$ktorVersion")
  implementation("io.ktor:ktor-client-jackson:$ktorVersion")
  implementation("io.ktor:ktor-server-core:$ktorVersion")
  implementation("io.ktor:ktor-server-netty:$ktorVersion")
  implementation("io.ktor:ktor-jackson:$ktorVersion")
  implementation("io.ktor:ktor-metrics:$ktorVersion")
  implementation("io.ktor:ktor-metrics-micrometer:$ktorVersion")
  implementation("io.micrometer:micrometer-registry-statsd:$micrometerVersion")
  implementation("ch.qos.logback:logback-classic:$logbackVersion")
  implementation("net.logstash.logback:logstash-logback-encoder:$logbackEncoderVersion")
  implementation("org.codehaus.janino:janino:$janinoVersion")
  implementation("io.insert-koin:koin-core:$koinVersion")
  implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")
  implementation("io.insert-koin:koin-ktor:$koinVersion")
  testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
  detekt("io.gitlab.arturbosch.detekt:detekt-cli:$detektVersion")
  detekt("io.gitlab.arturbosch.detekt:detekt-core:$detektVersion")
  detekt("io.gitlab.arturbosch.detekt:detekt-api:$detektVersion")
  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")

  //  Testing
  testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")
  testImplementation("org.junit.jupiter:junit-jupiter-params:$jUnitVersion")
}

application {
  mainClassName = "com.sant1g.app.App"
}

detekt {
  toolVersion = detektVersion
  input = files("src/main/kotlin", "src/test/kotlin")
  config = files("${project.projectDir}/config/detekt/config.yml")
  baseline = file("${project.projectDir}/config/detekt/baseline.xml")
}

tasks {
  withType<Detekt> {
    this.jvmTarget = JavaVersion.VERSION_11.toString()
  }

  withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    kotlinOptions.freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
  }

  named<ShadowJar>("shadowJar") {
    archiveBaseName.set(rootProject.name)
    archiveClassifier.set("")
    archiveVersion.set("")
    manifest {
      attributes(mapOf("Main-Class" to application.mainClassName))
    }
  }

  named<Test>("test") {
    useJUnitPlatform()
    testLogging {
      events("PASSED", "FAILED", "SKIPPED")
    }
  }

  named<JavaExec>("run") {
    doFirst {
      args = listOf("run")
    }
  }

  withType<JacocoReport>{
    dependsOn("test")
  }
}

//kotlin.sourceSets["main"].kotlin.srcDirs("src")
//kotlin.sourceSets["test"].kotlin.srcDirs("test")

//sourceSets["main"].resources.srcDirs("resources")
//sourceSets["test"].resources.srcDirs("testresources")
