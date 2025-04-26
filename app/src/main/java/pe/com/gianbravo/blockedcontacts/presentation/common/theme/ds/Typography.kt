package pe.com.gianbravo.blockedcontacts.presentation.common.theme.ds

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

data class IsicomTypography(
	val titleXXL: TextStyle = IsicomTypographyTokens.titleXXL,
	val titleXL: TextStyle = IsicomTypographyTokens.titleXL,
	val titleLG: TextStyle = IsicomTypographyTokens.titleLG,
	val titleMD: TextStyle = IsicomTypographyTokens.titleMD,
	val titleSM: TextStyle = IsicomTypographyTokens.titleSM,
	val titleXS: TextStyle = IsicomTypographyTokens.titleXS,

	val subtitle2xLarge: TextStyle = IsicomTypographyTokens.subtitle2xLarge,
	val subtitleXLarge: TextStyle = IsicomTypographyTokens.subtitleXLarge,
	val subtitleLarge: TextStyle = IsicomTypographyTokens.subtitleLarge,

	val bodyMD: TextStyle = IsicomTypographyTokens.bodyMD,
	val bodySM: TextStyle = IsicomTypographyTokens.bodySM,

    // Unknowns
	val unknown1: TextStyle = IsicomTypographyTokens.unknown1,
	val unknown2: TextStyle = IsicomTypographyTokens.unknown2,
	val unknown3: TextStyle = IsicomTypographyTokens.unknown3,
	val unknown4: TextStyle = IsicomTypographyTokens.unknown4,
	val unknown2Light: TextStyle = IsicomTypographyTokens.unknown2_light,
	val unknown2SemiLight: TextStyle = IsicomTypographyTokens.unknown2_semi_light,
	val unknown3Light: TextStyle = IsicomTypographyTokens.unknown3_light,
	val bodyDesktopXS: TextStyle = IsicomTypographyTokens.bodyDesktopXS,
	val bodyDesktopSM: TextStyle = IsicomTypographyTokens.bodyDesktopSM,
	val subtitleDesktopL: TextStyle = IsicomTypographyTokens.subtitleDesktopL,
)

internal val LocalDesignSystemTypography = staticCompositionLocalOf { IsicomTypography() }
