package ba.etf.rma21.projekat.view.fragmenti


import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.viewmodel.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FragmentPitanje() : Fragment() {
    private lateinit var odgovoriLista: ListView
    private lateinit var tekstPitanja: TextView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navigacijaPitanja: NavigationView
    private var dbViewModel = DBViewModel()
    private lateinit var pitanje: Pitanje
    private var pitanjeKvizListViewModel = PitanjeKvizListViewModel()
    private var odgovorViewModel = OdgovorViewModel()
    private var kvizListViewModel = KvizListViewModel()

    private var takenViewModel = KvizTakenViewModel()
    private var pozicija = -1
    private var idKvizTaken = 0
    private var idKviza = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var odabraniOdgovor = pitanjeKvizListViewModel.dajOdgovor(pitanje)
        if (odabraniOdgovor != null) {
            pozicija = odabraniOdgovor
        }
    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch(Dispatchers.IO) {
            launch {
                takenViewModel.zapocetiKvizoviTaken(
                    onSuccess = ::onSuccessTaken,
                    onError = ::onError
                )
            }
            delay(500)
            var kvizoviTaken = takenViewModel.kvizovi.value
            if (kvizoviTaken != null) {
                for (taken in kvizoviTaken) {
                    if (taken.id == idKvizTaken) {
                        idKviza = taken.KvizId
                        break
                    }
                }
            }
            launch {
                odgovorViewModel.getOdgovoriDB(
                    idKvizTaken = idKvizTaken,
                    context = requireContext()
                )
            }
            delay(300)
            launch {
                pitanjeKvizListViewModel.dajPitanjaDB(
                    onSuccess = ::onSuccessPitanja,
                    onError = ::onError,
                    idKviza = idKviza,
                    context = requireContext()
                )
            }
            delay(500)
            var questions = pitanjeKvizListViewModel.pitanjaDB.value
            var odgovori = odgovorViewModel.odgovoriDB.value
            if (odgovori != null) {
                for (odgovor in odgovori) {
                    if (odgovor.PitanjeId == pitanje.id) {
                        var refresh = Handler(Looper.getMainLooper())
                        refresh.post {
                            odgovoriLista.performItemClick(
                                odgovoriLista.findViewWithTag(
                                    odgovoriLista.adapter.getItem(
                                        odgovor.odgovoreno
                                    )
                                ),
                                odgovor.odgovoreno,
                                odgovoriLista.adapter.getItemId(odgovor.odgovoreno)
                            )

                        }
                        odgovoriLista.isEnabled = false

                        //bojimo navigaciju pitanja
                        var pozicija = 0
                        if (questions != null) {
                            for (quest in 0 until questions.size) {
                                if (questions[quest].id == pitanje.id) {
                                    pozicija = quest
                                }
                            }
                        }
                        var menuItem = navigacijaPitanja.menu.getItem(pozicija)
                        var s: SpannableString = SpannableString("")
                        s = SpannableString(menuItem?.title.toString() + " +")
                        if (odgovor.odgovoreno == pitanje.tacan) {
                            s.setSpan(
                                ForegroundColorSpan(Color.GREEN),
                                0,
                                s.length,
                                0
                            )
                        } else
                            s.setSpan(
                                ForegroundColorSpan(Color.RED),
                                0,
                                s.length,
                                0
                            )
                        val refresh2 = Handler(Looper.getMainLooper())
                        refresh2.post {
                            menuItem?.title = s
                            menuItem.isChecked = true
                        }
                        delay(2)
                        break
                    }

                }
            }


        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pitanje, container, false)
        odgovoriLista = view.findViewById(R.id.odgovoriLista)
        tekstPitanja = view.findViewById(R.id.tekstPitanja)
        navigacijaPitanja =
            requireParentFragment().requireView().findViewById(R.id.navigacijaPitanja)

        var bundle = this.arguments
        var poruka = bundle?.getString("tekstPitanja")
        var poruka2 = bundle?.getInt("idKvizTaken")
        if (poruka2 != null) {
            idKvizTaken = poruka2
        }

        tekstPitanja.text = pitanje.tekstPitanja
        val opcije = pitanje.opcije.split(",")
        val opcija1 = opcije[0]
        val opcija2 = opcije[1]
        val opcija3 = opcije[2]
        val listaVrijednosti = listOf(opcija1, opcija2, opcija3)
        val layoutID = android.R.layout.simple_list_item_1
        var adapter = view.context?.let { ArrayAdapter<String>(it, layoutID, listaVrijednosti) }
        odgovoriLista.adapter = adapter
        adapter?.notifyDataSetChanged()


        odgovoriLista.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                GlobalScope.launch(Dispatchers.IO) {
                    launch(Dispatchers.IO) {
                        odgovorViewModel.addOdgovor(
                            onSuccess = ::onSuccess,
                            onError = ::onError,
                            idKvizTaken = idKvizTaken,
                            idPitanje = pitanje.id,
                            odgovor = position,
                            context = requireContext()
                        )
                    }
                    delay(2000)
                    dbViewModel.update(
                        onSuccess = ::onSuccessUpdate,
                        onError = ::onError,
                        context = requireContext()
                    )
                }
                if (pitanjeKvizListViewModel.dajOdgovor(pitanje) == null)
                    pitanjeKvizListViewModel.addAnswer(pitanje, position)
                var menuItem = poruka?.toInt()?.let { navigacijaPitanja.menu.getItem(it) }
                var s: SpannableString = SpannableString("")
                if (position.equals(pitanje.tacan)) {
                    parent.getChildAt(position)
                        .setBackgroundColor(resources.getColor(R.color.tacno))
                    s = SpannableString(menuItem?.title.toString() + " +")
                    s.setSpan(ForegroundColorSpan(Color.GREEN), 0, s.length, 0)

                } else if (!position.equals(pitanje.tacan)) {
                    parent.getChildAt(position)
                        .setBackgroundColor(resources.getColor(R.color.netacno))
                    parent.getChildAt(pitanje.tacan)
                        .setBackgroundColor(resources.getColor(R.color.tacno))
                    s = SpannableString(menuItem?.title.toString() + " -")
                    s.setSpan(ForegroundColorSpan(Color.RED), 0, s.length, 0)
                }
                odgovoriLista.isEnabled = false
                menuItem?.title = s
            }
        return view
    }

    companion object {
        fun newInstance(): FragmentPitanje =
            FragmentPitanje()
    }

    constructor(pitanje: Pitanje) : this() {
        this.pitanje = pitanje
    }

    fun onSuccess(bodovi: Int) {
        val toast = Toast.makeText(context, "Kvizovi pronađeni", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onSuccessTaken(kvizTaken: List<KvizTaken>) {
        val toast = Toast.makeText(context, "Tražim", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onSuccessOdgovori(odgovori: List<Odgovor>) {
        val toast = Toast.makeText(context, "Tražim", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onSuccessPitanja(pitanja: List<Pitanje>) {
        val toast = Toast.makeText(context, "Tražim", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onSuccessUpdate(upis: Boolean) {
        val toast = Toast.makeText(context, "Update", Toast.LENGTH_SHORT)
        toast.show()
    }
}