package info.noahortega.bluecointracker

import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment

class CreditsDialogue : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Credits")
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
                .setNeutralButton("Close") { _, _ -> }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}