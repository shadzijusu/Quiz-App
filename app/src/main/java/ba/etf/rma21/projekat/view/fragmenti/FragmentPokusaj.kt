package ba.etf.rma21.projekat.view.fragmenti


import android.app.usage.UsageEvents
import android.app.usage.UsageEvents.Event.NONE
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.size
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Kviz
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
import java.io.Serializable


class FragmentPokusaj() : Fragment(), Serializable {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navigacijaPitanja: NavigationView
    private lateinit var framePitanje: FrameLayout
    private var brojPitanja: Int = 0
    private lateinit var pitanja: List<Pitanje>
    private lateinit var pitanje: Pitanje
    private var pitanjeKvizListViewModel = PitanjeKvizListViewModel()
    private var DBViewModel = DBViewModel()
    private var pitanjeFragment = FragmentPitanje()
    private var kvizListViewModel = KvizListViewModel()
    private var odabranoPitanje = 0
    private var odabraniOdgovor = -1
    private var nazivKviza = ""
    private var idKviza = 0
    private var odgovorListViewModel = OdgovorViewModel()
    private var itemId = 0
    private var idKvizTaken = 0
    private val kvizTakenViewModel = KvizTakenViewModel()

    private var mOnNavigationViewItemSelectedListener =
        NavigationView.OnNavigationItemSelectedListener { item ->
            if (item.title == "Rezultat") {
                val porukaFragment = FragmentPoruka.newInstance()
                otvoriPoruku(porukaFragment)
            } else {
                pitanje = pitanja.get(item.itemId)
                odabranoPitanje = item.itemId
                var bundle = Bundle()
                bundle.putString("tekstPitanja", item.itemId.toString())
                bundle.putInt("idKvizTaken", idKvizTaken)
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
                    klikPredaj.put(idKviza, true)
                    odgovorListViewModel.predaj(
                        idKviza = idKviza,
                        context = requireContext()
                    )

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
        GlobalScope.launch(Dispatchers.IO) {
            launch {
                odgovorListViewModel.getOdgovori(
                    onSuccess = ::onSuccessOdgovori,
                    onError = ::onError,
                    idKviza = idKviza
                )
            }
            delay(1000)
            var ima = false
            var odgovori = odgovorListViewModel.odgovori.value
            if (odgovori != null) {
                ima = true
            }
            if (ima) {
                launch{
                    pitanjeKvizListViewModel.dajPitanja(
                        onSuccess = ::onSuccessPitanja,
                        onError = ::onError,
                        idKviza = idKviza
                    )
                }
                delay(1000)
                var questions = pitanjeKvizListViewModel.pitanja.value
                if (odgovori != null) {
                    for(odgovor in odgovori) {
                        if (questions != null) {
                            for(i in 0 until questions.size) {
                                if(odgovor.PitanjeId == questions[i].id) {
                                    var menuItem = navigacijaPitanja.menu.getItem(i)
                                    var s: SpannableString = SpannableString("")
                                    s = SpannableString(menuItem?.title.toString() + " +")
                                    if (odgovor.odgovoreno == questions[i].tacan) {
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
                                    break
                                }
                            }
                        }
                    }
                }
                val refresh2 = Handler(Looper.getMainLooper())
                refresh2.post {
                    if (klikPredaj[idKviza] == true) {
                        navigacijaPitanja.menu.add(
                            0,
                            navigacijaPitanja.size,
                            UsageEvents.Event.NONE,
                            "Rezultat"
                        )
                        bottomNavigationView.menu.findItem(R.id.predajKviz).isVisible =
                            false
                        bottomNavigationView.menu.findItem(R.id.zaustaviKviz).isVisible =
                            false
                        bottomNavigationView.menu.findItem(R.id.kvizovi).isVisible = true
                        bottomNavigationView.menu.findItem(R.id.predmeti).isVisible = true
                        bottomNavigationView.selectedItemId = R.id.invisible
                    }
                    else {
                        bottomNavigationView.menu.findItem(R.id.predajKviz).isVisible =
                            true
                        bottomNavigationView.menu.findItem(R.id.zaustaviKviz).isVisible =
                            true
                        bottomNavigationView.menu.findItem(R.id.kvizovi).isVisible = false
                        bottomNavigationView.menu.findItem(R.id.predmeti).isVisible = false
                    }
                }
            }
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
        var bundle = this.arguments
        var poruka = bundle?.getInt("idKvizTaken")
        var poruka1 = bundle?.getInt("idKviza")
        if (poruka != null) {
            idKvizTaken = poruka
        }
        if (poruka1 != null) {
            idKviza = poruka1
        }
        navigacijaPitanja.setNavigationItemSelectedListener(mOnNavigationViewItemSelectedListener)
        mOnNavigationViewItemSelectedListener.onNavigationItemSelected(
            navigacijaPitanja.menu.getItem(
                0
            )
        )
        return view
    }


    fun otvoriPoruku(porukaFragment: FragmentPoruka) {
        GlobalScope.launch(Dispatchers.IO) {
            var percentage: Int = 0
            
            launch {
                kvizTakenViewModel.zapocetiKvizoviTaken(
                    onSuccess = ::onSuccessTaken,
                    onError = ::onError
                )
            }
            delay(500)
            var kvizoviTaken = kvizTakenViewModel.kvizovi.value
            if (kvizoviTaken != null) {
                for (kvizT in kvizoviTaken) {
                    if (kvizT.id == idKvizTaken) {
                        percentage = kvizT.osvojeniBodovi.toInt()
                        break
                    }
                }
            }
            delay(2000)
            var bundle1 = this@FragmentPokusaj.arguments
            nazivKviza = bundle1?.getString("naziv").toString()
            var bundle = Bundle()
            var poruka = "Završili ste kviz $nazivKviza sa tačnosti $percentage!"
            bundle.putString("data", poruka)
            porukaFragment.arguments = bundle
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.framePitanje, porukaFragment, "poruka")
            transaction?.addToBackStack("poruka")
            transaction?.commit()
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
        @JvmStatic var klikPredaj = hashMapOf<Int, Boolean>()
    }

    constructor(pitanja: List<Pitanje>) : this() {
        if (pitanja.isNotEmpty()) {
            this.pitanja = pitanja
            brojPitanja = pitanja.size

        }
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
    fun onSuccessKvizovi(kvizovi: List<Kviz>) {
        val toast = Toast.makeText(context, "Kvizovi pronađeni", Toast.LENGTH_SHORT)
        toast.show()
    }

}
