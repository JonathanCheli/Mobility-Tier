package com.example.tier_challeng_jonathancheli

import android.util.Log
import androidx.lifecycle.MutableLiveData
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class Repository private constructor() {

    private val scooter: MutableLiveData<List<Scooter>> = MutableLiveData<List<Scooter>>()

    fun getData(): MutableLiveData<List<Scooter>>{
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()

                val request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("secret-key", "\$2b\$10\$VE0tRqquld4OBl7LDeo9v.afsyRXFlXcQzmj1KpEB6K1wG2okzQcK")
                    .method(original.method, original.body)
                    .build()

                return chain.proceed(request)
            }
        })

        val listScooter: ArrayList<Scooter> = ArrayList<Scooter>()
        val url = "https://api.jsonbin.io/b/5fa8ff8dbd01877eecdb898f"
        val request = Request.Builder()
            .url(url)
            .build()
        val client = httpClient.build()

        client.newCall(request).enqueue(object :  Callback {
            override fun onResponse(call: Call, response: Response) {
                val bodyJson = response.body?.string()



                try {

                    val jsonOb = JSONObject(bodyJson!!)
                    val jsonObj: JSONObject = jsonOb.getJSONObject("data")
                    val jsonA: JSONArray = jsonObj.getJSONArray("current")

                    val limit : Int = jsonA.length()

                    for (i in 0 until limit) {

                        val `object` = jsonA.getJSONObject(i)

                        val id  =  `object`.getString("id")
                        val vehicleId  =  `object`.getString("vehicleId")
                        val hardwareId  =  `object`.getString("hardwareId")
                        val zoneId  =  `object`.getString("zoneId")
                        val resolution  =  `object`.optString("resolution")
                        val resolvedBy  =  `object`.optString("resolvedBy")
                        val resolvedAt  =  `object`.optString("resolvedAt")
                        val battery  =  `object`.getInt("battery")
                        val state  =  `object`.getString("state")
                        val model  =  `object`.getString("model")
                        val fleetbirdId  =  `object`.getInt("fleetbirdId")
                        val latitude  =  `object`.getString("latitude")
                        val longitude  =  `object`.getString("longitude")

                        val scooteData = Scooter (id,vehicleId,
                            hardwareId,zoneId,resolution,resolvedBy,
                            resolvedAt,battery, state,model,fleetbirdId,
                            latitude,longitude)

                        listScooter.add(scooteData)








                    }

                    } catch (e: JSONException){
                        Log.e("TAG", "Error processing JSON", e)
                    }



                scooter.postValue(listScooter)
               
                }


            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
                e.printStackTrace()
            }
        })

        return scooter
    }
    companion object {
        var instance: Repository? = null
            get() {
                if (field == null) {
                    field = Repository()
                }
                return field
            }
            private set
    }
}
