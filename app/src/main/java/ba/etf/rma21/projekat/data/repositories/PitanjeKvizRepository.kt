package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.staticdata.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PitanjeKvizRepository {
    private lateinit var context: Context
    fun setContext(_context:Context){
        context=_context
    }
    suspend fun getPitanja(idKviza: Int): List<Pitanje>? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajPitanja(idKviza)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    suspend fun getBodove(kvizId: Int): Int? {
        Thread.sleep(1000)
        return withContext(Dispatchers.IO) {
            var response = dajBodove(kvizId)
            return@withContext response
        }
    }

    suspend fun dodajKvizIBodove(kvizId: Int, bodovi: Int) {
        Thread.sleep(1000)
        withContext(Dispatchers.IO) {
            dodajKvizIBodove(kvizId, bodovi)

        }
    }

    fun getPitanjaa(nazivKviza: String, nazivPredmeta: String): List<Pitanje> {
        return pitanja(
            nazivKviza,
            nazivPredmeta
        )
    }

    fun getPitanje(nazivKviza: String): Pitanje {
        return pitanje(nazivKviza)
    }

    fun getDaLiJeZavrsen(nazivKviza: String): Boolean {
        return daLiJeZavrsen(nazivKviza)
    }

    fun finishKviz(nazivKviza: String) {
        zavrsiKviz(nazivKviza)
    }

    fun addAnswer(pitanje: Pitanje, odgovor: Int) {
        dodajOdgovor(pitanje, odgovor)
    }

    fun dajOdgovor(pitanje: Pitanje): Int? {
        return getOdgovor(pitanje)
    }

    fun getAll(): HashMap<Pitanje, Int> {
        return dajSve()
    }

    fun getKvizoveSPitanjima(): List<String> {
        return kvizoviSPitanjima()
    }

    fun setSve(qAndA: HashMap<Pitanje, Int>) {
        setAll(qAndA)
    }
}