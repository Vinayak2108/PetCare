package `in`.vrkhedkr.petcare.util

import `in`.vrkhedkr.petcare.app.PetCare

object DeviceHelper {

    fun getDensity(): Float {
        return PetCare.getContext().resources.displayMetrics.density
    }

}