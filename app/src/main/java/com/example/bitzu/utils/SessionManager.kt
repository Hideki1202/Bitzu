import android.content.Context

class SessionManager(private val context: Context) {
    fun saveToken(token: String) {
        val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("jwt_token", token).apply()
    }
}
