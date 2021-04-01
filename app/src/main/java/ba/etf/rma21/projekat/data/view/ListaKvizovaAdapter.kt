package ba.etf.rma21.projekat.data.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Kviz


class ListaKvizovaAdapter(private var kvizovi: List<Kviz>) :
    RecyclerView.Adapter<ListaKvizovaAdapter.KvizViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KvizViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_kviz, parent, false)
        return KvizViewHolder(view)
    }

    override fun getItemCount() = kvizovi.size

    override fun onBindViewHolder(holder: ListaKvizovaAdapter.KvizViewHolder, position: Int) {
        holder.nazivPredmeta.text = kvizovi[position].nazivPredmeta
        holder.nazivKviza.text = kvizovi[position].naziv

        holder.trajanjeKviza.text = kvizovi[position].trajanje.toString()
        holder.osvojeniBodovi.text = kvizovi[position].osvojeniBodovi.toString()
        val drawable: Int = holder.stanjeKviza.getTag() as Int
        when (drawable) {
            R.drawable.crvena -> holder.datumKviza.text = kvizovi[position].datumKraj.toString()
            R.drawable.zuta -> holder.datumKviza.text = kvizovi[position].datumPocetka.toString()
            R.drawable.plava -> holder.datumKviza.text = kvizovi[position].datumRada.toString()
            else -> holder.datumKviza.text = kvizovi[position].datumKraj.toString()
        }
    }

    fun updateKvizove(kvizovi: List<Kviz>) {
        this.kvizovi = kvizovi
        notifyDataSetChanged()
    }

    inner class KvizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nazivPredmeta: TextView = itemView.findViewById(R.id.nazivPredmeta)
        val nazivKviza: TextView = itemView.findViewById(R.id.nazivKviza)
        val datumKviza: TextView = itemView.findViewById(R.id.datumKviza)
        val trajanjeKviza: TextView = itemView.findViewById(R.id.trajanjeKviza)
        val osvojeniBodovi: TextView = itemView.findViewById(R.id.osvojeniBodovi)
        val stanjeKviza: ImageView = itemView.findViewById(R.id.stanjeKviza)

    }
}
