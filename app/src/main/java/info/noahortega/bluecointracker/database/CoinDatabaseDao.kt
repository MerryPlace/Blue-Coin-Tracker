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

    @Query("SELECT * from level_table")
    suspend fun getLevels(): List<Level>

    @Query("SELECT percentDone from level_table ORDER BY levelId ASC")
    suspend fun getLevelPercentages(): List<Int>

    @Query("SELECT * from level_table WHERE levelId = :id")
    suspend fun getLevelById(id: Int): Level

    @Query("SELECT * from coin_table WHERE coinId = :key")
    suspend fun getCoinById(key: Long): BlueCoin

    @Query("SELECT * FROM coin_table WHERE myLevelID = :key ORDER BY numInLevel ASC")
    suspend fun getAllCoinsInLevelId(key: Int): List<BlueCoin>

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