package `in`.vrkhedkr.petcare.viewmodel

import `in`.vrkhedkr.petcare.model.*
import `in`.vrkhedkr.petcare.network.*
import `in`.vrkhedkr.petcare.util.DateUtil
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(app: Application) : AndroidViewModel(app){

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
            Api().call(URL_GET_CLINIC_CONFIG, object : ApiCallBack{
                override fun onSuccess(successResponse: SuccessResponse) {
                    fun setError(errorCode:Int){
                        val errorStrings = getMessagesFor(errorCode)
                        _clinicConfigState.postValue(ClinicConfigState.Error(errorStrings.first,errorStrings.second))
                    }
                    try{
                        val jsonObject = successResponse.data.getJSONObject("settings")
                        val parsedData = ClinicMapper.from(jsonObject)
                        _clinicConfigState.postValue(ClinicConfigState.Loading(false))
                        if(parsedData != null){
                            _clinicConfigState.postValue(ClinicConfigState.Success(parsedData))
                        }else{
                            setError(UNKNOWN_ERROR)
                        }
                    }catch (e:Exception){
                        setError(UNKNOWN_ERROR)
                    }
                }
                override fun onFailure(errorResponse: ErrorResponse) {
                    _clinicConfigState.postValue(ClinicConfigState.Loading(false))
                    val errorStrings = getMessagesFor(errorResponse.code)
                    _clinicConfigState.postValue(ClinicConfigState.Error(errorStrings.first,errorStrings.second))
                }
            })
        }
    }

    fun loadPetList() {
        _petState.value = PetState.Loading(true)
        viewModelScope.launch(Dispatchers.IO) {
            Api().call(URL_GET_PET_LIST, object : ApiCallBack{
                override fun onSuccess(successResponse: SuccessResponse) {
                    fun setError(errorCode:Int){
                        val errorStrings = getMessagesFor(errorCode)
                        _petState.postValue(PetState.Error(errorStrings.first,errorStrings.second))
                    }
                    try{
                        val jsonArray = successResponse.data.getJSONArray("pets")
                        val parsedData = PetMapper.from(jsonArray)
                        _petState.postValue(PetState.Loading(false))
                        when {
                            parsedData?.isEmpty() == true -> {
                                setError(NO_DATA_FOUND)
                            }
                            parsedData != null -> {
                                _petState.postValue(PetState.Success(parsedData))
                            }
                            else -> {
                                setError(UNKNOWN_ERROR)
                            }
                        }
                    }catch (e:Exception){
                        setError(UNKNOWN_ERROR)
                    }
                }
                override fun onFailure(errorResponse: ErrorResponse) {
                    _petState.postValue(PetState.Loading(false))
                    val errorStrings = getMessagesFor(errorResponse.code)
                    _petState.postValue(PetState.Error(errorStrings.first,errorStrings.second))
                }
            })
        }
    }
}