package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.BuildConfig
import ba.etf.rma21.projekat.data.models.*
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface Api {
    @GET("kviz")
    suspend fun dajSveKvizove(
    ): Response<List<Kviz>>

    @GET("kviz/{id}")
    suspend fun dajKviz(
        @Path("id") id : Int
    ) : Response<Kviz>

    @GET("grupa/{id}/kvizovi")
    suspend fun dajUpisane(
        @Path("id") id : Int
    ): Response<List<Kviz>>

    @GET("predmet")
    suspend fun dajSvePredmete () : Response<List<Predmet>>
    @GET("predmet/{id}")
    suspend fun dajPredmet (
        @Path("id") id : Int
    ) : Response<Predmet>


    @GET("grupa")
    suspend fun dajSveGrupe () : Response<List<Grupa>>

    @GET("predmet/{id}/grupa")
    suspend fun dajGrupeZaPredmet(
        @Path("id") id : Int
    ): Response<List<Grupa>>

    @POST("grupa/{gid}/student/{id}")
    suspend fun upisiStudentaUGrupu(
        @Path("gid") gid : Int,
        @Path("id") id : String = "9d53bd38-18d2-49ec-889f-703ab44db589"
    ) : Response<ResponseBody>

    @GET("student/{id}/grupa")
    suspend fun dajStudentoveGrupe(
        @Path("id") id : String = "9d53bd38-18d2-49ec-889f-703ab44db589"
    ) : Response<List<Grupa>>

    @GET("kviz/{id}/pitanja")
    suspend fun dajPitanja(
        @Path("id") id : Int
    ) : Response<List<Pitanje>>

    @GET("kviz/{id}/grupa")
    suspend fun dajDostupneGrupe(
        @Path("id") id : Int
    ) : Response<List<Grupa>>

    @POST("student/{id}/kviz/{kid}")
    suspend fun zapocniKviz(
        @Path("kid") kid : Int,
        @Path("id") id : String = "9d53bd38-18d2-49ec-889f-703ab44db589"
    ) : Response<KvizTaken>

    @GET("student/{id}/kviztaken")
    suspend fun dajZapocete(
        @Path("id") id : String = "9d53bd38-18d2-49ec-889f-703ab44db589"
    ) : Response<List<KvizTaken>>

    @GET("student/{id}/kviztaken/{kid}/odgovori")
    suspend fun dajOdgovore(
        @Path("kid") kid : Int,
        @Path("id") id : String = "9d53bd38-18d2-49ec-889f-703ab44db589"
    ) : Response<List<Odgovor>>

    @POST("student/{id}/kviztaken/{ktid}/odgovor")
    suspend fun dodajOdgovor(
        @Path("ktid") ktid: Int,
        @Body body: JsonObject,
        @Path("id") id: String = "9d53bd38-18d2-49ec-889f-703ab44db589"
    ) : Response<Odgovor>

}