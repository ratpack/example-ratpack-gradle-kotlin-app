import ratpack.gradle.*
import com.github.jengelman.gradle.plugins.shadow.*
import org.gradle.api.*
import org.gradle.plugins.ide.idea.*
import org.gradle.plugins.ide.idea.model.*

buildscript {
    repositories {
        jcenter()
        gradleScriptKotlin()
    }

    dependencies {
        classpath("io.ratpack:ratpack-gradle:1.4.1")
        classpath("com.github.jengelman.gradle.plugins:shadow:1.2.3")
        classpath(kotlinModule("gradle-plugin"))
    }
}

if (!JavaVersion.current().isJava8Compatible()) {
    throw IllegalStateException("Must be built with Java 8 or higher")
}

apply {
    plugin<RatpackPlugin>()
    plugin<ShadowPlugin>()
    plugin("kotlin")
    plugin<IdeaPlugin>()
    from("$rootDir/gradle/idea.gradle")
}

repositories {
    jcenter()
    gradleScriptKotlin()
}

dependencies {
    compile(kotlinModule("stdlib"))
    project.extensions.getByType(RatpackExtension::class.java).let { ratpack ->
        compile(ratpack.dependency("guice"))
    }
    runtime("org.apache.logging.log4j:log4j-slf4j-impl:2.6.1")
    runtime("org.apache.logging.log4j:log4j-api:2.6.1")
    runtime("org.apache.logging.log4j:log4j-core:2.6.1")
    runtime("com.lmax:disruptor:3.3.4")
    testCompile("junit:junit:4.+")
    testCompile("org.jetbrains.spek:spek:1.0.+")
    testCompile(kotlinModule("test"))
}

configure<ApplicationPluginConvention> {
    mainClassName = "ratpack.example.kotlin.Main"
}
