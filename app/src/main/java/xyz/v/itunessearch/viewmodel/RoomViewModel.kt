package xyz.v.itunessearch.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.v.itunessearch.room.AppDatabase
import xyz.v.itunessearch.room.ResultEntity

class RoomViewModel() : ViewModel() {
    val result:MutableLiveData<List<ResultEntity>> = MutableLiveData()
    private var db:AppDatabase? = null

    fun buildDatabase(context: Context){
        db = Room.databaseBuilder(context,AppDatabase::class.java,"itunes-serach").build()
    }

    suspend fun getAllSerachResults():MutableLiveData<List<ResultEntity>>{
       withContext(Dispatchers.IO){result.postValue(db?.searchDao()?.getAll())}
        return result
    }

    suspend fun insertResult(query:ResultEntity){
        withContext(Dispatchers.IO){db?.searchDao()?.insertSerach(query)}
    }

    suspend fun deleteAll(){
        withContext(Dispatchers.IO){
            db?.searchDao()?.deleteAll()
        }
    }
}