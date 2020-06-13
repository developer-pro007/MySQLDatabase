package com.example.mysqldatabase

import android.provider.BaseColumns

/* class that defines the table structure */
object DBContract {
    class UserEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "table_one"
            val COLUMN_ID = "ID"
            val COLUMN_FNAME = "fname"
            val COLUMN_LNAME = "lname"
            val COLUMN_DATE = "date"
        }
    }
}