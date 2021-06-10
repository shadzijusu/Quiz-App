package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class KvizListViewModel() {
     val scope = CoroutineScope(
        Job() + Dispatchers.Main)
    var kvizovi = MutableLiveData<List<Kviz>>()
    var kvizoviZaGrupu = MutableLiveData<List<Kviz>>()
    var dostupne = MutableLiveData<List<Grupa>>()


    fun getMyKvizes( onSuccess: (kvizovi: List<Kviz>) -> Unit,
                onError: () -> Unit){
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = KvizRepository.getUpisani()

            // Display result of the network request to the user
            when (result) {
                is List<Kviz> -> {
                    onSuccess?.invoke(result)
                    kvizovi.postValue(result!!)
                }
                else-> onError?.invoke()
            }
        }
    }
    fun getKviz( onSuccess: (kviz: Kviz) -> Unit,
                     onError: () -> Unit, id : Int){
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = KvizRepository.getById(id)

            // Display result of the network request to the user
            when (result) {
                is Kviz -> onSuccess?.invoke(result!!)
                else-> onError?.invoke()
            }
        }
    }
    fun getAll( onSuccess: (kvizovi: List<Kviz>) -> Unit,
                     onError: () -> Unit){
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = KvizRepository.getAll()

            // Display result of the network request to the user
            when (result) {
                is List<Kviz> -> onSuccess?.invoke(result!!)
                else-> onError?.invoke()
            }
        }
    }
    fun getKvizoveZaGrupu(onSuccess: (kvizovi: List<Kviz>) -> Unit,
                          onError: () -> Unit, idGrupe : Int) {
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = KvizRepository.pomocna(idGrupe)

            // Display result of the network request to the user
            when (result) {
                is List<Kviz> -> {
                    onSuccess?.invoke(result)
                    kvizoviZaGrupu.postValue(result!!)
                }
                else-> onError?.invoke()
            }
        }
    }
    fun getDostupne(onSuccess: (grupe: List<Grupa>) -> Unit,
                    onError: () -> Unit, idKviza : Int) {
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = KvizRepository.dostupne(idKviza)

            // Display result of the network request to the user
            when (result) {
                is List<Grupa> -> {
                    onSuccess?.invoke(result)
                    dostupne.postValue(result!!)
                }
                else-> onError?.invoke()
            }
        }
    }

    fun getDone(): List<Kviz> {
        return KvizRepository.getDone()
    }

    fun getFuture(): List<Kviz> {
        return KvizRepository.getFuture()
    }

    fun getNotTaken(): List<Kviz> {
        return KvizRepository.getNotTaken()
    }

    fun addMine(kviz: Kviz) {
        KvizRepository.addMine(kviz)
    }
    fun getKviz(nazivKviza : String) : Kviz {
        return KvizRepository.getKviz(nazivKviza)
    }
}