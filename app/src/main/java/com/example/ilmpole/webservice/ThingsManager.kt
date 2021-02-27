package com.example.schnell_ccms.webservice

import android.content.Context
import com.android.volley.Request
import com.example.ilmpole.constants.ApplicationConstants
import com.example.ilmpole.model.AssetGroup
import com.example.ilmpole.model.Device
import com.example.ilmpole.model.DeviceCredential
import com.example.ilmpole.model.Entity
import com.example.ilmpole.webservice.AppPreference
import com.example.schnell_ccms.model.*
import com.google.gson.Gson
import com.schnell.http.*
import org.json.JSONObject
import org.json.JSONTokener
import java.lang.reflect.Method

/**
 * @since 24/2/17.<BR></BR>
 * Common request elements can be updated in this class.<BR></BR>c
 * Extend this class for all web service classes to avoid redundancy
 */

open class ThingsManager {
    interface API {
        companion object {

            /* User and Login APIs */
            const val login = "/api/auth/login"
            const val saveRelation = "/api/relation"
            const val saveDeviceCredential = "/api/device/credentials"
            const val ZoneList = "/api/relations"
            const val getDeviceName = "/api/device"
            const val getAssetName = "/api/asset"
            const val getfromdevice = "/api/relations/info?"
            const val device = "/api/device"
            const val asset = "/api/asset"
            const val getPoleDetails = "/api/tenant/assets?"

            const val getDeviceCredentialsByDeviceId = "/api/device/{assetId}/credentials"
            const val addAttribute = "/api/plugins/telemetry/{entityValType}/{entityId}/attributes/{scope}"

            const val getDeviceTelemetry = "/api/plugins/telemetry"
            const val getDeviceAssets = "/api/entityGroups/ASSET"
        }
    }

    companion object {

        private fun constructUrl(urlKey: String): String {
            return String.format("%s%s", ApplicationConstants.THINGS_BOARD_URL, urlKey)
        }

        private fun fillCommons(c: Context, r: VolleyClient) {
            r.addHeader("X-Authorization", "Bearer ${AppPreference[c, AppPreference.Key.accessToken, ""]}")
        }

        /**
         * @param c    Context of App
         * @param username - user given username
         * @param password - user given password
         * @return false if exception happened before http call
         */
        fun login(c: Context, username: String, password: String) {
            try {
                // Generating Req
                val client = JsonClient(
                        c, Request.Method.POST,
                        constructUrl(API.login), API.login.hashCode()
                )
                val jsonObject = JSONObject()
                jsonObject.put("username", username)
                jsonObject.put("password", password)
                client.execute(c as ResponseListener, jsonObject, LoginResponse::class.java)
            } catch (e: Exception) {
                throw e
            }
        }


        fun saveDevice(
                c: Context,
                l: ResponseListener,
                entityGroupId: String,
                device: Device,
                extraOutput: String? = null
        ) {
            try {
                // Generating Req
                val client = JsonClient(
                        c, Request.Method.POST,
                        constructUrl("${API.device}/?entityGroupId=$entityGroupId"), API.device.hashCode()
                )
                fillCommons(c = c, r = client)
                client.extraOutput = extraOutput
                val jsonObject = JSONTokener(Gson().toJson(device)).nextValue() as JSONObject
                client.execute(
                        l = l,
                        jsonObject = jsonObject,
                        responseType = Device::class.java
                )
            } catch (e: Exception) {
                throw e
            }
        }

        fun deleteDevice(c: Context, l: ResponseListener, deviceId: String) {
            try {
                // Generating Req
                val client = RestClient(
                        c, Request.Method.DELETE, constructUrl("${API.device}/$deviceId"), API.device.hashCode()
                )
                fillCommons(c = c, r = client)
                client.execute(l, responseType = Device::class.java)
            } catch (e: Exception) {
                throw e
            }
        }

        fun saveAsset(
                c: Context,
                l: ResponseListener,
                entityGroupId: String,
                asset: Asset,
                extraOutput: String? = null
        ) {
            try {
                // Generating Req
                val client = JsonClient(
                        c, Request.Method.POST,
                        constructUrl("${API.asset}/?entityGroupId=$entityGroupId"), API.asset.hashCode()
                )
                fillCommons(c = c, r = client)
                client.extraOutput = extraOutput
                val jsonObject = JSONTokener(Gson().toJson(asset)).nextValue() as JSONObject
                client.execute(
                        l = l,
                        jsonObject = jsonObject,
                        responseType = Asset::class.java
                )
            } catch (e: Exception) {
                throw e
            }
        }


        fun saveRelation(c: Context, gatewayDeviceId: String, device: Device) {
            try {
                // Generating Req
                val client = JsonClient(
                        c, Request.Method.POST,
                        constructUrl(API.saveRelation), API.saveRelation.hashCode()
                )
                fillCommons(c = c, r = client)

                val fromObject = Entity()
                fromObject.entityType = "ASSET"
                fromObject.id = gatewayDeviceId

                val jsonObject = JSONObject()
                jsonObject.put("type", "Contains")
                jsonObject.put("typeGroup", "COMMON")
                jsonObject.put("from", JSONTokener(Gson().toJson(fromObject)).nextValue() as JSONObject)
                jsonObject.put("to", JSONTokener(Gson().toJson(device.id)).nextValue() as JSONObject)
                client.execute(
                        c as ResponseListener,
                        jsonObject = jsonObject,
                        responseType = Response::class.java
                )
            } catch (e: Exception) {
                throw e
            }
        }

        fun saveDeviceCredential(c: Context, deviceCredential: DeviceCredential) {
            try {
                // Generating Req
                val client = JsonClient(
                        c, Request.Method.POST,
                        constructUrl(API.saveDeviceCredential), API.saveDeviceCredential.hashCode()
                )
                fillCommons(c = c, r = client)
                val jsonObject = JSONTokener(Gson().toJson(deviceCredential)).nextValue() as JSONObject
                client.execute(
                        c as ResponseListener,
                        jsonObject = jsonObject,
                        responseType = DeviceCredential::class.java
                )
            } catch (e: Exception) {
                throw e
            }
        }

        /**
         * Add number id attributes
         */
        fun addserverAttribute(c: Context, l: ResponseListener, deviceId: String, number: String, data: String) {
            try {
                // Generating Req`
                val client = JsonClient(
                        c, Request.Method.POST,
                        constructUrl(
                                API.addAttribute
                                        .replace("{entityValType}", "ASSET")
                                        .replace("{entityId}", deviceId)
                                        .replace("{scope}", "SERVER_SCOPE")
                        )
                        , API.addAttribute.hashCode()
                )
                fillCommons(c = c, r = client)
                val jsonObject = JSONObject()
                jsonObject.put(number, data)
                client.execute(l = l, jsonObject = jsonObject, responseType = Response::class.java)
            } catch (e: Exception) {
                throw e
            }
        }


        fun getDeviceAssets(
                c: Context,
                l: ResponseListener
        ) {
            try {
                // Generating Req
                val client = RestClient(
                        c,
                        Request.Method.GET,
                        constructUrl("${API.getDeviceAssets}"),
                        API.getDeviceAssets.hashCode()
                )
                fillCommons(c = c, r = client)
                client.execute(l, responseType = AssetGroup::class.java)
            } catch (e: Exception) {
                throw e
            }
        }
    }


//    fun getDeviceLatestAttributes(
//            c: Context,
//            l: ResponseListener,
//            deviceId: String,
//            entityType: String,
//            Keys: String
//    ) {
//        try {
//            // Generating Req
//            val client = RestClient(
//                    c,
//                    Request.Method.GET,
//                    constructUrl("${API.getDeviceTelemetry}/$entityType/$deviceId" + "/values/attributes?keys=$Keys"),
//                    API.getDeviceTelemetry.hashCode()
//            )
//            fillCommons(c = c, r = client)
//            client.execute(l, responseType = Asset::class.java)
//        } catch (e: Exception) {
//            throw e
//        }
//    }

}
