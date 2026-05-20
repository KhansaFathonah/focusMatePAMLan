package com.example.focusmate.presentation.progressPage.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusmate.domain.usecase.focus.GetFocusHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(

    private val getFocusHistoryUseCase: GetFocusHistoryUseCase

) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            HistoryUiState()
        )

    val uiState =
        _uiState.asStateFlow()

    init {

        loadHistory()
    }

    private fun loadHistory() {

        viewModelScope.launch {

            getFocusHistoryUseCase()
                .collect { sessions ->

                    _uiState.update { currentState ->

                        currentState.copy(
                            sessions = sessions
                        )
                    }
                }
        }
    }

    fun selectTab(
        tab: String
    ) {

        _uiState.update { currentState ->

            currentState.copy(
                selectedTab = tab
            )
        }
    }
}
