package ba.etf.rma21.projekat.data.repositories

class AccountRepository {

    companion object {
        //TODO Ovdje trebate dodati hash string vašeg accounta
        var acHash: String = ""

        fun postaviHash(acHash: String): Boolean {
            this.acHash = acHash
            return true
        }

        fun getHash(): String {
            return acHash
        }


    }
}