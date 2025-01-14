package com.edwardwongtl.podcastchallenge.ui.podcastdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwardwongtl.podcastchallenge.data.PodcastRepository
import com.edwardwongtl.podcastchallenge.domain.model.PodcastModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = PodcastDetailViewModel.Factory::class)
class PodcastDetailViewModel @AssistedInject constructor(
    @Assisted private val podcast: PodcastModel,
    private val podcastRepository: PodcastRepository
) : ViewModel() {
    private val _state = MutableStateFlow(PodcastDetailState(isLoading = true))
    val state: StateFlow<PodcastDetailState> = _state

    init {
        // Ignoring detail API for now
        // getPodcastDetail(podcastId)

        // Use constructor provided value as initial state
        _state.value = PodcastDetailState(podcast = podcast)
    }

    fun getPodcastDetail(podcastId: String) {
        viewModelScope.launch {
            podcastRepository.getPodcastDetail(podcastId)
                .onSuccess { _state.value = PodcastDetailState(podcast = it) }
                .onFailure { _state.value = PodcastDetailState(error = it) }
        }
    }

    fun toggleFavourite() {
        val podcast = state.value.podcast ?: return
        podcastRepository.setFavourite(podcast.id, !podcast.isFavourite)
        _state.value = _state.value.copy(
            podcast = podcast.copy(
                isFavourite = !podcast.isFavourite
            )
        )
    }

    @AssistedFactory
    interface Factory {
        fun create(podcast: PodcastModel): PodcastDetailViewModel
    }
}