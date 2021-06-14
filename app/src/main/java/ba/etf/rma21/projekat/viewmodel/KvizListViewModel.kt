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


class KvizListViewModel {
     val scope = CoroutineScope(
        Job() + Dispatchers.Main)
    var predan = MutableLiveData<Boolean>(false)
    var kvizovi = MutableLiveData<List<Kviz>>()
    var kvizoviDB = MutableLiveData<List<Kviz>>()
    var bodovi = MutableLiveData<Float>()
    var future = MutableLiveData<List<Kviz>>()
    var notTaken = MutableLiveData<List<Kviz>>()

    var kvizoviZaGrupu = MutableLiveData<List<Kviz>>()
    var dostupne = MutableLiveData<List<Grupa>>()

    fun getMyDB(onSuccess: (kvizovi: List<Kviz>) -> Unit,
                onError: () -> Unit,
    context: Context) {
        scope.launch {
            KvizRepository.setContext(context)
            val result = KvizRepository.getMyDb()
            when (result) {
                is List<*> -> {
                    onSuccess.invoke(result as List<Kviz>)
                    kvizoviDB.postValue(result as List<Kviz>?)
                }
                else -> onError.invoke()
            }

        }
    }
    fun getDone(onSuccess: (kvizovi: List<Kviz>) -> Unit,
                onError: () -> Unit,
                context: Context) {
        scope.launch {
            KvizRepository.setContext(context)
            val result = KvizRepository.getDone()
            when (result) {
                is List<*> -> {
                    onSuccess.invoke(result as List<Kviz>)
                }
                else -> onError.invoke()
            }

        }
    }
    fun isPredan(kvizId: Int,
                context: Context) {
        scope.launch {
            KvizRepository.setContext(context)
            val result = KvizRepository.isPredan(kvizId)
            predan.postValue(result)

        }
    }
    fun getBodove(kvizId: Int,
                 context: Context) {
        scope.launch {
            KvizRepository.setContext(context)
            val result = KvizRepository.getBodove(kvizId)
            bodovi.postValue(result!!)

        }
    }

    fun getMyKvizes( onSuccess: (kvizovi: List<Kviz>) -> Unit,
                onError: () -> Unit){
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = KvizRepository.getUpisani()

            // Display result of the network request to the user
            when (result) {
                is List<Kviz> -> {
                    onSuccess.invoke(result)
                    kvizovi.postValue(result!!)
                }
                else-> onError.invoke()
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
                is Kviz -> onSuccess.invoke(result)
                else-> onError.invoke()
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
                is List<Kviz> -> onSuccess.invoke(result)
                else-> onError.invoke()
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
                    onSuccess.invoke(result)
                    kvizoviZaGrupu.postValue(result!!)
                }
                else-> onError.invoke()
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
                    onSuccess.invoke(result)
                    dostupne.postValue(result!!)
                }
                else-> onError.invoke()
            }
        }
    }


    fun getFuture(onSuccess: (kvizovi: List<Kviz>) -> Unit,
                  onError: () -> Unit, context: Context) {
            scope.launch{
                // Make the network call and suspend execution until it finishes
                KvizRepository.setContext(context)
                val result = KvizRepository.getFuture()

                // Display result of the network request to the user
                when (result) {
                    is List<Kviz> -> {
                        onSuccess.invoke(result)
                        future.postValue(result!!)
                    }
                    else-> onError.invoke()
                }
            }
    }

    fun getNotTaken(onSuccess: (kvizovi: List<Kviz>) -> Unit,
                  onError: () -> Unit, context: Context) {
        scope.launch{
            // Make the network call and suspend execution until it finishes
            KvizRepository.setContext(context)
            val result = KvizRepository.getNotTaken()

            // Display result of the network request to the user
            when (result) {
                is List<Kviz> -> {
                    onSuccess.invoke(result)
                    notTaken.postValue(result!!)
                }
                else-> onError.invoke()
            }
        }
    }

    fun addMine(kviz: Kviz) {
        KvizRepository.addMine(kviz)
    }
    fun getKviz(nazivKviza : String) : Kviz {
        return KvizRepository.getKviz(nazivKviza)
    }
}