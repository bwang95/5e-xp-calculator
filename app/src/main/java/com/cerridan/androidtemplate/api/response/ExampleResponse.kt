package com.cerridan.androidtemplate.api.response

import com.squareup.moshi.Json

data class ExampleResponse(
  @Json(name = "example_field") val exampleField: String
)