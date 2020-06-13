package com.example.mysqldatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler.*

class RecyclerActivity : AppCompatActivity() {

    private lateinit var peopleAdapter: RecyclerAdapterPeople  // lateinit means will be initialized very soon
    var databasehelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
        initRecyclerView()
        peopleAdapter.submitList(databasehelper.readAllData())
    }

    private fun initRecyclerView(){
        // recyclerView - see activity_recycler.xml for this id name
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@RecyclerActivity)
            addItemDecoration(SpaceDecoration(5))
            peopleAdapter = RecyclerAdapterPeople()
            adapter = peopleAdapter
        }
    }
}
