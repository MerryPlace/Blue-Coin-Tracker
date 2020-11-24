package info.noahortega.bluecointracker.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CoinDatabaseDao {

    @Insert
    suspend fun insertLevel(level: Level)

    @Insert
    suspend fun insertCoin(coin: BlueCoin)

    @Update
    suspend fun updateBlueCoin(blueCoin: BlueCoin)

    @Update
    suspend fun updateLevel(level: Level)


    @Query("SELECT * FROM level_table ORDER BY levelId ASC")
    fun getLiveOrderedLevels(): LiveData<List<Level>>

    @Query("SELECT * FROM level_table ORDER BY levelId ASC")
    fun getOrderedLevels(): List<Level>

    @Query("SELECT * FROM level_table WHERE levelId = :levelId ORDER BY levelId ASC")
    suspend fun getLevelById(levelId: Int): Level

    @Query("SELECT * FROM coin_table WHERE myLevelID = :levelId ORDER BY numInLevel ASC")
    suspend fun getCoinsByLevelId(levelId: Int): List<BlueCoin>

    @Query("SELECT * FROM coin_table WHERE coinId = :key")
    suspend fun getCoinById(key: Long): BlueCoin

    @Query("SELECT checked FROM coin_table WHERE myLevelID = :levelId")
    suspend fun getLevelCheckedList(levelId: Int): List<Boolean>

    @Transaction
    @Query("SELECT * FROM level_table WHERE levelId = :id")
    suspend fun getLevelWithCoins(id: Int): LevelWithCoins
}