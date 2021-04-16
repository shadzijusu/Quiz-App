package ba.etf.rma21.projekat.view


import android.app.usage.UsageEvents.Event.NONE
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import android.widget.FrameLayout
import androidx.core.view.get
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class FragmentPokusaj() : Fragment() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navigacijaPitanja: NavigationView
    private lateinit var framePitanje: FrameLayout
    private var brojPitanja: Int = 0
    private lateinit var pitanja: List<Pitanje>
    private lateinit var listaKvizovaAdapter: ListaKvizovaAdapter
    private var kvizListViewModel = KvizListViewModel()
    private var pitanjeKvizListViewModel = PitanjeKvizListViewModel()
    private var pitanjeFragment = FragmentPitanje()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    private val onNavigationItemSelectedListener =
        NavigationView.OnNavigationItemSelectedListener { item ->
            val pitanje = pitanja.get(item.itemId)
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
                    requireActivity().onBackPressed()
//                    fragmentManager?.popBackStack()
//                    bottomNavigationView.selectedItemId = R.id.kvizovi
                    return@OnNavigationItemSelectedListener false
                }
            }
            true
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = if (view != null) view else inflater.inflate(
            R.layout.fragment_pokusaj,
            container,
            false
        )
    //    var view = inflater.inflate(R.layout.fragment_pokusaj, container, false)
        if (view != null) {
            navigacijaPitanja = view.findViewById(R.id.navigacijaPitanja)
        }
        if (view != null) {
            framePitanje = view.findViewById(R.id.framePitanje)
        }
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
        navigacijaPitanja.setNavigationItemSelectedListener(onNavigationItemSelectedListener)
        onNavigationItemSelectedListener.onNavigationItemSelected(navigacijaPitanja.menu.getItem(0));
        return view
    }

    private fun openFragment(porukaFragment: FragmentPoruka) {
        var brojTacnih = 0.0
        var menu: Menu = navigacijaPitanja.menu
        for (i in 0 until menu.size()) {
            var menuItem: MenuItem = navigacijaPitanja.menu.getItem(i)
            var item = menuItem.toString().split(" ")
            if (item[1] == "+") {
                brojTacnih++
            }
        }
        var nazivKviza = ""
        var svaPitanjaSNazivom = pitanjeKvizListViewModel.getSvaSNazivom()
        for (pitanjeKviz in svaPitanjaSNazivom) {
            for (pitanje in pitanja) {
                if (pitanje.tekst.equals(pitanjeKviz.pitanje.tekst)) {
                    nazivKviza = pitanjeKviz.nazivKviza
                    break
                }
            }
        }


        var brojSvih = brojPitanja.toDouble()
        var percentage = brojTacnih / brojSvih
        var bundle = Bundle()
        var poruka = "Završili ste kviz $nazivKviza sa tačnosti $percentage!"
        bundle.putString("data", poruka)
        porukaFragment.arguments = bundle
        val transaction = fragmentManager?.beginTransaction()
        transaction?.add(R.id.container, porukaFragment)
        transaction?.addToBackStack("poruka")
        transaction?.commit()

    }

    private fun redirectToFragment(pitanjeFragment: FragmentPitanje) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.framePitanje, pitanjeFragment).addToBackStack("pitanja")
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
