package com.example.colors

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.colors.databinding.ColorItemBinding
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar

class ColorsAdapter(
    private val context: Context,
    private val mainActivityLayout: CoordinatorLayout
): RecyclerView.Adapter<ColorsAdapter.ColorItemViewHolder>() {

    private fun createItemsList(): List<String> {
        val listOfItems = mutableListOf<String>()
        for (i in 0..49) {
            listOfItems.add(context.getString(R.string.item, i.toString()))
        }
        Log.d("ColorsAdapter", "listOfItems is $listOfItems")
        return listOfItems
    }

    private fun createMapItemsColors(): Map<Int, Color> {
        val listOfColors = mutableListOf<Color>()
        val itemsColors = mutableMapOf<Int, Color>()

        for (item in itemsList) {
            for (color in Color.values()) {
                listOfColors.add(color)
            }
        }

        itemsColors.apply {
            for (i in 0..49) {
                this[i] = listOfColors[i]
            }
        }
        return itemsColors
    }

    private val itemsList = createItemsList()
    private val mapItemsColors = createMapItemsColors()

    inner class ColorItemViewHolder(val binding: ColorItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorItemViewHolder {
        return ColorItemViewHolder(
            ColorItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ColorItemViewHolder, position: Int) {
        Log.d("ColorsAdapter", "MAP ${mapItemsColors[1]}")

        holder.binding.root.setOnClickListener {
            Snackbar
                .make(
                    mainActivityLayout,
                    context.getString(R.string.message_item_clicked, itemsList[position]),
                    LENGTH_SHORT
                )
                .show()
        }

        // Set text in TextView
        holder.binding.item.text = itemsList[position]
        // Need to change visibility to VISIBLE in case it was INVISIBLE before recycling
        if (holder.binding.itemColor.isInvisible) {
            holder.binding.itemColor.visibility = View.VISIBLE
        }
        // Set drawable in ImageView
        when (mapItemsColors[position]) {
            Color.RED -> {
                holder.binding.itemColor.setImageResource(R.drawable.round_icon_red)
            }
            Color.ORANGE -> {
                holder.binding.itemColor.setImageResource(R.drawable.round_icon_orange)
            }
            Color.YELLOW -> {
                holder.binding.itemColor.setImageResource(R.drawable.round_icon_yellow)
            }
            Color.GREEN -> {
                holder.binding.itemColor.setImageResource(R.drawable.round_icon_green)
            }
            Color.LIGHT_BLUE -> {
                holder.binding.itemColor.setImageResource(R.drawable.round_icon_light_blue)
            }
            Color.BLUE -> {
                holder.binding.itemColor.setImageResource(R.drawable.round_icon_blue)
            }
            Color.PURPLE -> {
                holder.binding.itemColor.setImageResource(R.drawable.round_icon_purple)
            }
            Color.COLORLESS -> {
                holder.binding.itemColor.visibility = View.INVISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d("ColorsAdapter", "itemsList is ${itemsList.size}")
        return itemsList.size
    }

}