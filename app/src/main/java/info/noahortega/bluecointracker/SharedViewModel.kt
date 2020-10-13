package info.noahortega.bluecointracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.navigation.NavController

import info.noahortega.bluecointracker.database.BlueCoin
import info.noahortega.bluecointracker.database.CoinDatabase
import info.noahortega.bluecointracker.database.CoinDatabaseDao
import info.noahortega.bluecointracker.database.Level

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private var database: CoinDatabaseDao = CoinDatabase.getInstance(application).coinDao

    private var ioJob = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + ioJob)
    private var uiJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + uiJob)


    val liveLevels: LiveData<List<Level>> = database.getLiveOrderedLevels()

    fun updateCoin(coinId: Long, isChecked: Boolean, updateList: Boolean) {
        ioScope.launch {
            var coin = database.getCoinById(coinId)
            coin.checked = isChecked
            database.updateBlueCoin(coin)
            updateLevelCompletion(coin.myLevelId)

            if(updateList) {
                listViewCoins = database.getCoinsByLevelId(listViewCoins!![0].myLevelId)
            }
        }
    }

    private var listViewCoins: List<BlueCoin>? = null
    fun getQueriedCoins() : List<BlueCoin>? { //getter
        return listViewCoins
    }
    fun navToListWithLevel(navCon: NavController, levelId: Int) {
        ioScope.launch {
            listViewCoins = database.getCoinsByLevelId(levelId)
            navCon.navigate(R.id.action_homeFragment_to_listFragment)
        }
    }

    private var detailViewCoin: BlueCoin? = null
    fun getSelectedCoin() : BlueCoin? { //getter
        return detailViewCoin
    }
    fun navToDetailWithCoin(navCon: NavController, coinId: Long) {
        ioScope.launch {
            detailViewCoin = database.getCoinById(coinId)
            navCon.navigate(R.id.action_listFragment_to_detailFragment)
        }
    }

    private fun updateLevelCompletion(levelId: Int) {
        ioScope.launch {
            var completed = 0.0
            val checkList: List<Boolean> = database.getLevelCheckedList(levelId)
            val level: Level = database.getLevelbyId(levelId)
            for (checked in checkList) {
                if (checked) {
                    completed++
                }
            }
            val percent = (((completed) / checkList.size) * 100).toInt()
            level.percentDone = percent
            database.updateLevel(level)
        }
    }
}



