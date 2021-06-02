package ba.etf.rma21.projekat.data.models

import com.google.gson.annotations.SerializedName
import java.util.*

class GetTakenKvizResponse(
    @SerializedName("id")
    var id: Int,

    @SerializedName("student")
    var student: String,


    @SerializedName("datumRada")
    var datumRada: Date?,

    @SerializedName("osvojeniBodovi")
    var osvojeniBodovi: Float,

    @SerializedName("message")
    var message: String?
)