package com.dojo.moovies.out.api.data.motn


internal data class PtInfo(
    val br : List<StreamingInfo>
)

internal data class StreamingInfo(
    val service:String,
    val streamingType: String,
    val link: String
)