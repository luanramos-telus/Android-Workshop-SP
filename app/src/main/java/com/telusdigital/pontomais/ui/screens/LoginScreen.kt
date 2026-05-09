package com.telusdigital.pontomais.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.telusdigital.pontomais.ui.theme.Juniper
import com.telusdigital.pontomais.ui.theme.Marble
import com.telusdigital.pontomais.ui.theme.Obsidian
import com.telusdigital.pontomais.ui.theme.Orchid
import com.telusdigital.pontomais.ui.theme.Pearl
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme
import com.telusdigital.pontomais.ui.theme.Slate
import com.telusdigital.pontomais.ui.theme.TelusPurple
import com.telusdigital.pontomais.R

private val HeroBrush = Brush.linearGradient(
    colors = listOf(Orchid, TelusPurple),
    start  = Offset(0f, 0f),
    end    = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
)

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    var email  by remember { mutableStateOf("") }
    var pass   by remember { mutableStateOf("") }
    var showPw by remember { mutableStateOf(false) }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val heroH = 350.dp
        val cardH = maxHeight - heroH + 32.dp   // card overlaps hero by 32 dp

        // ── HERO ─────────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(heroH)
                .background(HeroBrush)
                .statusBarsPadding(),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text  = buildAnnotatedString {
                        append("Ponto")
                        withStyle(SpanStyle(color = Juniper)) { append("+") }
                    },
                    style = MaterialTheme.typography.displayLarge.copy(
                        color         = Color.White,
                        fontSize      = 64.sp,
                        lineHeight    = 58.sp,
                        letterSpacing = (-2.88).sp,
                    ),
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text  = stringResource(R.string.app_brand_sub),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color         = Color.White.copy(alpha = 0.7f),
                        letterSpacing = 3.52.sp,
                        fontWeight    = FontWeight.Medium,
                    ),
                )
            }
        }

        // ── FORM CARD ─────────────────────────────────────────────────────
        Surface(
            modifier        = Modifier
                .fillMaxWidth()
                .height(cardH)
                .align(Alignment.BottomCenter),
            shape           = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color           = Pearl,
            shadowElevation = 10.dp,
            tonalElevation  = 0.dp,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 28.dp)
                    .padding(top = 32.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                // E-mail
                OutlinedTextField(
                    value         = email,
                    onValueChange = { email = it },
                    placeholder   = {
                        Text(
                            stringResource(R.string.login_email_label),
                            style = MaterialTheme.typography.bodyMedium.copy(color = Slate),
                        )
                    },
                    singleLine    = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    shape         = RoundedCornerShape(14.dp),
                    colors        = loginFieldColors(),
                    modifier      = Modifier.fillMaxWidth().height(56.dp),
                )

                // Senha
                OutlinedTextField(
                    value         = pass,
                    onValueChange = { pass = it },
                    placeholder   = {
                        Text(
                            stringResource(R.string.login_password_label),
                            style = MaterialTheme.typography.bodyMedium.copy(color = Slate),
                        )
                    },
                    singleLine            = true,
                    visualTransformation  = if (showPw) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions       = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon          = {
                        IconButton(onClick = { showPw = !showPw }) {
                            Icon(
                                imageVector        = if (showPw) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = if (showPw) stringResource(R.string.login_password_hide) else stringResource(R.string.login_password_show),
                                tint               = Slate,
                                modifier           = Modifier.size(20.dp),
                            )
                        }
                    },
                    shape   = RoundedCornerShape(14.dp),
                    colors  = loginFieldColors(),
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                )

                Spacer(Modifier.height(6.dp))

                // Entrar
                Button(
                    onClick  = onLogin,
                    shape    = RoundedCornerShape(14.dp),
                    colors   = ButtonDefaults.buttonColors(
                        containerColor = TelusPurple,
                        contentColor   = Color.White,
                    ),
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                ) {
                    Text(
                        text  = stringResource(R.string.login_cta),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize   = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                        ),
                    )
                }

                // Biometria  |  Esqueci a senha
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically,
                ) {
                    TextButton(onClick = onLogin) {
                        Icon(
                            imageVector        = Icons.Outlined.Fingerprint,
                            contentDescription = null,
                            tint               = TelusPurple,
                            modifier           = Modifier.size(20.dp),
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text  = stringResource(R.string.login_biometric_cta),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color      = TelusPurple,
                                fontWeight = FontWeight.SemiBold,
                            ),
                        )
                    }
                    TextButton(onClick = {}) {
                        Text(
                            text  = stringResource(R.string.login_forgot_password),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color      = Slate,
                                fontWeight = FontWeight.Medium,
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun loginFieldColors() = OutlinedTextFieldDefaults.colors(
    unfocusedBorderColor    = Marble,
    focusedBorderColor      = TelusPurple,
    unfocusedContainerColor = Color.White,
    focusedContainerColor   = Color.White,
    unfocusedTextColor      = Obsidian,
    focusedTextColor        = Obsidian,
    cursorColor             = TelusPurple,
)

@Preview(showBackground = true, device = "spec:width=412dp,height=892dp")
@Composable
private fun LoginScreenPreview() {
    PontoMaisTheme {
        LoginScreen(onLogin = {})
    }
}
