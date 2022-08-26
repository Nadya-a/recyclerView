package com.example.recuclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.recuclerview.model.Users
import com.example.recuclerview.model.UsersService

class DetailedAvtivity : AppCompatActivity() {

    private val usersService: UsersService get() = (applicationContext as App).usersService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_avtivity)

        val nameTV: EditText = findViewById(R.id.nameTextView)
        val departmentTV: EditText = findViewById(R.id.departmentTextView)
        val emailTV: EditText = findViewById(R.id.emailTextView)
        val moreTV: EditText = findViewById(R.id.moreTextView)
        val delIm:ImageView=findViewById(R.id.imageDelete)
        val saveIm:ImageView=findViewById(R.id.imageSave)

        val user = intent.getParcelableExtra<Users>("user")
        if (user != null) {
            nameTV.setText(user.name)
            departmentTV.setText(user.department)
            emailTV.setText(user.email)
            moreTV.setText(user.more)
        }

        saveIm.setOnClickListener{
            val newUser=Users(user!!.id, nameTV.text.toString(), departmentTV.text.toString(), emailTV.text.toString(), moreTV.text.toString())
            Toast.makeText(applicationContext,"clicked save", Toast.LENGTH_LONG).show()
            usersService.saveUser(newUser)
        }

        delIm.setOnClickListener {
            if (user != null) {
                deleteUserDialog(user)
            }
        }

    }

    fun deleteUserDialog(user: Users)
    {
        val ad= AlertDialog.Builder(this@DetailedAvtivity)
        ad.setTitle("Удаление")
        ad.setMessage("Вы уверены, что хотите удалить пользователя?")
        ad.setIcon(R.drawable.user_delete)
        ad.setPositiveButton("Yes"){dialogInterface, which ->
            //Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_LONG).show()
            usersService.deleteUser(user)
            Toast.makeText(applicationContext, "Пользователь удалён",Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        ad.setNegativeButton("Cancel"){dialogInterface, which ->
            Toast.makeText(applicationContext,"Отмена",Toast.LENGTH_LONG).show()
        }
        val alertDialog: AlertDialog = ad.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}
