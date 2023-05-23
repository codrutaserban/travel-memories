package com.example.travelmemories.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.travelmemories.R
import com.example.travelmemories.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var mapModeSelection: String= ""
    private var showMapScaleSelection = true
    private lateinit var sharedPref:SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sharedPref = activity?.getSharedPreferences("prefs",Context.MODE_PRIVATE)!!

        setMapModeSpinnerAdapter()
        setMapScaleSwitch()

        binding.buttonSaveSettings.setOnClickListener{
            if (sharedPref != null) {
                with (sharedPref.edit()) {
                    putString("preferedMapMode", mapModeSelection)
                    putBoolean("showMapSelection", showMapScaleSelection)
                    apply()
                }

                Snackbar.make(binding.root, getString(R.string.setting_saved_text), Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun setMapModeSpinnerAdapter(){
        val mapOptions = resources.getStringArray(R.array.types_of_app_map_mode)
        mapModeSelection = sharedPref.getString("preferedMapMode", mapOptions[0]).toString()
        Log.d("preferedMapMode",mapModeSelection)
        if (this.binding.spinnerMapMode != null) {
            val adapter = ArrayAdapter(binding.root.context,
                android.R.layout.simple_spinner_item, mapOptions)
            this.binding.spinnerMapMode.adapter = adapter
            this.binding.spinnerMapMode.setSelection(mapOptions.indexOf(mapModeSelection))

            this.binding.spinnerMapMode.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    mapModeSelection= mapOptions[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    mapModeSelection= mapOptions[0]
                }
            }
        }
    }

    private fun setMapScaleSwitch() {
        showMapScaleSelection = sharedPref.getBoolean("showMapSelection", true)
        binding.switchMapScale.isChecked = showMapScaleSelection
        binding.switchMapScale.setOnCheckedChangeListener { _, isChecked ->
            showMapScaleSelection = isChecked
        }
    }
}