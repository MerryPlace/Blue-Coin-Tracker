package info.noahortega.bluecointracker.BCDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import info.noahortega.bluecointracker.R
import info.noahortega.bluecointracker.SharedViewModel
import kotlinx.android.synthetic.main.fragment_image_zoom.*


class ImageZoomFragment : Fragment() {

    private val model: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_zoom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        zoomImage.setImageResource(model.imageID)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}