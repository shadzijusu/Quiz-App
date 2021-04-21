package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.models.PitanjeKviz
import ba.etf.rma21.projekat.data.staticdata.*

class PitanjeKvizRepository {

    companion object {
        // TODO: Implementirati
        init {
            // TODO: Implementirati
        }

        fun getPitanja(nazivKviza: String, nazivPredmeta: String): List<Pitanje> {
            return pitanja(
                nazivKviza,
                nazivPredmeta
            )
        }
        fun getSvaSNazivom() : List<PitanjeKviz> {
            return svaPitanjaSNazivomKviza()
        }
        fun getDaLiJeZavrsen(nazivKviza: String): Boolean {
            return daLiJeZavrsen(nazivKviza)
        }

        fun finishKviz(nazivKviza: String) {
            zavrsiKviz(nazivKviza)
        }
        fun addAnswer(pitanje : Pitanje, odgovor : Int) {
            dodajOdgovor(pitanje, odgovor)
        }
        fun dajOdgovor(pitanje : Pitanje): Int? {
            return getOdgovor(pitanje)
        }
        fun getAll() : Int {
            return dajSveOdgovore()
        }
        fun getKvizoveSPitanjima() : List<String> {
            return kvizoviSPitanjima()
        }
    }
}