package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.staticdata.*
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class KvizRepository {

    companion object {
        // TODO: Implementirati
        init {
            // TODO: Implementirati
        }

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
        fun addMine(kviz : Kviz) {
            dodajMoj(kviz)
        }
        fun getKviz(nazivKviza : String) : Kviz {
            return dajKviz(nazivKviza)
        }
        //new
        fun getAll():List<Kviz> {
            var kvizovi = arrayListOf<Kviz>()
            var url1 = "https://rma21-etf.herokuapp.com/kviz"
            val url = URL(url1)
            (url.openConnection() as? HttpURLConnection)?.run {
                val result = this.inputStream.bufferedReader().use { it.readText() }
                val jo = JSONObject(result)
                val results = jo.getJSONArray("results")
                for (i in 0 until results.length()) {//7
                    val kviz = results.getJSONObject(i)
                    val naziv = kviz.getString("naziv")
                    val datumPocetak = kviz.getString("datumPocetak")
                    var dijelovi = datumPocetak.split("-")
                    val g1: Int = dijelovi[0].toInt()
                    val m1: Int = dijelovi[1].toInt()
                    val d1: Int = dijelovi[2].toInt()
                    val dP = Date(g1, m1, d1)

                    val datumKraj = kviz.getString("datumKraj")
                    dijelovi = datumKraj.split("-")
                    val g2: Int = dijelovi[0].toInt()
                    val m2: Int = dijelovi[1].toInt()
                    val d2: Int = dijelovi[2].toInt()
                    val dK = Date(g2, m2, d2)
                    val trajanje = kviz.getInt("trajanje")
                    //nazivGrupe i osvojeni bodovi uraditi
                    kvizovi.add(Kviz(naziv, "", dP, Date(0,0,0),
                        dK, trajanje, "", 0f ))
                }
            }
            return kvizovi
        }
        fun getById(id:Int): Kviz? {
            var kviz: Kviz? = null
            var url1 = "https://rma21-etf.herokuapp.com/kviz/$id"
            val url = URL(url1)
            (url.openConnection() as? HttpURLConnection)?.run {
                val result = this.inputStream.bufferedReader().use { it.readText() }
                val jo = JSONObject(result)
                val naziv = jo.getString("naziv")
                val datumPocetak = jo.getString("datumPocetak")
                var dijelovi = datumPocetak.split("-")
                val g1: Int = dijelovi[0].toInt()
                val m1: Int = dijelovi[1].toInt()
                val d1: Int = dijelovi[2].toInt()
                val dP = Date(g1, m1, d1)

                val datumKraj = jo.getString("datumKraj")
                dijelovi = datumKraj.split("-")
                val g2: Int = dijelovi[0].toInt()
                val m2: Int = dijelovi[1].toInt()
                val d2: Int = dijelovi[2].toInt()
                val dK = Date(g2, m2, d2)
                val trajanje = jo.getInt("trajanje")
                kviz = Kviz(
                    naziv, "", dP, Date(0, 0, 0),
                    dK, trajanje, "", 0f
                )
            }
            return kviz
        }
        fun getUpisani(id:Int):List<Kviz> {
            var kvizovi = arrayListOf<Kviz>()
            var url1 = "https://rma21-etf.herokuapp.com/grupa/$id/kvizovi"
            val url = URL(url1)
            (url.openConnection() as? HttpURLConnection)?.run {
                val result = this.inputStream.bufferedReader().use { it.readText() }
                val jo = JSONObject(result)
                val results = jo.getJSONArray("results")
                for (i in 0 until results.length()) {//7
                    val kviz = results.getJSONObject(i)
                    val naziv = kviz.getString("naziv")
                    val datumPocetak = kviz.getString("datumPocetak")
                    var dijelovi = datumPocetak.split("-")
                    val g1: Int = dijelovi[0].toInt()
                    val m1: Int = dijelovi[1].toInt()
                    val d1: Int = dijelovi[2].toInt()
                    val dP = Date(g1, m1, d1)

                    val datumKraj = kviz.getString("datumKraj")
                    dijelovi = datumKraj.split("-")
                    val g2: Int = dijelovi[0].toInt()
                    val m2: Int = dijelovi[1].toInt()
                    val d2: Int = dijelovi[2].toInt()
                    val dK = Date(g2, m2, d2)

                    val trajanje = kviz.getInt("trajanje")
                    kvizovi.add(Kviz(naziv, "", dP, Date(0,0,0),
                        dK, trajanje, "", 0f ))
                }
            }
            return kvizovi
        }
    }
}