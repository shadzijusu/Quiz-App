package ba.etf.rma21.projekat.viewmodel

import android.content.Context
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


class PredmetListViewModel() {
    val scope = CoroutineScope(
        Job() + Dispatchers.Main
    )
    var predmeti = MutableLiveData<List<Predmet>?>()
    var grupeZaPredmet = MutableLiveData<List<Grupa>?>()
    var grupe = MutableLiveData<List<Grupa?>?>()
    var predmet = MutableLiveData<Predmet>()
    fun getUpisani(): List<Predmet> {
        return PredmetRepository.getUpisani()
    }

    fun getAll(
        onSuccess: (predmeti: List<Predmet>) -> Unit,
        onError: () -> Unit
    ) {
        // Create a new coroutine on the UI thread
        scope.launch {
            // Make the network call and suspend execution until it finishes
            val result = PredmetIGrupaRepository.getPredmeti()

            // Display result of the network request to the user
            when (result) {
                is List<Predmet> -> {
                    onSuccess?.invoke(result)
                    predmeti.postValue(result!!)
                }
                else -> onError?.invoke()
            }
        }
    }

    fun getAllGroups(
        onSuccess: (grupe: List<Grupa>) -> Unit,
        onError: () -> Unit
    ) {
        // Create a new coroutine on the UI thread
        scope.launch {
            // Make the network call and suspend execution until it finishes
            val result = PredmetIGrupaRepository.getGrupe()

            // Display result of the network request to the user
            when (result) {
                is List<Grupa> -> {
                    onSuccess?.invoke(result)
                    grupe.postValue(result)
                }
                else -> onError?.invoke()
            }
        }
    }

    fun getGrupeZaPredmet(
        onSuccess: (grupe: List<Grupa>) -> Unit,
        onError: () -> Unit, idPredmeta: Int
    ) {
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

    fun upisiStudenta(
        onSuccess: (uspjesno: Boolean) -> Unit,
        onError: () -> Unit, idGrupe: Int, context: Context
    ) {
        // Create a new coroutine on the UI thread
        scope.launch {
            // Make the network call and suspend execution until it finishes
            PredmetIGrupaRepository.setContext(context)
            val result = PredmetIGrupaRepository.upisiUGrupu(idGrupe)

            // Display result of the network request to the user
            when (result) {
                is Boolean -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun getUpisaneGrups(
        onSuccess: (grupe: List<Grupa>) -> Unit,
        onError: () -> Unit
    ) {
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

    fun getPredmet(
        onSuccess: (predmet: Predmet) -> Unit,
        onError: () -> Unit,
        predmetId : Int
    ) {
        // Create a new coroutine on the UI thread
        scope.launch {
            // Make the network call and suspend execution until it finishes
            val result = PredmetIGrupaRepository.getPredmet(predmetId)

            // Display result of the network request to the user
            when (result) {
                is Predmet -> {
                    onSuccess?.invoke(result)
                    predmet.postValue(result!!)
                }
                else -> onError?.invoke()
            }
        }


    }
}