package ba.etf.rma21.projekat.view.fragmenti


import android.app.usage.UsageEvents.Event.NONE
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import java.io.Serializable
import java.util.*


class FragmentPokusaj() : Fragment(), Serializable {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navigacijaPitanja: NavigationView
    private lateinit var framePitanje: FrameLayout
    private var brojPitanja: Int = 0
    private lateinit var pitanja: List<Pitanje>
    private lateinit var pitanje: Pitanje
    private var pitanjeKvizListViewModel = PitanjeKvizListViewModel(null, null)
    private var pitanjeFragment = FragmentPitanje()
    private var kvizListViewModel = KvizListViewModel(null, null)
    private var odabranoPitanje = 0
    private var odabraniOdgovor = -1
    private var nazivKviza = ""
    private var percentage = 0.0
    private var itemId = 0
    private var mOnNavigationViewItemSelectedListener =
        NavigationView.OnNavigationItemSelectedListener { item ->
            if (item.title == "Rezultat") {
                val porukaFragment = FragmentPoruka.newInstance()
                otvoriPoruku(porukaFragment)
            } else {
                pitanje = pitanja.get(item.itemId)
                odabranoPitanje = item.itemId
                var bundle = Bundle()
                bundle.putString("data", item.itemId.toString())
                pitanjeFragment =
                    FragmentPitanje(pitanje)
                pitanjeFragment.arguments = bundle
                redirectToFragment(pitanjeFragment)
            }
            item.isChecked = true
            true
        }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.predajKviz -> {
                    navigacijaPitanja.menu.add(0, itemId, NONE, "Rezultat")
                    val porukaFragment =
                        FragmentPoruka.newInstance()
                    otvoriPoruku(porukaFragment)
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
                R.id.predmeti -> {
                    val predmetiFragment = FragmentPredmeti.newInstance()
                    openFragment(predmetiFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.kvizovi -> {
                    val kvizoviFragment = FragmentKvizovi.newInstance()
                    openFragment(kvizoviFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            true
        }

    override fun onResume() {
        super.onResume()
        var present = false
        var odgovori = pitanjeKvizListViewModel.getAll().values.toMutableList()
        var questions = pitanjeKvizListViewModel.getAll().keys.toMutableList()
        for(question in questions) {
            if(question.tekstPitanja.equals(pitanja[0].tekstPitanja))
                present = true
        }
        if(present) {
                for (i in 0 until questions.size) {
                    for (j in 0 until pitanja.size) {
                        var menuItem = navigacijaPitanja.menu.getItem(j)
                        var s: SpannableString = SpannableString("")
                        s = SpannableString(menuItem?.title.toString() + " +")
                        if (questions[i].tekstPitanja == pitanja[j].tekstPitanja) {
                            if (pitanja[j].tacan == odgovori[i]) {
                                s.setSpan(ForegroundColorSpan(Color.GREEN), 0, s.length, 0)
                            } else
                                s.setSpan(ForegroundColorSpan(Color.RED), 0, s.length, 0)
                            menuItem?.title = s
                            menuItem.isChecked = true
                        }
                    }
                }
                navigacijaPitanja.menu.add(0, itemId, NONE, "Rezultat")
                bottomNavigationView.menu.findItem(R.id.predajKviz).isVisible = false
                bottomNavigationView.menu.findItem(R.id.zaustaviKviz).isVisible = false
                bottomNavigationView.menu.findItem(R.id.kvizovi).isVisible = true
                bottomNavigationView.menu.findItem(R.id.predmeti).isVisible = true
                bottomNavigationView.selectedItemId = R.id.invisible
            }
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
        return view
    }


    private fun otvoriPoruku(porukaFragment: FragmentPoruka) {
        var menu: Menu = navigacijaPitanja.menu
        var brojTacnih = 0.0
        var odgovori = pitanjeKvizListViewModel.getAll().values.toMutableList()
        var questions = pitanjeKvizListViewModel.getAll().keys.toMutableList()
        for (i in 0 until questions.size) {
            for (j in 0 until pitanja.size) {
                if (pitanja[j].tekstPitanja == questions[i].tekstPitanja) {
                    if (pitanja[j].tacan == odgovori[i])
                        brojTacnih++
                }
            }
        }

        var brojSvih = pitanja.size.toDouble()
        percentage = brojTacnih / brojSvih


        var bundle1 = this.arguments
        nazivKviza = bundle1?.getString("naziv").toString()
        var bundle = Bundle()
        var poruka = "Završili ste kviz $nazivKviza sa tačnosti $percentage!"
        bundle.putString("data", poruka)
        porukaFragment.arguments = bundle
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.framePitanje, porukaFragment, "poruka")
        transaction?.addToBackStack("poruka")
        transaction?.commit()
        var kviz = kvizListViewModel.getKviz(nazivKviza)
        if (kviz.osvojeniBodovi == null) {
            kviz.osvojeniBodovi = percentage.toFloat()
            kviz.datumRada = Calendar.getInstance().time
            kvizListViewModel.addMine(kviz)
        }
        bottomNavigationView.menu.findItem(R.id.predajKviz).isVisible = false
        bottomNavigationView.menu.findItem(R.id.zaustaviKviz).isVisible = false
        bottomNavigationView.menu.findItem(R.id.kvizovi).isVisible = true
        bottomNavigationView.menu.findItem(R.id.predmeti).isVisible = true
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.container, fragment)?.addToBackStack(null)
        transaction?.commit()
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
