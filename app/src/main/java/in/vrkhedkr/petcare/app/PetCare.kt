package `in`.vrkhedkr.petcare.app

import android.app.Application
import android.app.Person

class PetCare : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    companion object{
        lateinit var instance:PetCare
        fun getContext():PetCare{
            return instance
        }
    }

}