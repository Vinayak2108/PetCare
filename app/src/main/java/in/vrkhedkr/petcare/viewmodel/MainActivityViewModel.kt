package `in`.vrkhedkr.petcare.viewmodel

import `in`.vrkhedkr.petcare.app.PetCare
import `in`.vrkhedkr.petcare.model.ClinicConfigState
import `in`.vrkhedkr.petcare.model.Pet
import `in`.vrkhedkr.petcare.model.PetState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainActivityViewModel(app: PetCare) : AndroidViewModel(app){

    private val _petState: MutableLiveData<PetState> = MutableLiveData()
        val petState : LiveData<PetState> get() = _petState
    private val _clinicConfigState: MutableLiveData<ClinicConfigState> = MutableLiveData()
        val clinicConfigState : LiveData<ClinicConfigState> get() = _clinicConfigState



}