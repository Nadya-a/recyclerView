package com.example.recuclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetailedAvtivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_avtivity)

        val user=intent.getParcelableExtra<Users>("user")
        if (user!=null){
            val nameTV:TextView=findViewById(R.id.nameTextView)
            val departmentTV:TextView=findViewById(R.id.departmentTextView)
            val moreTV:TextView=findViewById(R.id.moreTextView)

            nameTV.text=user.name
            departmentTV.text=user.department
            moreTV.text=user.more
        }
    }
}