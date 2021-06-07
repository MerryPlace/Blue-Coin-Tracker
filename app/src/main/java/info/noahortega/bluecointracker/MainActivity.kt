package info.noahortega.bluecointracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO



class MainActivity : AppCompatActivity() {

    private lateinit var myMenu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if(AppCompatDelegate.getDefaultNightMode() == MODE_NIGHT_UNSPECIFIED) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_items, menu)
        myMenu = menu!!
        setThemeText();
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.credits_item -> creditsDialog()
            R.id.themes_item -> themeTapped()
            else -> Toast.makeText(this, "Error: invalid menu item", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun creditsDialog() {
        val dialog = CreditsDialogue()
        dialog.show(supportFragmentManager, "wazoo")
    }

    private fun themeTapped() {
        when(val theme = AppCompatDelegate.getDefaultNightMode()) {
            MODE_NIGHT_FOLLOW_SYSTEM -> {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }
            MODE_NIGHT_NO -> {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            }
            MODE_NIGHT_YES -> {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
            }
            else -> {
                Toast.makeText(this, "Error: invalid theme state ($theme)", Toast.LENGTH_SHORT).show()
            }
        }
        setThemeText()
    }

    private fun setThemeText() {
        val themeItem: MenuItem = myMenu.findItem(R.id.themes_item)

        when(AppCompatDelegate.getDefaultNightMode()) {
            MODE_NIGHT_FOLLOW_SYSTEM -> {
                themeItem.title = "Theme: System Default"
            }
            MODE_NIGHT_NO -> {
                themeItem.title = "Theme: Light"
            }
            MODE_NIGHT_YES -> {
                themeItem.title = "Theme: Dark"
            }
        }
    }
}
