package info.noahortega.bluecointracker

import info.noahortega.bluecointracker.database.BlueCoin

object Data {
    //state
    var levelSelectedId: Int = LevelCode.na.code
    var queriedCoins: List<BlueCoin>? = null
}

enum class LevelCode(val code: Int) {
    na(0),
    dp(1), bh(2), rh(3),
    gb(4), nb(5), pp(6),
    sb(7), pv(8), cm(9)

}

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

