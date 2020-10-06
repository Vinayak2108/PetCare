package `in`.vrkhedkr.petcare.view

import `in`.vrkhedkr.petcare.R
import android.content.Context
import androidx.appcompat.app.AlertDialog

object DialogHelper {
    fun showAlert(context: Context, msg:String){
        val alertDialog = AlertDialog.Builder(context)
            .setMessage(msg).setTitle(R.string.app_name)
            .create()
        alertDialog.show()
    }
}