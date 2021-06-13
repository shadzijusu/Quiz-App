package ba.etf.rma21.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random


@SuppressLint("StaticFieldLeak")
object OdgovorRepository {
    private lateinit var context: Context
    fun setContext(_context: Context){
        context=_context
    }
    suspend fun getOdgovoriKviz(idKviza: Int): List<Odgovor>? {
        return withContext(Dispatchers.IO) {
            var idKvizTaken : Int = 0
            var responseTaken = ApiAdapter.retrofit.dajZapocete().body()
            if (responseTaken != null) {
                for(taken in responseTaken) {
                    if(taken.KvizId == idKviza)
                        idKvizTaken = taken.id
                }
            }
            var response = ApiAdapter.retrofit.dajOdgovore(idKvizTaken)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }
    suspend fun postaviOdgovorKviz(idKvizTaken: Int, idPitanje: Int, odgovor: Int) : Int {
        return withContext(Dispatchers.IO) {
            var db = AppDatabase.getInstance(context)
            var pitanje = db.odgovorDao().getOdgovor(idPitanje)
            var bodovi = 0f
            var responseTaken = listOf<KvizTaken>()
            launch {
                responseTaken = ApiAdapter.retrofit.dajZapocete().body()!!
            }
            delay(1000)
            var kvizId = 0
            if(responseTaken != null) {
                for (kvizTaken in responseTaken) {
                    if (kvizTaken.id == idKvizTaken) {
                        kvizId = kvizTaken.KvizId
                    }
                }
            }
            var pitanja = listOf<Pitanje>()

            if(kvizId != 0) {
                PitanjeKvizRepository.setContext(context)
                launch {
                    pitanja = PitanjeKvizRepository.getPitanjaDB(kvizId)!!
                }
                delay(1000)
            }
            if(idKvizTaken != 0) {
                var odgovori = listOf<Odgovor>()
                launch {
                    odgovori = db.odgovorDao().getOdgovore(idKvizTaken)
                }
                delay(1000)
                var brojTacnih = 0
                for (odgovor in odgovori) {
                    for (pitanje in pitanja)
                        if (pitanje.id == odgovor.PitanjeId) {
                            if (odgovor.odgovoreno == pitanje.tacan)
                                brojTacnih++
                            break
                        }
                }
                if(pitanje != idPitanje) {
                    for (pitanje in pitanja) {
                        if (pitanje.id == idPitanje) {
                            if (odgovor == pitanje.tacan)
                                brojTacnih++
                            break
                        }
                    }
                }

                bodovi = brojTacnih.toFloat() / pitanja.size.toFloat()
            }
                var percentage = (bodovi * 100).toInt()
            if(pitanje != idPitanje)
                db.odgovorDao().dodajOdgovor(Random.nextInt(), odgovor, idPitanje, kvizId, idKvizTaken, percentage)
            return@withContext percentage
        }
    }
    suspend fun predajOdgovore(idKviz : Int) {
        withContext(Dispatchers.IO) {
            var greska = 0
            var db = AppDatabase.getInstance(context)
            var odgovori = db.odgovorDao().dajOdgovoreZaKviz(idKviz)
            for(odg in  odgovori) {
            val jsonObject = JsonObject()
                jsonObject.addProperty("odgovor", odg.odgovoreno)
                jsonObject.addProperty("pitanje", odg.PitanjeId)
                jsonObject.addProperty("bodovi", odg.bodovi)
                var result = ApiAdapter.retrofit.dodajOdgovor(odg.KvizTakenId, jsonObject)
                var resultBody = result.body()
                if (resultBody != null) {
                    if (resultBody.message != null) greska = -1
                    }
                }
            if(greska == 0) {
                db.kvizDao().predaj(idKviz)
            }

        }
    }
//    suspend fun postaviOdgovorKviz(idKvizTaken: Int, idPitanje: Int, odgovor: Int): Int? {
//        return withContext(Dispatchers.IO) {
//            var bodovi = 0f
//            var responseTaken = listOf<KvizTaken>()
//            launch {
//                responseTaken = ApiAdapter.retrofit.dajZapocete().body()!!
//            }
//            delay(1000)
//            var kvizId = 0
//            if(responseTaken != null) {
//                for (kvizTaken in responseTaken) {
//                    if (kvizTaken.id == idKvizTaken) {
//                        kvizId = kvizTaken.KvizId
//                    }
//                }
//            }
//            var pitanja = listOf<Pitanje>()
//            if(kvizId != 0) {
//                launch {
//                    pitanja = PitanjeKvizRepository.getPitanja(kvizId)!!
//                }
//                delay(1000)
//            }
//            if(idKvizTaken != 0) {
//                var odgovori = listOf<Odgovor>()
//                launch {
//                    odgovori = ApiAdapter.retrofit.dajOdgovore(idKvizTaken).body()!!
//                }
//                delay(1000)
//                var brojTacnih = 0
//                for (odgovor in odgovori) {
//                    for (pitanje in pitanja)
//                        if (pitanje.id == odgovor.PitanjeId) {
//                            if (odgovor.odgovoreno == pitanje.tacan)
//                                brojTacnih++
//                            break
//                        }
//                }
//                for (pitanje in pitanja) {
//                    if (pitanje.id == idPitanje) {
//                        if (odgovor == pitanje.tacan)
//                            brojTacnih++
//                        break
//                    }
//                }
//                bodovi = brojTacnih.toFloat() / pitanja.size.toFloat()
//            }
//                var percentage = (bodovi * 100).toInt()
//                val jsonObject = JsonObject()
//                jsonObject.addProperty("odgovor", odgovor)
//                jsonObject.addProperty("pitanje", idPitanje)
//                jsonObject.addProperty("bodovi", percentage)
//                var result = ApiAdapter.retrofit.dodajOdgovor(idKvizTaken, jsonObject)
//                var resultBody = result.body()
//                if (resultBody != null) {
//                    if (resultBody.message != null) return@withContext -1
//                }
//                return@withContext percentage
//            }

}
