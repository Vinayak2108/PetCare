package `in`.vrkhedkr.petcare.view.activity

import `in`.vrkhedkr.petcare.R
import android.os.Bundle
import android.view.MenuItem
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_pet_details.*

class PetDetails : AppCompatActivity() {

    lateinit var url:String
    lateinit var title:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_details)
        readBundle()
        initView()
        loadData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {onBackPressed();return true}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        errorView.setOnClickListener {
            loadData()
        }
        webView.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view : WebView, url : String) : Boolean{
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                setLoader(false)
            }

            override fun onReceivedHttpError(
                view: WebView?,
                request: WebResourceRequest?,
                errorResponse: WebResourceResponse?
            ) {
                showError(getString(R.string.unknown_error_msg), getString(R.string.unknown_error_sub_msg))
                super.onReceivedHttpError(view, request, errorResponse)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                showError(getString(R.string.unknown_error_msg), getString(R.string.unknown_error_sub_msg))
                super.onReceivedError(view, request, error)
            }

        }
    }

    private fun setLoader(command: Boolean) {
        loader.isVisible = command
    }

    private fun showError(msg:String, subMsg:String){
        setLoader(false)
        errorView.isVisible = true
        errorMsg.text = msg
        errorSubMsg.text = subMsg
    }

    private fun hideError(){
        errorView.isVisible = false
    }

    private fun loadData() {
        hideError()
        setLoader(true)
        webView.loadUrl(url)
    }

    private fun readBundle() {
        url = intent.getStringExtra("url") ?: ""
        title = intent.getStringExtra("title") ?: ""
    }
}