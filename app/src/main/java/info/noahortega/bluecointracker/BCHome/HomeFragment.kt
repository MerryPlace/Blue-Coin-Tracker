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
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var database: CoinDatabaseDao
    private var viewJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewJob)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        database = CoinDatabase.getInstance(activity as Context).coinDao

        //initDatabase()


        binding.DPImage.setOnClickListener{levelClicked(LevelCode.dp)}
        binding.BHImage.setOnClickListener{levelClicked(LevelCode.bh)}
        binding.RHImage.setOnClickListener{levelClicked(LevelCode.rh)}
        binding.GBImage.setOnClickListener{levelClicked(LevelCode.gb)}
        binding.NBImage.setOnClickListener{levelClicked(LevelCode.nb)}
        binding.PPImage.setOnClickListener{levelClicked(LevelCode.pp)}
        binding.SBImage.setOnClickListener{levelClicked(LevelCode.sb)}
        binding.PVImage.setOnClickListener{levelClicked(LevelCode.pv)}
        binding.CMImage.setOnClickListener{levelClicked(LevelCode.cm)}

        return binding.root
    }

    private suspend fun getLevels() : List<Level> {
        return withContext(Dispatchers.IO) {
            var levels = database.getLevels()
            levels
        }
    }


    //use when there is no database
    private fun initDatabase() {
        uiScope.launch {
            database.insertLevel(Level(1, "Delfino Plaza", "dp", 20, 0,"home_dp"))
            database.insertLevel(Level(2, "Bianco Hills", "bh", 30, 0,"home_bh"))
            database.insertLevel(Level(3, "Ricco Harbor", "rh", 30, 0,"home_rh"))
            database.insertLevel(Level(4, "Gelato Beach", "gb", 30, 0,"home_gb"))
            database.insertLevel(Level(5, "Noki Bay", "nb", 30, 0,"home_nb"))
            database.insertLevel(Level(6, "Pinna Park", "pp", 30, 0,"home_pp"))
            database.insertLevel(Level(7, "Sirena Beach", "sb", 30, 0,"home_sb"))
            database.insertLevel(Level(8, "Pianta Village", "pv", 30, 0,"home_pv"))
            database.insertLevel(Level(9, "Corona Mountain", "cm", 10, 0,"home_cm"))

            val levels: List<Level> = getLevels()
            var addition = "0"
            for (level in levels) {
                val nickname = level.nickname
                println(nickname)
                for (n in 1..level.bCoinCount) {

                    if(n == 1) {
                        addition = "0"
                    }
                    else if(n == 10) {
                        addition = ""
                    }

                    database.insertCoin(
                        BlueCoin(
                            myLevelId = level.levelId,
                            numInLevel = n,
                            imageAddress = "coin_" + nickname + "_" + addition + n,
                            youtubeLink = "coin_video_"+ nickname + "_" + addition + n,
                            shortTitle = "coin_title_"+ nickname + "_" + addition + n,
                            description = "coin_descr_"+ nickname + "_" + addition + n,
                        )
                    )
                }
            }
        }
        println("init??~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    }

    override fun onStart() {
        super.onStart()
        refreshPercentages()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    fun refreshPercentages() {
        binding.DPCompletedText.text = Data.calcPercComplete(LevelCode.dp).toString() + "%"
        binding.BHCompletedText.text = Data.calcPercComplete(LevelCode.bh).toString() + "%"
        binding.RHCompletedText.text = Data.calcPercComplete(LevelCode.rh).toString() + "%"
        binding.GBCompletedText.text = Data.calcPercComplete(LevelCode.gb).toString() + "%"
        binding.NBCompletedText.text = Data.calcPercComplete(LevelCode.nb).toString() + "%"
        binding.PPCompletedText.text = Data.calcPercComplete(LevelCode.pp).toString() + "%"
        binding.SBCompletedText.text = Data.calcPercComplete(LevelCode.sb).toString() + "%"
        binding.PVCompletedText.text = Data.calcPercComplete(LevelCode.pv).toString() + "%"
        binding.CMCompletedText.text = Data.calcPercComplete(LevelCode.cm).toString() + "%"
        val totalPercentDone = Data.calcTotalPercComplete()
        binding.totalCompletionText.text = "$totalPercentDone% to Completion"
        binding.totalProgressBar.progress = totalPercentDone
    }

    fun levelClicked(code: LevelCode) {
        Data.levelSelected = code
        this.findNavController().navigate(R.id.action_homeFragment_to_listFragment)
    }
}