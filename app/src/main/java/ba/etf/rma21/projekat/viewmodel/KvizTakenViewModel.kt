package ba.etf.rma21.projekat.viewmodel

import androidx.lifecycle.MutableLiveData
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.TakeKvizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class KvizTakenViewModel {
    val scope = CoroutineScope(
        Job() + Dispatchers.Main)
    var kvizovi = MutableLiveData<List<KvizTaken>?>()
    var zapoceti = MutableLiveData<KvizTaken>()
    var quizzess = MutableLiveData<List<Kviz>>()
    fun zapocniKviz( onSuccess: (kvizTaken: KvizTaken) -> Unit,
                     onError: () -> Unit, idKviza : Int){
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = TakeKvizRepository.zapocniKviz(idKviza)

            // Display result of the network request to the user
            when (result) {
                is KvizTaken -> {
                    onSuccess.invoke(result)
                    zapoceti.postValue(result)
                }
                else-> onError.invoke()
            }
        }
    }
    fun zapocetiKvizoviTaken(onSuccess: (kvizovi: List<KvizTaken>) -> Unit,
                             onError: () -> Unit) {
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = TakeKvizRepository.getPocetiKvizovi()

            // Display result of the network request to the user
            when (result) {
                is List<KvizTaken> -> {
                    onSuccess.invoke(result)
                    kvizovi.postValue(result)
                }
                else-> onError.invoke()
            }
        }
    }
    fun zapocetiKvizovi(onSuccess: (kvizovi: List<Kviz>) -> Unit,
                        onError: () -> Unit) {
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = TakeKvizRepository.getPocetiKvizovi()
            // Display result of the network request to the user
            when (result) {
                is List<KvizTaken> -> {
                    var uradjeni = mutableListOf<Kviz>()
                    var kvizoviTaken = kvizovi.value
                    var kvizovi = KvizRepository.getAll()
                    if (kvizovi != null) {
                        for (kviz in kvizovi) {
                            if (kvizoviTaken != null) {
                                for (taken in kvizoviTaken) {
                                    if (taken.KvizId == kviz.id) {
                                        kviz.datumRada = taken.datumRada
                                        kviz.osvojeniBodovi = taken.osvojeniBodovi
                                        if(!uradjeni.contains(kviz))
                                        uradjeni.add(kviz)
                                        break
                                    }
                                }
                            }
                        }
                    }
                    onSuccess.invoke(uradjeni)
                    quizzess.postValue(uradjeni)
                }
                else-> onError.invoke()
            }
        }
    }
}