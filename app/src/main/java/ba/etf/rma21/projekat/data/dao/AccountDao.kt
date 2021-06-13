package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Account

@Dao
interface AccountDao {
    @Query("DELETE FROM Account")
    suspend fun izbrisiSve()

    @Query("INSERT INTO Account VALUES(:acHash, :lastUpdate)")
    suspend fun upisi(acHash: String, lastUpdate: String)

    @Query("UPDATE Account SET lastUpdate= :lastUpdate WHERE acHash =:acHash")
    suspend fun setLastUpdate(acHash: String, lastUpdate: String)

    @Query("SELECT acHash from Account")
    suspend fun getHash() : String

    @Query("SELECT lastUpdate from Account")
    suspend fun getLastUpdate() : String
}