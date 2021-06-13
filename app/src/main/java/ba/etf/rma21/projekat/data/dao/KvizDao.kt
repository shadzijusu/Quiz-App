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
    @Query("SELECT * from Kviz")
    fun getMyKvizes() : List<Kviz>

    @Query("UPDATE Kviz SET predan = 'true' where id = :id")
    fun predaj(id: Int)

    @Query("SELECT predan from Kviz where id = :id")
    fun isPredan(id : Int) : Boolean
}