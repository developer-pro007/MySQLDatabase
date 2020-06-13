package com.example.mysqldatabase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.mysqldatabase.Models.People

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "First_SQL_Database.db"
        val TABLE_NAME = DBContract.UserEntry.TABLE_NAME
        val Col_1 = DBContract.UserEntry.COLUMN_ID
        val Col_2 = DBContract.UserEntry.COLUMN_FNAME
        val Col_3 = DBContract.UserEntry.COLUMN_LNAME
        val Col_4 = DBContract.UserEntry.COLUMN_DATE

        private val SQL_CREATE_DATABASE =
            "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + " (" +
                    DBContract.UserEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBContract.UserEntry.COLUMN_FNAME + " TEXT," +
                    DBContract.UserEntry.COLUMN_LNAME + " TEXT," +
                    DBContract.UserEntry.COLUMN_DATE + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME
    }


    override fun onCreate(db: SQLiteDatabase) {
        // create the database
        db.execSQL(SQL_CREATE_DATABASE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // discard the date and begin again
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertData(fname: String, lname: String, date: String): Long {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val insertvalues = ContentValues()
        insertvalues.put(DBContract.UserEntry.COLUMN_FNAME, fname)
        insertvalues.put(DBContract.UserEntry.COLUMN_LNAME, lname)
        insertvalues.put(DBContract.UserEntry.COLUMN_DATE, date)

        // Insert the new row, returning the primary key value of the new row
        val action = db.insert(DBContract.UserEntry.TABLE_NAME, null, insertvalues)
        db.close() //close database connection
        return action
    }

    @Throws(SQLiteConstraintException::class)
    fun changeData(peop: People): Int {
        // Gets the data repository in write mode
        val db = writableDatabase

        val changevalues = ContentValues()
        changevalues.put(DBContract.UserEntry.COLUMN_ID, peop.id)
        changevalues.put(DBContract.UserEntry.COLUMN_FNAME, peop.fname)
        changevalues.put(DBContract.UserEntry.COLUMN_LNAME, peop.lname)
        changevalues.put(DBContract.UserEntry.COLUMN_DATE, peop.date)

        // Which row to update, based on the ID
        val selection = "${DBContract.UserEntry.COLUMN_ID} LIKE ?"
        val selectionArgs = arrayOf(peop.id)
        val action = db.update(DBContract.UserEntry.TABLE_NAME, changevalues, selection, selectionArgs)
        db.close()
        return action
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteData(id: String): Int {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.UserEntry.COLUMN_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(id)
        // SQL statement which will return number of rows deleted
        val action = db.delete(DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)
        db.close()
        return action
    }


    fun readAllData(): ArrayList<People> {
        val dBdata = ArrayList<People>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_DATABASE)
            return ArrayList()
        }

        var id: String
        var fname: String
        var lname: String
        var date: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_ID))
                fname = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_FNAME))
                lname = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_LNAME))
                date = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DATE))

                dBdata.add(People(id, fname, lname, date))
                cursor.moveToNext()
            }
        }
        return dBdata
    }

}
