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

        binding.DPImage.setOnClickListener{levelClicked("DPImage")}
        binding.BHImage.setOnClickListener{levelClicked("BHImage")}
        binding.RHImage.setOnClickListener{levelClicked("RHImage")}
        binding.GBImage.setOnClickListener{levelClicked("GBImage")}
        binding.NBImage.setOnClickListener{levelClicked("NBImage")}
        binding.PPImage.setOnClickListener{levelClicked("PPImage")}
        binding.SBImage.setOnClickListener{levelClicked("SBImage")}
        binding.PVImage.setOnClickListener{levelClicked("PVImage")}
        binding.CMImage.setOnClickListener{levelClicked("CMImage")}

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



    fun levelClicked(id: String) {

        Toast.makeText(this.getActivity(), id, Toast.LENGTH_SHORT).show() //TODO: remove

//        when (id) {
//            binding.DPImage.id -> {
//            }
//            R.id.button2 -> {
//            }
//            R.id.button3 -> {
//            }
//        }
    }
}