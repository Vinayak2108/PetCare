package `in`.vrkhedkr.petcare.util

import java.lang.Exception
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.DAY_OF_WEEK

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

    fun isNowBetween(workingHR: String?): Boolean {
        if(workingHR == null){
            return false
        }else{
            try {
                val now = Calendar.getInstance()
                /**
                 * Considering WeekDays are fixed i.e Monday to Friday.
                 * Customisation is impossible with only first letter
                 * because more that one week day have same initial.
                 */
                if(now.get(DAY_OF_WEEK) >= Calendar.MONDAY && now.get(DAY_OF_WEEK) <= Calendar.FRIDAY){
                    val df = SimpleDateFormat("HH:mm", Locale.ENGLISH)
                    val startTime = df.parse(workingHR.substring(4,9))
                    val endTime = df.parse(workingHR.substring(12,17))
                    val currentTime = df.parse(df.format(now.time))
                    return currentTime?.after(startTime) ?: false && currentTime?.before(endTime) ?: false
                }else{
                    return false
                }

            }catch (e:Exception){
                return false
            }
        }
    }

}