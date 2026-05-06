package com.telusdigital.pontomais.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.telusdigital.pontomais.ui.components.CaradonnaBrush
import com.telusdigital.pontomais.ui.components.PontoButton
import com.telusdigital.pontomais.ui.components.PontoOutlinedButton
import com.telusdigital.pontomais.ui.components.PontoTextField
import com.telusdigital.pontomais.ui.theme.Forest
import com.telusdigital.pontomais.ui.theme.Hawthorn
import com.telusdigital.pontomais.ui.theme.Juniper
import com.telusdigital.pontomais.ui.theme.Marble
import com.telusdigital.pontomais.ui.theme.Moonstone
import com.telusdigital.pontomais.ui.theme.Obsidian
import com.telusdigital.pontomais.ui.theme.Pearl
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme
import com.telusdigital.pontomais.ui.theme.Slate
import com.telusdigital.pontomais.ui.theme.TelusGreen

private enum class LoginMode { Sso, Email }

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    var mode by remember { mutableStateOf(LoginMode.Sso) }
    var email by remember { mutableStateOf("luan.ramos@telusdigital.com") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CaradonnaBrush),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
        ) {
            // ── HERO ─────────────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RectangleShape), // clip leaf overflow at screen edges
            ) {
                LeafDecoration(
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = 40.dp, y = (-20).dp),
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp)
                        .padding(top = 24.dp, bottom = 40.dp),
                ) {
                    // Brand lockup
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = "TELUS Digital",
                            style = MaterialTheme.typography.labelMedium.copy(color = Color.White),
                        )
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(14.dp)
                                .background(Color.White.copy(alpha = 0.25f)),
                        )
                        Text(
                            text = "Ponto eletrônico",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White.copy(alpha = 0.6f),
                                letterSpacing = 0.8.sp,
                            ),
                        )
                    }

                    Spacer(Modifier.height(36.dp))

                    Text(
                        text = buildAnnotatedString {
                            append("Ponto")
                            withStyle(SpanStyle(color = Juniper)) { append("+") }
                        },
                        style = MaterialTheme.typography.displayLarge.copy(
                            color = Color.White,
                            letterSpacing = androidx.compose.ui.unit.TextUnit(
                                -0.035f, androidx.compose.ui.unit.TextUnitType.Em,
                            ),
                        ),
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = "Sua jornada de trabalho, registrada com segurança.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White.copy(alpha = 0.65f),
                        ),
                    )
                }
            }

            // ── WHITE CARD ────────────────────────────────────────────────────
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                color = Pearl,
                shadowElevation = 10.dp,
                tonalElevation = 0.dp,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                ) {
                    // Forest accent stripe
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .height(3.dp)
                            .background(Forest),
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 24.dp),
                    ) {
                        // Drag handle
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(width = 36.dp, height = 4.dp)
                                    .background(Marble, RoundedCornerShape(2.dp)),
                            )
                        }

                        Text(
                            text = "Acessar sua conta",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = Obsidian,
                                letterSpacing = androidx.compose.ui.unit.TextUnit(
                                    -0.02f, androidx.compose.ui.unit.TextUnitType.Em,
                                ),
                            ),
                        )
                        Text(
                            text = "Use suas credenciais TELUS Digital.",
                            style = MaterialTheme.typography.bodySmall.copy(color = Slate),
                            modifier = Modifier.padding(top = 4.dp),
                        )

                        Spacer(Modifier.height(20.dp))

                        LoginModeTabs(mode = mode, onModeChange = { mode = it })

                        Spacer(Modifier.height(20.dp))

                        when (mode) {
                            LoginMode.Sso -> SsoForm()
                            LoginMode.Email -> EmailForm(
                                email = email,
                                onEmailChange = { email = it },
                                password = password,
                                onPasswordChange = { password = it },
                            )
                        }

                        Spacer(Modifier.height(24.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            PontoButton(
                                label = if (mode == LoginMode.Sso) "Continuar com SSO" else "Entrar",
                                onClick = onLogin,
                                modifier = Modifier.fillMaxWidth(),
                                containerColor = Forest,
                                contentColor = Color.White,
                            )
                            PontoOutlinedButton(
                                label = "Entrar com biometria",
                                onClick = onLogin,
                                modifier = Modifier.fillMaxWidth(),
                                icon = Icons.Outlined.Fingerprint,
                                borderColor = Marble,
                                contentColor = Obsidian,
                            )
                        }

                        // Footer trust strip
                        Spacer(Modifier.height(20.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Marble),
                        )
                        Spacer(Modifier.height(14.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Security,
                                    contentDescription = null,
                                    tint = Forest,
                                    modifier = Modifier.size(12.dp),
                                )
                                Text(
                                    text = "Conexão segura · LGPD",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 11.sp,
                                        color = Slate,
                                    ),
                                )
                            }
                            Text(
                                text = "v2.4.1 · build 2026.05",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 11.sp,
                                    color = Slate,
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LeafDecoration(modifier: Modifier = Modifier) {
    val forestColor = Forest
    val greenColor = TelusGreen
    Canvas(modifier = modifier) {
        val sx = size.width / 220f
        val sy = size.height / 220f

        val leafPath = Path().apply {
            moveTo(60f * sx, 180f * sy)
            cubicTo(60f * sx, 80f * sy, 130f * sx, 30f * sy, 200f * sx, 30f * sy)
            cubicTo(200f * sx, 130f * sy, 150f * sx, 200f * sy, 80f * sx, 200f * sy)
            cubicTo(70f * sx, 200f * sy, 60f * sx, 195f * sy, 60f * sx, 180f * sy)
            close()
        }
        drawPath(
            path = leafPath,
            brush = Brush.linearGradient(listOf(forestColor, greenColor)),
            alpha = 0.18f,
        )

        val veinPath = Path().apply {
            moveTo(60f * sx, 180f * sy)
            cubicTo(100f * sx, 140f * sy, 140f * sx, 100f * sy, 200f * sx, 30f * sy)
        }
        drawPath(
            path = veinPath,
            color = forestColor,
            alpha = 0.4f,
            style = Stroke(width = 1.2f * sx),
        )
    }
}

@Composable
private fun LoginModeTabs(mode: LoginMode, onModeChange: (LoginMode) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Moonstone)
            .padding(4.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            LoginModeTab(
                label = "SSO corporativo",
                selected = mode == LoginMode.Sso,
                onClick = { onModeChange(LoginMode.Sso) },
                modifier = Modifier.weight(1f),
            )
            LoginModeTab(
                label = "E-mail e senha",
                selected = mode == LoginMode.Email,
                onClick = { onModeChange(LoginMode.Email) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun LoginModeTab(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(36.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(if (selected) Color.White else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = if (selected) Obsidian else Slate,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                fontSize = 13.sp,
            ),
        )
    }
}

@Composable
private fun SsoForm() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Tenant card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, Marble, RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Hawthorn),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Security,
                    contentDescription = null,
                    tint = Forest,
                    modifier = Modifier.size(20.dp),
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "VOCÊ ESTÁ EM",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Forest,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                    ),
                )
                Text(
                    text = "TELUS Digital · Brasil",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Obsidian,
                        fontWeight = FontWeight.SemiBold,
                    ),
                    modifier = Modifier.padding(top = 2.dp),
                )
            }

            Text(
                text = "Trocar",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Forest,
                    fontWeight = FontWeight.SemiBold,
                ),
                modifier = Modifier.clickable { },
            )
        }

        // Redirect note
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(horizontal = 4.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.Lock,
                contentDescription = null,
                tint = Slate,
                modifier = Modifier.size(12.dp),
            )
            Text(
                text = "Você será redirecionado ao login único da TELUS.",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Slate,
                    fontSize = 12.sp,
                ),
            )
        }
    }
}

@Composable
private fun EmailForm(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        PontoTextField(
            value = email,
            onValueChange = onEmailChange,
            label = "E-mail corporativo",
            leadingIcon = Icons.Outlined.Email,
        )
        PontoTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = "Senha",
            leadingIcon = Icons.Outlined.Lock,
            isPassword = true,
        )
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd,
        ) {
            Text(
                text = "Esqueci a senha",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Forest,
                    fontWeight = FontWeight.Medium,
                ),
                modifier = Modifier.clickable { },
            )
        }
    }
}

@Preview(showBackground = true, device = "spec:width=412dp,height=892dp")
@Composable
private fun LoginScreenSsoPreview() {
    PontoMaisTheme {
        LoginScreen(onLogin = {})
    }
}
