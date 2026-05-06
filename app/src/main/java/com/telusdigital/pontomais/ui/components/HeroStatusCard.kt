package com.telusdigital.pontomais.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.FreeBreakfast
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.telusdigital.pontomais.ui.theme.Iris
import com.telusdigital.pontomais.ui.theme.Juniper
import com.telusdigital.pontomais.ui.theme.Obsidian
import com.telusdigital.pontomais.ui.theme.Orchid
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme
import com.telusdigital.pontomais.ui.theme.Slate
import com.telusdigital.pontomais.ui.theme.TelusPurple
import com.telusdigital.pontomais.ui.theme.Verbena

private val IdleBrush    = Brush.linearGradient(listOf(Iris, Verbena))

@Composable
fun HeroStatusCard(
    time: String,
    date: String,
    isWorking: Boolean,
    nextActionLabel: String,
    onPunch: () -> Unit,
    modifier: Modifier = Modifier,
    showPauseIcon: Boolean = false,
) {
    val brush = if (isWorking) CaradonnaBrush else IdleBrush
    val textColor = if (isWorking) Color.White else Obsidian

    GradientCard(
        brush    = brush,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            // Status indicator
            Row(verticalAlignment = Alignment.CenterVertically) {
                PulsingDot(active = isWorking)
                Spacer(Modifier.width(8.dp))
                Text(
                    text  = if (isWorking) "Trabalhando" else "Fora do expediente",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = if (isWorking) Juniper else Slate,
                        letterSpacing = androidx.compose.ui.unit.TextUnit(0.08f, androidx.compose.ui.unit.TextUnitType.Em),
                    ),
                )
            }

            Spacer(Modifier.height(12.dp))

            // Clock
            Text(
                text  = time,
                style = MaterialTheme.typography.displayMedium.copy(
                    color = textColor,
                    letterSpacing = (-0.03f).let {
                        androidx.compose.ui.unit.TextUnit(it, androidx.compose.ui.unit.TextUnitType.Em)
                    },
                ),
            )
            Text(
                text  = date,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = textColor.copy(alpha = 0.85f),
                ),
            )

            Spacer(Modifier.height(20.dp))

            // Clock-in button
            Button(
                onClick = onPunch,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isWorking) Color.White else TelusPurple,
                    contentColor   = if (isWorking) TelusPurple else Color.White,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            ) {
                Surface(
                    shape = CircleShape,
                    color = if (isWorking) TelusPurple else Color.White,
                    modifier = Modifier.size(28.dp),
                ) {
                    Icon(
                        imageVector = if (showPauseIcon) Icons.Outlined.FreeBreakfast else Icons.Outlined.Fingerprint,
                        contentDescription = null,
                        tint = if (isWorking) Color.White else TelusPurple,
                        modifier = Modifier.padding(6.dp),
                    )
                }
                Spacer(Modifier.width(10.dp))
                Text(
                    text  = nextActionLabel,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

@Composable
private fun PulsingDot(active: Boolean) {
    if (active) {
        val infiniteTransition = rememberInfiniteTransition(label = "pulse")
        val scale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue  = 1.5f,
            animationSpec = infiniteRepeatable(
                animation  = tween(800, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse,
            ),
            label = "dot-scale",
        )
        Box(
            modifier = Modifier
                .size(8.dp)
                .graphicsLayer { scaleX = scale; scaleY = scale }
                .clip(CircleShape)
                .background(Juniper),
        )
    } else {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(Slate),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF2F3ED)
@Composable
private fun HeroStatusCardIdlePreview() {
    PontoMaisTheme {
        HeroStatusCard(
            time             = "14:32",
            date             = "terça-feira, 6 de maio",
            isWorking        = false,
            nextActionLabel  = "Iniciar expediente",
            onPunch          = {},
            modifier         = Modifier.padding(16.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF2F3ED)
@Composable
private fun HeroStatusCardWorkingPreview() {
    PontoMaisTheme {
        HeroStatusCard(
            time             = "14:32",
            date             = "terça-feira, 6 de maio",
            isWorking        = true,
            nextActionLabel  = "Iniciar pausa",
            onPunch          = {},
            showPauseIcon    = true,
            modifier         = Modifier.padding(16.dp),
        )
    }
}
