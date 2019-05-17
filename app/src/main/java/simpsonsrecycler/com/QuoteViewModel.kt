package simpsonsrecycler.com

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class QuoteViewModel(application: Application): AndroidViewModel(application) {

//    private val repository: QuoteRepository
//    val allQuotes: LiveData<List<Quote>>
//
//    init {
//        val quoteDao = QuoteDatabase.getDatabase(application, viewModelScope)!!.quoteDao()
//        repository = QuoteRepository(quoteDao)
//        allQuotes = repository.allQuotes
//    }
//
//    fun insert(quote: Quote) = viewModelScope.launch(Dispatchers.IO) {
//        repository.insert(quote)
//    }

    /*
    private var repository: QuoteRepository = QuoteRepository(application)

    private var allQuotes: LiveData<List<Quote>> = repository.getAllQuote()

    fun insert (quote: Quote) {
        repository.insert(quote)
    }

    fun getAllQuotes(): LiveData<List<Quote>> {
        return allQuotes
    }
    */


//move to Repository
    private var fetchedQuotes: MutableLiveData<List<Quote>> = MutableLiveData()
    val q: LiveData<List<Quote>> = fetchedQuotes

    private val baseURL = "https://thesimpsonsquoteapi.glitch.me/"

    fun loadData() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseURL).build()

        val postsApi = retrofit.create(INetworkAPI::class.java)

        val response = postsApi.getQuotes(40)

        response.observeOn(AndroidSchedulers.mainThread()).subscribeOn(IoScheduler()).subscribe { result ->
            fetchedQuotes.value = result
        }

        //add to list

    }
}


