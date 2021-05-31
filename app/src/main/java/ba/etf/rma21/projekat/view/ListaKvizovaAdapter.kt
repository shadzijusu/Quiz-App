package ba.etf.rma21.projekat.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.*
import ba.etf.rma21.projekat.view.fragmenti.FragmentKvizovi
import ba.etf.rma21.projekat.view.fragmenti.FragmentPokusaj
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import ba.etf.rma21.projekat.viewmodel.KvizTakenViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizListViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


class ListaKvizovaAdapter(
    private var kvizovi: List<Kviz>,
    private var fragment: FragmentKvizovi
) :
    RecyclerView.Adapter<ListaKvizovaAdapter.KvizViewHolder>() {
    private val pitanjeKvizListViewModel = PitanjeKvizListViewModel(null, null)
    private val kvizListViewModel = KvizListViewModel(null, null)
    private val kvizTakenViewModel = KvizTakenViewModel(null, null)
    private val predmetListViewModel = PredmetListViewModel(null, null)
    private lateinit var context: Context
    private lateinit var grupe: List<Grupa>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KvizViewHolder {
        grupe = listOf()
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_kviz, parent, false)
        context = view.context
        return KvizViewHolder(view)
    }

    override fun getItemCount() = kvizovi.size


    override fun onBindViewHolder(holder: ListaKvizovaAdapter.KvizViewHolder, position: Int) {
        //naziv predmeta add
        var grupe = arrayListOf<Grupa>()

        GlobalScope.launch(Dispatchers.IO) {
            launch(Dispatchers.IO) {
                predmetListViewModel.getAllGroups(
                    onSuccess = ::onSuccessGrupe,
                    onError = ::onError
                )
            }
            delay(500)
            grupe = predmetListViewModel.grupe.value as ArrayList<Grupa>
            var grupa = Grupa(0, "", 0)

            for (group in grupe) {
                launch(Dispatchers.IO) {
                    kvizListViewModel.getKvizoveZaGrupu(
                        onSuccess = ::onSuccessKvizoviZaGrupu,
                        onError = ::onError,
                        idGrupe = group.id
                    )
                }
                delay(500)
                for (kviz in kvizListViewModel.kvizoviZaGrupu.value!!) {
                    if (kviz.id == kvizovi[position].id) {
                        grupa = group
                        break
                    }
                }
            }
            launch(Dispatchers.IO) {
                predmetListViewModel.getPredmet(
                    onSuccess = ::onSuccessPredmet,
                    onError = ::onError,
                    predmetId = grupa.PredmetId
                )
            }
            delay(500)
            kvizovi[position].nazivPredmeta = predmetListViewModel.predmet.value!!.naziv
            val refresh = Handler(Looper.getMainLooper())
            refresh.post {
                holder.nazivPredmeta.text = kvizovi[position].nazivPredmeta
            }
            delay(10)
        }

        holder.nazivKviza.text = kvizovi[position].naziv
        holder.trajanjeKviza.text = kvizovi[position].trajanje.toString()
        val pattern = "dd.MM.yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)

        val datum = kvizovi[position].datumPocetka
        holder.datumKviza.text = simpleDateFormat.format(datum)
        holder.stanjeKviza.setImageResource(R.drawable.zelena)
        holder.osvojeniBodovi.text = ""


        holder.itemView.setOnClickListener {
            pitanjeKvizListViewModel.dajPitanja(
                onSuccess = ::onSuccess,
                onError = ::onError,
                idKviza = kvizovi[position].id
            )
            var questions = pitanjeKvizListViewModel.pitanja.value
            kvizTakenViewModel.zapocniKviz(
                onSuccess = ::onSuccessPocni,
                onError = ::onError,
                idKviza = kvizovi[position].id
            )
            var pokusajFragment = questions?.let { it1 -> FragmentPokusaj(it1) }

            var bundle = Bundle()
            bundle.putString("naziv", kvizovi[position].naziv)
            pokusajFragment?.arguments = bundle

            if (pokusajFragment != null) {
                fragment.activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, pokusajFragment)?.commit()
            }
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

    fun onSuccess(pitanja: List<Pitanje>) {
        val toast = Toast.makeText(context, "Upcoming movies found", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onSuccessPocni(kvizTaken: KvizTaken) {
        val toast = Toast.makeText(context, "Upcoming movies found", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onSuccessGrupe(grupe: List<Grupa>) {
        val toast = Toast.makeText(context, "Upcoming movies found", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onSuccessKvizoviZaGrupu(kvizovi: List<Kviz>) {
        val toast = Toast.makeText(context, "Upcoming movies found", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onSuccessPredmet(predmeti: Predmet) {
        val toast = Toast.makeText(context, "Upcoming movies found", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }
}


