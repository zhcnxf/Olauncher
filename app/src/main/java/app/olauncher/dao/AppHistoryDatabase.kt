package app.roomdatabase

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Database(
    entities = [
        AppHistory::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppHistoryDatabase : RoomDatabase() {

    abstract fun appHistoryDao(): AppHistoryDao

    companion object {
        private const val TAG = "AppHistoryDatabase"

        @Volatile
        private var instance: AppHistoryDatabase? = null

        fun getDatabase(context: Context): AppHistoryDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppHistoryDatabase::class.java,
                    "app_history.db",
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                    .also {
                        instance = it
                    }
            }
        }
    }
}

@Entity(tableName = "app_history", primaryKeys = ["packageName", "activityName"])
data class AppHistory(
    var packageName: String,
    var activityName: String,
    var lastOpened: Long,
)

@Dao
interface AppHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(appHistory: AppHistory)

    @Query("SELECT * FROM app_history")
    fun getAppHistoryFlow(): Flow<List<AppHistory>>
}