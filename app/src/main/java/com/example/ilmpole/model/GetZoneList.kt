package com.example.schnell_ccms.model


import com.example.ilmpole.model.Entity
import com.google.gson.annotations.SerializedName
import com.schnell.http.Response

class GetZoneList : Response() {

    @SerializedName("from")
    var fromentity: Entity? = null

    @SerializedName("to")
    var toentity: Entity? = null

    @SerializedName("type")
    var type: String? = null

    @SerializedName("typeGroup")
    var typeGroup: String? = null

    @SerializedName("additionalInfo")
    var additInfo: String? = null

    @SerializedName("data")
    var deviceList: ArrayList<GetZoneList>? = null
}

