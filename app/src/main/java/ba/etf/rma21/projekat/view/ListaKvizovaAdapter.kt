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
import ba.etf.rma21.projekat.viewmodel.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class ListaKvizovaAdapter(
    private var kvizovi: List<Kviz>,
    private var fragment: FragmentKvizovi
) :
    RecyclerView.Adapter<ListaKvizovaAdapter.KvizViewHolder>() {
    private val pitanjeKvizListViewModel = PitanjeKvizListViewModel()
    private val kvizListViewModel = KvizListViewModel()
    private val predmetListViewModel = PredmetListViewModel()
    private var kvizTakenViewModel = KvizTakenViewModel()
    private var accountViewModel = AccountViewModel()
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
        accountViewModel.setLastUpdate(
            context
        )
        var grupe = arrayListOf<Grupa>()
        GlobalScope.launch(Dispatchers.IO) {
            launch {
                kvizListViewModel.getMyDB(
                    onSuccess = ::onSuccessKvizovi,
                    onError = ::onError,
                    context = context
                )
            }
            delay(500)
            var quizzes = kvizListViewModel.kvizoviDB.value
            if (quizzes != null) {
                for (kviz in quizzes) {
                    if (kviz.id == kvizovi[position].id) {
                        kvizovi[position].nazivPredmeta = kviz.nazivPredmeta
                        kvizovi[position].predan = kviz.predan
                        break
                    }
                }
            }
            val refresh = Handler(Looper.getMainLooper())
            refresh.post {
                holder.nazivPredmeta.text = kvizovi[position].nazivPredmeta
            }
            var ima = false
            if (quizzes != null) {
                for(quiz in quizzes) {
                    if(quiz.id == kvizovi[position].id)
                        ima = true
                }
            }
            if (!ima) {
                GlobalScope.launch(Dispatchers.IO) {
                    launch(Dispatchers.IO) {
                        predmetListViewModel.getAllGroups(
                            onSuccess = ::onSuccessGrupe,
                            onError = ::onError
                        )
                    }
                    delay(1000)
                    grupe = predmetListViewModel.grupe.value as ArrayList<Grupa>
                    var grupa = Grupa(0, "", 0)

                    for (group in grupe) {
                        launch(Dispatchers.IO) {
                            kvizListViewModel.getKvizoveZaGrupu(
                                onSuccess = ::onSuccessKvizovi,
                                onError = ::onError,
                                idGrupe = group.id
                            )
                        }
                        delay(1000)
                        var nadjen = false
                        for (kviz in kvizListViewModel.kvizoviZaGrupu.value!!) {
                            if (kviz.id == kvizovi[position].id) {
                                grupa = group
                                nadjen = true
                                break
                            }
                        }
                        if (nadjen)
                            break
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
            }
        }
        holder.nazivKviza.text = kvizovi[position].naziv
        holder.trajanjeKviza.text = kvizovi[position].trajanje.toString()
        holder.itemView.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                launch(Dispatchers.IO) {
                    pitanjeKvizListViewModel.dajPitanjaDB(
                        onSuccess = ::onSuccess,
                        onError = ::onError,
                        idKviza = kvizovi[position].id,
                        context = context

                    )
                }
                delay(1000)
                var zapoceti = 0
                launch(Dispatchers.IO) {
                    kvizTakenViewModel.getTakenId(
                        idKviza = kvizovi[position].id,
                        context = context

                    )
                }
                delay(100)
                if(kvizTakenViewModel.zapocetId.value != 0) {
                    zapoceti = kvizTakenViewModel.zapocetId.value!!
                } else {
                    launch(Dispatchers.IO) {
                        kvizTakenViewModel.zapocniKviz(
                            onSuccess = ::onSuccessPocni,
                            onError = ::onError,
                            idKviza = kvizovi[position].id
                        )
                    }
                    delay(1000)

                    zapoceti = kvizTakenViewModel.zapoceti.value?.id!!
                    launch(Dispatchers.IO) {
                        kvizTakenViewModel.zapocni(
                            zapoceti,
                            idKviza = kvizovi[position].id,
                            context = context
                        )
                    }
                    delay(500)
                }

                var bundle = Bundle()
                var questions = pitanjeKvizListViewModel.pitanjaDB.value
                var pokusajFragment = questions?.let { it1 -> FragmentPokusaj(it1) }
                bundle.putString("naziv", kvizovi[position].naziv)
                if (zapoceti != null) {
                    bundle.putInt("idKvizTaken", zapoceti)
                }
                bundle.putInt("idKviza", kvizovi[position].id)
                pokusajFragment?.arguments = bundle

                if (pokusajFragment != null) {
                    fragment.activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.container, pokusajFragment)?.commit()

                }
            }
        }

        GlobalScope.launch(Dispatchers.IO) {
            launch {
                kvizTakenViewModel.zapocetiKvizoviTaken(
                    onSuccess = ::onSuccessTakenKvizovi,
                    onError = ::onError
                )
            }
            delay(1000)
            var quizzes = kvizListViewModel.kvizoviDB.value
            if (quizzes != null) {
                for (kviz in quizzes)
                    if (kviz.id == kvizovi[position].id && kviz.predan) {
                        kvizovi[position].datumRada = kviz.datumRada
                        kvizovi[position].osvojeniBodovi = kviz.osvojeniBodovi
                    }
            }
            launch {
                kvizListViewModel.getFuture(
                    onSuccess = ::onSuccessKvizovi,
                    onError = ::onError,
                    context = context
                )
            }
            delay(500)
            var future = kvizListViewModel.future.value
            var boja = "zelena"
            if (future != null) {
                for (kviz in future) {
                    if (kviz.id == kvizovi[position].id) {
                        boja = "zuta"
                    }

                }
            }
            launch {
                kvizListViewModel.getNotTaken(
                    onSuccess = ::onSuccessKvizovi,
                    onError = ::onError,
                    context = context
                )
            }
            delay(500)
            var notTaken = kvizListViewModel.notTaken.value
            if (notTaken != null) {
                for (kviz in notTaken) {
                    if (kviz.id == kvizovi[position].id) {
                        boja = "crvena"
                    }
                }
            }
            val refresh = Handler(Looper.getMainLooper())
            refresh.post {
                if (kvizovi[position].predan) {
                    holder.stanjeKviza.setImageResource(R.drawable.plava)
                    holder.osvojeniBodovi.text = kvizovi[position].osvojeniBodovi.toString()
                    holder.datumKviza.text = kvizovi[position].datumRada
                } else {
                    if (boja == "zuta")
                        holder.stanjeKviza.setImageResource(R.drawable.zuta)
                    else if (boja == "crvena")
                        holder.stanjeKviza.setImageResource(R.drawable.crvena)
                    else
                        holder.stanjeKviza.setImageResource(R.drawable.zelena)
                    holder.datumKviza.text = kvizovi[position].datumPocetka
                    holder.osvojeniBodovi.text = ""
                }
            }
        }

//                if (kvizovi[position].datumRada != null) {
//                    holder.datumKviza.text = simpleDateFormat.format(kvizovi[position].datumRada)
//                    holder.stanjeKviza.setImageResource(R.drawable.plava)
//                    holder.osvojeniBodovi.text = kvizovi[position].osvojeniBodovi.toString()
//                } else if (kvizovi[position].datumKraj != null && kvizovi[position].datumKraj?.compareTo(
//                        Calendar.getInstance().time.toString()
//                    )!! < 0
//                ) {
//                    holder.datumKviza.text = simpleDateFormat.format(kvizovi[position].datumKraj)
//                    holder.stanjeKviza.setImageResource(R.drawable.crvena)
//                    holder.osvojeniBodovi.text = ""
//                } else if (kvizovi[position].datumPocetka != null && kvizovi[position].datumPocetka?.compareTo(
//                        Calendar.getInstance().time.toString()
//                    )!! > 0
//                ) {
//                    holder.datumKviza.text = simpleDateFormat.format(kvizovi[position].datumPocetka)
//                    holder.stanjeKviza.setImageResource(R.drawable.zuta)
//                    holder.osvojeniBodovi.text = ""
//                } else {
//                    holder.datumKviza.text = simpleDateFormat.format(kvizovi[position].datumPocetka.)
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

    }

    fun onSuccessTakenKvizovi(taken: List<KvizTaken>) {

    }


    fun onSuccessGrupe(grupe: List<Grupa>) {

    }

    fun onSuccessKvizovi(kvizovi: List<Kviz>) {

    }

    fun onSuccessPredmet(predmeti: Predmet) {

    }

    fun onError() {

    }
    fun onSuccessPocni(kvizTaken: KvizTaken) {

    }
    companion object {
        @JvmStatic var kvizITakenId : HashMap<Int, Int> = hashMapOf()
    }
}




