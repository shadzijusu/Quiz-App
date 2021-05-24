package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Pitanje
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
    }
}