package `in`.vrkhedkr.petcare.view.activity

import `in`.vrkhedkr.petcare.R
import `in`.vrkhedkr.petcare.model.ClinicConfig
import `in`.vrkhedkr.petcare.model.ClinicConfigState
import `in`.vrkhedkr.petcare.model.Pet
import `in`.vrkhedkr.petcare.model.PetState
import `in`.vrkhedkr.petcare.repository.PetCareRepositoryImpl
import `in`.vrkhedkr.petcare.view.DialogHelper
import `in`.vrkhedkr.petcare.view.adpaters.PetListAdapter
import `in`.vrkhedkr.petcare.viewmodel.MainActivityViewModel
import `in`.vrkhedkr.petcare.viewmodel.MainActivityViewModelFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel:MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this,
            MainActivityViewModelFactory(PetCareRepositoryImpl())).get(MainActivityViewModel::class.java)
        initLayout()
        loadData()
    }

    private fun loadData() {
        loadConfig()
        loadPetList()
    }
    private fun loadPetList() {
        viewModel.loadPetList()
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
        listErrorView?.setOnClickListener {
            loadPetList()
        }

        chat.setOnClickListener {
            handleCommunicationRequest()
        }
        call.setOnClickListener {
            handleCommunicationRequest()
        }

    }

    private fun handleCommunicationRequest() {
       if(viewModel.isWithinWorkingHr(viewModel.getWorkingHR(), Calendar.getInstance())){
            DialogHelper.showAlert(this,getString(R.string.in_hour_msg))
       }else{
           DialogHelper.showAlert(this,getString(R.string.out_hour_msg))
       }
    }

    private fun setListData(petListData: List<Pet>) {
        setListLoader(false)
        petList.adapter = PetListAdapter(petListData)
    }
    private fun setConfig(config: ClinicConfig) {
        setLoader(false)
        call?.isVisible = config.isCallEnabled
        chat?.isVisible = config.isChatEnabled
        workingHR?.text = getString(R.string.working_hr ,config.workHours)
    }

    private fun setLoader(command: Boolean){
       loaderView?.isVisible = command
    }
    private fun showError(msg:String, subMessage:String){
        setLoader(false)
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
        setListLoader(false)
        listErrorView?.isVisible = true
        listErrorMsg?.text = msg
        listErrorSubMsg?.text = subMessage
    }
    private fun hideListError(){
        listErrorView?.isVisible = false
    }
}