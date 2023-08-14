package com.utad.utadonline_pmdm_ud3_gestiondedatos

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.DbItemBinding
import com.utad.utadonline_pmdm_ud3_gestiondedatos.shared_preferences.Player

class DataBasePlayerAdapter(
    private val deleteAction: (Player) -> Unit
) :
    ListAdapter<Player, DataBasePlayerAdapter.DataBasePlayerViewHolder>(PlayerItemCallBack) {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBasePlayerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding: DbItemBinding = DbItemBinding.inflate(inflater, parent, false)
        return DataBasePlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBasePlayerViewHolder, position: Int) {
        val player: Player = getItem(position)

        holder.binding.tvName.text = context.getString(R.string.item_name_title, player.name)
        holder.binding.tvAge.text = context.getString(R.string.item_age_title, "${player.age}")
        var playedPositions: String = ""
        player.playedPositions?.forEach { position ->
            playedPositions = ",$position"
        }
        if (playedPositions.isNotEmpty()) {
            playedPositions.removePrefix(",")
        }
        holder.binding.tvPlayedPosition.text =
            context.getString(R.string.item_played_position_title)
        holder.binding.btnDeletePlayer.setOnClickListener { deleteAction(player) }
    }

    inner class DataBasePlayerViewHolder(val binding: DbItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}

object PlayerItemCallBack : DiffUtil.ItemCallback<Player>() {
    override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem == newItem
    }

}