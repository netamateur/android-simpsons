package simpsonsrecycler.com

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {


    val viewModel: QuoteViewModel by lazy {
        ViewModelProviders.of(this).get(QuoteViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        viewModel.loadData()
        viewModel.q.observe(this, Observer{ quotes ->
            recyclerView.adapter = QuoteAdaptor(quotes?: listOf(), this)
        })

    }
}
