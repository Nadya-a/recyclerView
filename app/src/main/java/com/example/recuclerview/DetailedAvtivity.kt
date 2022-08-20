package com.example.recuclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.recuclerview.model.Users

class DetailedAvtivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_avtivity)

        val user=intent.getParcelableExtra<Users>("user")
        if (user!=null){
            val nameTV:TextView=findViewById(R.id.nameTextView)
            val departmentTV:TextView=findViewById(R.id.departmentTextView)
            val emailTV:TextView=findViewById(R.id.emailTextView)
            val moreTV:TextView=findViewById(R.id.moreTextView)

            nameTV.text=user.name
            departmentTV.text=user.department
            emailTV.text=user.email
            moreTV.text=user.more
        }
    }
}