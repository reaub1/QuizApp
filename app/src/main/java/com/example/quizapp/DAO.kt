import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM questions")
    suspend fun  getAllQuestions(): List<Question>

    @Query("SELECT * FROM reponses")
    suspend fun  getAllReponses(): List<reponse>
}