package info.noahortega.bluecointracker.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.Junction

@Entity(tableName = "level_table")
data class Level(
    @PrimaryKey()
    var levelId: Int = 0,
    @ColumnInfo
    var title: String = "NA",
    @ColumnInfo
    var nickname: String = "NA",
    @ColumnInfo
    var bCoinCount: Int = -1,
    @ColumnInfo
    var percentDone: Int = 0,
    @ColumnInfo
    var guideAddress: String = "home_na",
)

@Entity(tableName = "coin_table")
data class BlueCoin(
    @PrimaryKey(autoGenerate = true)
    var coinId: Long = 0L,
    @ColumnInfo
    var myLevelId: Int = 0,
    @ColumnInfo
    var numInLevel: Int = -1,
    @ColumnInfo
    var checked: Boolean = false,
    @ColumnInfo
    var imageAddress: String = "coin_na_00",
    @ColumnInfo
    var youtubeLink: String = "NA",
    @ColumnInfo
    var shortTitle: String = "NA",
    @ColumnInfo
    var description: String = "NA",
)

//one-to-many
data class LevelWithCoins(
    @Embedded val level: Level,
    @Relation(
        parentColumn = "levelId",
        entityColumn = "myLevelId"
    )
    val blueCoins: List<BlueCoin>
)

// ENTITIES BELOW ARE FOR FUTURE UPDATES (UNIMPLEMENTED)
@Entity(tableName = "condition_table")
data class Condition(
    @PrimaryKey(autoGenerate = true)
    var condId: Long = 0L,
    @ColumnInfo
    var name: String = "NA",
    @ColumnInfo
    var iconAddress: String = "cond_na",
)

@Entity(primaryKeys = ["coinId", "condId"])
data class CoinCondCrossRef(
    val coinId: Long,
    val condId: Long,
)

//many-to-many
data class CoinWithConditions(
    @Embedded val blueCoin:BlueCoin,
    @Relation(
        parentColumn = "coinId",
        entityColumn = "condId",
        associateBy = Junction(CoinCondCrossRef::class)
    )
    val conditions: List<Condition>
)

