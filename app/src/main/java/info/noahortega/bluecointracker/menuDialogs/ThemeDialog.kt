package info.noahortega.bluecointracker.menuDialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDialogFragment
import info.noahortega.bluecointracker.R

class ThemeDialog : AppCompatDialogFragment() {
    private val selectionToThemeCode = mapOf(
        0 to AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
        1 to AppCompatDelegate.MODE_NIGHT_NO,
        2 to AppCompatDelegate.MODE_NIGHT_YES,
    )

    private fun getSelectionFromThemeCode(chosenCode: Int): Int {
        for ((selection,themeCode) in selectionToThemeCode) {
            if(themeCode == chosenCode) {
                return selection
            }
        }
        return 0;
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            //TODO: maybe create a failsafe if a sharedpreference can't be created for some reason
            val sharedPref = requireActivity().getSharedPreferences(getString(R.string.shared_preferences_identifier),
                Context.MODE_PRIVATE)
            val prefNOTFOUND = -64 //default value if pref not found, isn't a dark mode code
            var selection = getSelectionFromThemeCode(sharedPref.getInt(getString(R.string.preference_key_theme), prefNOTFOUND))
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(getString(R.string.menu_item_theme_title))
                .setSingleChoiceItems(R.array.theme_options, selection) { _, which -> selection = which }
                .setPositiveButton(getString(R.string.dialog_apply))
                { _, _ ->
                    val prefEditor: SharedPreferences.Editor = sharedPref.edit()
                    prefEditor.putInt(getString(R.string.preference_key_theme), selectionToThemeCode[selection]!!)
                    prefEditor.apply()
                    dialog?.dismiss()
                    AppCompatDelegate.setDefaultNightMode(selectionToThemeCode[selection]!!)
                }
                .setNegativeButton(getString(R.string.dialog_cancel), null)

            builder.create()
        } ?: throw IllegalStateException("Theme Dialog: Activity cannot be null")
    }
}

