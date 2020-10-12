package info.noahortega.bluecointracker.BCDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

import info.noahortega.bluecointracker.SharedViewModel
import info.noahortega.bluecointracker.database.BlueCoin
import info.noahortega.bluecointracker.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val model: SharedViewModel by activityViewModels()

    lateinit var coin: BlueCoin

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coin = model.getSelectedCoin()!!

        binding.topMedia.setImageResource(
            resources.getIdentifier(coin.imageAddress, "drawable", requireContext().packageName)
        )

        binding.descriptionText.text = resources.getString(
            resources.getIdentifier(coin.description,"string", requireContext().packageName)
        )

        binding.checkBox.isChecked = coin.checked

        binding.checkBox.isEnabled = false //TODO: remove when checkClicked() is implemented
        binding.checkBox.setOnClickListener { checkClicked() }
    }

    private fun checkClicked() {
        //TODO: implement
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}