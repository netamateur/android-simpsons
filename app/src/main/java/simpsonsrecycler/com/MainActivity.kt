package simpsonsrecycler.com

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: QuoteViewModel
//    val viewModel: QuoteViewModel by lazy {
//        ViewModelProviders.of(this).get(QuoteViewModel::class.java)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(QuoteViewModel::class.java)

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            viewModel.loadData()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = QuoteAdaptor(emptyList(), this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.loadData()

        viewModel.q.observe(this, Observer<List<Quote>> { quotes ->
            recyclerView.adapter = QuoteAdaptor(quotes?: listOf(), this)
        })

    }
}
