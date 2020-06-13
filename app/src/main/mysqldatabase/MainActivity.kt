package com.example.mysqldatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysqldatabase.R
import com.example.mysqldatabase.Models.People
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var dbhelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_add.setOnClickListener{     addData()  }  // create button listener for "add button"
        btn_change.setOnClickListener{  changeData()  }  // create button listener for "change button"
        btn_delete.setOnClickListener{  deleteData()  }  // create button listener for "delete button"
        btn_show.setOnClickListener{    showAllData()  }  // create button listener for "show button"
    }

    fun addData(){
        // talk to database
        val fname = this.et_fname.text.toString()
        val lname = this.et_lname.text.toString()
        val date = this.et_date.text.toString()
        val result = dbhelper.insertData(fname = fname, lname = lname, date = date)

        // maintenance
        clearfields()
        this.tv_result.text = ("Added user: " + result)

    }


    fun changeData(){
        // talk to database
        val id = this.et_id.text.toString()
        val fname = this.et_fname.text.toString()
        val lname = this.et_lname.text.toString()
        val date = this.et_date.text.toString()
        val result = dbhelper.changeData(People(id, fname, lname, date))

        // maintenance
        clearfields()
        this.tv_result.text = ("Data changed: " + result)
    }


    fun deleteData(){
        // talk to database
        val id = this.et_id.text.toString()
        val result = dbhelper.deleteData(id)

        // maintenance
        clearfields()
        this.tv_result.text = ("Deleted data: " + result)
    }


    private fun showAllData(){
        val intent = Intent(this, RecyclerActivity::class.java)
        startActivity(intent)
    }


    private fun clearfields(){
        et_id.setText("")
        et_fname.setText("")
        et_lname.setText("")
        et_date.setText("")
    }

}
