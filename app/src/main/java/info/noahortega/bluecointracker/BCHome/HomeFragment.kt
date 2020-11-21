package info.noahortega.bluecointracker.BCHome

import android.annotation.SuppressLint
import android.graphics.Color.parseColor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import info.noahortega.bluecointracker.R
import info.noahortega.bluecointracker.SharedViewModel
import info.noahortega.bluecointracker.database.Level
import info.noahortega.bluecointracker.databinding.FragmentHomeBinding
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size


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

        activity?.title = getString(R.string.app_name)
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

    private var totalPercentDone : Int = -1
    @SuppressLint("SetTextI18n")
    private fun refreshPercentages(levels: List<Level>?) {
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

            totalPercentDone = ((totalDone) / 240.0).toInt()
            binding.totalCompletionText.text = getString(
                R.string.home_total_percentage_done,
                totalPercentDone
            )
            binding.totalProgressBar.progress = totalPercentDone

            binding.viewKonfetti.post(Runnable {
                if(totalPercentDone == 100) {
                    binding.totalCompletionText.text = getString(R.string.completion_text)
                    confettiTime()
                }
                else {
                    binding.viewKonfetti.reset()
                }
            })

        }
    }

    private fun confettiTime() {
        binding.viewKonfetti.build()
            .setPosition(-100f, binding.viewKonfetti.width + 100f, -50f, -50f)
            .setDirection(0.0, 359.0)
            .addShapes(Shape.Circle)
            .addColors(
                parseColor("#3e5fd5"),
                parseColor("#4d53c7"),
                parseColor("#4770EC"),
                parseColor("#22268c")
            )
            .addSizes(Size(20), Size(20, 3f))
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(2500L)
            .streamFor(50, 4000L)
    }
}



enum class LevelCode(val code: Int) {
    na(0),
    dp(1), bh(2), rh(3),
    gb(4), nb(5), pp(6),
    sb(7), pv(8), cm(9)
}