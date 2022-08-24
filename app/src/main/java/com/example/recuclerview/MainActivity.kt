package com.example.recuclerview

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recuclerview.model.Users
import com.example.recuclerview.model.UsersListener
import com.example.recuclerview.model.UsersService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var recyclerView: RecyclerView
    private val usersService: UsersService get() = (applicationContext as App).usersService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        usersAdapter= UsersAdapter()

       /* (object : UserActionListener {
            override fun onUserDelete(user: Users) {
                //usersService.deleteUser(user)
                deleteUserDialog()
            }

            override fun onUserDetails(user: Users) {
                //val intent = Intent(this@MainActivity, DetailedAvtivity::class.java)
                //intent.putExtra("user", it)
                //startActivity(intent)
                deleteUserDialog()
            }
        }) */
        recyclerView.adapter=usersAdapter

          usersAdapter.onItemClick = {
                val intent = Intent(this, DetailedAvtivity::class.java)
               intent.putExtra("user", it)
               startActivity(intent)
           }
           usersAdapter.deleteClick={
               //Toast.makeText(this, "Hello World",   Toast.LENGTH_LONG).show()
               deleteUserDialog(it)
           }

        usersService.addListener(usersListener)

    }

    private val usersListener: UsersListener = {
        usersAdapter.usersList = it
    }

    override fun onCreateOptionsMenu(menu: Menu?):Boolean{
        menuInflater.inflate(R.menu.item_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.add_item)
            showCreateUserDialog()
        return super.onOptionsItemSelected(item)
    }

    fun deleteUserDialog(user: Users)
    {
        val ad=AlertDialog.Builder(this@MainActivity)
        ad.setTitle("Удаление")
        ad.setMessage("Вы уверены, что хотите удалить пользователя?")
        ad.setIcon(R.drawable.user_delete)
        ad.setPositiveButton("Yes"){dialogInterface, which ->
            //Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_LONG).show()
            usersService.deleteUser(user)
            Toast.makeText(applicationContext, "Пользователь "+ user.name +" удалён",Toast.LENGTH_LONG).show()
        }
        ad.setNegativeButton("Cancel"){dialogInterface, which ->
            Toast.makeText(applicationContext,"clicked cancel",Toast.LENGTH_LONG).show()
        }
        val alertDialog: AlertDialog = ad.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun showCreateUserDialog() {
        val ad = AlertDialog.Builder(this@MainActivity)
        val inflater = layoutInflater
        val dialogLayout: View = inflater.inflate(R.layout.add_user_dialog, null)
        val editText1: EditText = dialogLayout.findViewById<EditText>(R.id.add_user_name)
        val editText2: EditText = dialogLayout.findViewById<EditText>(R.id.add_user_department)
        with(ad) {
            setTitle("Укажите данные пользователя!")
            setPositiveButton("OK") { dialog, which ->
                usersService.addUser(editText1.text.toString(),editText2.text.toString())
                Toast.makeText(applicationContext, "Пользователь "+editText1.text.toString()+" успешно добавлен", Toast.LENGTH_LONG).show()
            }
            setNegativeButton("Cancel") { dialog, which ->
                Toast.makeText(applicationContext, "clicked cancel", Toast.LENGTH_LONG).show()
            }
            setView(dialogLayout)
            show()
        }
    }
 }




