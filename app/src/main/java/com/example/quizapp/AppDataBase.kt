import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [User::class, Question::class, Reponse::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.userDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(userDao: UserDao) {
            // Supprime tout le contenu ici.
            userDao.deleteAll()

            // Ajoute des utilisateurs exemples.
            var user = User(name = "Alice", score = 100)
            userDao.insert(user)
            user = User(name = "Bob", score = 150)
            userDao.insert(user)

            // Ajoute des questions exemples.
            var question = Question(
                question1 = "Quelle est la capitale de la France?",
                reponse1 = "Paris",
                reponse2 = "Londres",
                reponse3 = "Berlin",
                reponse4 = "Madrid"
            )
            userDao.insertQuestion(question)
            question = Question(
                question1 = "Quelle est la plus grande planète du système solaire?",
                reponse1 = "Mars",
                reponse2 = "Jupiter",
                reponse3 = "Saturne",
                reponse4 = "Neptune"
            )
            userDao.insertQuestion(question)

            // Ajoute des réponses exemples.
            var reponse = Reponse(idQuestion = 1, indexReponse = 1)
            userDao.insertReponse(reponse)
            reponse = Reponse(idQuestion = 2, indexReponse = 2)
            userDao.insertReponse(reponse)
        }
    }
}