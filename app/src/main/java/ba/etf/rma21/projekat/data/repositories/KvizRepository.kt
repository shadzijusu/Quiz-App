package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.staticdata.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

sealed class Rezultat<out R> {
    data class Success<out T>(val data: T) : Rezultat<T>()
    data class Error(val exception: Exception) : Rezultat<Nothing>()
}

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
    suspend fun getAll(): Rezultat<List<Kviz>> {
        return withContext(Dispatchers.IO) {
            var kvizovi = arrayListOf<Kviz>()
            var url1 = "https://rma21-etf.herokuapp.com/kviz"
            try {
                val url = URL(url1)
                (url.openConnection() as? HttpURLConnection)?.run {
                    val result = this.inputStream.bufferedReader().use { it.readText() }
                    val jArray = JSONArray(result)
                    for (i in 0 until jArray.length()) {
                        val kviz = jArray.getJSONObject(i)
                        val naziv = kviz.getString("naziv")
                        val datumPocetak = kviz.getString("datumPocetak")
                        var dP = Date(0, 0, 0)
                        var dK = Date(0, 0, 0)
                        var dijelovi: List<String>
                        if (datumPocetak != "null") {
                            dijelovi = datumPocetak.split("-")
                            val g1: Int = dijelovi[0].toInt()
                            val m1: Int = dijelovi[1].toInt()
                            val d1: Int = dijelovi[2].toInt()
                            dP = Date(g1, m1, d1)
                        }
                        val datumKraj = kviz.getString("datumKraj")
                        if (datumKraj != "null") {
                            dijelovi = datumKraj.split("-")
                            val g2: Int = dijelovi[0].toInt()
                            val m2: Int = dijelovi[1].toInt()
                            val d2: Int = dijelovi[2].toInt()

                            dK = Date(g2, m2, d2)
                        }
                        val trajanje = kviz.getInt("trajanje")
                        //nazivGrupe i osvojeni bodovi uraditi
                        kvizovi.add(
                            Kviz(
                                naziv, "", dP, Date(0, 0, 0),
                                dK, trajanje, "", 0f
                            )
                        )
                    }
                }
                return@withContext Rezultat.Success(kvizovi);
            } catch (e: MalformedURLException) {
                return@withContext Rezultat.Error(Exception("Cannot open HttpURLConnection"))
            } catch (e: IOException) {
                return@withContext Rezultat.Error(Exception("Cannot read stream"))
            } catch (e: JSONException) {
                return@withContext Rezultat.Error(Exception("Cannot parse JSON"))
            }
        }
    }

    suspend fun getById(id: Int): Rezultat<Kviz?> {
        return withContext(Dispatchers.IO) {
            var kviz: Kviz? = null
            var url1 = "https://rma21-etf.herokuapp.com/kviz/$id"
            try {
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
                return@withContext Rezultat.Success(kviz);
            } catch (e: MalformedURLException) {
                return@withContext Rezultat.Error(Exception("Cannot open HttpURLConnection"))
            } catch (e: IOException) {
                return@withContext Rezultat.Error(Exception("Cannot read stream"))
            } catch (e: JSONException) {
                return@withContext Rezultat.Error(Exception("Cannot parse JSON"))
            }
        }
    }

    suspend fun getUpisani(id: Int): Rezultat<List<Kviz>> {
        return withContext(Dispatchers.IO) {
            var kvizovi = arrayListOf<Kviz>()
            var url1 = "https://rma21-etf.herokuapp.com/grupa/$id/kvizovi"
            try {
                val url = URL(url1)
                (url.openConnection() as? HttpURLConnection)?.run {
                    val result = this.inputStream.bufferedReader().use { it.readText() }
                    val jArray = JSONArray(result)
                    for (i in 0 until jArray.length()) {//7
                        val kviz = jArray.getJSONObject(i)
                        val naziv = kviz.getString("naziv")
                        val datumPocetak = kviz.getString("datumPocetak")
                        var dP = Date(0, 0, 0)
                        var dK = Date(0, 0, 0)
                        var dijelovi: List<String>
                        if (datumPocetak != "null") {
                            dijelovi = datumPocetak.split("-")
                            val g1: Int = dijelovi[0].toInt()
                            val m1: Int = dijelovi[1].toInt()
                            val d1: Int = dijelovi[2].toInt()

                            dP = Date(g1, m1, d1)
                        }
                        val pattern = "dd.MM.yyyy"
                        val simpleDateFormat = SimpleDateFormat(pattern)
                        val datumKraj = kviz.getString("datumKraj")
                        if (datumKraj != "null") {
                            dijelovi = datumKraj.split("-")
                            val g2: Int = dijelovi[0].toInt()
                            val m2: Int = dijelovi[1].toInt()
                            val d2: Int = dijelovi[2].toInt()

                            dK = Date(g2, m2, d2)
                        }
                        val trajanje = kviz.getInt("trajanje")
                        kvizovi.add(
                            Kviz(
                                naziv, "", dP, Date(0, 0, 0),
                                dK, trajanje, "", 0f
                            )
                        )
                    }
                }
                return@withContext Rezultat.Success(kvizovi);
            } catch (e: MalformedURLException) {
                println("HttpURLConnection")
                return@withContext Rezultat.Error(Exception("Cannot open HttpURLConnection"))
            } catch (e: IOException) {
                println("HttpURLConnection")
                return@withContext Rezultat.Error(Exception("Cannot read stream"))
            } catch (e: JSONException) {
                println("Parse")
                return@withContext Rezultat.Error(Exception("Cannot parse JSON"))
            }
        }
    }
}