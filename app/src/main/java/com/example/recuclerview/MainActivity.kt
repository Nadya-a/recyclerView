package com.example.recuclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        usersAdapter= UsersAdapter(readJson())
        recyclerView.adapter=usersAdapter

       usersAdapter.onItemClick = {
             val intent = Intent(this, DetailedAvtivity::class.java)
            intent.putExtra("user", it)
            startActivity(intent)
        }
        usersAdapter.deleteClick={
            //Toast.makeText(this, "Hello World",   Toast.LENGTH_LONG).show()
            dialog()
        }

    }

    fun readJson(): MutableList<Users> {
        var usersList = mutableListOf<Users>()
        var json: String
        val inputStream: InputStream = assets.open("users.json")
        try {
            json = inputStream.bufferedReader().use{it.readText()}
            usersList= Gson().fromJson(json, object : TypeToken<MutableList<Users>>() {}.type)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStream.close()
        }
        return usersList
    }

    fun dialog()
    {
        val ad=AlertDialog.Builder(this@MainActivity)
        ad.setTitle("Удаление")
        ad.setMessage("Вы уверены, что хотите удалить пользователя?")
        ad.setIcon(R.drawable.user_delete)
        ad.setPositiveButton("Yes"){dialogInterface, which ->
            Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_LONG).show()
        }
        ad.setNegativeButton("Cancel"){dialogInterface, which ->
            Toast.makeText(applicationContext,"clicked cancel",Toast.LENGTH_LONG).show()
        }
        val alertDialog: AlertDialog = ad.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
 }




