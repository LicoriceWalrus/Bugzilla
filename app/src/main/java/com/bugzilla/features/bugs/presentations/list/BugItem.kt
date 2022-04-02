package com.bugzilla.features.bugs.presentations.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign.Companion.End
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bugzilla.R
import com.bugzilla.features.bugs.domain.entity.Bug

@Composable
fun BugItem(bug: Bug) {
    Card(
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                textAlign = End,
                text = stringResource(id = R.string.id_title, bug.id),
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .fillMaxWidth(),
                text = bug.summary,
                color = Color.Gray
            )
        }
    }
}

@Composable
@Preview
private fun BugItemPreview() {
    BugItem(
        bug = Bug(
            id = "35",
            summary = "Краткое описание ошибки в несколько строчек для проверки переноса"
        )
    )
}