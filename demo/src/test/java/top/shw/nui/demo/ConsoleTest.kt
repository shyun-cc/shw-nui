package top.shw.nui.demo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import top.shw.nui.*

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], qualifiers = "w1000dp-h800dp-land")
class ConsoleTest {
    @get:Rule val compose = createComposeRule()

    @Test fun failedSwitchKeepsStoreAndRetryUsesEditedInput() {
        compose.setContent { ConsoleDemo() }
        compose.onNodeWithText("连接", substring = false).performClick()
        compose.waitUntil(6000) { compose.onAllNodesWithText("切换门店：demo.store").fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithText("切换门店：demo.store").performClick()
        compose.onNode(hasSetTextAction()).performTextReplacement("unreachable")
        compose.onNodeWithText("连接", substring = false).performClick()
        compose.waitUntil(6000) { compose.onAllNodesWithText("重试").fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithText("当前门店：demo.store").assertExists()
        compose.onNodeWithText("重试").performClick()
        compose.waitUntil(6000) { compose.onAllNodesWithText("重试").fetchSemanticsNodes().isNotEmpty() }
        compose.onNode(hasSetTextAction()).performTextReplacement("next.store")
        compose.onNodeWithText("连接", substring = false).performClick()
        compose.waitUntil(6000) { compose.onAllNodesWithText("切换门店：next.store").fetchSemanticsNodes().isNotEmpty() }
    }

    @Test fun resizePreservesSelectionAndDayNightResources() {
        val width = mutableStateOf(900.dp)
        val dark = mutableStateOf(false)
        compose.setContent {
            NuiTheme(dark.value) {
                val rgb = LocalNuiTokens.current.textPrimary
                Box(Modifier.width(width.value)) {
                    NuiPanes(true, {}, list = { Text("列表") }, detail = {
                        Text(if (rgb.red > .5f) "3号桌·暗色" else "3号桌·亮色")
                    })
                }
            }
        }
        compose.onNodeWithText("列表").assertExists()
        compose.onNodeWithText("3号桌·亮色").assertExists()
        compose.runOnIdle { width.value = 400.dp; dark.value = true }
        compose.onNodeWithText("列表").assertDoesNotExist()
        compose.onNodeWithText("3号桌·暗色").assertExists()
        compose.runOnIdle { width.value = 800.dp }
        compose.onNodeWithText("列表").assertExists()
        compose.onNodeWithText("3号桌·暗色").assertExists()
    }
}
