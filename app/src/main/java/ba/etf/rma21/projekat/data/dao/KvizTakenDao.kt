package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface KvizTakenDao {
    @Query("INSERT INTO KvizTaken(id, KvizId) VALUES(:id, :KvizId)")
    fun zapocni(id : Int, KvizId : Int)

    @Query("SELECT id from KvizTaken where KvizId = :kvizId")
    fun getKvizTakenId(kvizId : Int) : Int
}