package `in`.vrkhedkr.petcare.viewmodel

import `in`.vrkhedkr.petcare.app.PetCare
import `in`.vrkhedkr.petcare.model.*
import `in`.vrkhedkr.petcare.network.*
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainActivityViewModel(app: Application) : AndroidViewModel(app){

    private val _petState: MutableLiveData<PetState> = MutableLiveData()
        val petState : LiveData<PetState> get() = _petState
    private val _clinicConfigState: MutableLiveData<ClinicConfigState> = MutableLiveData()
        val clinicConfigState : LiveData<ClinicConfigState> get() = _clinicConfigState

    fun loadConfig(){
        viewModelScope.launch(Dispatchers.IO) {
            _clinicConfigState.postValue(ClinicConfigState.Loading(true))
            Api().call(URL_GET_CLINIC_CONFIG, object : ApiCallBack{
                override fun onSuccess(successResponse: SuccessResponse) {

                    fun setError(errorCode:Int){
                        val errorStrings = getMessagesFor(errorCode)
                        _clinicConfigState.postValue(ClinicConfigState.Error(errorStrings.first,errorStrings.second))
                    }

                   viewModelScope.launch(Dispatchers.IO) {
                       try{
                           val jsonObject = successResponse.data.getJSONObject("settings")
                           val parsedData = ClinicMapper.from(jsonObject)
                           withContext(Dispatchers.Main){
                               _clinicConfigState.postValue(ClinicConfigState.Loading(false))
                               if(parsedData != null){
                                   _clinicConfigState.postValue(ClinicConfigState.Success(parsedData))
                               }else{
                                   setError(UNKNOWN_ERROR)
                               }
                           }
                       }catch (e:Exception){
                           withContext(Dispatchers.Main){setError(UNKNOWN_ERROR)}
                       }
                   }
                }
                override fun onFailure(errorResponse: ErrorResponse) {
                    viewModelScope.launch(Dispatchers.Main){
                        _clinicConfigState.postValue(ClinicConfigState.Loading(false))
                        val errorStrings = getMessagesFor(errorResponse.code)
                        _clinicConfigState.postValue(ClinicConfigState.Error(errorStrings.first,errorStrings.second))
                    }
                }
            })
        }
    }

    fun loadPetList() {
        viewModelScope.launch(Dispatchers.IO) {
            _petState.postValue(PetState.Loading(true))
            Api().call(URL_GET_PET_LIST, object : ApiCallBack{
                override fun onSuccess(successResponse: SuccessResponse) {
                    fun setError(errorCode:Int){
                        val errorStrings = getMessagesFor(errorCode)
                        _petState.postValue(PetState.Error(errorStrings.first,errorStrings.second))
                    }
                    viewModelScope.launch(Dispatchers.IO) {
                        try{
                            val jsonArray = successResponse.data.getJSONArray("pets")
                            val parsedData = PetMapper.from(jsonArray)
                            withContext(Dispatchers.Main){
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
                            }
                        }catch (e:Exception){
                            withContext(Dispatchers.Main){setError(UNKNOWN_ERROR)}
                        }
                    }
                }
                override fun onFailure(errorResponse: ErrorResponse) {
                    viewModelScope.launch(Dispatchers.Main){
                        _petState.postValue(PetState.Loading(false))
                        val errorStrings = getMessagesFor(errorResponse.code)
                        _petState.postValue(PetState.Error(errorStrings.first,errorStrings.second))
                    }
                }
            })
        }
    }


}