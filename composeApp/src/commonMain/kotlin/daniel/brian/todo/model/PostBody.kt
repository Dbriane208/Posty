package daniel.brian.todo.model

data class PostBody(
    val title: String?,
    val description: String?,
    val userId: Int?,
    val postId: Int?
)
