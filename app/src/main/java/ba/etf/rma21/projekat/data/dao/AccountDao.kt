package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AccountDao {
    @Query("DELETE FROM Account")
    fun izbrisiSve()

    @Query("INSERT INTO Account VALUES(:acHash, :lastUpdate)")
    fun upisi(acHash: String, lastUpdate: String)

    @Query("UPDATE Account SET lastUpdate= :lastUpdate WHERE acHash =:acHash")
    fun setLastUpdate(acHash: String, lastUpdate: String)

    @Query("SELECT acHash from Account")
    fun getHash() : String
}