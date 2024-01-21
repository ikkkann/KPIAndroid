package com.example.lab3_interaction_with_the_user

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class MainActivity : Activity() {

    private lateinit var editText: EditText
    private lateinit var addButton: Button
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val dataList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        addButton = findViewById(R.id.addButton)
        listView = findViewById(R.id.listView)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dataList)

        listView.adapter = adapter

        addButton.setOnClickListener {
            val inputText = editText.text.toString()
            dataList.add(inputText)
            adapter.notifyDataSetChanged()

            editText.text.clear()
        }
    }
}
