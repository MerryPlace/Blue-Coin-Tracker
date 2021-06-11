package info.noahortega.bluecointracker.menuDialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import info.noahortega.bluecointracker.R

class CheckboxDialog : AppCompatDialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val sharedPref = requireActivity().getSharedPreferences(getString(R.string.shared_preferences_identifier),
                Context.MODE_PRIVATE)
            var selection = if (sharedPref.getBoolean(getString(R.string.preference_key_use_coin_checkbox), true)) 0 else 1
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(getString(R.string.menu_item_checkbox_title))
                .setSingleChoiceItems(R.array.checkbox_options, selection) { _, which -> selection = which }
                .setPositiveButton(getString(R.string.dialog_apply))
                { _, _ ->
                    val prefEditor: SharedPreferences.Editor = sharedPref.edit()
                    prefEditor.putBoolean(getString(R.string.preference_key_use_coin_checkbox), (selection == 0))
                    prefEditor.apply()
                }
                .setNegativeButton(getString(R.string.dialog_cancel), null)

            builder.create()
        } ?: throw IllegalStateException("Checkbox Dialog: Activity cannot be null")
    }
}