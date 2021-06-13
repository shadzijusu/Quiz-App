package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Predmet

@Dao
interface PredmetDao {

    @Query("SELECT * from Predmet where godina = :godina")
    fun dajPredmeteZaGodinu(godina: Int) : List<Predmet>

    @Query("INSERT INTO Predmet VALUES(:id, :naziv, :godina)")
    fun dodajPredmet(id : Int, naziv : String, godina : Int)

    @Query("DELETE from Predmet")
    fun izbrisiSve()
}