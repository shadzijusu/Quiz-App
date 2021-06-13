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
        var acHash: String = "9d53bd38-18d2-49ec-889f-703ab44db589"

         suspend fun postaviHash(acHash: String): Boolean {
             return withContext(Dispatchers.IO) {
                 var db = AppDatabase.getInstance(context)
                 db.accountDao().izbrisiSve()
                 db.grupaDao().izbrisiSve()
                 db.kvizDao().izbrisiSve()
                 db.predmetDao().izbrisiSve()
                 db.pitanjeDao().izbrisiSve()
                 db.accountDao().upisi(acHash, null.toString())
                 return@withContext true
             }
        }

         suspend fun getHash(): String {
             return withContext(Dispatchers.IO) {
                 var db = AppDatabase.getInstance(context)
                 var acHash = db.accountDao().getHash()
                 return@withContext acHash
             }
        }

}