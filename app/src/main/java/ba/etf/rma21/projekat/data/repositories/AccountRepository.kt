package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AccountRepository {
    private lateinit var context:Context
    fun setContext(_context:Context){
        context=_context
    }
        //TODO Ovdje trebate dodati hash string vašeg accounta
        var acHash: String = ""

         suspend fun postaviHash(acHash: String): Boolean {
             this.acHash = acHash
             return withContext(Dispatchers.IO) {
                 var db = AppDatabase.getInstance(context)
                 db.accountDao().izbrisiSve()
                 db.accountDao().upisi(acHash, null.toString())
                 return@withContext true
             }
        }

         fun getHash(): String {
            return acHash
        }

}