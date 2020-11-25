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


class SharedViewModel(app: Application) : AndroidViewModel(app) {

    private var database: CoinDatabaseDao = CoinDatabase.getInstance(app).coinDao

    private var ioJob = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + ioJob)

    val levelIDToNamesMap = mapOf(
        1 to app.getString(R.string.delfino_plaza),
        2 to app.getString(R.string.bianco_hills),
        3 to app.getString(R.string.ricco_harbor),
        4 to app.getString(R.string.gelato_beach),
        5 to app.getString(R.string.noki_bay),
        6 to app.getString(R.string.pinna_park),
        7 to app.getString(R.string.sirena_beach),
        8 to app.getString(R.string.pianta_village),
        9 to app.getString(R.string.corona_mountain)
    )

    val liveLevels: LiveData<List<Level>> = database.getLiveOrderedLevels()

    //For updating a coin's checked status in the database
    fun updateCoinCheck(coinId: Long, isChecked: Boolean) {
        ioScope.launch {
            val coin = database.getCoinById(coinId)
            coin.checked = isChecked
            database.updateBlueCoin(coin)
            updateLevelCompletion(coin.myLevelId)
        }
    }

    //For updating a level's percentage of coins collected
    private fun updateLevelCompletion(levelId: Int) {
        ioScope.launch {
            var completed = 0.0
            val checkList: List<Boolean> = database.getLevelCheckedList(levelId)
            val level: Level = database.getLevelById(levelId)
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

    //For use in the list fragment to avoid unnecessary fetches from the database
    private var fetchedCoins: List<BlueCoin>? = null
    var curLevelId = -1
    fun navToListWithLevel(navCon: NavController, levelId: Int) {
        ioScope.launch {
            curLevelId = levelId
            fetchedCoins = database.getCoinsByLevelId(levelId)
            navCon.navigate(R.id.action_homeFragment_to_listFragment)
        }
    }
    fun getFetchedCoins(): List<BlueCoin>? { //getter
        return fetchedCoins
    }
    fun editFetchedCoin(coinIndex: Int, checked: Boolean) { //setter
        fetchedCoins!![coinIndex].checked = checked
    }

    //For use in the detail fragment
    private var detailViewCoin: BlueCoin? = null
    private var detailCoinLevel: Level? = null
    private var fetchedIndex: Int = -1 //future proofing, in case fetched coins are sorted
    fun navToDetailWithCoin(navCon: NavController, coinId: Long, coinIndex: Int) {
        ioScope.launch {
            detailViewCoin = database.getCoinById(coinId)
            detailCoinLevel = database.getLevelById(detailViewCoin!!.myLevelId)
            fetchedIndex = coinIndex
            navCon.navigate(R.id.action_listFragment_to_detailFragment)
        }
    }
    fun getSelectedCoin(): BlueCoin? { //getter
        return detailViewCoin
    }
    fun getSelectedCoinLevel(): Level? { //getter
        return detailCoinLevel
    }
    fun getFetchedCoinIndex(): Int { //getter
        return fetchedIndex
    }

    //For use in the image zoom fragment
    var imageID = -1
    fun navToImageZoom(navCon: NavController, resourceID: Int) {
        imageID = resourceID
        navCon.navigate(R.id.action_detailFragment_to_imageZoomFragment)
    }


    /*public fun populateDatabase() {
        ioScope.launch {
            database.insertLevel(Level(1, "Delfino Plaza", "dp", 20, 0,"guide_link_dp"))
            database.insertLevel(Level(2, "Bianco Hills", "bh", 30, 0,"guide_link_bh"))
            database.insertLevel(Level(3, "Ricco Harbor", "rh", 30, 0,"guide_link_rh"))
            database.insertLevel(Level(4, "Gelato Beach", "gb", 30, 0,"guide_link_gb"))
            database.insertLevel(Level(5, "Noki Bay", "nb", 30, 0,"guide_link_nb"))
            database.insertLevel(Level(6, "Pinna Park", "pp", 30, 0,"guide_link_pp"))
            database.insertLevel(Level(7, "Sirena Beach", "sb", 30, 0,"guide_link_sb"))
            database.insertLevel(Level(8, "Pianta Village", "pv", 30, 0,"guide_link_pv"))
            database.insertLevel(Level(9, "Corona Mountain", "cm", 10, 0,"guide_link_cm"))

            val levels: List<Level> = database.getOrderedLevels()
            var addition = "0"
            for (level in levels) {
                val nickname = level.nickname
                println(nickname)
                for (n in 1..level.bCoinCount) {
                    if(n == 1) {
                        addition = "0"
                    }
                    else if(n == 10) {
                        addition = ""
                    }

                    database.insertCoin(
                        BlueCoin(
                            myLevelId = level.levelId,
                            numInLevel = n,
                            imageAddress = "coin_" + nickname + "_" + addition + n,
                            youtubeLink = "coin_video_"+ nickname + "_" + addition + n,
                            shortTitle = "coin_title_"+ nickname + "_" + addition + n,
                            description = "coin_descr_"+ nickname + "_" + addition + n,
                        )
                    )
                }
            }
        }
        println("init??~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    }*/
}




