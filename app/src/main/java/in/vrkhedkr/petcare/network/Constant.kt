package `in`.vrkhedkr.petcare.network

import `in`.vrkhedkr.petcare.R
import `in`.vrkhedkr.petcare.app.PetCare

private const val URL_END_POINT = "https://www.vrkhedkar.in/"
const val URL_GET_CLINIC_CONFIG = URL_END_POINT + "config.json"
const val URL_GET_PET_LIST = URL_END_POINT + "pets.json"


const val UNKNOWN_ERROR = 0
const val SERVER_ERROR = 500
const val TIME_OUT_ERROR = 504
const val NETWORK_ERROR = 523
const val NO_DATA_FOUND = 404

fun getMessagesFor(error:Int): Pair<String,String>{
    return when(error){
        UNKNOWN_ERROR ->{
            Pair(
                PetCare.getContext().getString(R.string.unknown_error_msg),
                PetCare.getContext().getString(R.string.unknown_error_sub_msg)
            )
        }
        SERVER_ERROR ->{
            Pair(
                PetCare.getContext().getString(R.string.server_error_msg),
                PetCare.getContext().getString(R.string.server_error_sub_msg)
            )
        }
        TIME_OUT_ERROR ->{
            Pair(
                PetCare.getContext().getString(R.string.timeout_error_msg),
                PetCare.getContext().getString(R.string.timeout_error_sub_msg)
            )
        }
        NETWORK_ERROR ->{
            Pair(
                PetCare.getContext().getString(R.string.network_error_msg),
                PetCare.getContext().getString(R.string.network_error_sub_msg)
            )
        }
        NO_DATA_FOUND ->{
            Pair(
                PetCare.getContext().getString(R.string.no_data_error_msg),
                PetCare.getContext().getString(R.string.no_data_error_sub_msg)
            )
        }
        else -> Pair(
            PetCare.getContext().getString(R.string.unknown_error_msg),
            PetCare.getContext().getString(R.string.unknown_error_sub_msg)
        )
    }
}
