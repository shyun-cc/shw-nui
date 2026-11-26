# Third-Party Notices

The repository incorporates the [Gradle 8.11.1 Wrapper](https://github.com/gradle/gradle/tree/v8.11.1) in `gradlew`, `gradlew.bat` and `gradle/wrapper/gradle-wrapper.jar`. The scripts retain their original copyright headers. The JAR matches the [published Gradle Wrapper checksum](https://downloads.gradle.org/distributions/gradle-8.11.1-wrapper.jar.sha256), and its original `META-INF/LICENSE` is reproduced in [LICENSES/gradle-wrapper.Apache-2.0.txt](LICENSES/gradle-wrapper.Apache-2.0.txt).

The Wrapper configuration in `gradle/wrapper/gradle-wrapper.properties` selects Gradle 8.11.1 and pins the distribution checksum. AndroidX and Kotlin are resolved through the Gradle build and retain their upstream license terms.
