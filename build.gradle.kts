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
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.isIncremental = true
    sourceCompatibility = "21"
}