package com.example.covid_19tracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class StateAdapter(private val DataList:List<StateData>):RecyclerView.Adapter<StateAdapter.StateViewModel>() {

     class StateViewModel(itemView: View) :RecyclerView.ViewHolder(itemView){
         val StateNameTV=itemView.findViewById<TextView>(R.id.stateName)
        val StateCasesTV = itemView.findViewById<TextView>(R.id.stateCases)
        val StateDeathsTV = itemView.findViewById<TextView>(R.id.stateDeaths)
        val stateRecovered = itemView.findViewById<TextView>(R.id.stateRecovered)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewModel {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.state_items,parent,false)
        return StateViewModel(view)
    }

    override fun onBindViewHolder(holder: StateViewModel, position: Int) {
        val StateData = DataList[position]
        with(holder) {
            StateNameTV.text = StateData.stateName.toString()
            StateCasesTV.text = StateData.cases.toString()
            StateDeathsTV.text=StateData.deaths.toString()
            stateRecovered.text=StateData.recovered.toString()
        }
    }

    override fun getItemCount(): Int {
        return DataList.size
    }
}