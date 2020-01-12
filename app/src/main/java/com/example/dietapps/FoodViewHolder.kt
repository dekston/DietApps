package com.example.dietapps

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.food_layout.view.*

class FoodViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){


    fun bindFood(food:Food?){
        with(food!!){
            itemView.textViewFoodName.text = foodName
            itemView.textViewFoodCalories.text = foodCalories.toString()
            Picasso.get().load(foodImage).into(itemView.imageViewFood)
            itemView.textViewFoodDescription.text = foodDescription
        }
    }

}