package ba.etf.rma21.projekat.view.fragmenti


import android.app.usage.UsageEvents.Event.NONE
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import java.util.*


class FragmentPokusaj() : Fragment() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navigacijaPitanja: NavigationView
    private lateinit var framePitanje: FrameLayout
    private var brojPitanja: Int = 0
    private lateinit var pitanja: List<Pitanje>
    private lateinit var pitanje: Pitanje
    private var pitanjeKvizListViewModel = PitanjeKvizListViewModel()
    private var pitanjeFragment = FragmentPitanje()
    private var kvizListViewModel = KvizListViewModel()
    private var boja = "bijela"
    private var odabranoPitanje = 0
    private var odabraniOdgovor = -1
    private var nazivKviza = ""
    private var percentage = 0.0

    private var mOnNavigationViewItemSelectedListener =
        NavigationView.OnNavigationItemSelectedListener { item ->
            pitanje = pitanja.get(item.itemId)
            odabranoPitanje = item.itemId
            var bundle = Bundle()
            bundle.putString("data", item.itemId.toString())
            pitanjeFragment =
                FragmentPitanje(pitanje)
            pitanjeFragment.arguments = bundle
            redirectToFragment(pitanjeFragment)
            item.isChecked = true
            true
        }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.predajKviz -> {
                    val porukaFragment =
                        FragmentPoruka.newInstance()
                    openFragment(porukaFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.zaustaviKviz -> {
                    bottomNavigationView.menu.findItem(R.id.predajKviz).isVisible = false
                    bottomNavigationView.menu.findItem(R.id.zaustaviKviz).isVisible = false
                    bottomNavigationView.menu.findItem(R.id.kvizovi).isVisible = true
                    bottomNavigationView.menu.findItem(R.id.predmeti).isVisible = true
                    fragmentManager?.popBackStack()
                    requireActivity().onBackPressed()
                    return@OnNavigationItemSelectedListener false
                }
                R.id.rezultat -> {
                    var rezultat = FragmentPoruka()
                    openFragment(rezultat)
                }
            }
            true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var bundle = this.arguments
        var poruka = bundle?.getString("naziv")
        pitanje = pitanjeKvizListViewModel.getPitanje(poruka.toString())
        var odabrano = pitanjeKvizListViewModel.dajOdgovor(pitanje)
        if (odabrano != null)
            odabraniOdgovor = odabrano
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_pokusaj, container, false)
        navigacijaPitanja = view.findViewById(R.id.navigacijaPitanja)
        framePitanje = view.findViewById(R.id.framePitanje)
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNav)
        bottomNavigationView.menu.findItem(R.id.predajKviz).isVisible = true
        bottomNavigationView.menu.findItem(R.id.zaustaviKviz).isVisible = true
        bottomNavigationView.menu.findItem(R.id.kvizovi).isVisible = false
        bottomNavigationView.menu.findItem(R.id.predmeti).isVisible = false
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        var menu: Menu = navigacijaPitanja.menu
        var itemId = 0

        for (i in 1..brojPitanja) {
            menu.add(0, itemId, NONE, "" + i)
            itemId++
        }

        navigacijaPitanja.setNavigationItemSelectedListener(mOnNavigationViewItemSelectedListener)
        mOnNavigationViewItemSelectedListener.onNavigationItemSelected(
            navigacijaPitanja.menu.getItem(
                0
            )
        )
//
//        if (odabraniOdgovor != -1) {
//            println("here")
//            if (odabraniOdgovor != pitanje.tacan)
//                boja = "crvena"
//            else if (odabraniOdgovor == pitanje.tacan)
//                boja = "zelena"
//        }
        return view
    }


    private fun openFragment(porukaFragment: FragmentPoruka) {
        var brojTacnih = 0.0
        var menu: Menu = navigacijaPitanja.menu
        var nistaOdabrano = true
        for (i in 0 until menu.size()) {
            var broj = i + 1
            var menuItem = navigacijaPitanja.menu.getItem(i)
            nistaOdabrano =
                !(menuItem.toString().equals("$broj +") || menuItem.toString().equals("$broj -"))
        }

        if (!nistaOdabrano) {
            for (i in 0 until menu.size()) {
                var menuItem: MenuItem = navigacijaPitanja.menu.getItem(i)
                var item = menuItem.toString().split(" ")
                if (item.get(1) == "+") {
                    brojTacnih++
                }
            }
        }
        var brojSvih = menu.size().toDouble()

        brojSvih = brojPitanja.toDouble()
        percentage = brojTacnih / brojSvih
      //  pitanjeKvizListViewModel.finishKviz(nazivKviza)

        var bundle1 = this.arguments
        nazivKviza = bundle1?.getString("naziv").toString()
        var bundle = Bundle()
        var poruka = "Završili ste kviz $nazivKviza sa tačnosti $percentage!"
        if (nistaOdabrano)
            poruka = "Završili ste kviz!"
        bundle.putString("data", poruka)
        porukaFragment.arguments = bundle
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.framePitanje, porukaFragment, "poruka")
        transaction?.addToBackStack("poruka")
        transaction?.commit()
        var kviz = kvizListViewModel.getKviz(nazivKviza)
        if(kviz.osvojeniBodovi == null) {
            kviz.osvojeniBodovi = percentage.toFloat()
            kviz.datumRada = Calendar.getInstance().time
            kvizListViewModel.addMine(kviz)
        }
        bottomNavigationView.menu.findItem(R.id.rezultat).isVisible = true
    }

    private fun redirectToFragment(pitanjeFragment: FragmentPitanje) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.framePitanje, pitanjeFragment).addToBackStack(null)
        transaction.commit()
    }


    companion object {
        fun newInstance(): FragmentPokusaj =
            FragmentPokusaj()
    }

    constructor(pitanja: List<Pitanje>) : this() {
        if (pitanja.isNotEmpty()) {
            this.pitanja = pitanja
            brojPitanja = pitanja.size
        }
    }
}
