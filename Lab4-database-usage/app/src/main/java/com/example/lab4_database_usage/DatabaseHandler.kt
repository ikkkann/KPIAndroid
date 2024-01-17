package com.example.lab4_database_usage

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "pizzasManager"
        private const val TABLE_PIZZAS = "pizzas"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_TOPPINGS = "toppings"
        private const val KEY_DIAMETER = "diameter"
        private const val KEY_CRUST_TYPE = "crustType"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_PIZZAS_TABLE = (
                "CREATE TABLE $TABLE_PIZZAS (" +
                        "$KEY_ID INTEGER PRIMARY KEY," +
                        "$KEY_NAME TEXT," +
                        "$KEY_TOPPINGS TEXT," +
                        "$KEY_DIAMETER INTEGER," +
                        "$KEY_CRUST_TYPE TEXT)"
                )
        db?.execSQL(CREATE_PIZZAS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PIZZAS")
        onCreate(db)
    }

    fun addPizza(pizza: Pizza) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, pizza.name)
        values.put(KEY_TOPPINGS, pizza.toppings)
        values.put(KEY_DIAMETER, pizza.diameter)
        values.put(KEY_CRUST_TYPE, pizza.crustType)

        db.insert(TABLE_PIZZAS, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getAllPizzas(): List<Pizza> {
        val pizzaList = mutableListOf<Pizza>()
        val selectQuery = "SELECT * FROM $TABLE_PIZZAS"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val pizza = Pizza(
                    id = cursor.getLong(cursor.getColumnIndex(KEY_ID)),
                    name = cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                    toppings = cursor.getString(cursor.getColumnIndex(KEY_TOPPINGS)),
                    diameter = cursor.getInt(cursor.getColumnIndex(KEY_DIAMETER)),
                    crustType = cursor.getString(cursor.getColumnIndex(KEY_CRUST_TYPE))
                )
                pizzaList.add(pizza)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return pizzaList
    }
}