package com.telusdigital.pontomais.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.telusdigital.pontomais.ui.theme.Juniper
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme

@Composable
fun PontoTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    isPassword: Boolean = false,
    dark: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val containerColor   = if (dark) Color.White.copy(alpha = 0.10f) else MaterialTheme.colorScheme.surfaceVariant
    val textColor        = if (dark) Color.White else MaterialTheme.colorScheme.onSurface
    val labelColor       = if (dark) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant
    val indicatorColor   = if (dark) Juniper else MaterialTheme.colorScheme.primary
    val iconColor        = if (dark) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else keyboardOptions,
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        leadingIcon = leadingIcon?.let {
            { Icon(imageVector = it, contentDescription = null, modifier = Modifier.size(22.dp), tint = iconColor) }
        },
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                        contentDescription = if (passwordVisible) "Ocultar senha" else "Mostrar senha",
                        tint = iconColor,
                    )
                }
            }
        } else null,
        colors = TextFieldDefaults.colors(
            focusedContainerColor   = containerColor,
            unfocusedContainerColor = containerColor,
            focusedTextColor        = textColor,
            unfocusedTextColor      = textColor,
            focusedLabelColor       = indicatorColor,
            unfocusedLabelColor     = labelColor,
            focusedIndicatorColor   = indicatorColor,
            unfocusedIndicatorColor = labelColor,
            cursorColor             = indicatorColor,
            focusedLeadingIconColor   = indicatorColor,
            unfocusedLeadingIconColor = iconColor,
        ),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF4B286D)
@Composable
private fun PontoTextFieldDarkPreview() {
    PontoMaisTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            PontoTextField(
                value = "ana.silva@telusdigital.com",
                onValueChange = {},
                label = "E-mail corporativo",
                leadingIcon = Icons.Outlined.Email,
                dark = true,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PontoTextFieldPasswordPreview() {
    PontoMaisTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            PontoTextField(
                value = "password123",
                onValueChange = {},
                label = "Senha",
                leadingIcon = Icons.Outlined.Lock,
                isPassword = true,
                dark = true,
            )
        }
    }
}
