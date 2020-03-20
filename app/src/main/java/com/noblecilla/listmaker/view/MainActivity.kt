package com.noblecilla.listmaker.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import com.noblecilla.listmaker.R
import com.noblecilla.listmaker.databinding.ActivityMainBinding
import com.noblecilla.listmaker.view.setting.Mode
import com.noblecilla.listmaker.view.setting.Preferences
import com.noblecilla.listmaker.viewmodel.ListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val listViewModel: ListViewModel by viewModel()

    companion object {
        const val LIST_KEY = "list"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        when (Preferences.nightMode(this)) {
            Mode.LIGHT.ordinal -> menu?.findItem(R.id.night)?.isVisible = true
            Mode.NIGHT.ordinal -> menu?.findItem(R.id.light)?.isVisible = true
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.light -> switchToMode(AppCompatDelegate.MODE_NIGHT_NO, Mode.LIGHT)
            R.id.night -> switchToMode(AppCompatDelegate.MODE_NIGHT_YES, Mode.NIGHT)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun switchToMode(nightMode: Int, mode: Mode) {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        Preferences.switchToMode(this, mode)
    }
}
