package com.itera.iteratime.ui.maps.respone

import com.google.gson.annotations.SerializedName

data class Distance(

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) {
	fun getText(): Any {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	fun getValue(): Any {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}