package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Odgovor
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject


object OdgovorRepository {
    suspend fun getOdgovoriKviz(idKviza: Int): List<Odgovor>? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajOdgovore(idKviza)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    suspend fun postaviOdgovorKviz(idKvizTaken: Int, idPitanje: Int, odgovor: Int): ResponseBody? {
        return withContext(Dispatchers.IO) {
            val jsonObject = JsonObject()
            jsonObject.addProperty("odgovor", odgovor)
            jsonObject.addProperty("pitanje", idPitanje)
            jsonObject.addProperty("bodovi", 0)
            var response = ApiAdapter.retrofit.dodajOdgovor(idKvizTaken, jsonObject)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }
}