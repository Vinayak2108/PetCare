package `in`.vrkhedkr.petcare.viewmodel

import `in`.vrkhedkr.petcare.app.PetCare
import `in`.vrkhedkr.petcare.model.ClinicConfigState
import `in`.vrkhedkr.petcare.model.ErrorResponse
import `in`.vrkhedkr.petcare.model.PetState
import `in`.vrkhedkr.petcare.model.SuccessResponse
import `in`.vrkhedkr.petcare.network.Api
import `in`.vrkhedkr.petcare.network.ApiCallBack
import `in`.vrkhedkr.petcare.network.URL_GET_CLINIC_CONFIG
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

    fun loadConfig(){
        viewModelScope.launch(Dispatchers.IO) {
            Api().call(URL_GET_CLINIC_CONFIG, object : ApiCallBack{
                override fun onSuccess(successResponse: SuccessResponse) {
                    TODO("Not yet implemented")
                }

                override fun onFailure(errorResponse: ErrorResponse) {
                    TODO("Not yet implemented")
                }

            })
        }
    }


}