package top.shw.nui

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext

val LocalNuiTokens = staticCompositionLocalOf<NuiTokens> { error("Wrap the component in NuiTheme") }

@Composable
fun NuiTheme(dark: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val context = LocalContext.current
    val current = LocalConfiguration.current
    val t = remember(context, current, dark) {
        val config = Configuration(current).apply {
            val mode = if (dark) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO
            uiMode = (uiMode and Configuration.UI_MODE_NIGHT_MASK.inv()) or mode
        }
        NuiTokens(context.createConfigurationContext(config).resources, dark)
    }
    val base = if (dark) darkColorScheme() else lightColorScheme()
    val colors = base.copy(primary = t.primary, onPrimary = t.textOnPrimary,
        background = t.bg, onBackground = t.textPrimary, surface = t.surface,
        onSurface = t.textPrimary, onSurfaceVariant = t.textSecondary,
        error = t.error, onError = t.textOnError, outline = t.border)
    val typography = Typography().let { it.copy(
        bodyLarge = it.bodyLarge.copy(fontSize = t.fontSize, fontWeight = t.fontWeightRegular),
        labelLarge = it.labelLarge.copy(fontSize = t.fontSizeMd, fontWeight = t.fontWeightMedium))
    }
    CompositionLocalProvider(LocalNuiTokens provides t) {
        MaterialTheme(colorScheme = colors, typography = typography, content = content)
    }
}
