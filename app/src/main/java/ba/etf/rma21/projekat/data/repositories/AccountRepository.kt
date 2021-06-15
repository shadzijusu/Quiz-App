package ba.etf.rma21.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("StaticFieldLeak")

object AccountRepository {

    private lateinit var context: Context
    fun setContext(_context: Context) {
        context = _context
    }

    //TODO Ovdje trebate dodati hash string vašeg accounta
    var acHash: String = ""
    suspend fun postaviHash(accHash: String): Boolean {
        acHash = accHash
        return withContext(Dispatchers.IO) {
            var db = AppDatabase.getInstance(context)
            var hashDB = db.accountDao().getHash()
                db.accountDao().izbrisiSve()
                db.grupaDao().izbrisiSve()
                db.kvizDao().izbrisiSve()
                db.predmetDao().izbrisiSve()
                db.pitanjeDao().izbrisiSve()
                db.odgovorDao().izbrisiSve()
//                var datum = Calendar.getInstance()
//                datum.add(Calendar.DATE, -1);
//                val pattern = "yyyy-MM-dd'T'hh:mm:ss"
//                val simpleDateFormat = SimpleDateFormat(pattern)
//                val update = simpleDateFormat.format(datum.time)
                val update = Date(0,0,0)
                db.accountDao().upisi(accHash, update.toString())

            return@withContext true
        }
    }

    fun getHash(): String {
        return acHash
    }

    suspend fun setLastUpdate() {
        return withContext(Dispatchers.IO) {
            var db = AppDatabase.getInstance(context)
            var accHash = db.accountDao().getHash()
            var datum = Calendar.getInstance()
            datum.add(Calendar.DATE, -1);
            val pattern = "yyyy-MM-dd'T'hh:mm:ss"
            val simpleDateFormat = SimpleDateFormat(pattern)
            val update = simpleDateFormat.format(datum.time)
            db.accountDao().setLastUpdate(accHash, update)
        }
    }
    suspend fun getLastUpdate() : String{
        return withContext(Dispatchers.IO) {
            var db = AppDatabase.getInstance(context)
            var last = db.accountDao().getLastUpdate()
            return@withContext last
        }
    }
}