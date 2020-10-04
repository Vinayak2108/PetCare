package `in`.vrkhedkr.petcare.util

import `in`.vrkhedkr.petcare.app.PetCare
import android.content.Context

object DimenUtil {

    fun pt2Px(points: Int): Float {
        return (points*PetCare.getContext().resources.displayMetrics.density)/72
    }

    fun dp2Px(dp: Float): Int {
        return (dp * PetCare.getContext().resources.displayMetrics.density + 0.5f).toInt()
    }

}