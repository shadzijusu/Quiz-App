package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.all
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.predmetsByGodina
import ba.etf.rma21.projekat.data.upisani
import ba.etf.rma21.projekat.data.upisi

class PredmetRepository {
    companion object {
        fun getUpisani(): List<Predmet> {
         return upisani()
        }

        fun getAll(): List<Predmet> {
            return all()
        }
        fun getPredmetsByGodina(godina : Int) : List<Predmet> {
            return predmetsByGodina(godina)
        }
        fun upisiPredmet(predmet : Predmet) {
            upisi(predmet)
        }
    }

}