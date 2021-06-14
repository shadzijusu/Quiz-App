package ba.etf.rma21.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.*

@SuppressLint("StaticFieldLeak")

object AccountRepository {

    private lateinit var context:Context
    fun setContext(_context:Context){
        context=_context
    }
        //TODO Ovdje trebate dodati hash string vašeg accounta
        var acHash: String = ""
        var lastUp : String = "null"
         suspend fun postaviHash(accHash: String): Boolean {
             return withContext(Dispatchers.IO) {
                 var db = AppDatabase.getInstance(context)
                 var hashDB = db.accountDao().getHash()
                 if(accHash != hashDB) {
                     db.accountDao().izbrisiSve()
                     db.grupaDao().izbrisiSve()
                     db.kvizDao().izbrisiSve()
                     db.predmetDao().izbrisiSve()
                     db.pitanjeDao().izbrisiSve()
                     db.odgovorDao().izbrisiSve()
                     db.accountDao().upisi(accHash, Calendar.getInstance().time.toString())
                 }
                 acHash = accHash
                 return@withContext true
             }
        }
    fun getHash(): String {
        return acHash
    }
}