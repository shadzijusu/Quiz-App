package ba.etf.rma21.projekat.data.repositories

import androidx.lifecycle.MutableLiveData
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

object PredmetIGrupaRepository {
    var predmetZaGrupu = MutableLiveData<Predmet?>()
    suspend fun getPredmeti(): Rezultat<List<Predmet>> {
        return withContext(Dispatchers.IO) {
            val predmeti = arrayListOf<Predmet>()
            var url1 = "https://rma21-etf.herokuapp.com/predmet"
            try {
                val url = URL(url1)
                (url.openConnection() as? HttpURLConnection)?.run {
                    val result = this.inputStream.bufferedReader().use { it.readText() }
                    val jArray = JSONArray(result)
                    for (i in 0 until jArray.length()) {
                        val predmet = jArray.getJSONObject(i)
                        val id = predmet.getInt("id")
                        val naziv = predmet.getString("naziv")
                        val godina = predmet.getInt("godina")
                        predmeti.add(Predmet(id, naziv, godina))
                    }
                }
                return@withContext Rezultat.Success(predmeti);
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

    suspend fun getPredmet(idPredmeta: Int): Rezultat<Predmet?> {
        return withContext(Dispatchers.IO) {
            var predmet: Predmet? = null
            var url1 = "https://rma21-etf.herokuapp.com/predmet/$idPredmeta"
            try {
                val url = URL(url1)
                (url.openConnection() as? HttpURLConnection)?.run {
                    val result = this.inputStream.bufferedReader().use { it.readText() }
                    println(result)
                    val jo = JSONObject(result)
                    val id = jo.getInt("id")
                    val naziv = jo.getString("naziv")
                    val godina = jo.getInt("godina")
                    if (id == idPredmeta)
                        predmet = Predmet(id, naziv, godina)
                    predmetZaGrupu.postValue(predmet)
                }
                return@withContext Rezultat.Success(predmet);
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

suspend fun getGrupe(): Rezultat<List<Grupa>> {
    return withContext(Dispatchers.IO) {
        val grupe = arrayListOf<Grupa>()
        val url1 = "https://rma21-etf.herokuapp.com/grupa"
        try {
            val url = URL(url1)
            (url.openConnection() as? HttpURLConnection)?.run {
                val result = this.inputStream.bufferedReader().use { it.readText() }
                val jArray = JSONArray(result)
                for (i in 0 until jArray.length()) {
                    val grupa = jArray.getJSONObject(i)
                    val id = grupa.getInt("id")
                    val naziv = grupa.getString("naziv")
                    val predmetId = grupa.getInt("PredmetId")
                    getPredmet(predmetId)
                    predmetZaGrupu.value?.naziv?.let { Grupa(id, naziv, it) }?.let { grupe.add(it) }
                }
            }
            return@withContext Rezultat.Success(grupe);
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

suspend fun getGrupeZaPredmet(idPredmeta: Int): Rezultat<List<Grupa>> {
    return withContext(Dispatchers.IO) {
        val grupe = arrayListOf<Grupa>()
        val url1 = "https://rma21-etf.herokuapp.com/predmet/$idPredmeta/grupa"
        try {
            val url = URL(url1)
            (url.openConnection() as? HttpURLConnection)?.run {
                val result = this.inputStream.bufferedReader().use { it.readText() }
                val jArray = JSONArray(result)
                for (i in 0 until jArray.length()) {
                    val grupa = jArray.getJSONObject(i)
                    val id = grupa.getInt("id")
                    val naziv = grupa.getString("naziv")
                    val predmetId = grupa.getInt("PredmetId")
                    getPredmet(predmetId)
                    predmetZaGrupu.value?.naziv?.let { Grupa(id, naziv, it) }?.let { grupe.add(it) }
                }
            }
            return@withContext Rezultat.Success(grupe);
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

suspend fun upisiUGrupu(idGrupa: Int): Rezultat<Boolean> {
    return withContext(Dispatchers.IO) {
    val url1 =
        "https://rma21-etf.herokuapp.com/grupa/$idGrupa/student/9d53bd38-18d2-49ec-889f-703ab44db589"
        try {
    val url = URL(url1)
    var upisan = false
    (url.openConnection() as? HttpURLConnection)?.run {
        upisan = true
    }
        return@withContext Rezultat.Success(upisan);
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

suspend fun getUpisaneGrupe(): Rezultat<List<Grupa>> {
    return withContext(Dispatchers.IO) {
    val grupe = arrayListOf<Grupa>()
    val url1 =
        "https://rma21-etf.herokuapp.com/student/9d53bd38-18d2-49ec-889f-703ab44db589/grup"
        try {
    val url = URL(url1)
    (url.openConnection() as? HttpURLConnection)?.run {
        val result = this.inputStream.bufferedReader().use { it.readText() }
        val jo = JSONObject(result)
        val results = jo.getJSONArray("results")
        for (i in 0 until results.length()) {
            val grupa = results.getJSONObject(i)
            val id = grupa.getInt("id")
            val naziv = grupa.getString("naziv")
            val predmetId = grupa.getInt("PredmetId")
            getPredmet(predmetId)
            predmetZaGrupu.value?.naziv?.let { Grupa(id, naziv, it) }?.let { grupe.add(it) }
        }
    }
            return@withContext Rezultat.Success(grupe);
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
    }}
}