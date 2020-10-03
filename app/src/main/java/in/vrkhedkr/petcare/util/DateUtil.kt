package `in`.vrkhedkr.petcare.util

import java.lang.Exception
import java.lang.StringBuilder

object DateUtil{

    private val MONTH_SHORT = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec")

    fun getSimpleDateString(serverFormatString : String): String {
        val builder = StringBuilder()
        return try {
            builder.append(serverFormatString.substring(8,10))
            builder.append(" ")
            builder.append(MONTH_SHORT[serverFormatString.substring(5,7).toInt()])
            builder.append(" ")
            builder.append(serverFormatString.substring(0,4))
            builder.toString()
        }catch (e:Exception){
            "Beginning"
        }
    }

}