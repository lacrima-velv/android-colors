package com.example.colors.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Element::class], version = 1, exportSchema = false)
abstract class ElementDatabase : RoomDatabase() {
    // Connects the database to DAO
    abstract val elementDao: ElementDao

    companion object {

        /*
        INSTANCE will keep a reference to any database returned via getInstance
        Volatile value will never be cached.
        Changes made by one thread to shared data are visible to other threads.
         */
        @Volatile
        private var INSTANCE: ElementDatabase? = null

        /*
        Helper function to get the database. If it has already been retrieved, the previous database
        will be returned.
         */
        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context): ElementDatabase {
            /*
             Use synchronized as it may be called by multiple threads, but we need to be sure that
             the database is initialized only once
             */
            synchronized(this) {
                /*
                Copy the current value of INSTANCE to a local variable so we can make smart cast.
                It is only available to local variables.
                 */
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ElementDatabase::class.java,
                        "element_database"
                    )
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        .fallbackToDestructiveMigration()
                        .build()
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }
                return instance

            }
        }
    }
}
