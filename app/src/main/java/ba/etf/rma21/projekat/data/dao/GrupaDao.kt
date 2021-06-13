package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet

@Dao
interface GrupaDao {
    @Query("SELECT * from Grupa where PredmetId = :PredmetId")
    fun dajGrupeZaPredmet(PredmetId: Int) : List<Grupa>

    @Query("INSERT INTO Grupa VALUES(:id, :naziv, :PredmetId)")
    fun upisiUGrupu(id : Int, naziv: String, PredmetId: Int)

    @Query("DELETE from Grupa")
    fun izbrisiSve()

    @Query("SELECT * from Grupa")
    fun getGrupe() : List<Grupa>
}