package info.noahortega.bluecointracker

import android.app.Application
import androidx.arch.core.util.Function
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import info.noahortega.bluecointracker.database.BlueCoin
import info.noahortega.bluecointracker.database.CoinDatabase
import info.noahortega.bluecointracker.database.CoinDatabaseDao
import info.noahortega.bluecointracker.database.Level
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class SharedViewModel(application: Application) : AndroidViewModel(application) {

    var database: CoinDatabaseDao = CoinDatabase.getInstance(application).coinDao
    private var viewJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewJob)


    val liveLevels: LiveData<List<Level>> = database.getLiveOrderedLevels()
    lateinit var liveQueriedCoins: LiveData<List<BlueCoin>>

    fun updateCoinQuery(levelId: Int) {
        liveQueriedCoins = database.getLiveCoinsInLevelId(levelId)
    }





}




