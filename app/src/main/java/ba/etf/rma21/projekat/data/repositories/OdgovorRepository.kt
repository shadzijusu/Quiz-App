package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object OdgovorRepository {
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

    suspend fun postaviOdgovorKviz(idKvizTaken: Int, idPitanje: Int, odgovor: Int): Int? {
        return withContext(Dispatchers.IO) {
            var bodovi = 0f
            var responseTaken = listOf<KvizTaken>()
            launch {
                responseTaken = ApiAdapter.retrofit.dajZapocete().body()!!
            }
            delay(500)
            var kvizId = 0
            for (kvizTaken in responseTaken) {
                if (kvizTaken.id == idKvizTaken) {
                    kvizId = kvizTaken.KvizId
                }
            }

            var pitanja = listOf<Pitanje>()

            launch {
                pitanja = ApiAdapter.retrofit.dajPitanja(kvizId).body()!!
            }
            delay(500)

            var odgovori = listOf<Odgovor>()
            launch {
                odgovori = ApiAdapter.retrofit.dajOdgovore(idKvizTaken).body()!!
            }

                var brojTacnih = 0
                for (pitanje in pitanja) {
                    if (pitanje.id == idPitanje) {
                        if (odgovor == pitanje.tacan)
                            brojTacnih++
                    } else {
                        for (odgovor in odgovori) {
                            if (pitanje.id == odgovor.PitanjeId) {
                                if (odgovor.odgovoreno == pitanje.tacan)
                                    brojTacnih++
                            }
                        }
                    }
            }
            bodovi = brojTacnih.toFloat()/pitanja.size.toFloat()
            var percentage = (bodovi*100).toInt()
            val jsonObject = JsonObject()
            jsonObject.addProperty("odgovor", odgovor)
            jsonObject.addProperty("pitanje", idPitanje)
            jsonObject.addProperty("bodovi", bodovi)
            var result = ApiAdapter.retrofit.dodajOdgovor(idKvizTaken, jsonObject)
        return@withContext percentage
    }
}
}
