package br.com.alexf.minhastarefas.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import br.com.alexf.minhastarefas.samples.generators.generateRandomTasks
import br.com.alexf.minhastarefas.ui.states.TasksListUiState
import br.com.alexf.minhastarefas.ui.theme.MinhasTarefasTheme

@Composable
fun TasksListScreen(
    uiState: TasksListUiState,
    modifier: Modifier = Modifier,
    onNewTaskClick: () -> Unit = {}
) {
    Box(modifier) {
        ExtendedFloatingActionButton(
            onClick = onNewTaskClick,
            Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
                .zIndex(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add new task icon")
                Text(text = "Nova Tarefa")
            }
        }
        LazyColumn(Modifier.fillMaxSize()) {
            items(uiState.tasks) { task ->
                Row(Modifier.fillMaxWidth()) {
                    Box(
                        Modifier
                            .padding(
                                vertical = 16.dp,
                                horizontal = 8.dp
                            )
                            .size(30.dp)
                            .border(
                                border = BorderStroke(2.dp, color = Color.Gray),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clip(shape = RoundedCornerShape(8.dp))
                            .clickable {
                                Log.i("TasksListScreen", "$task")
                                uiState.onTaskDoneChange(task)
                            }
                    ) {
                        if (task.isDone) {
                            Icon(
                                Icons.Filled.Done,
                                contentDescription = "Done icon",
                                Modifier
                                    .size(100.dp),
                                tint = Color.Green
                            )
                        }
                    }
                    Column(
                        Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = task.title,
                            style = TextStyle.Default.copy(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        task.description?.let { description ->
                            Text(
                                text = description,
                                style = TextStyle.Default.copy(fontSize = 24.sp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TasksListScreenPreview() {
    MinhasTarefasTheme {
        TasksListScreen(
            uiState = TasksListUiState(
                tasks = generateRandomTasks(5)
            )
        )
    }
}