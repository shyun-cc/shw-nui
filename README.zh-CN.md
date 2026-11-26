# shw-nui

[English](./README.md) | 简体中文 | [繁體中文](./README.zh-TW.md)

面向 Jetpack Compose 的原生 Android UI 组件。组件库提供亮色和暗色主题以及通用控件。随附的演示使用本地数据展示餐饮控制台示例。

## 构建与测试

使用 JDK 17、Android SDK Platform 35、Build Tools 35.0.0、Node.js 24 和 pnpm 11.0.9。确保 `ANDROID_HOME` 指向 Android SDK。依赖从公开的 npm、Google Maven、Maven Central 和 Gradle Plugin Portal 仓库获取。

锁文件将共享设计令牌固定到[公开的 GitHub Release](https://github.com/shyun-cc/shw-ui-tokens/releases/tag/v0.33.1)。

```sh
pnpm install --frozen-lockfile
pnpm test
./gradlew :nui:assembleRelease :nui:lintRelease :demo:assembleDebug :demo:testDebugUnitTest
```

组件库的 AAR 输出到 `nui/build/outputs/aar/`，演示应用的调试 APK 输出到 `demo/build/outputs/apk/debug/`。要运行演示，请在 Android Studio 中打开本仓库并选择 `demo` 模块。

## 鸣谢与第三方版权

- [Jetpack Compose](https://developer.android.com/compose)、[Compose Material 3](https://developer.android.com/jetpack/androidx/releases/compose-material3) 和 [AndroidX Activity Compose](https://developer.android.com/jetpack/androidx/releases/activity) 提供原生 UI、主题和 Activity 集成。这些 AndroidX 依赖按 [Apache-2.0](https://github.com/androidx/androidx/blob/androidx-main/LICENSE.txt) 分发。
- [Kotlin](https://github.com/JetBrains/kotlin)（Apache-2.0）、[Android Gradle Plugin](https://developer.android.com/build/releases/gradle-plugin) 和 [Gradle](https://github.com/gradle/gradle)（Apache-2.0）支持 Android 构建。本仓库包含 Gradle Wrapper 脚本和 JAR，其来源及上游许可见 [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md)。
- [JUnit 4](https://github.com/junit-team/junit4)（[EPL-1.0](https://github.com/junit-team/junit4/blob/r4.13.2/LICENSE-junit.txt)）和 [Robolectric](https://github.com/robolectric/robolectric)（[MIT](https://github.com/robolectric/robolectric/blob/robolectric-4.14.1/LICENSE)）用于演示项目的测试。

## 许可证

[MIT](LICENSE)。
