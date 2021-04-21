package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.models.PitanjeKviz
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import ba.etf.rma21.projekat.data.staticdata.getOdgovor

class PitanjeKvizListViewModel {
    fun getPitanja(nazivKviza: String, nazivPredmeta: String): List<Pitanje> {
        return PitanjeKvizRepository.getPitanja(nazivKviza, nazivPredmeta)
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
    fun getAll() : Int {
        return PitanjeKvizRepository.getAll()
    }
    fun getKvizoveSPitanjima() : List<String> {
        return PitanjeKvizRepository.getKvizoveSPitanjima()
    }
}