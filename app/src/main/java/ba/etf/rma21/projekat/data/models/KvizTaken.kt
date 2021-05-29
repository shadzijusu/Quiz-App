package ba.etf.rma21.projekat.data.models

import com.google.gson.annotations.SerializedName
import java.util.*

class KvizTaken(
    @SerializedName("id") var id: Int,
    @SerializedName("student") var student: String,
    @SerializedName("osvojeniBodovi") var osvojeniBodovi: Float,
    @SerializedName("datumRada") var datumRada: Date
)