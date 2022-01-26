package com.example.simplebluedo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EditScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_screen)
        val editItem = findViewById<EditText>(R.id.editTextField)
        val saveButton = findViewById<Button>(R.id.saveButton)
        //editItem.setText("Old item ...")
        editItem.setText(getIntent().getStringExtra("item_text"))
        saveButton.setOnClickListener(){
            val i = Intent()
            i.putExtra("item_text", editItem.getText().toString())
            i.putExtra("item_position", getIntent().getStringExtra("item_position"))
            setResult(RESULT_OK, i)
            finish()
        }
    }

}