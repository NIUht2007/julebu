package com.jczy.cyclone.club.mall.adapter

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
import com.jczy.cyclone.club.mall.model.Goods

class GoodsAdapter : PagingDataAdapter<Goods, GoodsAdapter.GoodsViewHolder>(GOODS_COMPARATOR) {
    
    companion object {
        private val GOODS_COMPARATOR = object : DiffUtil.ItemCallback<Goods>() {
            override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean {
                return oldItem.id == newItem.id
            }
            
            override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean {
                return oldItem == newItem
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_goods, parent, false)
        return GoodsViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        val goods = getItem(position)
        if (goods != null) {
            holder.bind(goods)
        }
    }
    
    class GoodsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val name: TextView = itemView.findViewById(R.id.name)
        private val price: TextView = itemView.findViewById(R.id.price)
        private val originalPrice: TextView = itemView.findViewById(R.id.original_price)
        private val sales: TextView = itemView.findViewById(R.id.sales)
        
        fun bind(goods: Goods) {
            // 加载图片
            Glide.with(itemView.context)
                .load(goods.image)
                .into(image)
            
            // 设置名称
            name.text = goods.name
            
            // 设置价格
            if (goods.isSeckill) {
                price.text = "¥${goods.seckillPrice}"
                originalPrice.text = "¥${goods.originalPrice}"
                originalPrice.visibility = View.VISIBLE
            } else {
                price.text = "¥${goods.price}"
                originalPrice.visibility = View.GONE
            }
            
            // 设置销量
            sales.text = "已售${goods.sales}"
        }
    }
}