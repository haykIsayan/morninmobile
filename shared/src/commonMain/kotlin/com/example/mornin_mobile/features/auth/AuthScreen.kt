package com.example.mornin_mobile.features.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mornin_mobile.MorninWarm

@Composable
fun AuthScreen(viewModel: AuthViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MorninWarm.Bg)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        when (val s = state) {
            is AuthViewModel.AuthState.EnteringRecipient ->
                EnterRecipientContent(
                    onSubmit = { viewModel.submitRecipient(it) }
                )

            is AuthViewModel.AuthState.RequestingOtp ->
                LoadingContent(message = "Sending code to ${s.recipient}…")

            is AuthViewModel.AuthState.EnteringOtp ->
                EnterOtpContent(
                    recipient = s.recipient,
                    onSubmit = { viewModel.submitOtp(it) }
                )

            is AuthViewModel.AuthState.VerifyingOtp ->
                LoadingContent(message = "Verifying…")

            is AuthViewModel.AuthState.Authenticated ->
                AuthenticatedContent()

            is AuthViewModel.AuthState.Error ->
                ErrorContent(
                    message = s.message,
                    onRetry = { viewModel.retry() }
                )
        }
    }
}

@Composable
private fun EnterRecipientContent(onSubmit: (String) -> Unit) {
    var recipient by remember { mutableStateOf("") }

    AuthFormColumn {
        AuthTitle("Good morning.")
        AuthSubtitle("Enter your email to receive a sign-in code.")

        Spacer(Modifier.height(32.dp))

        AuthTextField(
            value = recipient,
            onValueChange = { recipient = it },
            label = "Email",
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done,
            onImeAction = { if (recipient.isNotBlank()) onSubmit(recipient) }
        )

        Spacer(Modifier.height(16.dp))

        AuthButton(
            text = "Send Code",
            enabled = recipient.isNotBlank(),
            onClick = { onSubmit(recipient) }
        )
    }
}

@Composable
private fun EnterOtpContent(recipient: String, onSubmit: (String) -> Unit) {
    var code by remember { mutableStateOf("") }

    AuthFormColumn {
        AuthTitle("Check your inbox.")
        AuthSubtitle("We sent a 6-digit code to $recipient.")

        Spacer(Modifier.height(32.dp))

        AuthTextField(
            value = code,
            onValueChange = { if (it.length <= 6) code = it },
            label = "One-time code",
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done,
            onImeAction = { if (code.length == 6) onSubmit(code) }
        )

        Spacer(Modifier.height(16.dp))

        AuthButton(
            text = "Verify",
            enabled = code.length == 6,
            onClick = { onSubmit(code) }
        )
    }
}

@Composable
private fun LoadingContent(message: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = MorninWarm.Accent,
            modifier = Modifier.size(40.dp)
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text = message,
            fontSize = 14.sp,
            color = MorninWarm.TextMuted
        )
    }
}

@Composable
private fun AuthenticatedContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "You're in.",
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium,
            color = MorninWarm.Text
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Welcome back.",
            fontSize = 15.sp,
            color = MorninWarm.TextMuted
        )
    }
}

@Composable
private fun ErrorContent(message: String, onRetry: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            fontSize = 15.sp,
            color = MorninWarm.Accent,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(20.dp))
        TextButton(onClick = onRetry) {
            Text(
                text = "Try again",
                color = MorninWarm.Accent,
                fontSize = 14.sp
            )
        }
    }
}

// ── Shared helpers ────────────────────────────────────────────────────────────

@Composable
private fun AuthFormColumn(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) { content() }
}

@Composable
private fun AuthTitle(text: String) {
    Text(
        text = text,
        fontSize = 28.sp,
        fontWeight = FontWeight.Medium,
        color = MorninWarm.Text,
        letterSpacing = (-0.3).sp
    )
}

@Composable
private fun AuthSubtitle(text: String) {
    Spacer(Modifier.height(6.dp))
    Text(
        text = text,
        fontSize = 14.sp,
        color = MorninWarm.TextMuted,
        lineHeight = 20.sp
    )
}

@Composable
private fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    onImeAction: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = MorninWarm.TextMuted) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onDone = { onImeAction() }),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MorninWarm.Accent,
            unfocusedBorderColor = MorninWarm.Rule,
            focusedTextColor = MorninWarm.Text,
            unfocusedTextColor = MorninWarm.Text,
            cursorColor = MorninWarm.Accent
        )
    )
}

@Composable
private fun AuthButton(text: String, enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MorninWarm.Accent,
            contentColor = MorninWarm.Bg,
            disabledContainerColor = MorninWarm.Rule,
            disabledContentColor = MorninWarm.TextMuted
        )
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.5.sp
        )
    }
}