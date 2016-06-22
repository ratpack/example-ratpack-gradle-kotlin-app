This is an example Ratpack app that:

1. Is implemented in Kotlin
2. Use Guice for dependency injection
3. Uses the `ratpack` Gradle plugin
4. Uses SpringSource's SpringLoaded for runtime reloading of classes during development
5. Adds helper functions to make Ratpack more Kotlin like (see `helpers.kt`)

## Getting Started

Check this project out, cd into the directory and run:

    ./gradlew run

This will start the ratpack app in a development mode. In your browser go to `http://localhost:5050`.

The Gradle Ratpack plugin builds on the Gradle Application plugin. This means it's easy to create a standalone
distribution for your app.

Run:

    ./gradlew installApp
    cd build/install/example-ratpack-gradle-java-app
    bin/example-ratpack-gradle-java-app

Your app should now be running (see http://gradle.org/docs/current/userguide/application_plugin.html) for more
on what the Gradle application plugin can do for you.

## Development time reloading

Most application classes can be changed at runtime without needing to restart the application. This is made
possible by [Gradle's Continuous Mode](https://docs.gradle.org/current/userguide/continuous_build.html) via Ratpack's Gradle plugin.

If running the application via `./gradlew run --continuous` or `./gradlew run -t` for short, you will see your changes to source code be detected and applied.

## IDEA integration

The Ratpack Gradle plugin has special support for IntelliJ IDEA. To open the project in IDEA, run:

    ./gradlew idea

This will generate a `.ipr` file that you can use to open the project in IDEA.

In the “Run” menu, you will find a run configuration for launching the Ratpack app from within your IDE.

Hot reloading is not supported in this mode.

## More on Ratpack

To learn more about Ratpack, visit http://www.ratpack.io and join our slack channel https://slack-signup.ratpack.io/
