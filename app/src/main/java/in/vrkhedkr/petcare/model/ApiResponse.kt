package `in`.vrkhedkr.petcare.model

import org.json.JSONObject

data class SuccessResponse(val data:JSONObject)
data class ErrorResponse(val code:Int)