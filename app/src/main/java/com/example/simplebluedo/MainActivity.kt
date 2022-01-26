package com.example.simplebluedo

import android.content.Intent
import android.hardware.biometrics.BiometricManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTasks = mutableListOf<String>()
    lateinit var adapter : TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        findViewById<Button>(R.id.button).setOnClickListener {
//            Log.i("Clon", "User has clicked this!")
//        }
//        listOfTasks.add("Complete Pre-work")
//        listOfTasks.add("Soak beans")

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun itemLongClicked(position: Int) {
                // Move the item from the list
                listOfTasks.removeAt(position)
                // Notify the adapter
                adapter.notifyDataSetChanged()
                saveItems()
            }
        }
        loadItems()
        val onClickListener = object : TaskItemAdapter.OnClickListener{
            override fun itemClicked(position: Int) {
                //Log.i("Clon", "User has clicked once"+position)
                // Create an activity
                val i = Intent(this@MainActivity, EditScreen::class.java)
                // Send edited data
                i.putExtra("item_text", listOfTasks.get(position))
                i.putExtra("item_position", position)
                // Display with updated data
                startActivityForResult(i, 20)

            }
        }
        
        // Lookup the recyclerview in activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener, onClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        // Finding the id of Add Task
        findViewById<Button>(R.id.button).setOnClickListener{
            // Store the text of the task
            val userInputTask = inputTextField.text.toString()

            // Persist the task in listOfTasks
            listOfTasks.add(userInputTask)
            // Notify adapter
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // Reset the text field
            inputTextField.setText("")
            saveItems()
        }
    }
    // Persist user inputs
    // Create or Read
    fun getDataFile() : File {
        return File(filesDir, "data.txt")
    }

    // Load
    fun loadItems(){
        try {
            listOfTasks = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())

        }catch (ioException : IOException){
            ioException.printStackTrace()
        }
    }

    // Write
    fun saveItems(){
        try{
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)
        }catch (ioException : IOException){
            ioException.printStackTrace()
        }
    }

}