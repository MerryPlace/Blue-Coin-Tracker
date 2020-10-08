package info.noahortega.bluecointracker.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


//TODO: update exportSchema to TRUE before release
@Database(
    entities = [Level::class, BlueCoin::class, Condition::class, CoinCondCrossRef::class],
    version = 1,
    exportSchema = true
)
abstract class CoinDatabase: RoomDatabase() {

    abstract val coinDao: CoinDatabaseDao

    companion object{

        @Volatile
        private var INSTANCE: CoinDatabase? = null

        fun getInstance(context: Context) : CoinDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CoinDatabase::class.java,
                        "blue_coin_database"
                    )
                        .fallbackToDestructiveMigration() //TODO: bad. implement a migration methodology for upgrading between versions.
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
