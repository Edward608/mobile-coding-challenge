package com.edwardwongtl.podcastchallenge.ui.podcastdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edwardwongtl.podcastchallenge.data.PodcastRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PodcastDetailViewModel @AssistedInject constructor(
    @Assisted private val podcastId: String,
    private val podcastRepository: PodcastRepository
) : ViewModel() {
    init {
        getPodcastDetail(podcastId)
    }

    private val _state = MutableStateFlow(PodcastDetailState(isLoading = true))
    val state: StateFlow<PodcastDetailState> = _state

    fun getPodcastDetail(podcastId: String) {
        viewModelScope.launch {
            podcastRepository.getPodcastDetail(podcastId)
                .onSuccess { _state.value = PodcastDetailState(podcast = it) }
                .onFailure { _state.value = PodcastDetailState(error = it) }
        }
    }

    fun setFavourite(isFavourite: Boolean) {

    }

    @AssistedFactory
    interface PodcastDetailViewModelAssistedFactory {
        fun create(podcastId: String): PodcastDetailViewModel
    }

    class Factory (
        private val assistedFactory: PodcastDetailViewModelAssistedFactory,
        private val podcastId: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return assistedFactory.create(podcastId) as T
        }
    }
}