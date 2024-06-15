import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Insert
    suspend fun insertQuestion(question: Question)

    @Insert
    suspend fun insertReponse(reponse: Reponse)

    @Query("DELETE FROM user")
    suspend fun deleteAll()

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM questions")
    suspend fun getAllQuestions(): List<Question>

    @Query("SELECT * FROM reponses")
    suspend fun getAllReponses(): List<Reponse>
}