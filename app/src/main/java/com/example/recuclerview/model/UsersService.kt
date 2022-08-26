package com.example.recuclerview.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException
import java.io.InputStream

typealias UsersListener = (users: MutableList<Users>) -> Unit

class UsersService(private val context: Context) {

    private val path = "${context.cacheDir}/users.json"
    private var users:MutableList<Users> = readJson()
    private val listeners = mutableSetOf<UsersListener>()

    fun readJson(): MutableList<Users> {

        var result = ""
        val isFileExists = File(path).exists()
        val inputStream = if (isFileExists) {
            File(path).inputStream()
        } else {
            context.assets.open("users.json")
        }
        try {
            result = inputStream.bufferedReader().use { it.readText() }
            if (!isFileExists) writeDataToCacheFile(result)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream.close()
        }

        val listStudentType = object: TypeToken<MutableList<Users>>() {}.type // указание, какого типа данные ждать из считанной строки (переменная result)
        return Gson().fromJson(result, listStudentType) // парсинг массива по указанному типу строки
    }

   fun deleteUser(user: Users){
       val indexToDelete = users.indexOfFirst { it.id == user.id }
       if (indexToDelete != -1) {
           users.removeAt(indexToDelete)
       }
       var usersList=Gson().toJson(users)
       writeDataToCacheFile(usersList)
       notifyChanges()
    }

    fun addUser(name:String,department:String){
        val user=Users(users.size+1,name,department,"none","none")
        users.add(user)
        var usersList=Gson().toJson(users)
        writeDataToCacheFile(usersList)
        notifyChanges()
    }

    fun saveUser(user:Users) {
        val index = users.indexOfFirst { it.id == user.id }
        users.set(index, user)
        var usersList=Gson().toJson(users)
        writeDataToCacheFile(usersList)
        notifyChanges()
    }

    fun addListener(listener: UsersListener) {
        listeners.add(listener)
        listener.invoke(users)
    }

    fun removeListener(listener: UsersListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(users) }
    }

    private fun writeDataToCacheFile(result: String) {
        if (!File(path).exists()) {
            File(path).createNewFile()
        }
        val outputStream = File(path).outputStream()
        try {
            outputStream.bufferedWriter().use { it.write(result) }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            outputStream.close()
        }
    }
}
