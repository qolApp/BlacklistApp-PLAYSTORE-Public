package pe.com.gianbravo.blockedcontacts.presentation.common.theme.ds

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class IsicomDSColorScheme(
	val brand: Brand,
	val interactive: Interactive,
	val feedback: Feedback,
	val neutral: DSColorScheme,
	val shadow: ShadowColor,
) {
    data class Brand(
		val primary: DSColorScheme,
		val secondary: DSColorScheme
    )

    data class Feedback(
		val informative: DSColorScheme,
		val success: DSColorScheme,
		val warning: DSColorScheme,
		val critical: DSColorScheme
    )

    data class Interactive(
		val interactive1: DSColorScheme,
		val interactive2: DSColorScheme
    )

    data class ShadowColor(
        val medium: Color,
        val dark: Color,
    )

}

data class DSColorScheme(
    val darkest: Color,
    val dark: Color,
    val medium: Color,
    val light: Color,
    val lightest: Color
)

private val lightDSColorScheme = IsicomDSColorScheme(
    brand = IsicomDSColorScheme.Brand(
        primary = DSColorScheme(
            darkest = IsicomColorTokens.isi_primary_darkest,
            dark = IsicomColorTokens.isi_primary_dark,
            medium = IsicomColorTokens.isi_primary_medium,
            light = IsicomColorTokens.isi_primary_light,
            lightest = IsicomColorTokens.isi_primary_lightest,
        ),
        secondary = DSColorScheme(
            darkest = IsicomColorTokens.isi_secondary_darkest,
            dark = IsicomColorTokens.isi_secondary_dark,
            medium = IsicomColorTokens.isi_secondary_medium,
            light = IsicomColorTokens.isi_secondary_light,
            lightest = IsicomColorTokens.isi_secondary_lightest,
        )
    ),
    interactive = IsicomDSColorScheme.Interactive(
        interactive1 = DSColorScheme(
            darkest = IsicomColorTokens.isi_interactive_1_darkest,
            dark = IsicomColorTokens.isi_interactive_1_dark,
            medium = IsicomColorTokens.isi_interactive_1_medium,
            light = IsicomColorTokens.isi_interactive_1_light,
            lightest = IsicomColorTokens.isi_interactive_1_lightest,
        ),
        interactive2 = DSColorScheme(
            darkest = IsicomColorTokens.isi_interactive_2_darkest,
            dark = IsicomColorTokens.isi_interactive_2_dark,
            medium = IsicomColorTokens.isi_interactive_2_medium,
            light = IsicomColorTokens.isi_interactive_2_light,
            lightest = IsicomColorTokens.isi_interactive_2_lightest,
        )
    ),
    feedback = IsicomDSColorScheme.Feedback(
        informative = DSColorScheme(
            darkest = IsicomColorTokens.isi_feedback_informative_darkest,
            dark = IsicomColorTokens.isi_feedback_informative_dark,
            medium = IsicomColorTokens.isi_feedback_informative_medium,
            light = IsicomColorTokens.isi_feedback_informative_light,
            lightest = IsicomColorTokens.isi_feedback_informative_lightest,
        ),
        success = DSColorScheme(
            darkest = IsicomColorTokens.isi_feedback_success_darkest,
            dark = IsicomColorTokens.isi_feedback_success_dark,
            medium = IsicomColorTokens.isi_feedback_success_medium,
            light = IsicomColorTokens.isi_feedback_success_light,
            lightest = IsicomColorTokens.isi_feedback_success_lightest,
        ),
        warning = DSColorScheme(
            darkest = IsicomColorTokens.isi_feedback_warning_darkest,
            dark = IsicomColorTokens.isi_feedback_warning_dark,
            medium = IsicomColorTokens.isi_feedback_warning_medium,
            light = IsicomColorTokens.isi_feedback_warning_light,
            lightest = IsicomColorTokens.isi_feedback_warning_lightest,
        ),
        critical = DSColorScheme(
            darkest = IsicomColorTokens.isi_feedback_critical_darkest,
            dark = IsicomColorTokens.isi_feedback_critical_dark,
            medium = IsicomColorTokens.isi_feedback_critical_medium,
            light = IsicomColorTokens.isi_feedback_critical_light,
            lightest = IsicomColorTokens.isi_feedback_critical_lightest,
        )
    ),
    neutral = DSColorScheme(
        darkest = IsicomColorTokens.isi_neutral_darkest,
        dark = IsicomColorTokens.isi_neutral_dark,
        medium = IsicomColorTokens.isi_neutral_medium,
        light = IsicomColorTokens.isi_neutral_light,
        lightest = IsicomColorTokens.isi_neutral_lightest
    ),
    shadow = IsicomDSColorScheme.ShadowColor(
        dark = IsicomColorTokens.isi_shadow_dark,
        medium = IsicomColorTokens.isi_shadow_medium,
    )
)

internal val LocalDesignSystemColor = staticCompositionLocalOf { lightDSColorScheme }
