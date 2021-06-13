package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import ba.etf.rma21.projekat.data.models.Odgovor
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
    var odgovori = MutableLiveData<List<Odgovor>>()
    var odgovoriDB = MutableLiveData<List<Odgovor>>()

    var bodovi = MutableLiveData<Int>()
    fun addOdgovor(
        onSuccess: (bodovi: Int) -> Unit,
        onError: () -> Unit,
        idKvizTaken: Int, idPitanje: Int, odgovor: Int, context: Context
    ) {
        // Create a new coroutine on the UI thread
        scope.launch {
            // Make the network call and suspend execution until it finishes
            OdgovorRepository.setContext(context)
            val result = OdgovorRepository.postaviOdgovorKviz(idKvizTaken, idPitanje, odgovor)

            // Display result of the network request to the user
            when (result) {
                is Int -> {
                    onSuccess.invoke(result)
                    bodovi.postValue(result)
                }
                else -> onError.invoke()
            }
        }
    }

    fun getOdgovori(
        onSuccess: (List<Odgovor>) -> Unit,
        onError: () -> Unit,
        idKviza: Int
    ) {
        scope.launch {
            // Make the network call and suspend execution until it finishes
            val result = OdgovorRepository.getOdgovoriKviz(idKviza)

            // Display result of the network request to the user
            when (result) {
                is List<Odgovor> -> {
                    onSuccess.invoke(result)
                    odgovori.postValue(result!!)
                }
                else -> onError.invoke()
            }
        }

    }
    fun getOdgovoriDB(
        idKvizTaken: Int,
        idKviza : Int,
        context: Context
    ) {
        scope.launch {
            // Make the network call and suspend execution until it finishes
            OdgovorRepository.setContext(context)
            val result = OdgovorRepository.getOdgovoriKvizDB(idKviza, idKvizTaken)

                    odgovoriDB.postValue(result!!)

        }

    }
    fun predaj(
        idKviza: Int,
        context : Context
    ) {
        scope.launch {
            // Make the network call and suspend execution until it finishes
            OdgovorRepository.setContext(context)
            val result = OdgovorRepository.predajOdgovore(idKviza)

        }

    }
}