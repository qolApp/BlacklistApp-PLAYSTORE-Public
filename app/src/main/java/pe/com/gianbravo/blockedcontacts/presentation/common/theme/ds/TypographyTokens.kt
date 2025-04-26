package pe.com.gianbravo.blockedcontacts.presentation.common.theme.ds

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import pe.com.gianbravo.blockedcontacts.R

private val poppinsFont = FontFamily(
    Font(resId = R.font.poppins_light, weight = FontWeight.Light, style = FontStyle.Normal),
    Font(resId = R.font.poppins_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
    Font(resId = R.font.poppins_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
    // TODO: Add more font weights like SEMI_BOLD
    Font(resId = R.font.poppins_bold, weight = FontWeight.SemiBold, style = FontStyle.Normal),
)

internal object IsicomTypographyTokens {
    val titleXXL = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W700,
        fontSize = IsicomTypographyScaleTokens.isi_bds_font_size_5xlarge,
        lineHeight = IsicomTypographyScaleTokens.ISICOM_LINE_HEIGHT_TIGHT * IsicomTypographyScaleTokens.isi_bds_font_size_5xlarge
    )
    val titleXL = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W700,
        fontSize = IsicomTypographyScaleTokens.isi_bds_font_size_4xlarge,
        lineHeight = IsicomTypographyScaleTokens.ISICOM_LINE_HEIGHT_TIGHT * IsicomTypographyScaleTokens.isi_bds_font_size_4xlarge
    )
    val titleLG = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W700,
        fontSize = IsicomTypographyScaleTokens.isi_bds_font_size_3xlarge,
        lineHeight = IsicomTypographyScaleTokens.ISICOM_LINE_HEIGHT_TIGHT * IsicomTypographyScaleTokens.isi_bds_font_size_3xlarge
    )
    val titleMD = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W700,
        fontSize = IsicomTypographyScaleTokens.isi_bds_font_size_2xlarge,
        lineHeight = IsicomTypographyScaleTokens.ISICOM_LINE_HEIGHT_TIGHT * IsicomTypographyScaleTokens.isi_bds_font_size_2xlarge
    )
    val titleSM = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W700,
        fontSize = IsicomTypographyScaleTokens.isi_bds_font_size_xlarge,
        lineHeight = IsicomTypographyScaleTokens.ISICOM_LINE_HEIGHT_TIGHT * IsicomTypographyScaleTokens.isi_bds_font_size_xlarge
    )
    val titleXS = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W700,
        fontSize = IsicomTypographyScaleTokens.isi_bds_font_size_large,
        lineHeight = IsicomTypographyScaleTokens.ISICOM_LINE_HEIGHT_TIGHT * IsicomTypographyScaleTokens.isi_bds_font_size_xlarge
    )
    val subtitle2xLarge = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W600,
        fontSize = IsicomTypographyScaleTokens.isi_bds_font_size_2xlarge,
        lineHeight = IsicomTypographyScaleTokens.ISICOM_LINE_HEIGHT_MEDIUM * IsicomTypographyScaleTokens.isi_bds_font_size_2xlarge
    )
    val subtitleXLarge = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W600,
        fontSize = IsicomTypographyScaleTokens.isi_bds_font_size_xlarge,
        lineHeight = IsicomTypographyScaleTokens.ISICOM_LINE_HEIGHT_MEDIUM * IsicomTypographyScaleTokens.isi_bds_font_size_xlarge
    )
    val subtitleLarge = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W600,
        fontSize = IsicomTypographyScaleTokens.isi_bds_font_size_large,
        lineHeight = IsicomTypographyScaleTokens.ISICOM_LINE_HEIGHT_MEDIUM * IsicomTypographyScaleTokens.isi_bds_font_size_large
    )
    val bodyMD = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W500,
        fontSize = IsicomTypographyScaleTokens.isi_bds_font_size_medium,
        lineHeight = IsicomTypographyScaleTokens.ISICOM_LINE_HEIGHT_DISTANT * IsicomTypographyScaleTokens.isi_bds_font_size_medium
    )
    val bodySM = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W500,
        fontSize = IsicomTypographyScaleTokens.isi_bds_font_size_small,
        lineHeight = IsicomTypographyScaleTokens.ISICOM_LINE_HEIGHT_DISTANT * IsicomTypographyScaleTokens.isi_bds_font_size_small
    )
    val unknown1 = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W600,
        fontSize = 14.sp,
        lineHeight = 21.sp
    )
    val unknown2 = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp,
        lineHeight = 18.sp
    )
    val unknown3 = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        lineHeight = 24.sp
    )
    val unknown4 = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W700,
        fontSize = 14.sp,
        lineHeight = 21.sp
    )
    val unknown2_light = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W300,
        fontSize = 12.sp,
        lineHeight = 18.sp
    )
    val unknown2_semi_light = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 18.sp
    )
    val unknown3_light = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 24.sp
    )
    val bodyDesktopXS = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 21.sp
    )
    val bodyDesktopSM = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        lineHeight = 24.sp
    )
    val subtitleDesktopL = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
        lineHeight = (19.2).sp
    )
}
