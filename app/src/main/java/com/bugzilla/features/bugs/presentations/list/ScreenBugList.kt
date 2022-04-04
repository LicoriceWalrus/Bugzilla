package com.bugzilla.features.bugs.presentations.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bugzilla.R
import com.bugzilla.features.bugs.domain.entity.Bug
import com.bugzilla.features.bugs.domain.entity.FilterType
import com.bugzilla.sources.components.BugToolbar
import com.bugzilla.sources.components.LoadingBox
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.getViewModel

@Composable
fun ScreenBugList(
    vm: BugListViewModel = getViewModel(),
) {
    BugList(
        state = vm.screenState().collectAsState(),
        onBugClick = vm::changeInfoVisibility,
        onQueryChanged = vm::onQueryChanged,
        searchBugs = vm::searchBugs,
        filterTypeChanged = vm::filterTypeChanged,
        changeSearchMethod = vm::changeSearchMethod
    )
}

@Composable
private fun BugList(
    state: State<BugListScreenState>,
    onBugClick: (Bug) -> Unit,
    onQueryChanged: (String) -> Unit,
    searchBugs: () -> Unit,
    filterTypeChanged: (FilterType) -> Unit,
    changeSearchMethod: () -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    LoadingBox(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        loading = state.value.loading,
    ) {
        Column {
            BugToolbar(
                title = stringResource(id = R.string.search_bugs_toolbar_title),
                actions = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable {
                                expanded = !expanded
                            }
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .fillMaxWidth()
                                .clickable {
                                    changeSearchMethod()
                                },
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(CenterVertically),
                                text = stringResource(id = R.string.search_by_id)
                            )
                            Checkbox(
                                checked = state.value.isSearchById,
                                onCheckedChange = {
                                    changeSearchMethod()
                                })
                        }
                        Divider(
                            color = Color.LightGray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp, top = 8.dp, end = 8.dp),
                            thickness = 1.dp
                        )
                        Row(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .fillMaxWidth()
                                .clickable {
                                    filterTypeChanged(FilterType.ID)
                                    expanded = !expanded
                                },
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(CenterVertically),
                                text = stringResource(id = R.string.by_id_filter)

                            )
                            RadioButton(
                                selected = state.value.filterType == FilterType.ID,
                                onClick = {
                                    filterTypeChanged(FilterType.ID)
                                    expanded = !expanded
                                })
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    filterTypeChanged(FilterType.DATE)
                                    expanded = !expanded
                                },
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = stringResource(id = R.string.by_date_filter),
                                modifier = Modifier
                                    .padding(start = 12.dp)
                                    .align(CenterVertically)
                            )
                            RadioButton(
                                selected = state.value.filterType == FilterType.DATE,
                                onClick = {
                                    filterTypeChanged(FilterType.DATE)
                                    expanded = !expanded
                                })
                        }
                    }
                }
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            ) {
                state.value.bugs.let {
                    if (it.isEmpty()) {
                        Text(
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp)
                                .align(Alignment.Center),
                            text = state.value.message
                                ?: stringResource(id = R.string.upload_bugs_empty),
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(top = 8.dp)
                        ) {
                            val bugs = state.value.bugs.sortedBy { bug ->
                                when (state.value.filterType) {
                                    FilterType.ID -> bug.id.toString()
                                    else -> bug.creationTime
                                }
                            }
                            items(
                                count = bugs.size,
                                key = { position: Int -> bugs[position].id },
                            ) { position ->
                                BugItem(
                                    bug = bugs[position],
                                    onItemClick = { onBugClick(bugs[position]) })
                            }
                        }
                    }
                }
            }
            TextField(
                singleLine = true,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                value = state.value.query ?: "",
                onValueChange = onQueryChanged,
                label = {
                    Text(
                        text = stringResource(
                            id = if (state.value.isSearchById)
                                R.string.bug_search_by_id_hint
                            else R.string.bug_search_hint
                        )
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        state.value.query?.let {
                            if (it.isNotBlank()) {
                                searchBugs()
                            }
                        }
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (state.value.isSearchById) KeyboardType.Number else KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            state.value.query?.let {
                                if (it.isNotBlank()) {
                                    searchBugs()
                                }
                            }
                        },
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null
                    )
                }
            )
        }
    }
}

@Composable
@Preview
private fun EmptyBugListPreview() {
    BugList(
        state = flowOf<BugListScreenState>().collectAsState(
            initial = BugListScreenState(
                bugs = emptyList(),
                isSearchById = false
            )
        ),
        onBugClick = {},
        onQueryChanged = {},
        searchBugs = {},
        filterTypeChanged = {},
        changeSearchMethod = {}
    )
}

@Composable
@Preview
private fun BugListPreview() {
    BugList(
        state = flowOf<BugListScreenState>().collectAsState(
            initial = BugListScreenState(
                bugs = listOf(
                    Bug(
                        id = 65,
                        summary = "Краткое описание",
                        creationTime = "1.10.1990",
                        creator = "Vasiliy from Leningrad",
                        status = "Open",
                        severity = "Hot"
                    )
                ),
                isSearchById = false
            )
        ),
        onBugClick = {},
        onQueryChanged = {},
        searchBugs = {},
        filterTypeChanged = {},
        changeSearchMethod = {}
    )
}