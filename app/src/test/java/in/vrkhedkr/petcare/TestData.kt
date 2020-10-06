package `in`.vrkhedkr.petcare

import java.util.*

object TestData {

    /**
     * It provides different working hr String as key and
     * Expected result as value. this expected result must
     * be change if you change for respected now used in test
     */
    fun getCalenderOutOfWeekDaysRange(): Calendar {
        return Calendar.getInstance().apply {
            set(2020, 4,10)
        }
    }
    fun getCalenderInWeekDaysOutOFTimeRange(): Calendar {
        return Calendar.getInstance().apply {
            set(2020, 6,10,19,0,0)
        }
    }
    fun getCalenderInWeekDaysInTimeRange(): Calendar {
        return Calendar.getInstance().apply {
            set(2020, 6,10 ,10,0,0)
        }
    }

    fun getServerTimeStringData(): Pair<String, String> {
        return Pair("2018-07-30T18:10:57.027Z", "30 Jul 2018")
    }
    fun getServerTimeEmptyData(): Pair<String, String> {
        return Pair("", "Beginning")
    }

    fun getPtToPxTestData(): Pair<Int,Float> {
        //Considering Density = 420
        return Pair(12,70f)
    }

    fun getPtToPxTestDataForZero(): Pair<Int,Float> {
        //Considering Density = 420
        return Pair(0,0f)
    }

    fun getDpToPxTestData(): Pair<Float,Int> {
        //Considering Density = 420
        return Pair(12f,5040)
    }

    fun getDpToPxTestDataForZero(): Pair<Float,Int> {
        //Considering Density = 420
        return Pair(0f,0)
    }

}