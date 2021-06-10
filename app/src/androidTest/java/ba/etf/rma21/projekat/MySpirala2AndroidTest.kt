//package ba.etf.rma21.projekat
//
//import androidx.recyclerview.widget.RecyclerView
//import androidx.test.espresso.Espresso
//import androidx.test.espresso.Espresso.onData
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.action.ViewActions
//import androidx.test.espresso.action.ViewActions.click
//import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.contrib.NavigationViewActions
//import androidx.test.espresso.contrib.RecyclerViewActions
//import androidx.test.espresso.intent.rule.IntentsTestRule
//import androidx.test.espresso.matcher.ViewMatchers
//import androidx.test.espresso.matcher.ViewMatchers.*
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
//import org.hamcrest.CoreMatchers
//import org.hamcrest.CoreMatchers.anything
//import org.hamcrest.CoreMatchers.not
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//
//
//@RunWith(AndroidJUnit4::class)
//
//class MySpirala2AndroidTest {
//    @get:Rule
//    val intentsTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java)
//
//    @Test
//    fun testUpisPredmeta() {
//        //Upis predmeta
//        onView(withId(R.id.filterKvizova)).check(matches(isDisplayed()))
//        onView(withId(R.id.predmeti)).perform(ViewActions.click())
//        onView(withId(R.id.odabirGodina)).perform(ViewActions.click())
//        Espresso.onData(
//            CoreMatchers.allOf(
//                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
//                CoreMatchers.`is`(1.toString())
//            )
//        ).perform(ViewActions.click())
//        onView(withId(R.id.odabirPredmet)).perform(ViewActions.click())
//
//        Espresso.onData(
//            CoreMatchers.allOf(
//                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
//                CoreMatchers.`is`("VIS")
//            )
//        ).perform(ViewActions.click())
//        onView(ViewMatchers.withId(R.id.odabirGrupa)).perform(ViewActions.click())
//        Espresso.onData(
//            CoreMatchers.allOf(
//                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
//                CoreMatchers.`is`("Grupa2")
//            )
//        ).perform(ViewActions.click())
//        onView(withId(R.id.dodajPredmetDugme)).perform(ViewActions.click())
//        onView(ViewMatchers.withSubstring("Uspješno ste upisani u grupu Grupa2 predmeta VIS!"))
//    }
//
//    @Test
//    fun pitanjaTest() {
//        onView(withId(R.id.filterKvizova)).perform(click())
//        Espresso.onData(
//            CoreMatchers.allOf(
//                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
//                CoreMatchers.`is`("Svi moji kvizovi")
//            )
//        ).perform(click())
//        onView(withId(R.id.listaKvizova)).perform(
//            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
//                CoreMatchers.allOf(
//                    hasDescendant(withText("Kviz 2 - vježbe 4 i 5")),
//                    hasDescendant(withText("RMA"))
//                ), click()
//            )
//        )
//        onView(withId(R.id.navigacijaPitanja)).check(matches(isDisplayed()))
//        onView(withId(R.id.navigacijaPitanja)).check(matches(isDisplayed()))
//        val pitanja = PitanjeKvizRepository.getPitanja("Kviz 2 - vježbe 4 i 5", "RMA")
//        var indeks = 0
//        for (pitanje in pitanja) {
//            onView(withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(indeks))
//            when (indeks) {
//                0 -> onData(anything()).inAdapterView(withId(R.id.odgovoriLista)).atPosition(0)
//                    .perform(click())
//                1 -> onData(anything()).inAdapterView(withId(R.id.odgovoriLista)).atPosition(2)
//                    .perform(click())
//                2 -> onData(anything()).inAdapterView(withId(R.id.odgovoriLista)).atPosition(2)
//                    .perform(click())
//                3 -> onData(anything()).inAdapterView(withId(R.id.odgovoriLista)).atPosition(0)
//                    .perform(click())
//                4 -> onData(anything()).inAdapterView(withId(R.id.odgovoriLista)).atPosition(1)
//                    .perform(click())
//            }
//            indeks++
//        }
//
//        onView(withId(R.id.predajKviz)).perform(click())
//        onView(withSubstring("Završili ste kviz Kviz 2 - vježbe 4 i 5 sa tačnosti 0.8")).check(
//            matches(isDisplayed())
//        )
//        onView(withId(R.id.predajKviz)).check(matches(not(isDisplayed())))
//        onView(withId(R.id.zaustaviKviz)).check(matches(not(isDisplayed())))
//        onView(withId(R.id.kvizovi)).check(matches(isDisplayed()))
//        onView(withId(R.id.predmeti)).check(matches(isDisplayed()))
//        //Povratak na listu kvizova
//        onView(withId(R.id.kvizovi)).perform(click())
//        onView(withId(R.id.listaKvizova)).perform(
//            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
//                CoreMatchers.allOf(
//                    hasDescendant(withText("Kviz 2 - vježbe 4 i 5")),
//                    hasDescendant(withText("RMA"))
//                ), click()
//            )
//        )
//        onView(withId(R.id.predajKviz)).check(matches(not(isDisplayed())))
//        onView(withId(R.id.zaustaviKviz)).check(matches(not(isDisplayed())))
//        onView(withId(R.id.kvizovi)).check(matches(isDisplayed()))
//        onView(withId(R.id.predmeti)).check(matches(isDisplayed()))
//    }
//}