package cl.maleb.testcarsales.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.maleb.testcarsales.R
import cl.maleb.testcarsales.network.model.Covid
import kotlinx.android.synthetic.main.item_home.view.*

class HomeAdapter(private val context: Context) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private var list: List<Covid> = ArrayList()

    fun setCovidList(list: List<Covid>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.date.text = list[position].date
        holder.date.text = "22 de Junio del 2020"
        holder.confirmed.text = "Casos confirmados: " + list[position].confirmed.toString()
        holder.passed.text = "Cantidad de personas fallecidas: " + list[position].deaths.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_home,
                parent,
                false
            )
        )
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val date = v.tvDate!!
        val confirmed = v.tvConfirmedCases!!
        val passed = v.tvPassedAway!!

    }

}