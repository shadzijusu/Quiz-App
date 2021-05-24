package ba.etf.rma21.projekat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository
import ba.etf.rma21.projekat.data.repositories.PredmetRepository
import ba.etf.rma21.projekat.data.repositories.Rezultat
import kotlinx.coroutines.*


class PredmetListViewModel(private val searchDone: ((predmeti: List<Predmet>) -> Unit)?,
                        private val onError: (()->Unit)?
) {
    val scope = CoroutineScope(
        Job() + Dispatchers.Main)
    var predmeti = MutableLiveData<List<Predmet>>()

    fun getUpisani(): List<Predmet> {
        return PredmetRepository.getUpisani()
    }

     fun getAll() {
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = PredmetIGrupaRepository.getPredmeti()
            // Display result of the network request to the user
            when (result) {
                is Rezultat.Success<*> -> {
                    searchDone?.invoke(result.data as List<Predmet>)
                    predmeti.postValue(result.data as List<Predmet>)
                }
                else-> onError?.invoke()
            }
        }
    }
    fun upisiUGrupu(grupaId : Int) {
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = PredmetIGrupaRepository.upisiUGrupu(grupaId)
            // Display result of the network request to the user
//            when (result) {
//                is Rezultat.Success<*> -> {
//                    searchDone?.invoke(result.data as List<Predmet>)
//                    predmeti.postValue(result.data as List<Predmet>)
//                }
//                else-> onError?.invoke()
//            }
        }
    }
    fun getPredmetsByGodina(godina : Int) : List<Predmet> {
        return PredmetRepository.getPredmetsByGodina(godina)
    }
    fun upisiPredmet(predmet : Predmet) {
        PredmetRepository.upisiPredmet(predmet)
    }
}