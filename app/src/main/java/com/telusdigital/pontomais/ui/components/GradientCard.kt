package com.telusdigital.pontomais.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.telusdigital.pontomais.ui.theme.Orchid
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme
import com.telusdigital.pontomais.ui.theme.TelusPurple

// Caradonna gradient: Orchid → TELUS Purple, diagonal (≈ 135°) — hero cards.
val CaradonnaBrush = Brush.linearGradient(
    colors = listOf(Orchid, TelusPurple),
    start  = Offset(0f, 0f),
    end    = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
)

// Vertical variant (180°) — used for full-screen backgrounds like Login.
val CaradonnaVerticalBrush = Brush.verticalGradient(
    colors = listOf(Orchid, TelusPurple),
)

@Composable
fun GradientCard(
    modifier: Modifier = Modifier,
    brush: Brush = CaradonnaBrush,
    cornerRadius: Dp = 24.dp,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(brush),
        content = content,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFF2F3ED)
@Composable
private fun GradientCardPreview() {
    PontoMaisTheme {
        GradientCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = "Hero content",
                color = Color.White,
                modifier = Modifier.padding(24.dp),
            )
        }
    }
}
