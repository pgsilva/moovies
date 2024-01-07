package com.dojo.moovies.out.api.data.motn

internal data class StreamingInfoResponse(
    val result: TitleStreamingInfo
)

internal data class TitleStreamingInfo(
    val type: String,
    val title: String,
    val streamingInfo: PtInfo,
    val originalTitle: String,
)
