package com.ddesk.sddemoapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ddesk.sddemoapp.R
import com.ddesk.sddemoapp.data.model.UserEntity

class FavoriteMatchesAdapter(private val items: List<UserEntity>) : RecyclerView.Adapter<FavoriteMatchesViewHolder>() {
    private var onDeleteClick: ((UserEntity) -> Unit)? = null
    private var onCardClickListener: ((UserEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMatchesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_match, parent, false)
        return FavoriteMatchesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteMatchesViewHolder, position: Int) {
        holder.bind(items[position], onDeleteClick,onCardClickListener)
    }

    override fun getItemCount(): Int = items.size

    // Setters for click listeners
    fun setOnCardClickListener(listener: (UserEntity) -> Unit) {
        onCardClickListener = listener
    }
    fun setOnDeleteClickListener(listener: (UserEntity) -> Unit) {
        onDeleteClick = listener
    }

}

class FavoriteMatchesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(
        item: UserEntity,
        onDeleteClick: ((UserEntity) -> Unit)?,
        onCardClick: ((UserEntity) -> Unit)?,
        ) {
        val icon = itemView.findViewById<ImageView>(R.id.ivIcon)

        Glide.with(itemView.context)
            .load(item.picture)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(icon)

        itemView.findViewById<TextView>(R.id.tvName).text = item.fullName
        itemView.findViewById<TextView>(R.id.tvAgeAddress).text =
            "${item.age} ${item.street},${item.city},${item.street}"

        itemView.findViewById<ImageView>(R.id.icDelete).setOnClickListener {
            onDeleteClick?.invoke(item)
        }
        itemView.findViewById<CardView>(R.id.cvRoot).setOnClickListener {
            onCardClick?.invoke(item)
        }
    }
}