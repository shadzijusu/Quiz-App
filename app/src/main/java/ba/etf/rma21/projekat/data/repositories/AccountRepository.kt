package ba.etf.rma21.projekat.data.repositories

class AccountRepository {
    companion object {
        private val hexString = StringBuffer()
        fun postaviHash(acHash: String): Boolean {
            for (i in 0 until acHash.length) hexString.append(
                Integer.toHexString(
                    0xFF and acHash[i].toInt()
                )
            )
            return hexString.isNotEmpty()
        }

        fun getHash(): String {
            return hexString.toString()
        }
    }
}