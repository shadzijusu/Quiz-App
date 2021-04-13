package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.pitanja

class PitanjeKvizRepository {

    companion object {
        // TODO: Implementirati
        init {
            // TODO: Implementirati
        }

        fun getPitanja(nazivKviza: String, nazivPredmeta: String): List<Pitanje> {
            return pitanja(nazivKviza, nazivPredmeta)
        }
    }
}