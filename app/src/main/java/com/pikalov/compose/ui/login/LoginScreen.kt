package com.pikalov.compose.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pikalov.compose.R
import com.pikalov.compose.ui.theme.JetpackComposeLearningTheme

@Composable
fun LoginContent(onLoginClick: () -> Unit) {
    Box(modifier = Modifier.background(MaterialTheme.colors.secondary)) {
        Box(
            modifier = Modifier.align(Alignment.TopCenter)
                .padding(top = 64.dp)
        ) {
            Text(
                text = stringResource(id = R.string.app_title),
                style = MaterialTheme.typography.h1,
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colors.primary
            )
        }
        Box(
            modifier = Modifier.align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            Button(
                onClick = { onLoginClick.invoke() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colors.primary)

            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    modifier = Modifier
                        .padding(10.dp),
                    color = MaterialTheme.colors.secondary
                )
            }
        }
    }
}