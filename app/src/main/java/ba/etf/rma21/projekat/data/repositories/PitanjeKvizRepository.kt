package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.staticdata.*
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class PitanjeKvizRepository {

    companion object {
        // TODO: Implementirati
        init {
            // TODO: Implementirati
        }

        //use web servis
        fun getPitanja(idKviza: Int): List<Pitanje> {
            val pitanja = arrayListOf<Pitanje>()
            var url1 = "https://rma21-etf.herokuapp.com/kviz/$idKviza/pitanja"
            val url = URL(url1)
            (url.openConnection() as? HttpURLConnection)?.run {
                val result = this.inputStream.bufferedReader().use { it.readText() }
                val jo = JSONObject(result)
                val results = jo.getJSONArray("results")
                for (i in 0 until results.length()) {//7
                    val pitanje = results.getJSONObject(i)
                    val id = pitanje.getInt("id")
                    val naziv = pitanje.getString("naziv")
                    val tekst = pitanje.getString("tekstPitanja")
                    val opcijeArray = pitanje.getJSONArray("opcije")
                    val tacan = pitanje.getInt("tacan")
                    val opcije = arrayListOf<String>()
                    for (i in 0 until opcijeArray.length()) {
                        opcije.add(opcijeArray.getString(i))
                    }
                    pitanja.add(Pitanje(naziv, tekst, opcije, tacan))
                }
            }
            return pitanja
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
}