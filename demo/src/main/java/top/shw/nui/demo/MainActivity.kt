package top.shw.nui.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import top.shw.nui.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { ConsoleDemo() }
    }
}

@Composable
internal fun ConsoleDemo() {
    var appearance by rememberSaveable { mutableStateOf(0) }
    val dark = when (appearance) { 1 -> false; 2 -> true; else -> isSystemInDarkTheme() }
    var address by rememberSaveable { mutableStateOf("demo.store") }
    var active by rememberSaveable { mutableStateOf("") }
    var pending by rememberSaveable { mutableStateOf<String?>(null) }
    var failure by rememberSaveable { mutableStateOf(false) }
    var selected by rememberSaveable { mutableStateOf<Int?>(null) }
    var editing by rememberSaveable { mutableStateOf(true) }
    val connect = {
        if (pending == null && address.isNotBlank()) {
            failure = false
            pending = address.trim()
        }
    }
    LaunchedEffect(pending) {
        val target = pending ?: return@LaunchedEffect
        delay(450)
        failure = !target.endsWith(".store") || target.length <= 6
        if (!failure) {
            if (active != target) selected = null
            active = target
            editing = false
        }
        pending = null
    }

    NuiTheme(dark) {
        Scaffold { padding ->
            Column(Modifier.fillMaxSize().padding(padding).padding(12.dp)) {
                TextButton(onClick = { appearance = (appearance + 1) % 3 }) {
                    Text("主题：" + listOf("跟随系统", "浅色", "深色")[appearance])
                }
                if (editing) {
                    Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(if (active.isEmpty()) "连接门店" else "当前门店：$active")
                        Text("本地示例接受以 .store 结尾的地址")
                        NuiInput(address, { address = it; failure = false }, "门店地址",
                            enabled = pending == null, errorMessage = if (failure) "地址不可达" else null, onDone = connect)
                        when {
                            pending != null -> NuiFeedback(NuiFeedbackState.Loading, "正在连接")
                            failure -> NuiFeedback(NuiFeedbackState.Error, "请检查门店地址后重试", onRetry = connect)
                        }
                        NuiAction("连接", connect, enabled = address.isNotBlank(), loading = pending != null)
                        if (active.isNotEmpty()) TextButton(onClick = {
                            pending = null; address = active; failure = false; editing = false
                        }) { Text("返回当前门店") }
                    }
                } else {
                    TextButton(onClick = { editing = true; address = active }) { Text("切换门店：$active") }
                    NuiPanes(selected != null, { selected = null }, Modifier.fillMaxSize(), list = {
                        Column(Modifier.verticalScroll(rememberScrollState())) {
                            Text("桌台")
                            for (table in 1..3) TextButton(onClick = { selected = table }) { Text("${table}号桌") }
                        }
                    }, detail = {
                        Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            val table = selected
                            if (table == null) NuiFeedback(NuiFeedbackState.Empty, "请选择桌台")
                            else {
                                Text("${table}号桌")
                                if (table == 1) {
                                    NuiBadge("空桌", NuiTone.Info)
                                    NuiBadge("订单待付款", NuiTone.Warning)
                                } else {
                                    NuiBadge("用餐中", NuiTone.Success)
                                    NuiBadge("本次点单待确认", NuiTone.Warning)
                                }
                                TextButton(onClick = { selected = null }) { Text("返回桌台") }
                            }
                        }
                    })
                }
            }
        }
    }
}
