package info.noahortega.bluecointracker.BCDetail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import info.noahortega.bluecointracker.R

import info.noahortega.bluecointracker.SharedViewModel
import info.noahortega.bluecointracker.database.BlueCoin
import info.noahortega.bluecointracker.databinding.FragmentDetailBinding
import kotlinx.android.synthetic.main.activity_main.*


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

        activity?.title = requireContext().resources.getString(
            resources.getIdentifier(coin.shortTitle, "string", context?.packageName)
        )


        val imageID:Int = resources.getIdentifier(coin.imageAddress, "drawable", requireContext().packageName)
        binding.topMedia.setImageResource(imageID)

        binding.topMedia.setOnClickListener { model.navToImageZoom(findNavController(), imageID) }


        val text: String = getString(resources.getIdentifier(coin.description,"string", requireContext().packageName))

        if (Build.VERSION.SDK_INT < 24) {
            binding.descriptionText.text = Html.fromHtml(text)
        }
        else {
            val styledText: Spanned = Html.fromHtml(text, FROM_HTML_MODE_LEGACY)
            binding.descriptionText.text = styledText
        }

        binding.checkBox.isChecked = coin.checked

        binding.checkBox.setOnClickListener { checkClicked() }

        binding.creditFullText.text = resources.getString(
            resources.getIdentifier(model.getSelectedCoinLevel()!!.guideAddress,"string", requireContext().packageName)
        )
    }

    private fun checkClicked() {
        //in database
        model.updateCoin(coin.coinId,binding.checkBox.isChecked, true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}