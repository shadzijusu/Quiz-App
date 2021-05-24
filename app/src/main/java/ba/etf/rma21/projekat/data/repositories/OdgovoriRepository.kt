package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Odgovor
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class OdgovoriRepository {
    companion object {
        fun getOdgovoriKviz(idKviza: Int): List<Odgovor> {
            var odgovori = arrayListOf<Odgovor>()
            var url1 =
                "https://rma21-etf.herokuapp.com/student/9d53bd38-18d2-49ec-889f-703ab44db589/kviztaken/$idKviza/odgovori"
            val url = URL(url1)
            (url.openConnection() as? HttpURLConnection)?.run {
                val result = this.inputStream.bufferedReader().use { it.readText() }
                val jo = JSONObject(result)
                val results = jo.getJSONArray("results")
                for (i in 0 until results.length()) {//7
                    val odgovor = results.getJSONObject(i)
                    val odgovoreno = odgovor.getInt("odgovoreno")
                    odgovori.add(Odgovor(odgovoreno))
                }
            }
            return odgovori
        }
//        fun postaviOdgovorKviz(idKvizTaken:Int,idPitanje:Int,odgovor:Int):Int {
//            var url1 = "https://rma21-etf.herokuapp.com/student/9d53bd38-18d2-49ec-889f-703ab44db589/kviztaken/$idKvizTaken/$odgovor"
//        }
    }
}