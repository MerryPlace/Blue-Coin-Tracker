package info.noahortega.bluecointracker

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import info.noahortega.bluecointracker.menuDialogs.CheckboxDialog
import info.noahortega.bluecointracker.menuDialogs.CreditsDialog
import info.noahortega.bluecointracker.menuDialogs.ThemeDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: maybe create a failsafe if a theme sharedpreference can't be created for some reason
        val sharedPref = getSharedPreferences(getString(R.string.shared_preferences_identifier),Context.MODE_PRIVATE)

        //initialize theme pref and set
        val themePrefNOTFOUND = -64 //default value if pref not found, isn't a dark mode code
        var themePref = sharedPref.getInt(getString(R.string.preference_key_theme), themePrefNOTFOUND)

        if(themePref == themePrefNOTFOUND) {
            val prefEditor: SharedPreferences.Editor = sharedPref.edit()
            prefEditor.putInt(getString(R.string.preference_key_theme), AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            prefEditor.apply();

            themePref = sharedPref.getInt(getString(R.string.preference_key_theme), themePrefNOTFOUND)
        }
        AppCompatDelegate.setDefaultNightMode(themePref)

        //initialize checkbox pref
        if(!sharedPref.contains(getString(R.string.preference_key_use_coin_checkbox))) {
            val prefEditor: SharedPreferences.Editor = sharedPref.edit()
            prefEditor.putBoolean(getString(R.string.preference_key_use_coin_checkbox), false)
            //TODO: should be set to true by default
            prefEditor.apply();
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_items, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.credits_item -> creditsTapped()
            R.id.themes_item -> themeTapped()
            R.id.checkbox_item -> checkboxTapped()
            else -> Toast.makeText(this, "Error: invalid menu item", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun creditsTapped() {
        val dialog = CreditsDialog()
        dialog.show(supportFragmentManager, "credits")
    }

    private fun themeTapped() {
        val dialog = ThemeDialog()
        dialog.show(supportFragmentManager, "theme")
    }

    private fun checkboxTapped() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Toast.makeText(this, getString(R.string.older_version_checkbox_warning), Toast.LENGTH_LONG).show()
        }
        else {
            val dialog = CheckboxDialog()
            dialog.show(supportFragmentManager, "theme")
        }
    }
}
