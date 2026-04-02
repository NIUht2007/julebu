package com.jczy.cyclone.club.club.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jczy.cyclone.club.R
import com.jczy.cyclone.club.club.model.Club

class ClubAdapter : PagingDataAdapter<Club, ClubAdapter.ClubViewHolder>(CLUB_COMPARATOR) {
    
    companion object {
        private val CLUB_COMPARATOR = object : DiffUtil.ItemCallback<Club>() {
            override fun areItemsTheSame(oldItem: Club, newItem: Club): Boolean {
                return oldItem.id == newItem.id
            }
            
            override fun areContentsTheSame(oldItem: Club, newItem: Club): Boolean {
                return oldItem == newItem
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_club, parent, false)
        return ClubViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        val club = getItem(position)
        if (club != null) {
            holder.bind(club)
        }
    }
    
    class ClubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val logo: ImageView = itemView.findViewById(R.id.logo)
        private val name: TextView = itemView.findViewById(R.id.name)
        private val description: TextView = itemView.findViewById(R.id.description)
        private val memberCount: TextView = itemView.findViewById(R.id.member_count)
        private val activityCount: TextView = itemView.findViewById(R.id.activity_count)
        private val score: TextView = itemView.findViewById(R.id.score)
        
        fun bind(club: Club) {
            // 加载 logo
            Glide.with(itemView.context)
                .load(club.logo)
                .into(logo)
            
            // 设置名称
            name.text = club.name
            
            // 设置描述
            description.text = club.description
            
            // 设置统计数据
            memberCount.text = "${club.memberCount}人"
            activityCount.text = "${club.activityCount}活动"
            score.text = "${club.score}分"
        }
    }
}