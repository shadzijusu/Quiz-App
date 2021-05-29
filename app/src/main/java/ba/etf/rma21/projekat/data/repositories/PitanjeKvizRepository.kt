package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.staticdata.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object PitanjeKvizRepository {

        suspend fun getPitanja(idKviza: Int): List<Pitanje>? {
            return withContext(Dispatchers.IO) {
                var response = ApiAdapter.retrofit.dajPitanja(idKviza)
                val responseBody = response.body()
                return@withContext responseBody
            }
        }
        fun getPitanjaa(nazivKviza: String, nazivPredmeta: String): List<Pitanje> {
            return pitanja(
                nazivKviza,
                nazivPredmeta
            )
        }
        fun getPitanje(nazivKviza: String) : Pitanje {
            return pitanje(nazivKviza)
        }
        fun getDaLiJeZavrsen(nazivKviza: String): Boolean {
            return daLiJeZavrsen(nazivKviza)
        }

        fun finishKviz(nazivKviza: String) {
            zavrsiKviz(nazivKviza)
        }
        fun addAnswer(pitanje : Pitanje, odgovor : Int) {
            dodajOdgovor(pitanje, odgovor)
        }
        fun dajOdgovor(pitanje : Pitanje): Int? {
            return getOdgovor(pitanje)
        }
        fun getAll() : HashMap<Pitanje, Int> {
            return dajSve()
        }
        fun getKvizoveSPitanjima() : List<String> {
            return kvizoviSPitanjima()
        }
        fun setSve(qAndA : HashMap<Pitanje, Int> ) {
            setAll(qAndA)
        }
}