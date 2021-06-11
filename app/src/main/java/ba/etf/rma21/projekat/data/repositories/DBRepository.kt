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
                var mjesec = Calendar.getInstance().time.month + 1
                var godina = (Calendar.getInstance().time.year) + 1900
                var vrijeme = Calendar.getInstance().time.time
                println(vrijeme)
                var datum = "$godina-$mjesec-$dan$vrijeme"
                //slati datum posljednjeg azuriranja
                AccountRepository.setContext(context)
                var response = ApiAdapter.retrofit.update(AccountRepository.getHash(), datum)
                val ima: String? = response?.get("changed").asString
                return@withContext ima == "true"
            }
        }
    }
}