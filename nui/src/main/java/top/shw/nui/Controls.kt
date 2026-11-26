package top.shw.nui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun NuiInput(value: String, onChange: (String) -> Unit, label: String, modifier: Modifier = Modifier,
             enabled: Boolean = true, errorMessage: String? = null, onDone: (() -> Unit)? = null) {
    OutlinedTextField(value, onChange, modifier = modifier.semantics {
        if (errorMessage != null) error(errorMessage)
    }, label = { Text(label) }, enabled = enabled, singleLine = true, isError = errorMessage != null,
        supportingText = errorMessage?.let { { Text(it) } },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = if (onDone == null) KeyboardActions.Default else KeyboardActions(onDone = { onDone() }))
}

@Composable
fun NuiAction(label: String, onClick: () -> Unit, modifier: Modifier = Modifier,
              enabled: Boolean = true, loading: Boolean = false) {
    Button(onClick, enabled = enabled && !loading, modifier = modifier) {
        if (loading) {
            CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp)
            Spacer(Modifier.width(LocalNuiTokens.current.spaceSm))
        }
        Text(label)
    }
}

enum class NuiTone { Info, Success, Warning, Error }

@Composable
fun NuiBadge(label: String, tone: NuiTone, modifier: Modifier = Modifier) {
    val t = LocalNuiTokens.current
    val (background, foreground) = when (tone) {
        NuiTone.Info -> t.info to t.textOnInfo
        NuiTone.Success -> t.success to t.textOnSuccess
        NuiTone.Warning -> t.warning to t.textOnWarning
        NuiTone.Error -> t.error to t.textOnError
    }
    Surface(modifier = modifier, color = background, contentColor = foreground, shape = MaterialTheme.shapes.small) {
        Text(label, Modifier.padding(horizontal = t.spaceSm, vertical = t.spaceXs))
    }
}

enum class NuiFeedbackState { Loading, Empty, Error }

@Composable
fun NuiFeedback(state: NuiFeedbackState, message: String, modifier: Modifier = Modifier,
                retryLabel: String = "重试", onRetry: (() -> Unit)? = null) {
    Column(modifier.semantics { liveRegion = LiveRegionMode.Polite }) {
        if (state == NuiFeedbackState.Loading) CircularProgressIndicator()
        Text(message)
        if (state == NuiFeedbackState.Error && onRetry != null) NuiAction(retryLabel, onRetry)
    }
}

@Composable
fun NuiPanes(detailSelected: Boolean, onBack: () -> Unit, modifier: Modifier = Modifier,
             list: @Composable () -> Unit, detail: @Composable () -> Unit) {
    BoxWithConstraints(modifier) {
        val wide = maxWidth >= 720.dp
        BackHandler(enabled = !wide && detailSelected, onBack = onBack)
        if (wide) {
            Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.spacedBy(LocalNuiTokens.current.spaceMd)) {
                Box(Modifier.weight(1f)) { list() }
                Box(Modifier.weight(2f)) { detail() }
            }
        } else if (detailSelected) detail() else list()
    }
}
