package ba.etf.rma21.projekat.viewmodel

import androidx.lifecycle.MutableLiveData
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.OdgovorRepository
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OdgovorViewModel {
    val scope = CoroutineScope(
        Job() + Dispatchers.Main
    )
    var bodovi = MutableLiveData<Int>()
    fun addOdgovor(
        onSuccess: (bodovi: Int) -> Unit,
        onError: () -> Unit,
        idKvizTaken: Int, idPitanje: Int, odgovor: Int
    ) {
        // Create a new coroutine on the UI thread
        scope.launch {
            // Make the network call and suspend execution until it finishes
            val result = OdgovorRepository.postaviOdgovorKviz(idKvizTaken, idPitanje, odgovor)

            // Display result of the network request to the user
            when (result) {
                is Int -> {
                    onSuccess?.invoke(result)
                    bodovi.postValue(result!!)
                }
                else -> onError?.invoke()
            }
        }
    }
}