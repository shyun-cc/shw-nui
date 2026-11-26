# shw-nui

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

## License

[MIT](LICENSE).
