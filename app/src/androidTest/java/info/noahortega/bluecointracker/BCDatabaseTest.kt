package info.noahortega.bluecointracker

import androidx.room.Room
import info.noahortega.bluecointracker.database.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class BCDatabaseTest {

    private lateinit var coinDao: CoinDatabaseDao
    private lateinit var db: CoinDatabase

    @Before
    fun createDb() {
        println("starting database")
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, CoinDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        coinDao = db.coinDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetLevel() {
        val level = Level()
        level.nickname = "GB"
        coinDao.insertLevel(level)
        val levelFromDB = coinDao.getLevelByNickname("GB")
        assertEquals(levelFromDB?.nickname, level.nickname)
    }
}