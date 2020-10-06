package `in`.vrkhedkr.petcare.network.lazyimg

import `in`.vrkhedkr.petcare.app.PetCare
import `in`.vrkhedkr.petcare.util.DeviceHelper
import `in`.vrkhedkr.petcare.util.DimenUtil
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*


object LazyImage {

    var memoryCatch = FastCatch()

    fun load(url: String, view: ImageView, errorImage:Int) {
        view.setImageBitmap(null)
        val  catchBitmap = getCatchBitmap(url)
        if( catchBitmap != null){
            view.setImageBitmap(catchBitmap)
        }else{
            if(url.isBlank()){
                view.setImageResource(errorImage)
            }else{
                loadImage(url,view, memoryCatch, errorImage)
            }
        }
    }

    private fun getCatchBitmap(url: String): Bitmap? {
        return  memoryCatch[url]
    }

    private fun loadImage(
        url: String,
        view: ImageView,
        memoryCatch: FastCatch,
        errorImage: Int
    ){
        (view.tag as Job?)?.cancel()
        val job = GlobalScope.launch (Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder().url(url)
                .build()
            val response = client.newCall(request).execute()
            val inputStream : InputStream? = response.body?.byteStream()
            if(inputStream != null){
                val processedBitmap = processBitmap(inputStream)
                if(processedBitmap == null && isActive){
                    withContext(Dispatchers.Main) {
                        view.setImageResource(errorImage)
                    }
                }else{
                    memoryCatch.put(url,processedBitmap)
                    withContext(Dispatchers.Main){
                        if(isActive)
                            view.setImageBitmap(processedBitmap)
                    }
                }
            }else{
                withContext(Dispatchers.Main) {
                    if (isActive)
                        view.setImageResource(errorImage)
                }
            }
        }
        view.tag = job
    }

    private fun processBitmap(inputStream: InputStream): Bitmap? {
        val bmOptions = BitmapFactory.Options()
        val tempFileName = Calendar.getInstance().timeInMillis.toString()
        val tempFile = createTempFile(tempFileName, "", PetCare.getContext().cacheDir)
        writeToFile(inputStream, tempFile.outputStream())
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeStream(tempFile.inputStream(),null, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight
        val desiredWidth = DimenUtil.dp2Px(80f,DeviceHelper.getDensity())
        val scaleFactor: Int = (photoW / desiredWidth).coerceAtLeast(photoH / desiredWidth)
        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor
        val finalBitmap =  BitmapFactory.decodeStream(tempFile.inputStream(),null,bmOptions)
        tempFile.delete()
        return finalBitmap
    }

    private fun writeToFile(
        inputStream: InputStream,
        outputStream: FileOutputStream
    ) {
        var read: Int
        val bytes = ByteArray(1024)
        while (inputStream.read(bytes).also { read = it } != -1) {
            outputStream.write(bytes, 0, read)
        }
    }

}