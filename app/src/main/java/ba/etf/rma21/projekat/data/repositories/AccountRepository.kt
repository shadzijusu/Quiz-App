package ba.etf.rma21.projekat.data.repositories

class AccountRepository {

    companion object {
        //TODO Ovdje trebate dodati hash string vašeg accounta
        var acHash: String = "9d53bd38-18d2-49ec-889f-703ab44db589"

         fun postaviHash(acHash: String): Boolean {
            this.acHash = acHash
            return true
        }

        fun getHash(): String {
            return acHash
        }


    }
}