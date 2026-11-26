# shw-nui

[简体中文](./README.md) | [English](./README.en.md) | 繁體中文

面向 Jetpack Compose 的原生 Android UI 元件。元件庫提供亮色和暗色主題以及通用控制元件。隨附的演示使用本地資料展示餐飲控制檯示例。

## 建置與測試

使用 JDK 17、Android SDK Platform 35、Build Tools 35.0.0、Node.js 24 和 pnpm 11.0.9。確保 `ANDROID_HOME` 指向 Android SDK。相依從公開的 npm、Google Maven、Maven Central 和 Gradle Plugin Portal 儲存庫獲取。

鎖定檔將共享設計權杖固定到[公開的 GitHub Release](https://github.com/shyun-cc/shw-ui-tokens/releases/tag/v0.33.1)。

```sh
pnpm install --frozen-lockfile
pnpm test
./gradlew :nui:assembleRelease :nui:lintRelease :demo:assembleDebug :demo:testDebugUnitTest
```

元件庫的 AAR 輸出到 `nui/build/outputs/aar/`，演示應用的除錯 APK 輸出到 `demo/build/outputs/apk/debug/`。要執行演示，請在 Android Studio 中開啟本儲存庫並選擇 `demo` 模組。

## 鳴謝與第三方版權

- [Jetpack Compose](https://developer.android.com/compose)、[Compose Material 3](https://developer.android.com/jetpack/androidx/releases/compose-material3) 和 [AndroidX Activity Compose](https://developer.android.com/jetpack/androidx/releases/activity) 提供原生 UI、主題和 Activity 整合。這些 AndroidX 相依按 [Apache-2.0](https://github.com/androidx/androidx/blob/androidx-main/LICENSE.txt) 散布。
- [Kotlin](https://github.com/JetBrains/kotlin)（Apache-2.0）、[Android Gradle Plugin](https://developer.android.com/build/releases/gradle-plugin) 和 [Gradle](https://github.com/gradle/gradle)（Apache-2.0）支援 Android 建置。本儲存庫包含 Gradle Wrapper 指令碼和 JAR，其來源及上游授權請見 [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md)。
- [JUnit 4](https://github.com/junit-team/junit4)（[EPL-1.0](https://github.com/junit-team/junit4/blob/r4.13.2/LICENSE-junit.txt)）和 [Robolectric](https://github.com/robolectric/robolectric)（[MIT](https://github.com/robolectric/robolectric/blob/robolectric-4.14.1/LICENSE)）用於演示專案的測試。

## 許可證

[MIT](LICENSE)。
