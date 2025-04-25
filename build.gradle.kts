plugins {
    application
    id("java")
}

application.mainClass = "com.fingerissue.atelier.Sketchbook"
group = "com.fingerissue.atelier"
version = "1.0"

val jdaVersion = "5.4.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:$jdaVersion")
    implementation("org.yaml:snakeyaml:2.2")
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.11")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.isIncremental = true
    sourceCompatibility = "21"
}