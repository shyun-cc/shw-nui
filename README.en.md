# shw-nui

[简体中文](./README.md) | English | [繁體中文](./README.zh-TW.md)

Native Android UI components for Jetpack Compose. The library provides a light and dark theme and shared controls. The included demo shows a sample restaurant console with local data.

## Build and test

Use JDK 17, Android SDK Platform 35 and Build Tools 35.0.0, Node.js 24, and pnpm 11.0.9. Ensure that `ANDROID_HOME` points to the Android SDK. Dependencies resolve from the public npm, Google Maven, Maven Central and Gradle Plugin Portal repositories.

The lockfile pins the shared design tokens to a [public GitHub release](https://github.com/shyun-cc/shw-ui-tokens/releases/tag/v0.33.1).

```sh
pnpm install --frozen-lockfile
pnpm test
./gradlew :nui:assembleRelease :nui:lintRelease :demo:assembleDebug :demo:testDebugUnitTest
```

The library AAR is written to `nui/build/outputs/aar/`. The debug demo APK is written to `demo/build/outputs/apk/debug/`. To run the demo, open this repository in Android Studio and select the `demo` module.

## Acknowledgments & Licenses

- [Jetpack Compose](https://developer.android.com/compose), [Compose Material 3](https://developer.android.com/jetpack/androidx/releases/compose-material3) and [AndroidX Activity Compose](https://developer.android.com/jetpack/androidx/releases/activity) provide the native UI, theming and activity integration. These AndroidX dependencies are distributed under [Apache-2.0](https://github.com/androidx/androidx/blob/androidx-main/LICENSE.txt).
- [Kotlin](https://github.com/JetBrains/kotlin) (Apache-2.0), the [Android Gradle Plugin](https://developer.android.com/build/releases/gradle-plugin) and [Gradle](https://github.com/gradle/gradle) (Apache-2.0) power the Android build. The repository includes the Gradle Wrapper scripts and JAR. See [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md) for their provenance and upstream license.
- [JUnit 4](https://github.com/junit-team/junit4) ([EPL-1.0](https://github.com/junit-team/junit4/blob/r4.13.2/LICENSE-junit.txt)) and [Robolectric](https://github.com/robolectric/robolectric) ([MIT](https://github.com/robolectric/robolectric/blob/robolectric-4.14.1/LICENSE)) support the demo tests.

## License

[MIT](LICENSE).
