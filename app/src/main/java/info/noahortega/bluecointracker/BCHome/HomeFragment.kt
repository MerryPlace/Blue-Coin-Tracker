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
    private val uiScope = CoroutineScope(Dispatchers.Main + viewJob)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        database = CoinDatabase.getInstance(activity as Context).coinDao

        uiScope.launch {
            refreshPercentages()
            binding.DPImage.setOnClickListener { levelClicked(LevelCode.dp.code) }
            binding.BHImage.setOnClickListener { levelClicked(LevelCode.bh.code) }
            binding.RHImage.setOnClickListener { levelClicked(LevelCode.rh.code) }
            binding.GBImage.setOnClickListener { levelClicked(LevelCode.gb.code) }
            binding.NBImage.setOnClickListener { levelClicked(LevelCode.nb.code) }
            binding.PPImage.setOnClickListener { levelClicked(LevelCode.pp.code) }
            binding.SBImage.setOnClickListener { levelClicked(LevelCode.sb.code) }
            binding.PVImage.setOnClickListener { levelClicked(LevelCode.pv.code) }
            binding.CMImage.setOnClickListener { levelClicked(LevelCode.cm.code) }
        }

        return binding.root
    }

    private fun levelClicked(code: Int) {
        val navController = this.findNavController()

        uiScope.launch {
            Data.levelSelectedId = code
            Data.queriedCoins = database.getAllCoinsInLevelId(code)
            navController.navigate(R.id.action_homeFragment_to_listFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    suspend fun refreshPercentages() {
        val levelPercentages = database.getOrderedLevelPercentages()

        binding.DPCompletedText.text = "${levelPercentages[0].toString()}%"
        binding.BHCompletedText.text = "${levelPercentages[1].toString()}%"
        binding.RHCompletedText.text = "${levelPercentages[2].toString()}%"
        binding.GBCompletedText.text = "${levelPercentages[3].toString()}%"
        binding.NBCompletedText.text = "${levelPercentages[4].toString()}%"
        binding.PPCompletedText.text = "${levelPercentages[5].toString()}%"
        binding.SBCompletedText.text = "${levelPercentages[6].toString()}%"
        binding.PVCompletedText.text = "${levelPercentages[7].toString()}%"
        binding.CMCompletedText.text = "${levelPercentages[8].toString()}%"

        val levelCoinCount = database.getOrderedLevelCoinCount()

        var totalDone = 0
        for (level in 0..8) {
            totalDone += (levelCoinCount[level] * levelPercentages[level])
        }

        val totalPercentDone = ((totalDone)/240.0).toInt()

        binding.totalCompletionText.text = "You are $totalPercentDone% to completion"
        binding.totalProgressBar.progress = totalPercentDone
    }
}