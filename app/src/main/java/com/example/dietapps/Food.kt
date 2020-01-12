package com.example.dietapps

import com.google.firebase.database.Exclude

class Food{
    var foodName:String? = null
    var foodCalories:Long? = null
    var foodImage:String? = null
    var foodDescription:String? = null


    constructor(){}

    constructor(
        foodName: String?,
        foodCalories: Long?,
        foodImage: String?,
        foodDescription: String?
    ) {
        this.foodName = foodName
        this.foodCalories = foodCalories
        this.foodImage = foodImage
        this.foodDescription = foodDescription
    }

    constructor(foodName: String?, foodCalories: Long?, foodDescription: String?) {
        this.foodName = foodName
        this.foodCalories = foodCalories
        this.foodDescription = foodDescription
    }

    @Exclude
    fun toMap():Map<String,Any>{
        val result = HashMap<String, Any>()
        result.put("foodName",foodName!!)
        result.put("foodCalories",foodCalories!!)
        result.put("foodImage",foodImage!!)
        result.put("foodDescription",foodDescription!!)

        return result
    }
}