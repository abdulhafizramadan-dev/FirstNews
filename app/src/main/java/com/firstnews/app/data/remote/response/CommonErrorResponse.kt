package com.firstnews.app.data.remote.response

import com.google.gson.annotations.SerializedName

data class CommonErrorResponse(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
