import com.example.bitzu.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @GET("/usuario")
    fun getUsuarios(): Call<List<User>>

    @POST("/usuario/create")
    fun createUser(@Body user: User): Call<User>
}
