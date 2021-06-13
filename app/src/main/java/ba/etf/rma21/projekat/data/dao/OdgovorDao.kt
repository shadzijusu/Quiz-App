package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Odgovor

@Dao
interface OdgovorDao {
    @Query("INSERT INTO Odgovor VALUES(:id, :odgovoreno, :PitanjeId, :KvizId, :KvizTakenId, :bodovi)")
    fun dodajOdgovor(id : Int, odgovoreno : Int, PitanjeId : Int, KvizId: Int, KvizTakenId: Int, bodovi : Int)

    @Query("SELECT * from Odgovor where KvizTakenId = :KvizTakenId")
    fun getOdgovore(KvizTakenId : Int) : List<Odgovor>

    @Query("DELETE from Odgovor")
    fun izbrisiSve()
    @Query("SELECT PitanjeId from Odgovor where PitanjeId = :PitanjeId")
    fun getOdgovor(PitanjeId: Int) : Int

    @Query("SELECT * from Odgovor where KvizId = :kvizId")
    fun dajOdgovoreZaKviz(kvizId : Int) : List<Odgovor>
}