package ba.etf.rma21.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Predmet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.*

@SuppressLint("StaticFieldLeak")
object PredmetIGrupaRepository {
    private lateinit var context: Context
    fun setContext(_context:Context){
        context=_context
    }
    suspend fun getPredmeti(): List<Predmet>? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajSvePredmete()
            val responseBody = response.body()
            return@withContext responseBody
        }
    }
    suspend fun getPredmet(predmetId : Int): Predmet? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajPredmet(predmetId)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }
    suspend fun getGrupe(): List<Grupa>? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajSveGrupe()
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    suspend fun getGrupeZaPredmet(idPredmeta: Int): List<Grupa>? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajGrupeZaPredmet(idPredmeta)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    suspend fun upisiUGrupu(idGrupa: Int): Boolean? {
        return withContext(Dispatchers.IO) {
                var response = ApiAdapter.retrofit.upisiStudentaUGrupu(idGrupa)

            var grupa = ApiAdapter.retrofit.dajGrupu(idGrupa).body()
            var db = AppDatabase.getInstance(context)


            if (grupa != null) {
                db.grupaDao().upisiUGrupu(idGrupa, grupa.naziv, grupa.PredmetId)

                AccountRepository.setContext(context)
                db.accountDao().setLastUpdate(
                    db.accountDao().getHash(),
                    (Calendar.getInstance().time.toString())
                )
            }
            //predmet
            if(grupa != null) {
                var predmet = ApiAdapter.retrofit.dajPredmet(grupa.PredmetId).body()
                var db = AppDatabase.getInstance(context)
                if (predmet != null) {
                    db.predmetDao().dodajPredmet(predmet.id, predmet.naziv, predmet.godina)

                    //kvizove za tu grupu
                    if (grupa != null) {
                        var grupe = db.grupaDao().getGrupe()
                        var kvizovi = arrayListOf<Kviz>()
                        for(grup in grupe) {
                            var quizes = ApiAdapter.retrofit.dajUpisane(grup.id).body()
                            var tajPredmet = ApiAdapter.retrofit.dajPredmet(grup.PredmetId)
                            if (quizes != null) {
                                for(kv in quizes)
                                    kv.nazivPredmeta = tajPredmet.body()!!.naziv
                                    if (quizes != null) {
                                        kvizovi.addAll(quizes)
                                    }
                            }
                        }

                        if (kvizovi != null) {
                            db.kvizDao().insertAll(kvizovi)
                        }
                        //pitanja za kviz dodajemo u bazu
                        for(kviz in kvizovi) {
                            var pitanja = PitanjeKvizRepository.getPitanja(kviz.id)
                            println(pitanja)
                            if (pitanja != null) {
                                for(pitanje in pitanja)
                                    pitanje.kvizId = kviz.id
                            }
                            if (pitanja != null) {
                                db.pitanjeDao().insertAll(pitanja)
                            }
                        }
                    }
                }
            }
            val responseBody = response.body()
            if (responseBody != null) {
                if(responseBody.message.contains("Ne postoji account", true) ||
                    responseBody.message.contains("Grupa not found"))
                    return@withContext false
            }
            return@withContext true
        }
    }

    suspend fun getUpisaneGrupe(): List<Grupa>? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajStudentoveGrupe()
            val responseBody = response.body()
            return@withContext responseBody
        }
    }
}