package com.example.tier_challeng_jonathancheli

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class DataViewModel : ViewModel() {
    private val mutableLiveData: MutableLiveData<Scooter>
    private var repository: Repository? = null
    private var scooterData: MutableLiveData<List<Scooter>>? = null
    fun init() {
        if (scooterData == null) {
            repository = Repository.instance
            scooterData = repository!!.getData()
        }
    }
    fun getScooterData(): MutableLiveData<List<Scooter>>? {
        return scooterData
    }
    init {
        mutableLiveData = MutableLiveData()
        getScooterData()
    }
}