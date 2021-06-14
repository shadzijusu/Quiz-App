package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "KvizTaken")
data class KvizTaken(
    @PrimaryKey @SerializedName("id") var id: Int,
    @ColumnInfo(name = "student") @SerializedName("student") var student: String?,
    @ColumnInfo(name = "datumRada") @SerializedName("datumRada") var datumRada: String?,
    @ColumnInfo(name = "osvojeniBodovi") @SerializedName("osvojeniBodovi") var osvojeniBodovi: Float?,
    @ColumnInfo(name = "KvizId") @SerializedName("KvizId") var KvizId : Int
    )
