package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.predmetsByGodina
import ba.etf.rma21.projekat.data.repositories.PredmetRepository

class PredmetListViewModel {
    fun getUpisani(): List<Predmet> {
        return PredmetRepository.getUpisani()
    }

    fun getAll(): List<Predmet> {
        return PredmetRepository.getAll()
    }
    fun getPredmetsByGodina(godina : Int) : List<Predmet> {
        return PredmetRepository.getPredmetsByGodina(godina)
    }
    fun upisiPredmet(predmet : Predmet) {
        PredmetRepository.upisiPredmet(predmet)
    }
}