package com.example.dietapps

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.ChangeEventListener
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.food_layout.view.*


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val REQUIRED = "Required"

    //access database table
    private var foodDatabase: DatabaseReference? = null
    //to get the current database pointer
    private var foodReference: DatabaseReference? = null
    private var foodListener: ChildEventListener? = null

    //no need
    private var foodAdapter: FirebaseRecyclerAdapter<Food, FoodViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //to get the root folder
        foodDatabase = FirebaseDatabase.getInstance().reference
        //to access to the table
        foodReference = FirebaseDatabase.getInstance().getReference("Food")

        firebaseListenerInit()
        foodRecycleView.layoutManager = LinearLayoutManager(this)
        val query = foodReference!!.limitToLast(8)

        foodAdapter = object: FirebaseRecyclerAdapter<Food, FoodViewHolder>(
            Food::class.java, R.layout.food_layout, FoodViewHolder::class.java,query
        ){
            override fun populateViewHolder(viewHolder: FoodViewHolder?, model: Food?, position: Int) {
                viewHolder!!.bindFood(model)
            }

            override fun onChildChanged(
                type: ChangeEventListener.EventType?,
                snapshot: DataSnapshot?,
                index: Int,
                oldIndex: Int
            ) {
                super.onChildChanged(type, snapshot, index, oldIndex)
                foodRecycleView.scrollToPosition(index)
            }
            //this one no need because no need start intent to view the location

        }
        foodRecycleView.adapter = foodAdapter

        fabAddfoodPost.setOnClickListener{
            val intent = Intent(this, AddFood::class.java)

            startActivity(intent)
        }
    }

    private fun firebaseListenerInit() {
        val childEventListener = object: ChildEventListener{
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "postMessages:onCancelled", databaseError!!.toException())
                //Toast.makeText(this, "Failed to load Message.", Toast.LENGTH_SHORT).show()
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, food: String?) {
                Log.e(TAG, "onChildMoved:" + dataSnapshot!!.key)

                // A food has changed position
                val food = dataSnapshot.getValue(Food::class.java)
                //toast here
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, food: String?) {
                Log.e(TAG, "onChildChanged: " + dataSnapshot!!.key)

                val food = dataSnapshot.getValue(Food::class.java)
                //toast here
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, food: String?) {
                val food = dataSnapshot!!.getValue(Food::class.java)

                Log.e(TAG, "onChildAdded:" + food!!)
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                Log.e(TAG, "onChildRemoved:" + dataSnapshot!!.key)

                // A message has been removed
                val food = dataSnapshot.getValue(Food::class.java)
            }
        }
    }
    override fun onStop() {
        super.onStop()

        if (foodListener != null) {
            foodReference!!.removeEventListener(foodListener!!)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        foodAdapter!!.cleanup()
    }
}