package com.vishnu.testapplication.data.source.local

import androidx.annotation.WorkerThread
import androidx.room.*

@Database(entities = [Session::class], version = 1, exportSchema = false)
abstract class SessionDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
}

@Entity(tableName = "session_table")
data class Session(
    @PrimaryKey @ColumnInfo(name = "key") val key: String,
    @ColumnInfo(name = "value") val value: String
)

@Dao
interface SessionDao {
    @WorkerThread
    @Query("SELECT value from session_table where `key` = :key")
    fun get(key: String): String?

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(session: Session)

    @WorkerThread
    @Query("DELETE FROM session_table")
    fun clearAll()
}