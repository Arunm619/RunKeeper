package io.arunbuilds.runkeeper.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.arunbuilds.runkeeper.R
import io.arunbuilds.runkeeper.other.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFieldsFromSharedPref()

        btnApplyChanges.setOnClickListener {
            val success = applyChangesToSharedPref()
            if (success)
                Snackbar.make(view, "Saved Changes.", Snackbar.LENGTH_LONG).show()
            else
                Snackbar.make(view, "Please fill out all the fields.", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun loadFieldsFromSharedPref() {
        val name = sharedPreferences.getString(Constants.KEY_NAME, "") ?: ""
        val weight = sharedPreferences.getFloat(Constants.KEY_WEIGHT, 80f)
        etName.setText(name)
        etWeight.setText(weight.toString())
    }

    private fun applyChangesToSharedPref(): Boolean {

        val name = etName.text.toString()
        val weight = etWeight.text.toString()
        if (name.isEmpty() || weight.isEmpty()) {
            return false
        }

        sharedPreferences.edit().apply {
            putString(Constants.KEY_NAME, name)
            putFloat(Constants.KEY_WEIGHT, weight.toFloat())
            putBoolean(Constants.KEY_FIRST_TIME_TOGGLE, false)
            apply()
        }
        val toolbarText = "Let's go, ${name}!"
        requireActivity().tvToolbarTitle.text = toolbarText
        return true
    }
}