import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.unbrokendome.gradle.plugins.helm.command.internal.setFrom

plugins {
    val kotlinVersion = "1.7.0"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion

    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"

    val helmPluginVersion = "1.7.0"
    id("org.unbroken-dome.helm") version helmPluginVersion
    id("org.unbroken-dome.helm-releases") version helmPluginVersion
}

group = "com.flexicondev"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    // Spring dependency platform
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Test containers
    val testContainersVersion = "1.17.3"
    testImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")
    testImplementation("org.testcontainers:mongodb:$testContainersVersion")
}

helm {
    val projectChartName = project.name.replace("-", "")

    charts {
        create(projectChartName) {
            chartName.set(projectChartName)
            sourceDir.set(project.projectDir.resolve("ops/helm"))
        }
    }

    releases {
        create(projectChartName) {
            from(chart(projectChartName))
            version.set(project.version.toString())
            valueFiles.from("ops/helm/values.yaml")
            values.set(mapOf("projectRoot" to project.projectDir))
        }
    }
}

tasks.getByName("helmInstall") {
    dependsOn("bootJar")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
