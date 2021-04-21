package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.staticdata.*

class KvizRepository {

    companion object {
        // TODO: Implementirati
        init {
            // TODO: Implementirati
        }

        fun getMyKvizes(): List<Kviz> {
            return myKvizes()
        }

        fun getAll(): List<Kviz> {
            return allKvizes()
        }

        fun getDone(): List<Kviz> {
          return done()
        }

        fun getFuture(): List<Kviz> {
          return future()
        }

        fun getNotTaken(): List<Kviz> {
            return notTaken()
        }
        // TODO: Implementirati i ostale potrebne metode
        fun addMine(kviz : Kviz) {
            dodajMoj(kviz)
        }
        fun getKviz(nazivKviza : String) : Kviz {
            return dajKviz(nazivKviza)
        }
    }
}