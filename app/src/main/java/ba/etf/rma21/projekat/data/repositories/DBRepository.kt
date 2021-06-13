package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
import kotlinx.coroutines.*
import java.util.*

class DBRepository {
    companion object{
        private lateinit var context:Context
        fun setContext(_context: Context){
            context=_context
        }
        suspend fun updateNow(): Boolean? {
            return withContext(Dispatchers.IO) {
                var db = AppDatabase.getInstance(context)
                    var acHash = db.accountDao().getHash()
                    var lastUpdate = db.accountDao().getLastUpdate()
                    var responseBody = ApiAdapter.retrofit.update(acHash, lastUpdate).body()
                if(responseBody?.message?.contains("Ne postoji account") == true)
                    return@withContext false
                else {
                    var ima = responseBody?.changed.toString()
                    return@withContext ima == "true"
                }
            }
        }
    }
}