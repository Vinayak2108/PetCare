package `in`.vrkhedkr.petcare.model

import `in`.vrkhedkr.petcare.model.PetMapper.from
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

interface Mapper {
    fun from(obj: JSONObject): Any?
    fun from(objArray: JSONArray): List<Any>?
}

object ClinicMapper : Mapper {
    override fun from(obj: JSONObject): ClinicConfig? {
        return try {
            val isChatEnabled = obj.getBoolean("isChatEnabled")
            val isCallEnabled = obj.getBoolean("isCallEnabled")
            val workHours = obj.getString("workHours")
            ClinicConfig(
                isCallEnabled = isCallEnabled,
                isChatEnabled = isChatEnabled,
                workHours = workHours
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null;
        }
    }

    override fun from(objArray: JSONArray): List<ClinicConfig>? {
        val dataList = ArrayList<ClinicConfig>()
        return try {
            for (i in 0 until objArray.length()) {
                val data = from(objArray[i] as JSONObject)
                if (data != null) {
                    dataList.add(data)
                }
            }
            dataList
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

object PetMapper : Mapper {

    override fun from(obj: JSONObject): Pet? {
        return try {
            val title: String = obj.getString("title")
            val imgUrl: String = obj.getString("image_url")
            val contentUrl: String = obj.getString("content_url")
            val dateAdded: String = obj.getString("date_added")
            Pet(title = title, imgUrl = imgUrl, contentUrl = contentUrl, dateAdded = dateAdded)
        } catch (e: Exception) {
            null;
        }
    }

    override fun from(objArray: JSONArray): List<Pet>? {
        val dataList = ArrayList<Pet>()
        return try {
            for (i in 0 until objArray.length()) {
                val data = from(objArray[i] as JSONObject)
                if (data != null) {
                    dataList.add(data)
                }
            }
            dataList
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}