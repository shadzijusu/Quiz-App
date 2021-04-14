package ba.etf.rma21.projekat


import android.app.usage.UsageEvents.Event.NONE
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.view.ListaKvizovaAdapter
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
    private lateinit var drawerLayout: DrawerLayout

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.predajKviz -> {
                    val porukaFragment = FragmentPoruka.newInstance()
                    openFragment(porukaFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.zaustaviKviz -> {
                  //  val kvizoviFragment = FragmentKvizovi.newInstance()
                  //  openFragment(kvizoviFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_pokusaj, container, false)
        navigacijaPitanja = view.findViewById(R.id.navigacijaPitanja)
        drawerLayout = view.findViewById(R.id.drawer_layout)
        framePitanje = view.findViewById(R.id.framePitanje)
        pitanja = pitanjeKvizListViewModel.getPitanja("", "")
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNav)
        bottomNavigationView.findViewById<View>(R.id.kvizovi).visibility = View.GONE
        bottomNavigationView.findViewById<View>(R.id.predmeti).visibility = View.GONE
        bottomNavigationView.findViewById<View>(R.id.predajKviz).visibility = View.VISIBLE
        bottomNavigationView.findViewById<View>(R.id.zaustaviKviz).visibility = View.VISIBLE

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        var menu: Menu = navigacijaPitanja.menu
        var itemId = 0

        for (i in 1..brojPitanja) {
            menu.add(0, itemId, NONE, "" + i)
            itemId++

        }
        navigacijaPitanja.setNavigationItemSelectedListener { menuItem ->
            val pitanje = pitanja.get(menuItem.itemId)
            var bundle = Bundle()
            bundle.putString("data", menuItem.itemId.toString())
            val pitanjeFragment = FragmentPitanje(pitanje)
            pitanjeFragment.arguments = bundle
            redirectToFragment(pitanjeFragment)
            menuItem.isChecked = true
            true
        }
        return view
    }

    private fun openFragment(porukaFragment: FragmentPoruka) {
        var brojTacnih = 0.0
        var menu: Menu = navigacijaPitanja.menu
        for(i in 0 until menu.size()) {
            var menuItem : MenuItem = navigacijaPitanja.menu.getItem(i)
            var item = menuItem.toString().split(" ")
            if(item[1] == "+") {
                brojTacnih++
            }
        }
        var brojSvih = brojPitanja.toDouble()
        var percentage = brojTacnih/brojSvih
        println(percentage)
        var bundle = Bundle()
        bundle.putString("data", "Završili ste kviz Naziv sa tačnosti $percentage!")
        porukaFragment.arguments = bundle
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.constraintL, porukaFragment)
        transaction?.addToBackStack(null)
        transaction?.commit()

    }
    private fun redirectToFragment(pitanjeFragment: FragmentPitanje) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.framePitanje, pitanjeFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    companion object {
        fun newInstance(): FragmentPokusaj = FragmentPokusaj()
    }

    constructor(pitanja: List<Pitanje>) : this() {
        if (pitanja.isNotEmpty()) {
            this.pitanja = pitanja
            brojPitanja = pitanja.size
        }

    }


}
