package com.example.covid_19tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    lateinit var worldCases:TextView
    lateinit var worldRecovered:TextView
    lateinit var worldDeaths:TextView
    lateinit var IndiaCases:TextView
    lateinit var IndiaRecovered:TextView
    lateinit var IndiaDeaths:TextView
    lateinit var StateRv:RecyclerView
    lateinit var stateAdapter:StateAdapter
    lateinit var StateList:List<StateData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        worldCases = findViewById(R.id.worldCases)
        worldRecovered=findViewById(R.id.worldRecovered)
        worldDeaths=findViewById(R.id.worldDeaths)
        IndiaCases=findViewById(R.id.indiaCases)
        IndiaRecovered=findViewById(R.id.indiaRecovered)
        IndiaDeaths=findViewById(R.id.indiaDeaths)
        StateRv = findViewById(R.id.stateStats)
        StateList=ArrayList<StateData>()
        getStateData()
        getWorldData()

    }

    private fun getStateData() {
        val url = "https://api.rootnet.in/covid19-in/stats/latest"
        val queue = Volley.newRequestQueue(this@MainActivity)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val dataObj = response.getJSONObject("data")
                    val summeryObj = dataObj.getJSONObject("summary")
                    val cases = summeryObj.getInt("total")
                    val recovered = summeryObj.getInt("discharged")
                    val death = summeryObj.getInt("deaths")

                    IndiaCases.text = cases.toString()
                    IndiaRecovered.text=recovered.toString()
                    IndiaDeaths.text=death.toString()

                    val regionalArray = dataObj.getJSONArray("regional")
                    for (i in 0 until regionalArray.length()){
                        val regionalObj = regionalArray.getJSONObject(i)
                        val stateName = regionalObj.getString("loc")
                        val totalCases = regionalObj.getInt("totalConfirmed")
                        val stateRecovered = regionalObj.getInt("discharged")
                        val stateDeaths = regionalObj.getInt("deaths")

                       val stateData = StateData(stateName,stateRecovered,stateDeaths,totalCases)
                        StateList=StateList+stateData
                    }
                     stateAdapter = StateAdapter(StateList)
                    StateRv.layoutManager=LinearLayoutManager(this)
                    StateRv.adapter= stateAdapter
                }
                catch (e:JSONException){
                    e.printStackTrace()
                }
            },
            {
                Toast.makeText(this,"Failed to load data",Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
    }

    private fun getWorldData(){
        val url = "https://disease.sh/v3/covid-19/all"
        val queue  = Volley.newRequestQueue(this@MainActivity)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val cases = response.getInt("cases")
                    val worldrecovered = response.getInt("recovered")
                    val worlddeaths = response.getInt("deaths")

                    worldCases.text=cases.toString()
                    worldRecovered.text = worldrecovered.toString()
                    worldDeaths.text = worlddeaths.toString()
                }
                catch (e:JSONException){
                    e.printStackTrace()
                }


            },
            {
                Toast.makeText(this,"Failed to load data",Toast.LENGTH_LONG).show()

            }
        )
        queue.add(jsonObjectRequest)

    }
}