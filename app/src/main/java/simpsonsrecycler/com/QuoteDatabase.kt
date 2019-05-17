package simpsonsrecycler.com

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Quote::class], version = 1)
abstract class QuoteDatabase: RoomDatabase() {

    abstract fun quoteDao(): QuoteDao

    companion object {
        @Volatile
        private var instance: QuoteDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope
        ): QuoteDatabase? {
            if (instance == null) {
                synchronized(QuoteDatabase::class) {
                    instance = Room.databaseBuilder<QuoteDatabase>(
                        context.applicationContext,
                        QuoteDatabase::class.java, "quotes_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(QuoteDatabaseCallback(scope))
                        .build()
                }
            }
            return instance
        }

//        private val roomCallback = object : RoomDatabase.Callback() {
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//                PopulateDbAsyncTask(instance)
//                    .execute()
//            }
//        }
//    }

        private class QuoteDatabaseCallback(
            private val scope: CoroutineScope
        ) :
            RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                instance?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.quoteDao())
                    }
                }
            }

            fun populateDatabase(quoteDao: QuoteDao) {
                //api call here?
                //method in repository?
                //QuoteRepository.downloadData()


                quoteDao.insertAll()
            }
        }
    }
}

//class PopulateDbAsyncTask(db: QuoteDatabase?) : AsyncTask<Unit, Unit, Unit>() {
//
//    private val quoteDao = db?.quoteDao()
//
//    override fun doInBackground(vararg params: Unit?) {
//        quoteDao?.insertAll(*fetchedQuoteList.toTypeArray())
//    }
//}