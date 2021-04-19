package xyz.v.itunessearch.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SearchDao {

@Insert
fun insertSerach(result:ResultEntity)

@Query("SELECT * FROM  search_results")
fun getAll():List<ResultEntity>

@Query("DELETE from search_results")
fun deleteAll()

}