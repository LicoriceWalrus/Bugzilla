package com.bugzilla.sources.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DescriptionText(title: String, text: String) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(end = 4.dp),
                color = Color.Black,
                style = MaterialTheme.typography.caption,
                text = title
            )
            Text(
                modifier = Modifier.weight(1f),
                color = Color.Gray,
                style = MaterialTheme.typography.caption,
                text = text
            )
        }
        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, end = 64.dp),
            thickness = 1.dp
        )
    }
}

@Composable
@Preview
private fun DescriptionTextPreview() {
    DescriptionText(
        text = "text",
        title = "title: "
    )
}