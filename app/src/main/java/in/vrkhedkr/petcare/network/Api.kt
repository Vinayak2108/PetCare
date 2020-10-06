package `in`.vrkhedkr.petcare.network

import `in`.vrkhedkr.petcare.model.ErrorResponse
import `in`.vrkhedkr.petcare.model.SuccessResponse
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class Api {

    fun call(url: String, callback: ApiCallBack){
        val request = buildGetRequest(url)
        OkClientProvider.client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                when(e){
                    is java.net.UnknownHostException -> {callback.onFailure(ErrorResponse(NETWORK_ERROR))}
                    is java.net.SocketTimeoutException -> {callback.onFailure(ErrorResponse(TIME_OUT_ERROR))}
                    else -> {callback.onFailure(ErrorResponse(SERVER_ERROR))}
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.code == 200){
                    try {
                        val obj = JSONObject(response.body?.string() ?: "")
                        callback.onSuccess(SuccessResponse(obj))
                    }catch (e:Exception){
                        callback.onFailure(ErrorResponse(UNKNOWN_ERROR))
                    }
                }else{
                    callback.onFailure(ErrorResponse(UNKNOWN_ERROR))
                }
            }
        })
    }

    private fun buildGetRequest(url:String) : Request {
        return Request.Builder()
            .url(url)
            .method("GET", null)
            .build()
    }

}

/**
 * Avoiding Multiple instances of OkHttpClient
 * as it is heavy to have multiple clients
 */
object OkClientProvider{
    val client by lazy { OkHttpClient().newBuilder().build() }
}

interface ApiCallBack{
    fun onSuccess(successResponse: SuccessResponse)
    fun onFailure(errorResponse: ErrorResponse)
}
