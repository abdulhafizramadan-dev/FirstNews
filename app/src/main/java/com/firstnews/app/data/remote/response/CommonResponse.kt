package com.firstnews.app.data.remote.response

import com.google.gson.annotations.SerializedName

data class CommonResponse<T>(

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("articles")
	val data: T? = null,

	@field:SerializedName("status")
	val status: String? = null
)



