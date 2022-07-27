package com.hazem.homebase.shiftapp.detailscreen

import androidx.lifecycle.*
import com.hazem.homebase.shiftapp.models.AppResults
import com.hazem.homebase.shifts.models.Color
import com.hazem.homebase.shifts.models.NewShift
import com.hazem.homebase.shifts.usecases.CreateNewShiftUseCase
import com.hazem.homebase.shifts.usecases.LoadShiftInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

data class ShiftInfo(
    var name: String = "",
    var role: String = "",
    var color: String = "",
    var start: Date? = null,
    var end: Date? = null
)

class CreateNewShiftViewModel(
    private val loadShiftInfoUseCase: LoadShiftInfoUseCase,
    private val createNewShiftUseCase: CreateNewShiftUseCase
) :
    ViewModel() {

    private val shiftInfo = ShiftInfo()

    fun addName(name: String) {
        shiftInfo.name = name
    }

    fun addColor(color: String) {
        shiftInfo.color = color
    }

    fun addRole(role: String) {
        shiftInfo.role = role
    }

    fun addStartDate(date: Date) {
        shiftInfo.start = date
    }

    fun addEndDate(date: Date) {
        shiftInfo.end = date
    }

    private val _namesLd: MutableLiveData<AppResults<List<String>>> = MutableLiveData()
    private val _rolesLd: MutableLiveData<AppResults<List<String>>> = MutableLiveData()
    private val _colorsLd: MutableLiveData<AppResults<List<String>>> = MutableLiveData()

    val namesLd: LiveData<AppResults<List<String>>> = _namesLd
    val rolesLd: LiveData<AppResults<List<String>>> = _rolesLd
    val colorsLd: LiveData<AppResults<List<String>>> = _colorsLd

    private val _createNewShift: MutableLiveData<AppResults<Unit>> = MutableLiveData()
    val createNewShift: LiveData<AppResults<Unit>> = _createNewShift

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
        val result = invalidData()
        if (result)
            return
        viewModelScope.launch(Dispatchers.IO) {
            createNewShiftUseCase.addNewShift(
                NewShift(
                    shiftInfo.name,
                    shiftInfo.role,
                    Color.valueOf(shiftInfo.color.uppercase()),
                    shiftInfo.start!!,
                    shiftInfo.end!!
                )
            )
        }
    }

    private fun invalidData(): Boolean {
        val result = when {
            shiftInfo.name.isEmpty() -> {
                _createNewShift.value = AppResults.EmptyName
                true
            }
            shiftInfo.role.isEmpty() -> {
                _createNewShift.value = AppResults.EmptyRole
                true
            }
            shiftInfo.color.isEmpty() -> {
                _createNewShift.value = AppResults.EmptyColor
                true
            }
            shiftInfo.start == null -> {
                _createNewShift.value = AppResults.EmptyStartDate
                true
            }
            shiftInfo.end == null -> {
                _createNewShift.value = AppResults.EmptyEndDate
                true
            }
            else -> false
        }
        return result
    }
}

class NewShiftVmFactory(
    private val useCase: LoadShiftInfoUseCase,
    private val createNewShiftUseCase: CreateNewShiftUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateNewShiftViewModel(useCase, createNewShiftUseCase) as T
    }
}