package ba.etf.rma21.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.staticdata.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.*

@SuppressLint("StaticFieldLeak")
object KvizRepository {
    private lateinit var context: Context
    fun setContext(_context: Context){
        context=_context
    }
    fun getMyKvizes(): List<Kviz> {
        return myKvizes()
    }


    suspend fun getFuture(): List<Kviz> {
        return withContext(Dispatchers.IO) {
            var db = AppDatabase.getInstance(KvizRepository.context)
            var datum = Calendar.getInstance().time.year.toString() + "-" +  Calendar.getInstance().time.month.toString() + "-" +  Calendar.getInstance().time.day.toString()
            println(datum)
            var kvizovi = db.kvizDao().getFuture(datum)
            return@withContext kvizovi
        }
    }

    suspend fun getNotTaken(): List<Kviz> {
        return withContext(Dispatchers.IO) {
            var db = AppDatabase.getInstance(KvizRepository.context)
            var datum = Calendar.getInstance().time.year.toString() + "-" +  Calendar.getInstance().time.month.toString() + "-" +  Calendar.getInstance().time.day.toString()
            var kvizovi = db.kvizDao().getNotTaken(datum)
            return@withContext kvizovi
        }
    }

    // TODO: Implementirati i ostale potrebne metode
    fun addMine(kviz: Kviz) {
        dodajMoj(kviz)
    }

    fun getKviz(nazivKviza: String): Kviz {
        return dajKviz(nazivKviza)
    }

    //new
    suspend fun getAll(): List<Kviz>? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajSveKvizove()
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    suspend fun getDone(): List<Kviz>? {
        return withContext(Dispatchers.IO) {
            var db = AppDatabase.getInstance(KvizRepository.context)
            var kvizovi = db.kvizDao().getDone()
            return@withContext kvizovi
        }
    }

    suspend fun getById(id: Int): Kviz? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajKviz(id)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    suspend fun getUpisani(): List<Kviz>? {
        return withContext(Dispatchers.IO) {
            var korisnikoviKvizovi = arrayListOf<Kviz>()
            var kvizovi = listOf<Kviz>()
            var grupe = ApiAdapter.retrofit.dajStudentoveGrupe().body()!!
            if (grupe != null) {
                for (grupa in grupe)
                    launch(Dispatchers.IO) {
                        kvizovi = ApiAdapter.retrofit.dajUpisane(grupa.id).body()!!
                    }
                delay(1000)
                korisnikoviKvizovi.addAll(kvizovi)
            }
            return@withContext korisnikoviKvizovi
        }
    }

    suspend fun pomocna(idGrupe: Int): List<Kviz>? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajUpisane(idGrupe)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }
    suspend fun getMyDb() : List<Kviz?> {
        return withContext(Dispatchers.IO) {
            var db = AppDatabase.getInstance(KvizRepository.context)
            var kvizovi = db.kvizDao().getMyKvizes()
            return@withContext kvizovi
        }
    }
    suspend fun isPredan(kvizId : Int) : Boolean{
        return withContext(Dispatchers.IO) {
            var db = AppDatabase.getInstance(context)
            var isP = db.kvizDao().isPredan(kvizId)
            return@withContext isP
        }
}
suspend fun getBodove(kvizId : Int) : Float{
    return withContext(Dispatchers.IO) {
        var db = AppDatabase.getInstance(KvizRepository.context)
        var points = db.kvizDao().getBodove(kvizId)
        return@withContext points
    }
}
    suspend fun dostupne(idKviza: Int): List<Grupa>? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajDostupneGrupe(idKviza)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }
}