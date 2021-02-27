package com.example.schnell_ccms.model

import com.example.ilmpole.model.Device
import com.google.gson.annotations.SerializedName

class Asset : ThingsBoardResponse() {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("type")
    var type: String? = null

    @SerializedName("lastUpdateTs")
    var userts: String? = null

    @SerializedName("key")
    var userkey: String? = null

    @SerializedName("value")
    var userval: String? = null

    @SerializedName("data")
    var deviceList: ArrayList<Device>? = null

    fun getDisplayName(): String {
        return if (additionalInfo != null && additionalInfo!!.displayName != null) additionalInfo!!.displayName!! else name!!
    }
}