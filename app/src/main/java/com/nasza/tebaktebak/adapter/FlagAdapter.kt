package com.nasza.tebaktebak

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class FlagAdapter(private val flagList: List<Flag>, private val onFlagClick: (Flag) -> Unit) :
    RecyclerView.Adapter<FlagAdapter.FlagViewHolder>() {

    inner class FlagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flagImageView: ImageView = itemView.findViewById(R.id.flagImageView)
        val statusImageView: ImageView = itemView.findViewById(R.id.statusImageView)

        init {
            itemView.setOnClickListener {
                val flag = flagList[adapterPosition]
                onFlagClick(flag)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlagViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_flag, parent, false)
        return FlagViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlagViewHolder, position: Int) {
        val flag = flagList[position]
        holder.flagImageView.setImageResource(flag.imageResId)
        when (flag.status) {
            FlagStatus.CORRECT -> {
                holder.statusImageView.setImageResource(R.drawable.ic_check)
                holder.statusImageView.visibility = View.VISIBLE
            }
            FlagStatus.INCORRECT -> {
                holder.statusImageView.setImageResource(R.drawable.ic_warning)
                holder.statusImageView.visibility = View.VISIBLE
            }
            else -> holder.statusImageView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = flagList.size
}
