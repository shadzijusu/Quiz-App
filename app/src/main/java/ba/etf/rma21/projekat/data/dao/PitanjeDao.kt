package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Pitanje

@Dao
interface PitanjeDao {
    @Query("DELETE from Pitanje")
    suspend fun izbrisiSve()

    @Insert
    suspend fun insertAll(pitanja : List<Pitanje>)

    @Query("SELECT * from Pitanje where kvizId = :kvizId")
    suspend fun getAllZaKviz(kvizId : Int) : List<Pitanje>
}