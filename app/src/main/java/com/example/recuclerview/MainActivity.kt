package com.example.recuclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var usersList : ArrayList<Users>
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView=findViewById(R.id.RecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        usersList= ArrayList()

        usersList.add(Users("Константинов Тарас", "Google", "Любит кошек"))
        usersList.add(Users("Крылова Зара", "Google", "Ест младенцев на завтрак"))
        usersList.add(Users("Шарапов Климент", "Google", "Три года работал в Африке"))
        usersList.add(Users("Петухов Матвей", "Google","Громко ругается"))
        usersList.add(Users("Максимова Кара", "Google", "Знает все треки Леди Гаги"))
        usersList.add(Users("Константинов Тарас", "Google", "Ничего не говорит о себе"))
        usersList.add(Users("Кристина Завойченко", "Google","Любит солянку"))
        usersList.add(Users("Беляев Захар", "Google","Ходит в качалку"))
        usersList.add(Users("Кабанова Мирра", "Google","Однажды пришла на работу без сумки"))
        usersList.add(Users("Прохоров Григорий", "Google","Папа - местный батюшка"))
        usersList.add(Users("Богданова Юфеза", "Google","Никто о ней ничего не знает"))

        usersAdapter = UsersAdapter(usersList)
        recyclerView.adapter = usersAdapter

        usersAdapter.onItemClick={
            val intent=Intent(this, DetailedAvtivity::class.java)
            intent.putExtra("user", it)
            startActivity(intent)
        }
    }
}