package ba.etf.rma21.projekat


import android.app.usage.UsageEvents.Event.NONE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.view.ListaKvizovaAdapter
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class FragmentPokusaj() : Fragment() {
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var navigacijaPitanja: NavigationView
    private lateinit var framePitanje: FrameLayout
    private var brojPitanja: Int = 0
    private lateinit var pitanja: List<Pitanje>
    private lateinit var listaKvizovaAdapter: ListaKvizovaAdapter
    private var kvizListViewModel = KvizListViewModel()
    private var pitanjeKvizListViewModel = PitanjeKvizListViewModel()
    private lateinit var drawerLayout : DrawerLayout
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

        var menu: Menu = navigacijaPitanja.menu
        var itemId = 0

        for (i in 1..brojPitanja) {
            menu.add(0, itemId, NONE, "" + i)
            itemId++

        }
        navigacijaPitanja.setNavigationItemSelectedListener { menuItem ->
            val pitanje = pitanja.get(menuItem.itemId)
            var bundle = Bundle()
            bundle.putString("odabranoPitanje", menuItem.itemId.toString())
            val pitanjeFragment = FragmentPitanje(pitanje)
            redirectToFragment(pitanjeFragment)
            menuItem.isChecked = true
            true
        }
        return view
    }


    private fun redirectToFragment(pitanjeFragment: FragmentPitanje) {
        val manager: FragmentManager? = activity?.supportFragmentManager
        val ft: FragmentTransaction? = manager?.beginTransaction()
        ft?.replace(R.id.framePitanje, pitanjeFragment)
        ft?.commit()
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
