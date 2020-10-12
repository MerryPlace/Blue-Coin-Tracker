package info.noahortega.bluecointracker.BCHome

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.noahortega.bluecointracker.Data
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import info.noahortega.bluecointracker.LevelCode
import info.noahortega.bluecointracker.R
import info.noahortega.bluecointracker.SharedViewModel
import info.noahortega.bluecointracker.database.CoinDatabase
import info.noahortega.bluecointracker.database.CoinDatabaseDao
import info.noahortega.bluecointracker.database.Level
import info.noahortega.bluecointracker.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var viewJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewJob)

    private val model: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val levelObserver: Observer<List<Level>> =
            Observer { newLevels -> // Update the UI, in this case, a TextView.
                refreshPercentages(newLevels) }

        model.liveLevels.observe(viewLifecycleOwner, levelObserver)


        uiScope.launch {
//            refreshPercentages()
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
    }

    private fun levelClicked(code: Int) {
        val navController = this.findNavController()
        uiScope.launch {
//            Data.levelSelectedId = code
//            Data.queriedCoins = model.liveQueriedCoins
            navController.navigate(R.id.action_homeFragment_to_listFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
     fun refreshPercentages(levels : List<Level>?) {
        if(levels != null) {
            binding.DPCompletedText.text = "${levels[0].percentDone.toString()}%"
            binding.BHCompletedText.text = "${levels[1].percentDone.toString()}%"
            binding.RHCompletedText.text = "${levels[2].percentDone.toString()}%"
            binding.GBCompletedText.text = "${levels[3].percentDone.toString()}%"
            binding.NBCompletedText.text = "${levels[4].percentDone.toString()}%"
            binding.PPCompletedText.text = "${levels[5].percentDone.toString()}%"
            binding.SBCompletedText.text = "${levels[6].percentDone.toString()}%"
            binding.PVCompletedText.text = "${levels[7].percentDone.toString()}%"
            binding.CMCompletedText.text = "${levels[8].percentDone.toString()}%"

            var totalDone = 0
            for (level in 0..8) {
                totalDone += ( levels[level].bCoinCount * levels[level].percentDone)
            }

            val totalPercentDone = ((totalDone)/240.0).toInt()

            binding.totalCompletionText.text = "You are $totalPercentDone% to completion"
            binding.totalProgressBar.progress = totalPercentDone



        }
    }
}