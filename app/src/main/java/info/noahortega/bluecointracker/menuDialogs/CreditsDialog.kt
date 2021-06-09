package info.noahortega.bluecointracker.menuDialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import info.noahortega.bluecointracker.R

class CreditsDialog : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(getString(R.string.menu_item_credits_title))
                .setItems(R.array.app_credits) { _, which ->
                    val array = resources.getStringArray(R.array.app_credits_links)

                    val creditLinkIntent = Intent(Intent.ACTION_VIEW, Uri.parse(array[which]))

                    try {
                        startActivity(creditLinkIntent)
                    } catch (e: ActivityNotFoundException) {
                        println("Error: bad URL $e")
                        Toast.makeText(
                            requireContext(),
                            "Error: could not open link",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .setNeutralButton(getString(R.string.dialog_close)) { _, _ -> }

            builder.create()
        } ?: throw IllegalStateException("Credits Dialog: Activity cannot be null")
    }
}