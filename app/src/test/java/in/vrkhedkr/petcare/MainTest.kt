package `in`.vrkhedkr.petcare

import `in`.vrkhedkr.petcare.util.DateUtil
import `in`.vrkhedkr.petcare.util.DimenUtil
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class MainTest {

    private val workingHR = "M-F 09:00 - 18:00"
    private val workingHR_improper = "M-F09:00 - 18:00"
    private val density = 420f

    //<editor-fold desc="DateUtil.class">
    @Test
    fun `working HR outside week days range`() {
        val cal = TestData.getCalenderOutOfWeekDaysRange()
        assertTrue(!DateUtil.isNowBetween(workingHR, cal))
    }
    @Test
    fun `working HR inside week days range but out side time range`() {
        val cal = TestData.getCalenderInWeekDaysOutOFTimeRange()
        assertTrue(!DateUtil.isNowBetween(workingHR, cal))
    }
    @Test
    fun `working HR inside week days range and in side time range`() {
        val cal = TestData.getCalenderInWeekDaysInTimeRange()
        assertTrue(DateUtil.isNowBetween(workingHR, cal))
    }
    @Test
    fun `null working HR`() {
        val cal = TestData.getCalenderInWeekDaysInTimeRange()
        assertTrue(!DateUtil.isNowBetween(null, cal))
    }
    @Test
    fun `improper working HR String`() {
        val cal = TestData.getCalenderInWeekDaysInTimeRange()
        assertTrue(!DateUtil.isNowBetween(workingHR_improper, cal))
    }
    @Test
    fun `get simple date string for server time` (){
        val testPair = TestData.getServerTimeStringData()
        assertEquals(DateUtil.getSimpleDateString(testPair.first),testPair.second)
    }
    @Test
    fun `get simple date string for server time empty case` (){
        val testPair = TestData.getServerTimeEmptyData()
        assertEquals(DateUtil.getSimpleDateString(testPair.first),testPair.second)
    }
    //</editor-fold>


    //<editor-fold desc="Dimen Util">
    @Test
    fun `test pt to px conversion` () {
        val testPair = TestData.getPtToPxTestData()
        assertEquals(DimenUtil.pt2Px(testPair.first,density),testPair.second)
    }
    @Test
    fun `test pt to px conversion for zero` () {
        val testPair = TestData.getPtToPxTestDataForZero()
        assertEquals(DimenUtil.pt2Px(testPair.first, density),testPair.second)
    }
    @Test
    fun `test dp to px conversion` () {
        val testPair = TestData.getDpToPxTestData()
        assertEquals(DimenUtil.dp2Px(testPair.first,density),testPair.second)
    }
    @Test
    fun `test dp to px conversion for zero` () {
        val testPair = TestData.getDpToPxTestDataForZero()
        assertEquals(DimenUtil.dp2Px(testPair.first, density),testPair.second)
    }
    //</editor-fold>

}