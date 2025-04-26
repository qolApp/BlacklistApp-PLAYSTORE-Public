package pe.com.gianbravo.blockedcontacts.presentation.common.theme.ds

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import pe.com.gianbravo.blockedcontacts.presentation.common.theme.ds.IsicomBorderRadiusTokens
import pe.com.gianbravo.blockedcontacts.presentation.common.theme.ds.IsicomBorderWidthTokens

@Immutable
data class BorderRadius(
	val none: Dp = IsicomBorderRadiusTokens.isi_border_radius_none,
	val small: Dp = IsicomBorderRadiusTokens.isi_border_radius_small,
	val medium: Dp = IsicomBorderRadiusTokens.isi_border_radius_medium,
	val large: Dp = IsicomBorderRadiusTokens.isi_border_radius_large,
	val xlarge: Dp = IsicomBorderRadiusTokens.isi_border_radius_xlarge,
	val pill: Dp = IsicomBorderRadiusTokens.isi_border_radius_pill
)

@Immutable
data class StrokeWidth(
	val none: Dp = IsicomBorderWidthTokens.isi_stroke_width_none,
	val hairline: Dp = IsicomBorderWidthTokens.isi_stroke_width_hairline,
	val thin: Dp = IsicomBorderWidthTokens.isi_stroke_width_thin,
	val thick: Dp = IsicomBorderWidthTokens.isi_stroke_width_thick,
	val heavy: Dp = IsicomBorderWidthTokens.isi_stroke_width_heavy
)

internal val LocalDesignSystemBorderRadius = staticCompositionLocalOf { BorderRadius() }
internal val LocalDesignSystemStrokeWidth = staticCompositionLocalOf { StrokeWidth() }
