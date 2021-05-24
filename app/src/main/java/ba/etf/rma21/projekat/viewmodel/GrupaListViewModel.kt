package ba.etf.rma21.projekat.viewmodel

import androidx.lifecycle.MutableLiveData
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.GrupaRepository
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository
import ba.etf.rma21.projekat.data.repositories.Rezultat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class GrupaListViewModel(private val searchDone: ((grupe: List<Grupa>) -> Unit)?,
                           private val onError: (()->Unit)?
) {
    val scope = CoroutineScope(
        Job() + Dispatchers.Main)
    var grupe = MutableLiveData<List<Grupa>>()

    fun getGroupsByPredmet(nazivPredmeta: String): List<Grupa> {
        return GrupaRepository.getGroupsByPredmet(nazivPredmeta)
    }

    fun getGrupeZaPredmet(predmetId : Int) {
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = PredmetIGrupaRepository.getGrupeZaPredmet(predmetId)

            // Display result of the network request to the user
            when (result) {
                is Rezultat.Success<*> -> {
                    searchDone?.invoke(result.data as List<Grupa>)
                    grupe.postValue(result.data as List<Grupa>)
                }
                else-> onError?.invoke()
            }
        }
    }

}