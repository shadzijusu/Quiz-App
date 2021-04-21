package ba.etf.rma21.projekat

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.GrupaRepository
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PredmetRepository
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.beans.HasPropertyWithValue.hasProperty
import org.junit.Test
import java.util.*
import org.hamcrest.CoreMatchers.`is` as Is
class RepositoryUnitClass {
    @Test
    fun testGetGroupsByPredmet() {
        val grupe = GrupaRepository.getGroupsByPredmet("OOAD")
        assertEquals(grupe.size, 5)
        assertThat(grupe, hasItem<Grupa>(hasProperty("naziv", Is("UTO12"))))
        assertThat(grupe, hasItem<Grupa>(hasProperty("naziv", Is("PET10"))))
    }
    @Test
    fun testGetUpisaniIUpisiPredmet() {
        val upisani = PredmetRepository.getUpisani()
        assertEquals(upisani.size, 4)
        val novi = Predmet("OBP", 2)
        PredmetRepository.upisiPredmet(novi)
        assertEquals(upisani.size, 5)
        assertThat(upisani, hasItem<Predmet>(hasProperty("naziv", Is("OBP"))))
        assertThat(upisani, hasItem<Predmet>(hasProperty("naziv", Is("RPR"))))
    }
    @Test
    fun testGetAll() {
        val predmeti = PredmetRepository.getAll()
        assertEquals(predmeti.size, 20)
        assertThat(predmeti, hasItem<Predmet>(hasProperty("naziv", Is("WT"))))
        assertThat(predmeti, hasItem<Predmet>(hasProperty("naziv", Is("MPVI"))))
    }
    @Test
    fun testGetPredmetiByGodina() {
        val predmeti = PredmetRepository.getPredmetsByGodina(3)
        assertEquals(predmeti.size, 4)
        assertThat(predmeti, hasItem<Predmet>(hasProperty("naziv", Is("WT"))))
        assertThat(predmeti, hasItem<Predmet>(hasProperty("naziv", Is("OOI"))))
        assertThat(predmeti, hasItem<Predmet>(hasProperty("naziv", Is("VVS"))))
        assertThat(predmeti, hasItem<Predmet>(hasProperty("naziv", Is("VI"))))
    }
    @Test
    fun testGetMyKvizes() {
        val kvizovi = KvizRepository.getMyKvizes()
        assertEquals(kvizovi.size, 5)
        assertThat(kvizovi, hasItem<Kviz>(hasProperty("naziv", Is("Kviz - OOP u Javi"))))
        assertThat(kvizovi, hasItem<Kviz>(hasProperty("nazivGrupe", Is("Grupa4"))))
    }
    @Test
    fun testGetAllKvizes() {
        val kvizovi = KvizRepository.getAll()
        assertEquals(kvizovi.size, 14)
        assertThat(kvizovi, hasItem<Kviz>(hasProperty("naziv", Is("Priprema za ispit"))))
    }
    @Test
    fun testGetDone() {
        val kvizovi = KvizRepository.getDone()
        assertEquals(kvizovi.size, 2)
        assertThat(kvizovi, hasItem<Kviz>(hasProperty("naziv", Is("Kviz - OOP u Javi"))))
    }
    @Test
    fun testGetFuture() {
        val kvizovi = KvizRepository.getFuture()
        assertEquals(kvizovi.size, 1)
        assertThat(kvizovi, hasItem<Kviz>(hasProperty("naziv", Is("Test2"))))
    }
    @Test
    fun testGetNotTaken() {
        val kvizovi = KvizRepository.getNotTaken()
        assertEquals(kvizovi.size, 1)
        assertThat(kvizovi, hasItem<Kviz>(hasProperty("naziv", Is("Kviz1"))))
    }
    @Test
    fun testAddMyKviz() {
        val kvizovi = KvizRepository.getMyKvizes()
        assertEquals(kvizovi.size, 5)
        val kviz = Kviz(
            "Red i stek", "ASP", Date(2021, 3, 15),
            Date(2021, 4, 30), Date(0, 0, 0),
            6, "Grupa2A", null
        )
        KvizRepository.addMine(kviz)
        assertEquals(kvizovi.size, 6)
        assertThat(kvizovi, hasItem<Kviz>(hasProperty("naziv", Is("Red i stek"))))
    }
}