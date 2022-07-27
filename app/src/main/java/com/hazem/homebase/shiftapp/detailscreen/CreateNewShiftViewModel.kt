package com.hazem.homebase.shiftapp.detailscreen

import androidx.lifecycle.*
import com.hazem.homebase.shiftapp.models.AppResults
import com.hazem.homebase.shifts.usecases.LoadShiftInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

data class ShiftInfo(
    val name: String = "",
    val role: String = "",
    val color: String = "",
    val start: Date? = null,
    val end: Date? = null
)

class CreateNewShiftViewModel(private val loadShiftInfoUseCase: LoadShiftInfoUseCase) :
    ViewModel() {

    val shiftInfo = ShiftInfo()

    private val _namesLd: MutableLiveData <AppResults<List<String>>> = MutableLiveData()
    private val _rolesLd: MutableLiveData <AppResults<List<String>>> = MutableLiveData()
    private val _colorsLd: MutableLiveData<AppResults<List<String>>> = MutableLiveData()

    val namesLd: LiveData <AppResults<List<String>>> = _namesLd
    val rolesLd: LiveData <AppResults<List<String>>> = _rolesLd
    val colorsLd: LiveData<AppResults<List<String>>> = _colorsLd

    private val createNewShift: MutableLiveData<AppResults<Unit>> = MutableLiveData()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadShiftInfoUseCase.apply {
                loadEmployeeNames().onSuccess {
                    _namesLd.postValue(AppResults.Success(it))
                }.onFailure {
                    _namesLd.postValue(AppResults.LoadingError)
                }
                loadRoleLists().onSuccess {
                    _rolesLd.postValue(AppResults.Success(it))
                }.onFailure {
                    _rolesLd.postValue(AppResults.LoadingError)
                }
                loadColorList().onSuccess {
                    _colorsLd.postValue(AppResults.Success(it))
                }.onFailure {
                    _colorsLd.postValue(AppResults.LoadingError)
                }
            }
        }
    }

    fun createNewShift() {
        val result = validateData()
        if (!result)
            return
    }

    private fun validateData(): Boolean {
        val result = when {
            shiftInfo.name.isEmpty() -> {
                createNewShift.value = AppResults.EmptyName
                false
            }
            shiftInfo.role.isEmpty() -> {
                createNewShift.value = AppResults.EmptyRole
                false
            }
            shiftInfo.color.isEmpty() -> {
                createNewShift.value = AppResults.EmptyColor
                false
            }
            shiftInfo.start == null -> {
                createNewShift.value = AppResults.EmptyStartDate
                false
            }
            shiftInfo.end == null -> {
                createNewShift.value = AppResults.EmptyEndDate
                false
            }
            else -> false
        }
        return result
    }
}

class NewShiftVmFactory(private val useCase: LoadShiftInfoUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateNewShiftViewModel(useCase) as T
    }
}