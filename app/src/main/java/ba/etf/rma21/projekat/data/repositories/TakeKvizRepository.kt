package ba.etf.rma21.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.KvizTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

@SuppressLint("StaticFieldLeak")
object TakeKvizRepository {
    private lateinit var context: Context
    fun setContext(_context: Context){
        context=_context
    }
    suspend fun zapocniKviz(idKviza: Int): KvizTaken? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.zapocniKviz(idKviza)
            val responseBody = response.body()
            if(responseBody?.message != null)
                return@withContext null
            else {
                var kvizTaken = responseBody?.id?.let { responseBody.datumRada?.let { it1 ->
                    KvizTaken(it, responseBody.student,
                       "", responseBody.osvojeniBodovi, idKviza)
                } }
                return@withContext kvizTaken
            }
        }
    }

    suspend fun getPocetiKvizovi(): List<KvizTaken>? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajZapocete()
            val responseBody = response.body()
            if(responseBody?.isEmpty()!!)
                return@withContext null
           else return@withContext responseBody
        }
    }
    suspend fun zapocniDB(id : Int, idKviza: Int) {
         withContext(Dispatchers.IO) {
            var db = AppDatabase.getInstance(context)
            db.kvizTakenDao().zapocni(id, idKviza)
        }
    }
    suspend fun getKvizTakenId(idKviza: Int) : Int{
        return withContext(Dispatchers.IO) {
            var db = AppDatabase.getInstance(context)
            var id = db.kvizTakenDao().getKvizTakenId(idKviza)
            return@withContext id
        }
    }
}