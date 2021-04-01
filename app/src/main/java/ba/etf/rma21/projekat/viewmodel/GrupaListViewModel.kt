package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.repositories.GrupaRepository

class GrupaListViewModel {
    fun getGroupsByPredmet(nazivPredmeta: String): List<Grupa> {
        return GrupaRepository.getGroupsByPredmet(nazivPredmeta)
    }
}