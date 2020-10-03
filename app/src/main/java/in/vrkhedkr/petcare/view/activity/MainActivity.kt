package `in`.vrkhedkr.petcare.view.activity

import `in`.vrkhedkr.petcare.R
import `in`.vrkhedkr.petcare.model.ClinicConfig
import `in`.vrkhedkr.petcare.model.ClinicConfigState
import `in`.vrkhedkr.petcare.model.Pet
import `in`.vrkhedkr.petcare.model.PetState
import `in`.vrkhedkr.petcare.view.adpaters.PetListAdapter
import `in`.vrkhedkr.petcare.viewmodel.MainActivityViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel:MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(MainActivityViewModel::class.java)
        initLayout()
        loadData()
    }

    private fun loadData() {
        loadConfig()
        loadPetList()
    }

    private fun loadPetList() {

    }

    private fun loadConfig() {
        viewModel.loadConfig()
    }

    private fun initLayout() {

        viewModel.clinicConfigState.observe(this , Observer { configState ->
            when(configState){
                is ClinicConfigState.Loading -> {setLoader(configState.command)}
                is ClinicConfigState.Error -> {showError(configState.msg,configState.subMessage)}
                is ClinicConfigState.Success -> {
                    hideError()
                    setConfig(configState.config)
                }
            }
        })

        viewModel.petState.observe(this , Observer { petState ->
            when(petState){
                is PetState.Loading -> {setListLoader(petState.command)}
                is PetState.Error -> {showListError(petState.msg,petState.subMessage)}
                is PetState.Success -> {
                    hideListError()
                    setListData(petState.petList)
                }
            }
        })

        errorView?.setOnClickListener {
            loadData()
        }

        errorView?.setOnClickListener {
            loadPetList()
        }

    }

    private fun setListData(petListData: List<Pet>) {
        petList.adapter = PetListAdapter(petListData)
    }

    private fun setConfig(config: ClinicConfig) {
        call?.isVisible = config.isCallEnabled
        chat?.isVisible = config.isChatEnabled
        workingHR?.text = getString(R.string.working_hr ,config.workHours)
    }

    private fun setLoader(command: Boolean){
       loaderView?.isVisible = command
    }
    private fun showError(msg:String, subMessage:String){
        errorView?.isVisible = true
        errorMsg?.text = msg
        errorSubMsg?.text = subMessage
    }
    private fun hideError(){
        errorView?.isVisible = false
    }

    private fun setListLoader(command: Boolean){
        listLoaderView?.isVisible = command
    }
    private fun showListError(msg:String, subMessage:String){
        listErrorView?.isVisible = true
        listErrorMsg?.text = msg
        listErrorSubMsg?.text = subMessage
    }
    private fun hideListError(){
        listErrorView?.isVisible = false
    }

}