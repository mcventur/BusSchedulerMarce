package com.example.busschedule.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.busschedule.database.schedule.Schedule
import com.example.busschedule.databinding.BusStopItemBinding
import java.text.SimpleDateFormat
import java.util.Date

class BusStopAdapter(private val onItemClicked: (Schedule) -> Unit) :
    ListAdapter<Schedule, BusStopAdapter.BusStopViewHolder> (DiffCallBack){

    class BusStopViewHolder(private var binding: BusStopItemBinding):
        RecyclerView.ViewHolder(binding.root){

        //Esto indica al IDE que no muestre una sugerencia acerca de la llamada indica
        //TODO: Pendiente de revisar si podemos hacerle caso a la sugerencia, que igual vale la pena
        @SuppressLint("SimpleDateFormat")
        fun bind(schedule: Schedule){
            binding.stopNameTextView.text = schedule.stopName
            binding.arrivalTimeTextView.text = SimpleDateFormat("h:mm a")
                .format(Date(schedule.arrivalTime.toLong() * 1000))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStopViewHolder {
        val binding = BusStopItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        val viewHolder = BusStopViewHolder(binding)
        viewHolder.itemView.setOnClickListener{
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: BusStopViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object{
        private val DiffCallBack = object : DiffUtil.ItemCallback<Schedule>(){
            //Esta función comprueba si dos items de la lista son el mismo
            override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                //Esto es válido para comparar el contenido de cada instancia
                //porque Schedule es una data class, y por tanto ya admite comparaciones de este modo
                return oldItem == newItem
            }

        }
    }
}