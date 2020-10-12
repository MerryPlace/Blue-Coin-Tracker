package info.noahortega.bluecointracker.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CoinDatabaseDao {

    @Insert
    suspend fun insertLevel(level: Level)

    @Insert
    suspend fun insertCoin(coin: BlueCoin)

    @Insert
    suspend fun insertCond(condition: Condition)

    @Insert
    suspend fun connectCoinCond(connection: CoinCondCrossRef)

    @Update
    suspend fun updateBlueCoin(blueCoin: BlueCoin)

    @Update
    suspend fun updateLevel(level: Level)

    @Query("SELECT * FROM level_table ORDER BY levelId ASC")
    fun getLiveOrderedLevels(): LiveData<List<Level>>


    @Query("SELECT * FROM coin_table WHERE coinId = :key")
    suspend fun getCoinById(key: Long): BlueCoin

    @Query("SELECT * FROM coin_table WHERE myLevelID = :levelId ORDER BY numInLevel ASC")
    fun getLiveCoinsInLevelId(levelId: Int): LiveData<List<BlueCoin>>

    @Query("SELECT checked FROM coin_table WHERE myLevelID = :levelId")
    suspend fun getLevelCheckedList(levelId: Int): List<Boolean>

    @Transaction
    @Query("SELECT * FROM level_table WHERE levelId = :id")
    suspend fun getLevelWithCoins(id: Int): LevelWithCoins

    @Transaction
    @Query("SELECT * FROM coin_table")
    suspend fun getCoinsWithConditions(): List<CoinWithConditions>

    @Transaction //probably shouldn't use
    @Query("SELECT * FROM level_table WHERE nickname = :nickname")
    suspend fun getLevelsWithCoinsWithSongs(nickname: String): List<LevelWithCoinsAndCond>

    @Query("SELECT COUNT(coinId) FROM coin_table")
    suspend fun countCoins() : Int

}