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
    fun getLiveLevels(): LiveData<List<Level>>

    @Query("SELECT * from level_table")
    fun getLevels(): List<Level>

    @Query("SELECT * from coin_table WHERE myLevelId = :levelId")
    fun getLiveCoins(levelId: Long): LiveData<List<BlueCoin>>

    @Query("SELECT * from level_table WHERE nickname = :nn")
    suspend fun getLevelByNickname(nn: String): Level

    @Query("SELECT * from coin_table WHERE coinId = :key")
    suspend fun getCoinById(key: Long): BlueCoin

    @Query("SELECT * FROM coin_table WHERE myLevelID = :key ORDER BY numInLevel ASC")
    suspend fun getAllCoinsInLevelId(key: Long): List<BlueCoin>

    @Transaction
    @Query("SELECT * FROM level_table WHERE nickname = :nickname")
    suspend fun getLevelWithCoins(nickname: String): List<LevelWithCoins>

    @Transaction
    @Query("SELECT * FROM coin_table")
    suspend fun getCoinsWithConditions(): List<CoinWithConditions>

    @Transaction //probably shouldn't use
    @Query("SELECT * FROM level_table WHERE nickname = :nickname")
    suspend fun getLevelsWithCoinsWithSongs(nickname: String): List<LevelWithCoinsAndCond>

}