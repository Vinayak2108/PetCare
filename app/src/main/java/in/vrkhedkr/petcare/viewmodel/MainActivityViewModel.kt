package `in`.vrkhedkr.petcare.viewmodel

import `in`.vrkhedkr.petcare.model.ClinicConfigState
import `in`.vrkhedkr.petcare.model.PetState
import `in`.vrkhedkr.petcare.repository.PetCareRepository
import `in`.vrkhedkr.petcare.util.DateUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(private val repo: PetCareRepository) : ViewModel(){

    private val _petState: MutableLiveData<PetState> = MutableLiveData()
    val petState : LiveData<PetState> get() = _petState
    private val _clinicConfigState: MutableLiveData<ClinicConfigState> = MutableLiveData()
    val clinicConfigState : LiveData<ClinicConfigState> get() = _clinicConfigState

    private fun getWorkingHR(): String? {
        return try {
            (clinicConfigState.value as ClinicConfigState.Success).config.workHours
        }catch (e:Exception){
            null
        }
    }

    fun isWithinWorkingHr(): Boolean {
        return DateUtil.isNowBetween(getWorkingHR())
    }

    fun loadConfig(){
        _clinicConfigState.value = ClinicConfigState.Loading(true)
        viewModelScope.launch(Dispatchers.IO) {
            val holder = repo.getClinicConfig()
            withContext(Dispatchers.Main){
                holder.observeForever{
                    _clinicConfigState.postValue(it)
                }
            }
        }
    }

    fun loadPetList() {
        _petState.value = PetState.Loading(true)
        viewModelScope.launch(Dispatchers.IO) {
            val holder = repo.getPetList()
            withContext(Dispatchers.Main){
                holder.observeForever{
                    _petState.postValue(it)
                }
            }
        }
    }
}