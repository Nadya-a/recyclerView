package com.example.recuclerview

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

class MainActivity : AppCompatActivity() {
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var recyclerView: RecyclerView
    private val usersService: UsersService get() = (applicationContext as App).usersService

    var num = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        usersAdapter= UsersAdapter()

        recyclerView.adapter=usersAdapter

          usersAdapter.onItemClick = {
                val intent = Intent(this, DetailedAvtivity::class.java)
               intent.putExtra("user", it)
               startActivity(intent)
           }
           usersAdapter.deleteClick={
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
       /* if(item.itemId==R.id.add_item)
            showCreateUserDialog()
        return super.onOptionsItemSelected(item) */
        return when (item.itemId) {
            R.id.add_item -> {
                showCreateUserDialog()
                true
            }
            R.id.sort_item -> {
                num = sortItems(num)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun sortItems(n:Int):Int{
        var num=n
        when(num){
            1-> {
                usersAdapter.sortBeginning()
            num++
            }
            2->{
                usersAdapter.sortLast()
                num++
            }
            3->{
                usersAdapter.cancel()
                num -= 2
            }
        }
        return num
    }

    fun deleteUserDialog(user: Users)
    {
        val ad=AlertDialog.Builder(this@MainActivity)
        ad.setTitle("Удаление")
        ad.setMessage("Вы уверены, что хотите удалить пользователя?")
        ad.setIcon(R.drawable.user_delete)
        ad.setPositiveButton("Yes"){dialogInterface, which ->
            usersService.deleteUser(user)
            Toast.makeText(applicationContext, "Пользователь удалён",Toast.LENGTH_LONG).show()
        }
        ad.setNegativeButton("Cancel"){dialogInterface, which ->
            Toast.makeText(applicationContext,"Отмена",Toast.LENGTH_LONG).show()
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




