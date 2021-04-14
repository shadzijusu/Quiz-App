package ba.etf.rma21.projekat


import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
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
        bottomNavigation = findViewById(R.id.bottomNav)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        //bottomNavigation.findViewById<View>(R.id.predajKviz).visibility = View.GONE
        //bottomNavigation.findViewById<View>(R.id.zaustaviKviz).visibility = View.GONE
        bottomNavigation.menu.findItem(R.id.predajKviz).isVisible = false
        bottomNavigation.menu.findItem(R.id.zaustaviKviz).isVisible = false

        //Defaultni fragment
        bottomNavigation.selectedItemId = R.id.kvizovi
        val kvizoviFragment = FragmentKvizovi.newInstance()
        openFragment(kvizoviFragment)


    }

    //Funkcija za izmjenu fragmenta
    private fun openFragment(fragment : Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment).addToBackStack(null)
        transaction.commit()
    }


    override fun onBackPressed() {
        val bottomNavigationView =
            findViewById<View>(R.id.bottomNav) as BottomNavigationView
        bottomNavigationView.menu.findItem(R.id.predajKviz).isVisible = false
        bottomNavigationView.menu.findItem(R.id.zaustaviKviz).isVisible = false
        bottomNavigationView.menu.findItem(R.id.kvizovi).isVisible = true
        bottomNavigationView.menu.findItem(R.id.predmeti).isVisible = true
        val selectedItemId = bottomNavigationView.selectedItemId
        if (R.id.kvizovi != selectedItemId) {
            supportFragmentManager.popBackStack();
            setHomeItem(this@MainActivity)
        } else {
            super.onBackPressed()
        }
    }

    fun setHomeItem(activity: Activity) {
        val bottomNavigationView =
            activity.findViewById<View>(R.id.bottomNav) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.kvizovi
    }
}



