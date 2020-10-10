package info.noahortega.bluecointracker.BCHome

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import info.noahortega.bluecointracker.Data
import info.noahortega.bluecointracker.LevelCode
import info.noahortega.bluecointracker.R
import info.noahortega.bluecointracker.database.*
import info.noahortega.bluecointracker.databinding.FragmentHomeBinding
import kotlinx.coroutines.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var database: CoinDatabaseDao
    private var viewJob = Job()
    private val myScope = CoroutineScope(Dispatchers.Main + viewJob)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        database = CoinDatabase.getInstance(activity as Context).coinDao

        myScope.launch {
            populateDatabase()
            refreshPercentages()
        }



        binding.DPImage.setOnClickListener { levelClicked(LevelCode.dp.code) }
        binding.BHImage.setOnClickListener { levelClicked(LevelCode.bh.code) }
        binding.RHImage.setOnClickListener { levelClicked(LevelCode.rh.code) }
        binding.GBImage.setOnClickListener { levelClicked(LevelCode.gb.code) }
        binding.NBImage.setOnClickListener { levelClicked(LevelCode.nb.code) }
        binding.PPImage.setOnClickListener { levelClicked(LevelCode.pp.code) }
        binding.SBImage.setOnClickListener { levelClicked(LevelCode.sb.code) }
        binding.PVImage.setOnClickListener { levelClicked(LevelCode.pv.code) }
        binding.CMImage.setOnClickListener { levelClicked(LevelCode.cm.code) }

        return binding.root
    }

    private fun levelClicked(code: Int) {
        val navController = this.findNavController()

        myScope.launch {
            Data.levelSelectedId = code
            Data.queriedCoins = database.getAllCoinsInLevelId(code)
            navController.navigate(R.id.action_homeFragment_to_listFragment)
        }
    }

    private suspend fun getLevels(): List<Level> {
        var levels = database.getLevels()
        return levels
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //TODO: flashing of UI
    @SuppressLint("SetTextI18n")
    suspend fun refreshPercentages() {

        updateLevelCompletion(LevelCode.dp.code)
        updateLevelCompletion(LevelCode.bh.code)
        updateLevelCompletion(LevelCode.rh.code)
        updateLevelCompletion(LevelCode.gb.code)
        updateLevelCompletion(LevelCode.nb.code)
        updateLevelCompletion(LevelCode.pp.code)
        updateLevelCompletion(LevelCode.sb.code)
        updateLevelCompletion(LevelCode.pv.code)
        updateLevelCompletion(LevelCode.cm.code)

        val levelPercentages = database.getLevelPercentages()

        binding.DPCompletedText.text = "${levelPercentages[0].toString()}%"
        binding.BHCompletedText.text = "${levelPercentages[1].toString()}%"
        binding.RHCompletedText.text = "${levelPercentages[2].toString()}%"
        binding.GBCompletedText.text = "${levelPercentages[3].toString()}%"
        binding.NBCompletedText.text = "${levelPercentages[4].toString()}%"
        binding.PPCompletedText.text = "${levelPercentages[5].toString()}%"
        binding.SBCompletedText.text = "${levelPercentages[6].toString()}%"
        binding.PVCompletedText.text = "${levelPercentages[7].toString()}%"
        binding.CMCompletedText.text = "${levelPercentages[8].toString()}%"

        //val totalPercentDone = calcTotalCompletion()
        //binding.totalCompletionText.text = "$totalPercentDone% to Completion"
        //binding.totalProgressBar.progress = totalPercentDone
    }

    suspend fun updateLevelCompletion(levelId: Int) {
        var completed = 0.0

        var checkList: List<Boolean> = database.getLevelCheckedList(levelId)
        var level: Level = database.getLevelById(levelId)

        for (checked in checkList) {
            if (checked) {
                completed++
            }
        }

        val percent = (((completed) / checkList.size) * 100).toInt()

        level.percentDone = percent
        database.updateLevel(level)
    }

    //use when there is no database
    private suspend fun populateDatabase() {
        if (database.countCoins() < 240) {
            database.insertLevel(Level(1, "Delfino Plaza", "dp", 20, 0, "home_dp"))
            database.insertLevel(Level(2, "Bianco Hills", "bh", 30, 0, "home_bh"))
            database.insertLevel(Level(3, "Ricco Harbor", "rh", 30, 0, "home_rh"))
            database.insertLevel(Level(4, "Gelato Beach", "gb", 30, 0, "home_gb"))
            database.insertLevel(Level(5, "Noki Bay", "nb", 30, 0, "home_nb"))
            database.insertLevel(Level(6, "Pinna Park", "pp", 30, 0, "home_pp"))
            database.insertLevel(Level(7, "Sirena Beach", "sb", 30, 0, "home_sb"))
            database.insertLevel(Level(8, "Pianta Village", "pv", 30, 0, "home_pv"))
            database.insertLevel(Level(9, "Corona Mountain", "cm", 10, 0, "home_cm"))

            val levels: List<Level> = getLevels()
            var addition = "0"
            for (level in levels) {
                val nickname = level.nickname
                //println(nickname)
                for (n in 1..level.bCoinCount) {
                    if (n == 1) {
                        addition = "0"
                    } else if (n == 10) {
                        addition = ""
                    }

                    database.insertCoin(
                        BlueCoin(
                            myLevelId = level.levelId, numInLevel = n, checked = false,
                            imageAddress = "coin_" + nickname + "_" + addition + n,
                            youtubeLink = "coin_video_" + nickname + "_" + addition + n,
                            shortTitle = "coin_title_" + nickname + "_" + addition + n,
                            description = "coin_descr_" + nickname + "_" + addition + n,
                        )
                    )
                }
            }
            println("DATABASE POPULATED~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
        }
    }
}