package com.dojo.moovies.out.api.data.motn

internal data class StreamingServicesResponse(
    val result: StreamingServicesListByCountry
)

internal data class StreamingServicesListByCountry(
    val br: StreamingServicesByCountry
)

internal data class StreamingServicesByCountry(
    val countryCode: String,
    val name: String,
    val services: Map<String, StreamingService>
)

internal data class StreamingService(
    val id: String,
    val name: String,
    val homePage: String,
    val themeColorCode: String,
    val images: StreamingServiceImages
)

internal data class StreamingServiceImages(
    val lightThemeImage: String,
    val darkThemeImage: String,
    val whiteImage: String
)