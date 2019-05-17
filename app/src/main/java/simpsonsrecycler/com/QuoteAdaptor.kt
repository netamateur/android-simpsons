package simpsonsrecycler.com

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class QuoteAdaptor(private var quoteList: List<Quote>, private val context: Context) : RecyclerView.Adapter<QuoteAdaptor.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txtName.text = quoteList[position].character
        holder.txtQuote.text = quoteList[position].quote

        Picasso.with(this.context).load(quoteList[position].image)
            .placeholder(R.drawable.simpsons_placeholder)
            .fit()
            .into(holder.charImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = when (viewType) {
            TYPE_LEFT -> LayoutInflater.from(parent.context).inflate(R.layout.item_left_layout, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.item_right_layout, parent, false)
        }
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return quoteList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (quoteList[position].direction) {
            ViewDirection.VIEW_LEFT.direction -> TYPE_LEFT
            else -> TYPE_RIGHT
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtName = itemView.findViewById<TextView>(R.id.character_text)!!
        val txtQuote = itemView.findViewById<TextView>(R.id.quote_text)!!
        val charImage = itemView.findViewById<ImageView>(R.id.image)!!
    }

    companion object {
        const val TYPE_LEFT = 0
        const val TYPE_RIGHT = 1
    }

}