package info.noahortega.bluecointracker

import android.provider.ContactsContract

object Data {
    //state
    var levelSelected: Int = 0;

    var levels = arrayOf<Level>(
        Level("Delfino Plaza","DP", 20),
        Level("Bianco Hills", "BH", 30),
        Level("Ricco Harbor","RH", 30),
        Level("Gelato Beach","GB", 30),
        Level("Noki Bay","NB", 30),
        Level("Pinna Park","PP", 30),
        Level("Sirena Beach","SB", 30),
        Level("Pianta Village","PV", 30),
        Level("Corona Mountain","CM", 10))
    init {
        for (n in levels.indices) {
            populateLevelCoins(levels[n])
        }
    }

    private fun populateLevelCoins(level: Level) {
        val bCoins = arrayOfNulls<BlueCoin>(level.bCoinCount)
        for (n in bCoins.indices) {
                bCoins[n] = BlueCoin(false, "test description", null)
            }
        level.blueCoins = bCoins.filterNotNull().toTypedArray()
    }

    fun printData() {
        for (level in levels) {
            println(level.title)
//            for(n in 0 until level.bCoinCount) {
//                println("----"+ level.nickname + n + " " + level.blueCoins[n].checked)
//            }
        }
    }

}

data class Level(val title: String, val nickname: String?, val bCoinCount: Int) {
    var blueCoins = arrayOf<BlueCoin>()
}

data class BlueCoin(var checked: Boolean, val description: String?, val youtubeLink: String?, ) {
    var conditions = arrayOf<Condition>()
}
//TODO: pictures

data class Condition( val name: String) {
}
//TODO: icon

enum class LevelCode(val code: Int){
    dp(1),    bh(2),    rh(3),
    gb(4),    nb(5),    pp(6),
    sb(7),    pv(8),    cm(9)

}

