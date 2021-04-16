package ba.etf.rma21.projekat

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingPolicies
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matchers.instanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.EnumSet.allOf
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)

class MySpirala2AndroidTest {
    @get:Rule
    val intentsTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun upisiPredmetTest() {

        onView(withId(R.id.filterKvizova)).check(matches(isDisplayed()))
        onView(withId(R.id.predmeti)).perform(ViewActions.click())
        onView(withId(R.id.odabirGodina)).perform(ViewActions.click())
        Espresso.onData(
            CoreMatchers.allOf(
                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                CoreMatchers.`is`(1.toString())
            )
        ).perform(ViewActions.click())
        onView(withId(R.id.odabirPredmet)).perform(ViewActions.click())

        Espresso.onData(
            CoreMatchers.allOf(
                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                CoreMatchers.`is`("VIS")
            )
        ).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.odabirGrupa)).perform(ViewActions.click())
        Espresso.onData(
            CoreMatchers.allOf(
                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                CoreMatchers.`is`("Grupa2")
            )
        ).perform(ViewActions.click())
        onView(withId(R.id.dodajPredmetDugme)).perform(ViewActions.click())
        onView(ViewMatchers.withSubstring("Uspješno ste upisani u grupu Grupa2 predmeta VIS!"))
        Espresso.pressBack()
        onView(withId(R.id.filterKvizova)).perform(click())
        Espresso.onData(
            CoreMatchers.allOf(
                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                CoreMatchers.`is`("Svi moji kvizovi")
            )
        ).perform(ViewActions.click())

        onView(withId(R.id.listaKvizova)).perform(
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(CoreMatchers.allOf(
                ViewMatchers.hasDescendant(ViewMatchers.withText("RMA")),
                ViewMatchers.hasDescendant(ViewMatchers.withText("Kviz 1 - vježbe 2 i 3"))
                ))).perform(click())
        onView(withId(R.id.navigacijaPitanja)).check(matches(isDisplayed()))
    }
    @Test
    fun pitanjaTest() {
        onView(withId(R.id.listaKvizova)).perform(
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(CoreMatchers.allOf(
                ViewMatchers.hasDescendant(ViewMatchers.withText("Kviz 1 - vježbe 2 i 3")),
                ViewMatchers.hasDescendant(ViewMatchers.withText("RMA"))
            ))).perform(click())


    }
}