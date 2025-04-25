import com.example.bitzu.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET("/usuario")
    fun getUsuarios(): Call<List<User>>

    @GET("/usuario/email/{email}")
    fun getUsuarioByEmail(@Path("email") email: String, @Header("Authorization") token: String): Call<User>

    @POST("/usuario/create")
    fun createUser(@Body user: User): Call<User>

}
