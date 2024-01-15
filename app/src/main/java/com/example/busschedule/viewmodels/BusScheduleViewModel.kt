package com.example.busschedule.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.busschedule.database.schedule.Schedule
import com.example.busschedule.database.schedule.ScheduleDao

class BusScheduleViewModel(private val scheduleDao: ScheduleDao): ViewModel() {
    fun fullSchedule(): List<Schedule> = scheduleDao.getAll()

    fun scheduleForStopName(name: String): List<Schedule> = scheduleDao.getStopByName(name)

    //Necesitamos este factory para poder crear intancias del viewmodel con el dao
    //El codelab lo propone en una clase aparte
    class BusSchedulerViewModelFactory(private val scheduleDao: ScheduleDao): ViewModelProvider.Factory{
        override fun <T:ViewModel> create(modelClass: Class<T>):T{
            if(modelClass.isAssignableFrom(scheduleDao::class.java)){
                @Suppress("UNCHECKED_CAST")
                return BusScheduleViewModel(scheduleDao) as T
            }
            throw IllegalArgumentException("UNKNOWN VIEW MODEL CLASS")
        }
    }
}