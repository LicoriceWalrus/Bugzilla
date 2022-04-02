package com.bugzilla.features.bugs.presentations.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bugzilla.R
import com.bugzilla.features.bugs.domain.entity.Bug

@Composable
fun BugItem(
    bug: Bug,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick()
            },
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .weight(1f),
                    text = bug.alias.uppercase(),
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(id = R.string.id_title, bug.id),
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Row {
                Text(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .weight(1f),
                    style = MaterialTheme.typography.caption,
                    text = bug.summary,
                    color = Color.Gray
                )
                Icon(
                    modifier = Modifier
                        .align(Bottom),
                    painter = painterResource(
                        id = if (bug.isMoreInformationMode) R.drawable.ic_expand_less
                        else R.drawable.ic_expand_more
                    ),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
@Preview
private fun BugItemPreview() {
    BugItem(
        bug = Bug(
            id = "35",
            summary = "Краткое описание ошиби",
            alias = "name"
        ),
        onItemClick = {}
    )
}