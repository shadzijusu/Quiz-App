package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import ba.etf.rma21.projekat.data.repositories.AccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AccountViewModel {
    val scope = CoroutineScope(
        Job() + Dispatchers.Main)
    fun upisi(acHash : String, onSuccess: (uspjesno : Boolean) -> Unit,
              onError: () -> Unit){
        scope.launch{
            val result = AccountRepository.postaviHash(acHash)
            when (result) {
                is Boolean -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }
}