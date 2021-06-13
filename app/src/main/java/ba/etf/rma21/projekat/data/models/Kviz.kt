package ba.etf.rma21.projekat.data.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*
@Entity(tableName = "Kviz")
data class Kviz(
    @PrimaryKey @SerializedName("id") var id: Int,
    @ColumnInfo(name = "naziv") @SerializedName("naziv") var naziv: String,
    @ColumnInfo(name = "datumPocetka")  @SerializedName("datumPocetak") var datumPocetka: String?,
    @ColumnInfo(name = "datumKraj") @SerializedName("datumKraj") var datumKraj: String?,
    @ColumnInfo(name = "trajanje")  @SerializedName("trajanje") var trajanje: Int,
    @ColumnInfo(name = "nazivPredmeta")  @SerializedName("nazivPredmeta") var nazivPredmeta: String,
    @ColumnInfo(name = "datumRada") @SerializedName("datumRada") var datumRada: String?,
    @ColumnInfo(name = "osvojeniBodovi")  @SerializedName("osvojeniBodovi") var osvojeniBodovi: Float,
    @ColumnInfo(name = "predan") @SerializedName("predan") var predan : Boolean
)