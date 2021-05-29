package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Odgovor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

object OdgovorRepository {
    suspend fun getOdgovoriKviz(idKviza:Int):List<Odgovor>? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajOdgovore(idKviza)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }
}