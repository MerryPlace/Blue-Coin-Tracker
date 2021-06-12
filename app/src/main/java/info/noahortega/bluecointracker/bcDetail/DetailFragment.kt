package info.noahortega.bluecointracker.bcDetail

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import info.noahortega.bluecointracker.R
import info.noahortega.bluecointracker.SharedViewModel
import info.noahortega.bluecointracker.database.BlueCoin
import info.noahortega.bluecointracker.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val model: SharedViewModel by activityViewModels()

    private lateinit var myCoin: BlueCoin

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            myCoin = model.getSelectedCoin()!!
        }
        catch (e: NullPointerException) { //
            println("$e: detailFrag couldn't load coin from view model")
            this.findNavController().navigate(R.id.detail_panic_home)
            Toast.makeText(requireContext(), getString(R.string.panic_to_home), Toast.LENGTH_SHORT).show()
            return
        }

        populateView()
    }

    private fun populateView() {
        //set title
        activity?.title = requireContext().resources.getString(
            resources.getIdentifier(myCoin.shortTitle, "string", context?.packageName)
        )

        //check pref and set checkbox accordingly
        val sharedPref = activity?.getSharedPreferences(getString(R.string.shared_preferences_identifier), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            if(sharedPref.getBoolean(getString(R.string.preference_key_use_coin_checkbox), false)) {
                binding.coinCheckbox.buttonDrawable = ContextCompat.getDrawable(requireActivity(), R.drawable.btn_coin)
            }
        }

        //load top media resource and set click listener to navigate to zoom
        val imageID:Int = resources.getIdentifier(
            myCoin.imageAddress,
            "drawable",
            requireContext().packageName
        )
        binding.topMedia.setImageResource(imageID)
        binding.topMedia.setOnClickListener { model.navToImageZoom(findNavController(), imageID) }


        //load description text resource with html formatting and set it
        val description: String = getString(
            resources.getIdentifier(
                myCoin.description,
                "string",
                requireContext().packageName
            )
        )
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            //use depreciated html parse
            binding.descriptionText.text = Html.fromHtml(description)
        }
        else {
            val styledText: Spanned = Html.fromHtml(description, FROM_HTML_MODE_LEGACY)
            binding.descriptionText.text = styledText
        }


        //load youtube URL and create intent to open it when clicked
        val youtubeURL = getString(resources.getIdentifier(
            myCoin.youtubeLink,
            "string",
            requireContext().packageName)
        )

        val youtubeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeURL))
        binding.youtubeClickArea.setOnClickListener{
            try {startActivity(youtubeIntent) }
            catch (e: ActivityNotFoundException) {
                println("Error: bad URL $e")
                Toast.makeText(requireContext(), "Error: could not find video", Toast.LENGTH_SHORT).show()
            }
        }


        //load guide URL and create intent to open it when either credit text area is clicked
        val guideURL = resources.getString(
            resources.getIdentifier(
                model.getSelectedCoinLevel()!!.guideAddress,
                "string",
                requireContext().packageName
            )
        )
        binding.creditFullText.text = guideURL

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(guideURL))
        binding.creditTopText.setOnClickListener {
            try {startActivity(browserIntent) }
            catch (e: ActivityNotFoundException) {
                println("Error: bad URL $e")
                Toast.makeText(requireContext(), "Error: could not open URL", Toast.LENGTH_SHORT).show()
            }
        }
        binding.creditFullText.setOnClickListener {
            try {startActivity(browserIntent) }
            catch (e: ActivityNotFoundException) {
                println("Error: bad URL $e")
                Toast.makeText(requireContext(), "Error: could not open URL", Toast.LENGTH_SHORT).show()
            }
        }


        //checkbox setup
        binding.coinCheckbox.isChecked = myCoin.checked

        binding.checkBoxContainer.setOnClickListener {
            binding.coinCheckbox.performClick()
            checkClicked()
        }
    }


    private fun checkClicked() {
        //in database
        model.updateCoinCheck(myCoin.coinId, binding.coinCheckbox.isChecked)
        //in model
        model.editFetchedCoin(model.getFetchedCoinIndex(), binding.coinCheckbox.isChecked)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}