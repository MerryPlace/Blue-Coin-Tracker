package info.noahortega.bluecointracker

import info.noahortega.bluecointracker.database.BlueCoin


object Data {
    //state
    var levelSelectedId: Int = LevelCode.na.code;
    var queriedCoins: List<BlueCoin>? = null

}


enum class LevelCode(val code: Int) {
    na(0),
    dp(1), bh(2), rh(3),
    gb(4), nb(5), pp(6),
    sb(7), pv(8), cm(9)

}

//    var levels = arrayOf<Level>(
//        info.noahortega.bluecointracker.Level("Delfino Plaza", "DP", 20),
//        info.noahortega.bluecointracker.Level("Bianco Hills", "BH", 30),
//        info.noahortega.bluecointracker.Level("Ricco Harbor", "RH", 30),
//        info.noahortega.bluecointracker.Level("Gelato Beach", "GB", 30),
//        info.noahortega.bluecointracker.Level("Noki Bay", "NB", 30),
//        info.noahortega.bluecointracker.Level("Pinna Park", "PP", 30),
//        info.noahortega.bluecointracker.Level("Sirena Beach", "SB", 30),
//        info.noahortega.bluecointracker.Level("Pianta Village", "PV", 30),
//        info.noahortega.bluecointracker.Level("Corona Mountain", "CM", 10)
//    )
//    init {
//        for (n in levels.indices) {
//            populateLevelCoins(levels[n])
//        }
//    }
//
//    private fun populateLevelCoins(level: Level) {
//        val bCoins = arrayOfNulls<BlueCoin>(level.bCoinCount)
//        for (n in bCoins.indices) {
//                bCoins[n] = BlueCoin(false, "description coming soon", null)
//            }
//        level.blueCoins = bCoins.filterNotNull().toTypedArray()
//    }
//
//    fun printData() {
//        for (level in levels) {
//            println(level.title)
////            for(n in 0 until level.bCoinCount) {
////                println("----"+ level.nickname + n + " " + level.blueCoins[n].checked)
////            }
//        }
//    }
//}
//
//data class Level(val title: String, val nickname: String?, val bCoinCount: Int) {
//    var blueCoins = arrayOf<BlueCoin>()
//}
//
//data class BlueCoin(var checked: Boolean, val description: String?, val youtubeLink: String?, ) {
//    var conditions = arrayOf<Condition>()
//}
////TODO: pictures
//
//data class Condition( val name: String) {
//}
////TODO: icon











//        when (code) {
//            LevelCode.dp -> {
//            }
//            LevelCode.bh -> {
//            }
//            LevelCode.rh -> {
//            }
//            LevelCode.gb -> {
//            }
//            LevelCode.nb -> {
//            }
//            LevelCode.pp -> {
//            }
//            LevelCode.sb -> {
//            }
//            LevelCode.pv -> {
//            }
//            LevelCode.cm -> {
//            }
//        }

/*
binding.DPCompletedText
binding.BHCompletedText
binding.RHCompletedText
binding.GBCompletedText
binding.NBCompletedText
binding.PPCompletedText
binding.SBCompletedText
binding.PVCompletedText
binding.CMCompletedText
 */

