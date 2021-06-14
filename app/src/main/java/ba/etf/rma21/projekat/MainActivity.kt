package ba.etf.rma21.projekat


import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.data.repositories.AccountRepository
import ba.etf.rma21.projekat.data.repositories.TakeKvizRepository

import ba.etf.rma21.projekat.view.fragmenti.FragmentKvizovi
import ba.etf.rma21.projekat.view.fragmenti.FragmentPredmeti
import ba.etf.rma21.projekat.viewmodel.AccountViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var kvizoviFragment: FragmentKvizovi
    private lateinit var bottomNavigation: BottomNavigationView
    private var accountViewModel = AccountViewModel()
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
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
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val action: String? = intent?.action
        val data: Uri? = intent?.data

        val user : String? = intent.extras?.getString("payload")
        if (user != null) {
            accountViewModel.upisi(applicationContext ,user, onSuccess = ::onSuccess, onError = ::onError )
            }

        bottomNavigation = findViewById(R.id.bottomNav)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottomNavigation.menu.findItem(R.id.predajKviz).isVisible = false
        bottomNavigation.menu.findItem(R.id.zaustaviKviz).isVisible = false
        //Defaultni fragment
        bottomNavigation.selectedItemId = R.id.kvizovi
        kvizoviFragment = FragmentKvizovi.newInstance()
        openFragment(kvizoviFragment)
        TakeKvizRepository.setContext(applicationContext)

    }

    //Funkcija za izmjenu fragmenta
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment).addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (R.id.predajKviz == bottomNavigation.selectedItemId) {
            supportFragmentManager.popBackStack("poruka", 1)
            bottomNavigation.selectedItemId = R.id.kvizovi
        } else if (R.id.kvizovi != bottomNavigation.selectedItemId) {
            bottomNavigation.selectedItemId = R.id.kvizovi

        }
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottomNavigation.menu.findItem(R.id.predajKviz).isVisible = false
        bottomNavigation.menu.findItem(R.id.zaustaviKviz).isVisible = false
        bottomNavigation.menu.findItem(R.id.kvizovi).isVisible = true
        bottomNavigation.menu.findItem(R.id.predmeti).isVisible = true
        bottomNavigation.selectedItemId = R.id.kvizovi
    }

    override fun onResume() {
        super.onResume()
        bottomNavigation.selectedItemId = R.id.kvizovi
//        val intent = getIntent()
//        println(intent.extras)
//        val data: Uri? = intent.data
//            println("deeplinkingcallback   :- $data")

    }
    fun onSuccess(uspjesno:Boolean){
        val toast = Toast.makeText(applicationContext, "Spaseno", Toast.LENGTH_SHORT)
        toast.show()
    }
    fun onError() {
        val toast = Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT)
        toast.show()
    }
}



