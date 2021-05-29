package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.KvizTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object TakeKvizRepository {
    suspend fun zapocniKviz(idKviza: Int): KvizTaken? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.zapocniKviz(idKviza)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    suspend fun getPocetiKvizovi(): List<KvizTaken>? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajZapocete()
            val responseBody = response.body()
            return@withContext responseBody
        }
    }
}