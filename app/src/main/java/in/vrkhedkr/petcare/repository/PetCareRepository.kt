package `in`.vrkhedkr.petcare.repository

import `in`.vrkhedkr.petcare.model.ClinicConfigState
import `in`.vrkhedkr.petcare.model.PetState
import androidx.lifecycle.LiveData

interface PetCareRepository {
    fun getClinicConfig(): LiveData<ClinicConfigState>
    fun getPetList(): LiveData<PetState>
}