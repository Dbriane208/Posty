package daniel.brian.todo.viewmodel

import daniel.brian.todo.model.Post
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

// This will help us to expose our state to the UI
data class PostUiState(val posts: List<Post>)

class PostViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PostUiState(emptyList()))
    val uiState = _uiState.asStateFlow()

    fun updatePosts() {
        viewModelScope.launch {
            val posts = getPosts()
            _uiState.update {
                it.copy(posts = posts)
            }
        }
    }

    val httpClient = HttpClient {
        install(ContentNegotiation){
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                }
            )
        }
    }

    private suspend fun getPosts(): List<Post> =
        httpClient
            .get("https://jsonplaceholder.typicode.com/posts")
            .body<List<Post>>()

}