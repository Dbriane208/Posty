package daniel.brian.todo.viewmodel

import daniel.brian.todo.model.Post
import daniel.brian.todo.repository.PostRepository
import daniel.brian.todo.utils.NetworkResult
import daniel.brian.todo.utils.PostUiState
import daniel.brian.todo.utils.UiEvents
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    private val postRepository = PostRepository()

    private val _uiState = MutableStateFlow(PostUiState())
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow: SharedFlow<UiEvents> = _eventFlow.asSharedFlow()

    fun getAllPosts() {
        viewModelScope.launch {
            when(val posts = postRepository.getPosts()){
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            posts = posts.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = posts.message.toString()
                        )
                    }

                    _eventFlow.emit(
                        UiEvents.SnackBarEvent(
                            message = posts.message.toString()
                        )
                    )
                }
            }
        }
    }

    fun createNewPost() {
        viewModelScope.launch {
            when(
                val newPost = postRepository.createPost(
                    title = uiState.value.newPostTitle,
                    description = uiState.value.newPostDescription
                )
            ){
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            posts = if (newPost.data != null) listOf(newPost.data) else it.posts,
                            isLoading = false
                        )
                    }

                    _eventFlow.emit(
                        UiEvents.SnackBarEvent(
                            message = "Post created successfully"
                        )
                    )
                }
                
                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = newPost.message.toString()
                        )
                    }

                    _eventFlow.emit(
                        UiEvents.SnackBarEvent(
                            message = newPost.message.toString()
                        )
                    )
                }

            }
        }
    }

//    fun updatePost(){
//        viewModelScope.launch {
//            when(
//                val updatePost = postRepository.updatePost(
//                    title = _uiState.value.newPostTitle,
//                    description = _uiState.value.newPostDescription,
//                    postId = _uiState.value.postId.toString()
//                )
//            ){
//                is NetworkResult.Success -> {
//                    _uiState.update {
//                        it.copy(
//
//                        )
//                    }
//                }
//            }
//        }
//    }
    fun onPostSelected(post: Post){
        _uiState.value = _uiState.value.copy(
            selectedPost = post,
            showCreatePostDialog = true
        )
    }

    fun onDismissDialog(){
        _uiState.value = _uiState.value.copy(
            selectedPost = null,
            showCreatePostDialog = false
        )
    }

    fun onPostConfirmed(post: Post){
        viewModelScope.launch {

        }
    }

}