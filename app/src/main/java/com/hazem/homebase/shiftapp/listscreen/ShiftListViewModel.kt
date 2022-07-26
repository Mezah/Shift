package com.hazem.homebase.shiftapp.listscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hazem.homebase.shiftapp.models.AppResults
import com.hazem.homebase.shifts.models.ShiftViewModel
import com.hazem.homebase.shifts.usecases.LoadShiftViewModelListUseCase

class ShiftListViewModel(private val shiftViewModelListUseCase: LoadShiftViewModelListUseCase) :
    ViewModel() {

    private val _listLiveData: MutableLiveData<AppResults<List<ShiftViewModel>>> = MutableLiveData()
    val listLiveData: LiveData<AppResults<List<ShiftViewModel>>> = _listLiveData

    fun loadShiftList() {
        shiftViewModelListUseCase.loadShiftsViewModelList()
            .onSuccess {
                _listLiveData.value = AppResults.Success(it)
            }.onFailure {
                _listLiveData.value = AppResults.Failure
            }
    }
}

class ShiftListViewModelFactory(private val shiftViewModelListUseCase: LoadShiftViewModelListUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShiftListViewModel(shiftViewModelListUseCase) as T
    }
}