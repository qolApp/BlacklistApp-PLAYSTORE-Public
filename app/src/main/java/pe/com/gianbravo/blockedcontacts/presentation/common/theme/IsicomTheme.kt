package pe.com.gianbravo.blockedcontacts.presentation.common.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import pe.com.gianbravo.blockedcontacts.presentation.common.theme.ds.BorderRadius
import pe.com.gianbravo.blockedcontacts.presentation.common.theme.ds.IsicomColorTokens.isi_neutral_light
import pe.com.gianbravo.blockedcontacts.presentation.common.theme.ds.IsicomDSColorScheme
import pe.com.gianbravo.blockedcontacts.presentation.common.theme.ds.IsicomTypography
import pe.com.gianbravo.blockedcontacts.presentation.common.theme.ds.LocalDesignSystemBorderRadius
import pe.com.gianbravo.blockedcontacts.presentation.common.theme.ds.LocalDesignSystemColor
import pe.com.gianbravo.blockedcontacts.presentation.common.theme.ds.LocalDesignSystemStrokeWidth
import pe.com.gianbravo.blockedcontacts.presentation.common.theme.ds.LocalDesignSystemTypography
import pe.com.gianbravo.blockedcontacts.presentation.common.theme.ds.StrokeWidth
import dagger.hilt.android.internal.managers.FragmentComponentManager
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat

private val lightColors = lightColorScheme(
    primary = md_theme_light_primary,
    secondary = md_theme_light_secondary,
    background = isi_neutral_light,
    surface = md_theme_light_surface,
    onPrimary = isi_neutral_light,
    onSecondary = md_theme_light_onSecondary,
    onBackground = md_theme_light_onBackground,
    onSurface = md_theme_light_onSurface
)

@Composable
fun IsicomTheme(
	color: IsicomDSColorScheme = IsicomTheme.dSColor,
	borderRadius: BorderRadius = IsicomTheme.dSBorderRadius,
	strokeWidth: StrokeWidth = IsicomTheme.dSStrokeWidth,
	typography: IsicomTypography = IsicomTheme.dSTypography,
	content: @Composable () -> Unit,
) {

    CompositionLocalProvider(
        LocalDesignSystemTypography provides typography,
        LocalDesignSystemBorderRadius provides borderRadius,
        LocalDesignSystemStrokeWidth provides strokeWidth,
        LocalDesignSystemColor provides color
    ) {
        val view = LocalView.current
        if (!view.isInEditMode) {
            SideEffect {
                val context = FragmentComponentManager.findActivity(view.context)
                val window = (context as Activity).window
                window.statusBarColor = color.neutral.light.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
            }
        }

        MaterialTheme(
            // Here we use the MaterialFormat to let the components render with no problem with its
            //  own colors
            colorScheme = lightColors,
            content = {
                // Set the default background color for all screens
                Surface(color = color.neutral.light) {
                    content()
                }
            }
        )

    }
}

// Access point to all the DS colors, typo, size, and more.
// This attach the value to the project by IsicomTheme object

// TODO GBA CHANGE NAME
object IsicomTheme {
    val dSTypography: IsicomTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalDesignSystemTypography.current

    val dSBorderRadius: BorderRadius
        @Composable
        @ReadOnlyComposable
        get() = LocalDesignSystemBorderRadius.current

    val dSStrokeWidth: StrokeWidth
        @Composable
        @ReadOnlyComposable
        get() = LocalDesignSystemStrokeWidth.current

    val dSColor: IsicomDSColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalDesignSystemColor.current
}