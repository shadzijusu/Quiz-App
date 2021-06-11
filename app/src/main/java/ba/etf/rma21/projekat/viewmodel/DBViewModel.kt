package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.DBRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DBViewModel {
    val scope = CoroutineScope(
        Job() + Dispatchers.Main)
    val updated = MutableLiveData<Boolean>()
    fun update(onSuccess: (uspjesno: Boolean) -> Unit,
                    onError: () -> Unit,
    context: Context) {
        scope.launch {
            DBRepository.setContext(context)
            val result = DBRepository.updateNow()
            when (result) {
                is Boolean -> {
                    onSuccess?.invoke(result)
                    updated.postValue(result!!)
                }
                else-> onError?.invoke()
            }

        }
    }
}