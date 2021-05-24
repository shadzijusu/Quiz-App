package ba.etf.rma21.projekat.data.repositories

class ApiConfig {
    private var baseURL : String = "https://rma21-etf.herokuapp.com"
    fun postaviBaseURL(baseUrl:String):Unit {
        baseURL = baseUrl
    }

}