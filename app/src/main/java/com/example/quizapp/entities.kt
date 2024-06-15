import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val score: Int
)

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val question1: String,
    val reponse1: String,
    val reponse2: String,
    val reponse3: String,
    val reponse4: String
)
@Entity(tableName = "reponses")
data class Reponse(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idQuestion: Int,
    val indexReponse: Int
)

