package `in`.vrkhedkr.petcare.repository

import `in`.vrkhedkr.petcare.model.*
import `in`.vrkhedkr.petcare.network.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PetCareRepositoryImpl : PetCareRepository {

    private val _petState: MutableLiveData<PetState> = MutableLiveData()
    private val petState : LiveData<PetState> get() = _petState
    private val _clinicConfigState: MutableLiveData<ClinicConfigState> = MutableLiveData()
    private val clinicConfigState : LiveData<ClinicConfigState> get() = _clinicConfigState

    override fun getClinicConfig(): LiveData<ClinicConfigState> {
        Api().call(URL_GET_CLINIC_CONFIG, object : ApiCallBack {
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
        return clinicConfigState
    }

    override fun getPetList() : LiveData<PetState>{
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
        return petState
    }

}