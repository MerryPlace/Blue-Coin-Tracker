package info.noahortega.bluecointracker


object Data {
    //state
    var levelSelected: LevelCode = LevelCode.cm;

    var levels = arrayOf<Level>(
        info.noahortega.bluecointracker.Level("Delfino Plaza", "DP", 20),
        info.noahortega.bluecointracker.Level("Bianco Hills", "BH", 30),
        info.noahortega.bluecointracker.Level("Ricco Harbor", "RH", 30),
        info.noahortega.bluecointracker.Level("Gelato Beach", "GB", 30),
        info.noahortega.bluecointracker.Level("Noki Bay", "NB", 30),
        info.noahortega.bluecointracker.Level("Pinna Park", "PP", 30),
        info.noahortega.bluecointracker.Level("Sirena Beach", "SB", 30),
        info.noahortega.bluecointracker.Level("Pianta Village", "PV", 30),
        info.noahortega.bluecointracker.Level("Corona Mountain", "CM", 10)
    )
    init {
        for (n in levels.indices) {
            populateLevelCoins(levels[n])
        }
    }

    private fun populateLevelCoins(level: Level) {
        val bCoins = arrayOfNulls<BlueCoin>(level.bCoinCount)
        for (n in bCoins.indices) {
                bCoins[n] = BlueCoin(false, "description coming soon", null)
            }
        level.blueCoins = bCoins.filterNotNull().toTypedArray()
    }

    public fun calcPercComplete(code: LevelCode): Int {

        var completed = 0.0
        var level: Level = levels[code.code]
        for (coin in level.blueCoins) {
            if(coin.checked) {
                completed++
            }
        }

        return (((completed)/level.bCoinCount)*100).toInt()
    }

    public fun calcTotalPercComplete(): Int {
        var completed = 0.0
        val totalCoins = 240
        for (level in levels) {
            for (coin in level.blueCoins) {
                if(coin.checked) {
                    completed++
                }
            }
        }

        return (((completed)/totalCoins)*100).toInt()
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
    dp(0),    bh(1),    rh(2),
    gb(3),    nb(4),    pp(5),
    sb(6),    pv(7),    cm(8)

}

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

