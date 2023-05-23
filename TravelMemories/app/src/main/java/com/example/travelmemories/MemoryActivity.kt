package com.example.travelmemories

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.location.Address
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.travelmemories.networkconnection.OpenWheatherServiceApi
import com.example.travelmemories.networkconnection.Weather
import com.google.android.material.slider.Slider
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar


class MemoryActivity : AppCompatActivity() {

    private lateinit var action: String

    private lateinit var placeName: EditText
    private lateinit var locationButton: Button
    private lateinit var textLocation: TextView
    private lateinit var calendar: CalendarView
    private lateinit var spinner: Spinner
    private lateinit var moodSlider: Slider
    private lateinit var notesText: EditText

    private var travelType = ""
    private var selectedDate = ""
    private var address: Address? = null
    private var editMemoryId = -1
    private lateinit var currentMemory: MemoryEntity
    private val requestMapActivityCode = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Current action", "on create")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.add_activity_title)

        this.editMemoryId = intent.getIntExtra("memory_id",-1)
        this.action = intent.getStringExtra("action").toString()

        setSpinnerAdapter()
        bindComponents()
        if(this.action == "edit"){
            populateComponents()
        }

        this.locationButton.setOnClickListener{
            val intent = Intent(this, MapsActivity::class.java)
            startActivityForResult(intent, requestMapActivityCode)
        }

        Toast.makeText(this, "$editMemoryId", Toast.LENGTH_SHORT).show()
        Log.d("Current action", action)

    }
    private fun setSpinnerAdapter(){
        this.spinner = findViewById(R.id.type_travel_picker)
        val tarvelTypes = resources.getStringArray(R.array.types_of_travel_options)
        if (this.spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, tarvelTypes)
            this.spinner.adapter = adapter

            this.spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    travelType=tarvelTypes[position]
                    Toast.makeText(this@MemoryActivity,tarvelTypes[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }

    override fun onResume() {
        Log.d("Current action", "on resume")

        super.onResume()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d("Current action", "in onCreateOptionsMenu")

        if(action == "add"){
            menuInflater.inflate(R.menu.memory_add, menu)
        }
        else {
            menuInflater.inflate(R.menu.memory_edit, menu)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("Memory item", item.itemId.toString())
        Log.d("Memory item", R.id.save_activity_button.toString())
        val memoriesDAO =  TravelMemoriesDb.getInstances(this).memoriesDAO()

        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.save_activity_button-> {
                val newMemory = getActivityEntityFromView()
                Log.d("Save memory activity", newMemory.toString())
                lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    memoriesDAO.insert(newMemory)
                     }
                }
                Toast.makeText(this, "save button clicked", Toast.LENGTH_SHORT).show()
                finish()
                return true
            }
            R.id.save_edit_activity_button ->{
                val updatedMemory = getActivityEntityFromView()
                updatedMemory.id = editMemoryId
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        memoriesDAO.update(updatedMemory)
                    }
                }
                finish()
                return true
            }
            R.id.delete_activity_button ->{
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        memoriesDAO.delete(currentMemory)
                    }
                }
                finish()
                return true
            }
            R.id.weather_activity_button -> {
                val moshi: Moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
                val retrofit= Retrofit.Builder()
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .baseUrl("https://api.openweathermap.org/data/3.0/")
                    .build()

                val openServiceAPI: OpenWheatherServiceApi = retrofit.create(OpenWheatherServiceApi::class.java)

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Location weather")

                lifecycleScope.launch {
                    var weather: Weather? = null

                    try{
                        weather= openServiceAPI.getCurrentWeather(currentMemory.latitude,currentMemory.longitude, OpenWheatherServiceApi.API_KEY )
                    }
                    catch(exception:Exception) {
                            builder.setMessage("Location must be set")
                            builder.show()
                    }
                    withContext(Dispatchers.Main){
                        // Update UI
                        if(weather!=null){
                            Log.d("Weather", weather!!.current.temp.toString())
                            builder.setMessage("Weather value is ${weather!!.current.temp}\nBut it feels like ${weather!!.current.feels_like}")
                            builder.show()
                        }
                        else{
                            Log.d("in context", "weather=null")
                        }
                    }
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun bindComponents() {
        this.placeName= findViewById(R.id.place_name)
        this.locationButton= findViewById(R.id.add_location)
        this.textLocation= findViewById(R.id.text_location)
        this.calendar= findViewById(R.id.calendar_picker)
        this.moodSlider= findViewById(R.id.mood_slider)
        this.notesText= findViewById(R.id.notes_text)
        this.calendar.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth.${month+1}.$year"
        })
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK) return
        when(requestCode) {
            requestMapActivityCode -> {
               val op = data?.getStringExtra("operation")
                if(op=="save"){
                    Toast.makeText(this, "s-a intors cu save", Toast.LENGTH_SHORT).show()
                    this.address = data?.getParcelableExtra("address", Address::class.java)!!
                    Log.d("back in memory cu save", this.address!!.getAddressLine(0))
                    Log.d("back in memory cu save", this.address!!.latitude.toString())
                    Log.d("back in memory cu save", this.address!!.longitude.toString())

                    this.textLocation.text= this.address!!.getAddressLine(0)
                }
                else if(op=="back"){
                    Toast.makeText(this, "s-a intors cu back", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {}
        }
    }

    private fun populateComponents() {

        val memoriesDAO =  TravelMemoriesDb.getInstances(this).memoriesDAO()
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                currentMemory = memoriesDAO.get(editMemoryId)
                Log.d("Current memory", currentMemory.toString())
                placeName.setText(currentMemory.name)
                textLocation.text = currentMemory.location
                val df = SimpleDateFormat("dd.MM.yyyy")
                calendar.date = df.parse(currentMemory.date).time
                moodSlider.value= currentMemory.mood.toFloat()
                setSpinText(currentMemory.type)
                notesText.setText(currentMemory.notes)

            }
        }
    }

    private fun setSpinText(text: String?) {
        for (i in 0 until spinner.adapter.count) {
            if (spinner.adapter.getItem(i).toString().contains(text!!)) {
                spinner.setSelection(i)
            }
        }
    }

    private fun getActivityEntityFromView(): MemoryEntity {
        if(selectedDate==""){
            val time = Calendar.getInstance().time
            val formatter = SimpleDateFormat("dd.MM.yyyy")
            val today = formatter.format(time)
            selectedDate=today
        }
        var lat = 0.0
        var lng = 0.0
        if(this.address!=null){
            lat= this.address!!.latitude
            lng= this.address!!.longitude
        }
        val newMemory = MemoryEntity(
            0,
            placeName.text.toString(),
            textLocation.text.toString(),
            lat,
            lng,
            selectedDate,
            travelType,
            moodSlider.value.toInt(),
            notesText.text.toString(),
            "res/drawable/travel_book.png"
        )
        return newMemory
    }

}