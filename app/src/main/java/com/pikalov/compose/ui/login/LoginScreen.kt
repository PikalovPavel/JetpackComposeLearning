package com.pikalov.compose.ui.login

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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = stringResource(id = R.string.app_title),
            style = MaterialTheme.typography.h1,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        )
    }
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onLoginClick.invoke() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .clip(RoundedCornerShape(8.dp))

        ) {
            Text(
                text = stringResource(id = R.string.login),
                modifier = Modifier.padding(10.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Login() {
    JetpackComposeLearningTheme {
        LoginContent({})
    }
}