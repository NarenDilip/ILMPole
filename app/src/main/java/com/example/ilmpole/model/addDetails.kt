package com.example.schnell_ccms.model

import com.google.gson.annotations.SerializedName
import com.schnell.http.Response

class addDetails : Response(){

    @SerializedName("description")
    var description: String? = null
}
