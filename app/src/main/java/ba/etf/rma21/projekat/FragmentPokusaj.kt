package ba.etf.rma21.projekat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.data.models.Pitanje
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class FragmentPokusaj() : Fragment() {
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var navigacijaPitanja: NavigationView
    private lateinit var framePitanje: FrameLayout
    private  var brojPitanja : Int = 0
    private  lateinit var pitanja : List<Pitanje>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_pokusaj, container, false)
        navigacijaPitanja = view.findViewById(R.id.navigacijaPitanja)
        framePitanje = view.findViewById(R.id.framePitanje)
//        bottomNavigation = view.findViewById(R.id.bottomNav)
//        bottomNavigation.findViewById<View>(R.id.predajKviz).visibility = View.VISIBLE
//        bottomNavigation.findViewById<View>(R.id.zaustaviKviz).visibility = View.VISIBLE
//        bottomNavigation.findViewById<View>(R.id.kvizovi).visibility = View.GONE
//        bottomNavigation.findViewById<View>(R.id.predmeti).visibility = View.GONE
        val pitanjeFragment = FragmentPitanje(Pitanje("Pitanje 1", "Ako želimo da BroadcastReceiver osluškuje obavijesti čak i kada aplikacija nije pokrenuta, tada taj BroadcastReceiver registrujemo u ...",
            listOf("manifestu", "u glavnoj klasi aktivnosti aplikacije", "nemoguće"), 0))
        fragmentManager
            ?.beginTransaction()
            ?.replace(R.id.framePitanje, pitanjeFragment)
            ?.commit();

        var menu: Menu = navigacijaPitanja.getMenu()
        for (i in 1 .. brojPitanja) {
            menu.add("" + i)
        }
        return view
    }

    companion object {
        fun newInstance(): FragmentPokusaj = FragmentPokusaj()
    }
    constructor(pitanja : List<Pitanje>) : this() {
        this.pitanja = pitanja
        brojPitanja = pitanja.size
    }
    //popuniti navigacijuPitanja
    //klik na element navigacijaPitanja -> fill framePitanje sa odgovarajucim pitanjem
}
