package simpsonsrecycler.com

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quote: Quote)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg quote: Quote)

    @Query("SELECT * FROM quotes_table")
    fun getAllQuotes(): LiveData<List<Quote>>

//    @get:Query("SELECT * FROM quotes_table")
//    val allQuotes: LiveData<List<Quote>>
}


