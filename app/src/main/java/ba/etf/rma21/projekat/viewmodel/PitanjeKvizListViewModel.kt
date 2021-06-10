package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import ba.etf.rma21.projekat.data.staticdata.getOdgovor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PitanjeKvizListViewModel () {
    val scope = CoroutineScope(
        Job() + Dispatchers.Main)
    var pitanja = MutableLiveData<List<Pitanje>>()





        fun dajPitanja(onSuccess: (pitanja: List<Pitanje>) -> Unit,
               onError: () -> Unit, idKviza : Int) {
        // Create a new coroutine on the UI thread
        scope.launch {
            // Make the network call and suspend execution until it finishes
            val result = PitanjeKvizRepository.getPitanja(idKviza)

            // Display result of the network request to the user
            when (result) {
                is List<Pitanje> -> {
                    onSuccess?.invoke(result)
                    pitanja.postValue(result!!)
                }
                else -> onError?.invoke()
            }
        }
    }


    fun getPitanja(nazivKviza: String, nazivPredmeta: String): List<Pitanje> {
        return PitanjeKvizRepository.getPitanjaa(nazivKviza, nazivPredmeta)
    }

    fun getPitanje(nazivKviza: String): Pitanje{
        return PitanjeKvizRepository.getPitanje(nazivKviza)
    }

    fun getDaLiJeZavrsen(nazivKviza: String): Boolean {
        return PitanjeKvizRepository.getDaLiJeZavrsen(nazivKviza)
    }

    fun finishKviz(nazivKviza: String) {
        return PitanjeKvizRepository.finishKviz(nazivKviza)

    }

    fun addAnswer(pitanje: Pitanje, odgovor: Int) {
        return PitanjeKvizRepository.addAnswer(pitanje, odgovor)

    }
    fun dajOdgovor(pitanje : Pitanje): Int? {
        return PitanjeKvizRepository.dajOdgovor(pitanje)
    }
    fun getAll() : HashMap<Pitanje, Int> {
        return PitanjeKvizRepository.getAll()
    }
    fun getKvizoveSPitanjima() : List<String> {
        return PitanjeKvizRepository.getKvizoveSPitanjima()
    }
    fun setAll(qAndA: HashMap<Pitanje, Int>) {
        PitanjeKvizRepository.setSve(qAndA)
    }
    suspend fun getBodove(kvizId : Int): Int? {
        return PitanjeKvizRepository.getBodove(kvizId)
    }
    suspend fun dodajKvizIBodove(kvizId: Int, bodovi : Int) {
        PitanjeKvizRepository.dodajKvizIBodove(kvizId, bodovi)
    }
}