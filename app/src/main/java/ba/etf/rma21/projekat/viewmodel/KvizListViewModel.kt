package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.Rezultat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class KvizListViewModel(private val searchDone: ((kvizovi: List<Kviz>) -> Unit)?,
                         private val onError: (()->Unit)?
) {
     val scope = CoroutineScope(
        Job() + Dispatchers.Main)
    fun getMyKvizes() {
        scope.launch{

            // Make the network call and suspend execution until it finishes
            val result = KvizRepository.getUpisani(1)

            // Display result of the network request to the user
            when (result) {
                is Rezultat.Success<*> -> searchDone?.invoke(result.data as List<Kviz>)
                else-> onError?.invoke()
            }
        }
    }

    fun getAll(){

        // Create a new coroutine on the UI thread
        scope.launch{

            // Make the network call and suspend execution until it finishes
            val result = KvizRepository.getAll()

            // Display result of the network request to the user
            when (result) {
                is Rezultat.Success<*> -> searchDone?.invoke(result.data as List<Kviz>)
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