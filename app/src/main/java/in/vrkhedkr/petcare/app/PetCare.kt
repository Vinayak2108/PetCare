package `in`.vrkhedkr.petcare.app

import `in`.vrkhedkr.petcare.network.lazyimg.LazyImage
import android.app.Application
import android.app.Person

class PetCare : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    companion object{
        private lateinit var instance:PetCare
        fun getContext():PetCare{
            return instance
        }
    }

}