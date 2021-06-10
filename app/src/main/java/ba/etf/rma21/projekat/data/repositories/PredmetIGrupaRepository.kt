package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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