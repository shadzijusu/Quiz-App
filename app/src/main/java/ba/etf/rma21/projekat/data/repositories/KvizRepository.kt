package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.staticdata.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

object KvizRepository {

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
    fun addMine(kviz: Kviz) {
        dodajMoj(kviz)
    }

    fun getKviz(nazivKviza: String): Kviz {
        return dajKviz(nazivKviza)
    }

    //new
    suspend fun getAll(): List<Kviz>? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajSveKvizove()
            val responseBody = response.body()
            return@withContext responseBody
        }
    }



    suspend fun getById(id: Int): Kviz? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajKviz(id)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    suspend fun getUpisani(): List<Kviz>? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajUpisane()
            val responseBody = response.body()
            return@withContext responseBody
        }
    }
}