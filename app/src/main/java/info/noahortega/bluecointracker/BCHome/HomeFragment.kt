package info.noahortega.bluecointracker.BCHome

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import info.noahortega.bluecointracker.SharedViewModel
import info.noahortega.bluecointracker.database.Level
import info.noahortega.bluecointracker.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

        model.liveLevels.observe(viewLifecycleOwner, { newLevels ->
            refreshPercentages(newLevels)
        })

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

    private fun levelClicked(levelId: Int) {
        model.navToListWithLevel(this.findNavController(), levelId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    fun refreshPercentages(levels: List<Level>?) {
        if (levels != null) {
            binding.DPCompletedText.text = "${levels[0].percentDone}%"
            binding.BHCompletedText.text = "${levels[1].percentDone}%"
            binding.RHCompletedText.text = "${levels[2].percentDone}%"
            binding.GBCompletedText.text = "${levels[3].percentDone}%"
            binding.NBCompletedText.text = "${levels[4].percentDone}%"
            binding.PPCompletedText.text = "${levels[5].percentDone}%"
            binding.SBCompletedText.text = "${levels[6].percentDone}%"
            binding.PVCompletedText.text = "${levels[7].percentDone}%"
            binding.CMCompletedText.text = "${levels[8].percentDone}%"

            var totalDone = 0
            for (level in 0..8) {
                totalDone += (levels[level].bCoinCount * levels[level].percentDone)
            }

            val totalPercentDone = ((totalDone) / 240.0).toInt()
            binding.totalCompletionText.text = "You are $totalPercentDone% to completion"
            binding.totalProgressBar.progress = totalPercentDone
        }
    }
}

enum class LevelCode(val code: Int) {
    na(0),
    dp(1), bh(2), rh(3),
    gb(4), nb(5), pp(6),
    sb(7), pv(8), cm(9)
}