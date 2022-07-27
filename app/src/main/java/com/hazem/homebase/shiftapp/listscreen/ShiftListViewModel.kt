package com.hazem.homebase.shiftapp.listscreen

import android.util.Log
import androidx.lifecycle.*
import com.hazem.homebase.shiftapp.models.AppResults
import com.hazem.homebase.shifts.models.ShiftViewModel
import com.hazem.homebase.shifts.usecases.LoadShiftListViewModelUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShiftListViewModel(private val shiftViewModelListUseCase: LoadShiftListViewModelUseCase) :
    ViewModel() {

    private val _listLiveData: MutableLiveData<AppResults<List<ShiftViewModel>>> = MutableLiveData()
    val listLiveData: LiveData<AppResults<List<ShiftViewModel>>> = _listLiveData

    fun loadShiftList() {
        viewModelScope.launch(Dispatchers.IO) {
            shiftViewModelListUseCase.loadShiftList()
                .onSuccess {
                    _listLiveData.postValue(AppResults.Success(it))
                }.onFailure {
                    Log.d("ERROR_===",it.message ?: "")
                    _listLiveData.postValue(AppResults.LoadingError)
                }
        }
    }
}

class ShiftListViewModelFactory(private val shiftViewModelListUseCase: LoadShiftListViewModelUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShiftListViewModel(shiftViewModelListUseCase) as T
    }
}