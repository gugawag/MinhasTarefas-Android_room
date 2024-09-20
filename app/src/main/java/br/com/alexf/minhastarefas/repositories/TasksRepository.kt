package br.com.alexf.minhastarefas.repositories

import br.com.alexf.minhastarefas.database.dao.TaskDAO
import br.com.alexf.minhastarefas.database.entities.TaskEntity
import br.com.alexf.minhastarefas.models.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class TasksRepository(
    private val dao: TaskDAO
) {

    val tasks get() = dao.findAll()

    suspend fun save(task: Task) = withContext(Dispatchers.IO) {
        dao.save(task.toTaskEntity())
    }

    suspend fun toggleIsDone(task: Task) = withContext(Dispatchers.IO) {
        val entity = task.copy(isDone = !task.isDone)
        dao.save(entity.toTaskEntity())
    }

}

fun Task.toTaskEntity() = TaskEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    isDone = this.isDone
)

fun TaskEntity.toTask() = Task(
    id = this.id,
    title = this.title,
    description = this.description,
    isDone = this.isDone
)