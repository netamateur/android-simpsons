package simpsonsrecycler.com

import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("quote") val quote: String,
    @SerializedName("character") val character: String
)