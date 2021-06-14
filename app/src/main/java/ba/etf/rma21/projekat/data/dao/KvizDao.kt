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

    @Query("UPDATE Kviz SET predan = 1, datumRada = :datumRada where id = :id")
    fun predaj(id: Int, datumRada : String)

    @Query("SELECT predan from Kviz where id = :id")
    fun isPredan(id : Int) : Boolean

    @Query("UPDATE Kviz set osvojeniBodovi = :bodovi where id = :kvizId")
    fun dodajBodove(bodovi : Int, kvizId : Int)

    @Query("SELECT osvojeniBodovi from Kviz where id = :kvizId")
    fun getBodove(kvizId: Int) : Float

    //uradjen
    @Query("SELECT * from Kviz where predan = 1")
    fun getDone() : List<Kviz>

    //future
    @Query("SELECT * from Kviz where datumPocetka > :datum AND datumRada == 'NULL'")
    fun getFuture(datum : String) : List<Kviz>

    //not taken
    @Query("SELECT * from Kviz where datumKraj < :datum AND predan = 0")
    fun getNotTaken(datum : String) : List<Kviz>
}
