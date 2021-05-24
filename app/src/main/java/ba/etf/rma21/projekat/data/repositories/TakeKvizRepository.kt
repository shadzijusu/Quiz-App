package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.KvizTaken
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class TakeKvizRepository {
    companion object {
        var mojHash = "http://rma21-etf.herokuapp.com/account/9d53bd38-18d2-49ec-889f-703ab44db589"

        fun zapocniKviz(idKviza: Int): KvizTaken? {
            var url1 = "https://rma21-etf.herokuapp.com/student/$mojHash/kviz/$idKviza"
            val url = URL(url1)
            var kvizTaken: KvizTaken? = null
            (url.openConnection() as? HttpURLConnection)?.run {
                kvizTaken = KvizTaken("shadzijusu1@etf.unsa.ba", 0f, Calendar.getInstance().time)
            }
            return kvizTaken
        }

        fun getPocetiKvizovi(): List<KvizTaken>? {
            val taken = arrayListOf<KvizTaken>()
            var url1 = "https://rma21-etf.herokuapp.com/student/$mojHash/kviztaken"
            val url = URL(url1)
            (url.openConnection() as? HttpURLConnection)?.run {
                val result = this.inputStream.bufferedReader().use { it.readText() }
                val jo = JSONObject(result)
                val results = jo.getJSONArray("results")
                for (i in 0 until results.length()) {//7
                    val takenKviz = results.getJSONObject(i)
                    val id = takenKviz.getInt("id")
                    val student = takenKviz.getString("student")
                    val osvojeniBodovi = takenKviz.getLong("osvojeniBodovi")
                    //"2021-05-20"
                    val datumRada = takenKviz.getString("datumRada")
                    val dijelovi = datumRada.split("-")
                    val godina: Int = dijelovi[0].toInt()
                    val mjesec: Int = dijelovi[1].toInt()
                    val dan: Int = dijelovi[2].toInt()
                    taken.add(
                        KvizTaken(
                            student,
                            osvojeniBodovi.toFloat(),
                            Date(dan, mjesec, godina)
                        )
                    )
                }
            }
            if (taken.size != 0) return taken
            return null
        }
    }

}