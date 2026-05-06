package com.telusdigital.pontomais.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.telusdigital.pontomais.ui.components.CaradonnaVerticalBrush
import com.telusdigital.pontomais.ui.components.GradientCard
import com.telusdigital.pontomais.ui.components.PontoButton
import com.telusdigital.pontomais.ui.components.PontoOutlinedButton
import com.telusdigital.pontomais.ui.components.PontoTextField
import com.telusdigital.pontomais.ui.theme.Juniper
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme
import com.telusdigital.pontomais.ui.theme.TelusPurple

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    var email    by remember { mutableStateOf("ana.silva@telusdigital.com") }
    var password by remember { mutableStateOf("") }
    var remember by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CaradonnaVerticalBrush),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp)
                .padding(top = 56.dp, bottom = 32.dp),
        ) {
            // Logo lockup
            Text(
                text = "TELUS Digital",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Color.White.copy(alpha = 0.85f),
                    letterSpacing = 0.08.sp,
                ),
            )

            Spacer(Modifier.height(32.dp))

            Text(
                text = buildAnnotatedString {
                    append("Ponto")
                    withStyle(SpanStyle(color = Juniper)) { append("+") }
                },
                style = MaterialTheme.typography.displayLarge.copy(
                    color = Color.White,
                    letterSpacing = (-0.03f).let {
                        androidx.compose.ui.unit.TextUnit(it, androidx.compose.ui.unit.TextUnitType.Em)
                    },
                ),
            )

            Text(
                text = "Bem-vinda de volta. Vamos registrar seu dia.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White.copy(alpha = 0.78f),
                ),
                modifier = Modifier.padding(top = 8.dp),
            )

            Spacer(Modifier.height(40.dp))

            // Fields
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                PontoTextField(
                    value         = email,
                    onValueChange = { email = it },
                    label         = "E-mail corporativo",
                    leadingIcon   = Icons.Outlined.Email,
                    dark          = true,
                )
                PontoTextField(
                    value         = password,
                    onValueChange = { password = it },
                    label         = "Senha",
                    leadingIcon   = Icons.Outlined.Lock,
                    isPassword    = true,
                    dark          = true,
                )
            }

            Spacer(Modifier.height(12.dp))

            // Remember + forgot
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.clickable { remember = !remember },
                ) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(if (remember) Juniper else Color.Transparent)
                            .border(
                                width = 2.dp,
                                color = if (remember) Juniper else Color.White.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(2.dp),
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (remember) {
                            Icon(
                                imageVector = Icons.Outlined.Check,
                                contentDescription = null,
                                tint = TelusPurple,
                                modifier = Modifier.size(14.dp),
                            )
                        }
                    }
                    Text(
                        text  = "Lembrar de mim",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                    )
                }

                Text(
                    text  = "Esqueci a senha",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Juniper,
                        fontWeight = FontWeight.Medium,
                    ),
                    modifier = Modifier.clickable { },
                )
            }

            Spacer(Modifier.weight(1f))

            // Actions
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                PontoButton(
                    label          = "Entrar",
                    onClick        = onLogin,
                    modifier       = Modifier.fillMaxWidth(),
                    containerColor = Juniper,
                    contentColor   = TelusPurple,
                )
                PontoOutlinedButton(
                    label   = "Entrar com biometria",
                    onClick = onLogin,
                    modifier = Modifier.fillMaxWidth(),
                    icon    = Icons.Outlined.Fingerprint,
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text  = "v2.4.1 · Ponto+ © TELUS Digital",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White.copy(alpha = 0.55f),
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }
    }
}

@Preview(showBackground = true, device = "spec:width=412dp,height=892dp")
@Composable
private fun LoginScreenPreview() {
    PontoMaisTheme {
        LoginScreen(onLogin = {})
    }
}
