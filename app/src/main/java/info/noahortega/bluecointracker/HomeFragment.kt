package info.noahortega.bluecointracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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

        return binding.root
    }

    private var mContext: Context? = null

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    fun levelClicked(code: LevelCode) {

        Toast.makeText(this.getActivity(), code.toString(), Toast.LENGTH_SHORT).show() //TODO: remove

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
    }
}