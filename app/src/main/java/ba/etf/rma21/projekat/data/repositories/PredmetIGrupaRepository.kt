package ba.etf.rma21.projekat.data.repositories

import androidx.lifecycle.MutableLiveData
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

sealed class Rezultat<out R> {
    data class Success<out T>(val data: T) : Rezultat<T>()
    data class Error(val exception: Exception) : Rezultat<Nothing>()
}

object PredmetIGrupaRepository {
    var predmetZaGrupu = MutableLiveData<Predmet?>()
    suspend fun getPredmeti(): List<Predmet>? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajSvePredmete()
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

    suspend fun upisiUGrupu(idGrupa: Int): ResponseBody? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.upisiStudentaUGrupu(idGrupa)
            val responseBody = response.body()
            return@withContext responseBody
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