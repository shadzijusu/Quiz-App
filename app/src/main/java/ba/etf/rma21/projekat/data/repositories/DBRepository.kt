package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class DBRepository {
    companion object{
        private lateinit var context:Context
        fun setContext(_context: Context){
            context=_context
        }
        suspend fun updateNow(): Boolean? {
            return withContext(Dispatchers.IO) {
                var dan = Calendar.getInstance().time.day
                var mjesec = Calendar.getInstance().time.month
                var godina = Calendar.getInstance().time.year
                var vrijeme = "2021-06-10T12:00:00"
                var datum = "$godina-$mjesec-$dan$vrijeme"
                var response = ApiAdapter.retrofit.update(datum)
                val responseBody = response.body()
                val ima: String? = responseBody?.get("changed").toString()
                return@withContext ima == "true"
            }
        }
    }
}