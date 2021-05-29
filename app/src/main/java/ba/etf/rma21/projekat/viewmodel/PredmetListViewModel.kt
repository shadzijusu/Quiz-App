package ba.etf.rma21.projekat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository
import ba.etf.rma21.projekat.data.repositories.PredmetRepository
import kotlinx.coroutines.*
import okhttp3.ResponseBody


class PredmetListViewModel(private val searchDone: ((predmeti: List<Predmet>) -> Unit)?,
                        private val onError: (()->Unit)?
) {
    val scope = CoroutineScope(
        Job() + Dispatchers.Main)
    var predmeti = MutableLiveData<List<Predmet>?>()
    var grupeZaPredmet = MutableLiveData<List<Grupa>?>()
    fun getUpisani(): List<Predmet> {
        return PredmetRepository.getUpisani()
    }

    fun getAll( onSuccess: (predmeti: List<Predmet>) -> Unit,
                     onError: () -> Unit){
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = PredmetIGrupaRepository.getPredmeti()

            // Display result of the network request to the user
            when (result) {
                is List<Predmet> -> {
                    onSuccess?.invoke(result)
                    predmeti.postValue(result!!)
                }
                else-> onError?.invoke()
            }
        }
    }
    fun getAllGroups( onSuccess: (grupe: List<Grupa>) -> Unit,
                      onError: () -> Unit) {
        // Create a new coroutine on the UI thread
        scope.launch {
            // Make the network call and suspend execution until it finishes
            val result = PredmetIGrupaRepository.getGrupe()

            // Display result of the network request to the user
            when (result) {
                is List<Grupa> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }
    fun getGrupeZaPredmet( onSuccess: (grupe: List<Grupa>) -> Unit,
                      onError: () -> Unit, idPredmeta : Int) {
        // Create a new coroutine on the UI thread
        scope.launch {
            // Make the network call and suspend execution until it finishes
            val result = PredmetIGrupaRepository.getGrupeZaPredmet(idPredmeta)

            // Display result of the network request to the user
            when (result) {
                is List<Grupa> -> {
                    onSuccess?.invoke(result)
                    grupeZaPredmet.postValue(result)
                }
                else -> onError?.invoke()
            }
        }
    }
    fun upisiStudenta( onSuccess: (uspjesno: ResponseBody) -> Unit,
                           onError: () -> Unit, idGrupe : Int) {
        // Create a new coroutine on the UI thread
        scope.launch {
            // Make the network call and suspend execution until it finishes
            val result = PredmetIGrupaRepository.upisiUGrupu(idGrupe)

            // Display result of the network request to the user
            when (result) {
                is ResponseBody -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }
    fun getUpisaneGrups( onSuccess: (grupe: List<Grupa>) -> Unit,
                       onError: () -> Unit) {
        // Create a new coroutine on the UI thread
        scope.launch {
            // Make the network call and suspend execution until it finishes
            val result = PredmetIGrupaRepository.getUpisaneGrupe()

            // Display result of the network request to the user
            when (result) {
                is List<Grupa> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }


}