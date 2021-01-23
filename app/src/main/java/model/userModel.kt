package model

import com.google.firebase.database.*
import java.util.*

data class userModel(var id:String ,val username:String, val email:String)
{
    constructor() : this("","",""){

    }
}