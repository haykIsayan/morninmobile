package com.example.mornin_mobile.features.topics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mornin_mobile.domain.entity.TopicEntity
import com.example.mornin_mobile.domain.topics.CreateTopicUseCase
import com.example.mornin_mobile.domain.topics.DeleteTopicUseCase
import com.example.mornin_mobile.domain.topics.GetTopicsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TopicsViewModel(
    private val getTopicsUseCase: GetTopicsUseCase,
    private val createTopicUseCase: CreateTopicUseCase,
    private val deleteTopicUseCase: DeleteTopicUseCase
) : ViewModel() {

    data class State(
        val topics: List<TopicEntity> = emptyList(),
        val isLoading: Boolean = false,
        val showAddDialog: Boolean = false,
        val topicToDelete: TopicEntity? = null
    )

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state

    init {
        loadTopics()
    }

    fun loadTopics() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val topics = getTopicsUseCase.execute()
                _state.update { it.copy(topics = topics, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onAddClicked() = _state.update { it.copy(showAddDialog = true) }

    fun onAddDismissed() = _state.update { it.copy(showAddDialog = false) }

    fun onAddConfirmed(name: String) {
        _state.update { it.copy(showAddDialog = false) }
        val tempTopic = TopicEntity(id = "", name = name)
        _state.update { it.copy(topics = it.topics + tempTopic) }
        viewModelScope.launch {
            try {
                val created = createTopicUseCase.execute(name)
                _state.update { s -> s.copy(topics = s.topics.map { if (it === tempTopic) created else it }) }
            } catch (_: Exception) {
                _state.update { s -> s.copy(topics = s.topics.filter { it !== tempTopic }) }
            }
        }
    }

    fun onDeleteClicked(topic: TopicEntity) = _state.update { it.copy(topicToDelete = topic) }

    fun onDeleteDismissed() = _state.update { it.copy(topicToDelete = null) }

    fun onDeleteConfirmed() {
        val topic = _state.value.topicToDelete ?: return
        val topicsBeforeDelete = _state.value.topics
        _state.update { it.copy(topicToDelete = null, topics = it.topics.filter { t -> t.id != topic.id }) }
        viewModelScope.launch {
            try {
                deleteTopicUseCase.execute(topic.id)
            } catch (_: Exception) {
                _state.update { it.copy(topics = topicsBeforeDelete) }
            }
        }
    }

    companion object {
        fun factory(
            getTopicsUseCase: GetTopicsUseCase,
            createTopicUseCase: CreateTopicUseCase,
            deleteTopicUseCase: DeleteTopicUseCase
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                TopicsViewModel(getTopicsUseCase, createTopicUseCase, deleteTopicUseCase)
            }
        }
    }
}
