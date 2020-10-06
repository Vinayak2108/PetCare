package `in`.vrkhedkr.petcare.util

object DimenUtil {

    fun pt2Px(points: Int,density: Float): Float {
        return (points*density)/72
    }

    fun dp2Px(dp: Float, density: Float): Int {
        return (dp * density + 0.5f).toInt()
    }

}