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
            // 加载封面图
            Glide.with(itemView.context)
                .load(goods.coverUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(image)

            // 商品标题
            name.text = goods.displayTitle()

            // 价格
            val displayPrice = goods.displayPrice()
            val originalPriceVal = goods.displayOriginalPrice()
            price.text = "¥${String.format("%.2f", displayPrice)}"
            if (originalPriceVal > 0 && originalPriceVal > displayPrice) {
                originalPrice.text = "¥${String.format("%.2f", originalPriceVal)}"
                originalPrice.visibility = View.VISIBLE
            } else {
                originalPrice.visibility = View.GONE
            }

            // 销量
            val sold = if (goods.monthSalesNum > 0) goods.monthSalesNum else goods.soldNum
            sales.text = "已售${sold}"
        }
    }
}