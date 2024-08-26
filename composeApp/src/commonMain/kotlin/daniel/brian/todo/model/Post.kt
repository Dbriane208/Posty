package daniel.brian.todo.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val userId: Int? = null,
    @SerialName("id") val postId: Int? = null,
    val title: String? = null,
    @SerialName("body") val description: String? = null
)
