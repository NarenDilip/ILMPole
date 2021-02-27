package com.example.schnell_ccms.model

import com.example.ilmpole.model.Entity
import com.google.gson.annotations.SerializedName
import com.schnell.http.Response

class AssetDetails : Response() {

    @SerializedName("id")
    var id: Entity? = null

    @SerializedName("customerId")
    var customerid: Entity? = null

    @SerializedName("tenantId")
    var tenantid: Entity? = null

    @SerializedName("ownerId")
    var ownerid: Entity? = null

    @SerializedName("createdTime")
    var createdtime: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("type")
    var type: String? = null

    @SerializedName("label")
    var label: String? = null

    @SerializedName("additionalInfo")
    var additInfo: addDetails? = null

    @SerializedName("data")
    var devicedataList: ArrayList<GetZoneList>? = null

}