package `in`.vrkhedkr.petcare.model

sealed class PetState{
    class Success(val petList:List<Pet>): PetState()
    class Error(val msg:String, val subMessage:String) : PetState()
    class Loading(val command: Boolean) : PetState()
}