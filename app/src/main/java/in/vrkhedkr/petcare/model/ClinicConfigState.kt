package `in`.vrkhedkr.petcare.model

sealed class ClinicConfigState{
    class Success(val config:ClinicConfig) : ClinicConfigState()
    class Error(val msg:String, val subMessage:String) : ClinicConfigState()
    class Loading(val command: Boolean) : ClinicConfigState()
}