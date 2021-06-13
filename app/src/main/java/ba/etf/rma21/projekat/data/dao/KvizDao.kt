package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Kviz

@Dao
interface KvizDao {
    @Insert
    suspend fun insertAll(kvizovi : List<Kviz>)
    @Query("DELETE from Kviz")
    fun izbrisiSve()
}