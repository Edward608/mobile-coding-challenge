package com.edwardwongtl.podcastchallenge.ui.podcastlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwardwongtl.podcastchallenge.data.PodcastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodcastListViewModel @Inject constructor(
    private val podcastRepository: PodcastRepository
) : ViewModel() {
    init {
        getPodcasts()
    }

    private val _state = MutableStateFlow<PodcastListState>(PodcastListState())
    val state: StateFlow<PodcastListState> = _state

    fun getPodcasts() {
        viewModelScope.launch {
            // Page number is not relevant here since mock server's return data are hardcoded
            podcastRepository.getBestPodcasts(0)
                .onSuccess { _state.value = _state.value.copy(podcasts = it) }
                .onFailure { _state.value = _state.value.copy(error = it) }
        }
    }
}