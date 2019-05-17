package simpsonsrecycler.com

import android.app.Application
import android.os.AsyncTask
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class QuoteRepository(private val quoteDao: QuoteDao) {

    val allQuotes: LiveData<List<Quote>> = quoteDao.getAllQuotes()

    @WorkerThread
    suspend fun insert(quote: Quote) {
        quoteDao.insert(quote)
    }
}

// class QuoteRepository(application: Application) {
//
// private var quoteDao: QuoteDao
// private var allQuotes: LiveData<List<Quote>>
//
//
//
// init {
// val database: QuoteDatabase = QuoteDatabase.getDatabase(
// application.applicationContext
// )!!
// quoteDao = database.quoteDao()
// allQuotes = quoteDao.getAllQuotes()
// }
//
// fun insert(quote: Quote) {
// val insertQuoteAsyncTask = InsertQuoteAsyncTask(quoteDao).execute(quote)
// }
//
// fun getAllQuote(): LiveData<List<Quote>> {
// return allQuotes
// }
//
// private class InsertQuoteAsyncTask(quoteDao: QuoteDao) : AsyncTask<Quote, Unit, Unit>() {
// override fun doInBackground(vararg params: Quote?) {
// quoteDao.insert(params[0]!!)
// }
//
// val quoteDao = quoteDao
// }
//
//
//
private lateinit var fetchedQuotes: List<Quote>

private val baseURL = "https://thesimpsonsquoteapi.glitch.me/"

fun downloadData() {
    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(baseURL).build()

    val postsApi = retrofit.create(INetworkAPI::class.java)

    val response = postsApi.getQuotes(10)

    response.observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(IoScheduler())
        .subscribe { result ->
            fetchedQuotes = result
        }
}