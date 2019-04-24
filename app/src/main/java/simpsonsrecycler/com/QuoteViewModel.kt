package simpsonsrecycler.com

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class QuoteViewModel : ViewModel() {

    private var quotes: MutableLiveData<List<Quote>> = MutableLiveData()
    val q: LiveData<List<Quote>> = quotes

    private val baseURL = "https://thesimpsonsquoteapi.glitch.me/"

    fun loadData() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseURL).build()

        val postsApi = retrofit.create(INetworkAPI::class.java)

        val response = postsApi.getQuotes(40)

        response.observeOn(AndroidSchedulers.mainThread()).subscribeOn(IoScheduler()).subscribe { result ->
            quotes.value = result
        }
    }
}


