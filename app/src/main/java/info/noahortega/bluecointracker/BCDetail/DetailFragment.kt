package info.noahortega.bluecointracker.BCDetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import info.noahortega.bluecointracker.Data
import info.noahortega.bluecointracker.SharedViewModel
import info.noahortega.bluecointracker.database.BlueCoin
import info.noahortega.bluecointracker.database.CoinDatabase
import info.noahortega.bluecointracker.database.CoinDatabaseDao
import info.noahortega.bluecointracker.databinding.FragmentDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var database: CoinDatabaseDao
    private var viewJob = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewJob)

    private val model: SharedViewModel by activityViewModels()

    var coin: BlueCoin = Data.coinSelected!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        database = CoinDatabase.getInstance(activity as Context).coinDao

        binding.topMedia.setImageResource(
            resources.getIdentifier(coin.imageAddress, "drawable", requireContext().packageName))

        binding.descriptionText.text = resources.getString(
            resources.getIdentifier(coin.description,"string", requireContext().packageName));

        binding.checkBox.isChecked = coin.checked

        binding.checkBox.isEnabled = false //TODO: remove when checkClicked() is implemented
        binding.checkBox.setOnClickListener { checkClicked() }
        return binding.root
    }

    private fun checkClicked() {
        //TODO: implement
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}