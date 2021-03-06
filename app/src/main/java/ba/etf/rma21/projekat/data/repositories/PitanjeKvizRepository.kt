package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
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
            var pitanja = arrayListOf<Pitanje>()
            if (responseBody != null) {
                for(quest in responseBody){
                    var id = quest.id
                    var naziv = quest.naziv
                    var tacan = quest.tacan
                    var tekst = quest.tekstPitanja
                    var opcije = quest.opcije[0] + "," + quest.opcije[1] + "," + quest.opcije[2]
                    pitanja.add(Pitanje(id, naziv, tekst, opcije, tacan, idKviza))
                }
            }
            return@withContext pitanja
        }
    }
    suspend fun getPitanjaDB(idKviza: Int) : List<Pitanje> {
        return withContext(Dispatchers.IO) {
            var db = AppDatabase.getInstance(context)
            var pitanja = db.pitanjeDao().getAllZaKviz(idKviza)
            return@withContext pitanja
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