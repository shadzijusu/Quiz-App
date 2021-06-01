package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.staticdata.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object KvizRepository {

    fun getMyKvizes(): List<Kviz> {
        return myKvizes()
    }

    fun getDone(): List<Kviz> {
        return done()
    }

    fun getFuture(): List<Kviz> {
        return future()
    }

    fun getNotTaken(): List<Kviz> {
        return notTaken()
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

    suspend fun dostupne(idKviza: Int): List<Grupa>? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajDostupneGrupe(idKviza)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }
}