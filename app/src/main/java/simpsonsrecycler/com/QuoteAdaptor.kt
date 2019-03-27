package simpsonsrecycler.com

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.io.File

class QuoteAdaptor(val quoteList: List<Quote>) : RecyclerView.Adapter<QuoteAdaptor.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.txtName?.text = quoteList[position].character
        holder?.txtQuote?.text = quoteList[position].quote
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return quoteList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtName = itemView.findViewById<TextView>(R.id.character_text)!!
        val txtQuote = itemView.findViewById<TextView>(R.id.quote_text)!!

    }

}