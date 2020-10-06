package `in`.vrkhedkr.petcare.viewmodel

import `in`.vrkhedkr.petcare.repository.PetCareRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivityViewModelFactory(private val repo: PetCareRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PetCareRepository::class.java).newInstance(repo)
    }
}