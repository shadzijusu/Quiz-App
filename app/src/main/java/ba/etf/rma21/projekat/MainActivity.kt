package ba.etf.rma21.projekat


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
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
        bottomNavigation= findViewById(R.id.bottomNav)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        //Defaultni fragment
        bottomNavigation.selectedItemId= R.id.kvizovi
        val kvizoviFragment = FragmentKvizovi.newInstance()
        openFragment(kvizoviFragment)


    }
    //Funkcija za izmjenu fragmenta
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}


