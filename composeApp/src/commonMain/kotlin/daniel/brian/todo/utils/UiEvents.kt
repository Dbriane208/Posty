package daniel.brian.todo.utils

sealed class UiEvents {
    data class SnackBarEvent(val message: String) : UiEvents()
}