package simpsonsrecycler.com

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "quotes_table")
data class Quote(
    @SerializedName("quote") val quote: String,
    @SerializedName("character") val character: String,
    @SerializedName("image") val image: String,
    @SerializedName("characterDirection") val direction: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}


enum class ViewDirection (val direction: String) {
    VIEW_LEFT("Left"),
    VIEW_RIGHT("Right")
}