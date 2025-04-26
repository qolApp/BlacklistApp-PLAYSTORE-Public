package pe.com.gianbravo.blockedcontacts.presentation.common.theme.ds

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import pe.com.gianbravo.blockedcontacts.presentation.common.extensions.boxShadow
import pe.com.gianbravo.blockedcontacts.presentation.common.theme.IsicomTheme

@Composable
fun Modifier.addIsiShadow(
	color: Color = IsicomTheme.dSColor.shadow.medium,
	shape: Shape, isiShadowType: IsiShadowType = IsiShadowType.LEVEL_1
): Modifier {
    return boxShadow(
        color = color,
        blurRadius = isiShadowType.blurRadius,
        offset = isiShadowType.offset,
        shape = shape,
        spreadRadius = isiShadowType.spreadRadius
    )
}

enum class IsiShadowType {
    LEVEL_1;


    private fun getValues(): Shadow {
        return when (this) {
            LEVEL_1 -> Shadow(
                blurRadius = 8.dp,
                spreadRadius = 0.dp,
                offset = DpOffset(0.dp, 4.dp)
            )
        }
    }

    // Don't put directly the values here, because it will be evaluated at compile time
    val blurRadius
        get() = getValues().blurRadius
    val spreadRadius
        get() = getValues().spreadRadius
    val offset
        get() = getValues().offset

}

data class Shadow(
    val blurRadius: Dp,
    val spreadRadius: Dp,
    val offset: DpOffset
)