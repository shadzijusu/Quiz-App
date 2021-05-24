package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class PredmetIGrupaRepository {
    companion object {
        fun getPredmeti(): List<Predmet> {
            val predmeti = arrayListOf<Predmet>()
            var url1 = "https://rma21-etf.herokuapp.com/predmet"
            val url = URL(url1)
            (url.openConnection() as? HttpURLConnection)?.run {
                val result = this.inputStream.bufferedReader().use { it.readText() }
                val jo = JSONObject(result)
                val results = jo.getJSONArray("results")
                for (i in 0 until results.length()) {
                    val predmet = results.getJSONObject(i)
                    val id = predmet.getInt("id")
                    val naziv = predmet.getString("naziv")
                    val godina = predmet.getInt("godina")
                    predmeti.add(Predmet(naziv, godina))
                }
            }
            return predmeti
        }

        fun getPredmet(idPredmeta: Int): Predmet? {
            var predmet: Predmet? = null
            var url1 = "https://rma21-etf.herokuapp.com/predmet/$idPredmeta"
            val url = URL(url1)
            (url.openConnection() as? HttpURLConnection)?.run {
                val result = this.inputStream.bufferedReader().use { it.readText() }
                val jo = JSONObject(result)
                val id = jo.getInt("id")
                val naziv = jo.getString("naziv")
                val godina = jo.getInt("godina")
                if (id == idPredmeta)
                    predmet = Predmet(naziv, godina)
            }
            return predmet
        }
    }

    fun getGrupe(): List<Grupa> {
        val grupe = arrayListOf<Grupa>()
        val url1 = "https://rma21-etf.herokuapp.com/grupa"
        val url = URL(url1)
        (url.openConnection() as? HttpURLConnection)?.run {
            val result = this.inputStream.bufferedReader().use { it.readText() }
            val jo = JSONObject(result)
            val results = jo.getJSONArray("results")
            for (i in 0 until results.length()) {
                val grupa = results.getJSONObject(i)
                val naziv = grupa.getString("naziv")
                val predmetId = grupa.getInt("PredmetId")
                val predmet: Predmet? = getPredmet(predmetId)
                val nazivPredmeta = predmet?.naziv
                grupe.add(Grupa(naziv, nazivPredmeta.toString()))
            }
        }
        return grupe
    }

    fun getGrupeZaPredmet(idPredmeta: Int): List<Grupa> {
        val grupe = arrayListOf<Grupa>()
        val url1 = "https://rma21-etf.herokuapp.com/predmet/$idPredmeta/grupa"
        val url = URL(url1)
        (url.openConnection() as? HttpURLConnection)?.run {
            val result = this.inputStream.bufferedReader().use { it.readText() }
            val jo = JSONObject(result)
            val results = jo.getJSONArray("results")
            for (i in 0 until results.length()) {
                val grupa = results.getJSONObject(i)
                val naziv = grupa.getString("naziv")
                val predmetId = grupa.getInt("PredmetId")
                val predmet: Predmet? = getPredmet(predmetId)
                val nazivPredmeta = predmet?.naziv
                grupe.add(Grupa(naziv, nazivPredmeta.toString()))
            }
        }
        return grupe
    }

    fun upisiUGrupu(idGrupa: Int): Boolean {
        val url1 =
            "https://rma21-etf.herokuapp.com/grupa/$idGrupa/student/9d53bd38-18d2-49ec-889f-703ab44db589"
        val url = URL(url1)
        var upisan = false
        (url.openConnection() as? HttpURLConnection)?.run {
            upisan = true
        }
        return upisan
    }

    fun getUpisaneGrupe(): List<Grupa> {
        val grupe = arrayListOf<Grupa>()
        val url1 =
            "https://rma21-etf.herokuapp.com/student/9d53bd38-18d2-49ec-889f-703ab44db589/grup"
        val url = URL(url1)
        (url.openConnection() as? HttpURLConnection)?.run {
            val result = this.inputStream.bufferedReader().use { it.readText() }
            val jo = JSONObject(result)
            val results = jo.getJSONArray("results")
            for (i in 0 until results.length()) {
                val grupa = results.getJSONObject(i)
                val naziv = grupa.getString("naziv")
                val predmetId = grupa.getInt("PredmetId")
                val predmet: Predmet? = getPredmet(predmetId)
                val nazivPredmeta = predmet?.naziv
                grupe.add(Grupa(naziv, nazivPredmeta.toString()))
            }
        }
        return grupe
    }
}