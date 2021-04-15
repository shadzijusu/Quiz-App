package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.staticdata.groupsByPredmet
import ba.etf.rma21.projekat.data.models.Grupa

class GrupaRepository {
    companion object {
        init {
            // TODO: Implementirati
        }

        fun getGroupsByPredmet(nazivPredmeta: String): List<Grupa> {
           return groupsByPredmet(
               nazivPredmeta
           )
        }
    }
}