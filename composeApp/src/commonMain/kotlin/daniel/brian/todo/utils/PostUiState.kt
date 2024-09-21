package daniel.brian.todo.utils

import daniel.brian.todo.model.Post

// This will help us to expose our state to the UI
data class PostUiState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val showCreatePostDialog: Boolean = false,
    val selectedPost: Post? = null,
    val newPostTitle: String = "",
    val newPostDescription: String = "",
    val postId: Int? = null
)
