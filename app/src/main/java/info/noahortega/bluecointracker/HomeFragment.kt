package info.noahortega.bluecointracker

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import info.noahortega.bluecointracker.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)



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
        val totalPercentDone = Data.calcTotalPercComplete();
        binding.totalCompletionText.text = "$totalPercentDone% to Completion"
        binding.totalProgressBar.progress = totalPercentDone
    }



    fun levelClicked(code: LevelCode) {
        Data.levelSelected = code;
        this.findNavController().navigate(R.id.action_homeFragment_to_listFragment)

    }
}