package com.example.lab4_database_usage

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

class MainActivity : Activity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextToppings: EditText
    private lateinit var editTextDiameter: EditText
    private lateinit var editTextCrustType: EditText
    private lateinit var addButton: Button
    private lateinit var listView: ListView
    private lateinit var db: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        editTextToppings = findViewById(R.id.editTextToppings)
        editTextDiameter = findViewById(R.id.editTextDiameter)
        editTextCrustType = findViewById(R.id.editTextCrustType)
        addButton = findViewById(R.id.addButton)
        listView = findViewById(R.id.listView)

        db = DatabaseHandler(this)

        updateListView()

        addButton.setOnClickListener {
            val name = editTextName.text.toString()
            val toppings = editTextToppings.text.toString()
            val diameterText = editTextDiameter.text.toString()
            val crustType = editTextCrustType.text.toString()

            if (name.isNotBlank() && toppings.isNotBlank() && diameterText.isNotBlank() && crustType.isNotBlank()) {
                try {
                    val diameter = diameterText.toInt()

                    val newPizza = Pizza(
                        name = name,
                        toppings = toppings,
                        diameter = diameter,
                        crustType = crustType
                    )
                    db.addPizza(newPizza)

                    editTextName.text.clear()
                    editTextToppings.text.clear()
                    editTextDiameter.text.clear()
                    editTextCrustType.text.clear()

                    updateListView()
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Некоректний діаметр", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Заповніть всі поля", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateListView() {
        val pizzaList = db.getAllPizzas()

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, pizzaList)
        listView.adapter = arrayAdapter
    }
}