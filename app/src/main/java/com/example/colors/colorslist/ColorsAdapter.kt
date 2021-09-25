package com.example.colors.colorslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.colors.R
import com.example.colors.colorslist.ElementDiffCallback.getColorToContentDescriptionString
import com.example.colors.data.Color
import com.example.colors.data.Element
import com.example.colors.databinding.ColorItemBinding
import kotlinx.android.synthetic.main.color_item.view.*

class ColorsAdapter(
    private val onItemClick: (String) -> Unit,
    private val onDeleteOneElementButtonClick: (Long, String) -> Unit
): ListAdapter<Element, ColorsAdapter.ColorItemViewHolder>(ElementDiffCallback) {

    inner class ColorItemViewHolder(private val binding: ColorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(element: Element) {
                binding.root.setOnClickListener {
                    onItemClick(element.elementName)
                }
                // Set click listener for Delete button
                binding.deleteElement.setOnClickListener {
                    onDeleteOneElementButtonClick(element.id, element.elementName)
                }

                // Set text in TextView
                binding.root.item.text = element.elementName

                // Set content description for colored round image
                binding.root.itemColor.contentDescription =
                    getColorToContentDescriptionString(itemView.context)[element.elementColor]

                // Need to change visibility to VISIBLE in case it was INVISIBLE before recycling
                if (binding.root.itemColor.isInvisible) {
                    binding.root.itemColor.visibility = View.VISIBLE
                }

                // Set drawable in ImageView
                when (getItem(bindingAdapterPosition).elementColor) {
                    Color.RED -> {
                        binding.root.itemColor.setImageResource(R.drawable.round_icon_red)
                    }
                    Color.ORANGE -> {
                        binding.root.itemColor.setImageResource(R.drawable.round_icon_orange)
                    }
                    Color.YELLOW -> {
                        binding.root.itemColor.setImageResource(R.drawable.round_icon_yellow)
                    }
                    Color.GREEN -> {
                        binding.root.itemColor.setImageResource(R.drawable.round_icon_green)
                    }
                    Color.LIGHT_BLUE -> {
                        binding.root.itemColor.setImageResource(R.drawable.round_icon_light_blue)
                    }
                    Color.BLUE -> {
                        binding.root.itemColor.setImageResource(R.drawable.round_icon_blue)
                    }
                    Color.PURPLE -> {
                        binding.root.itemColor.setImageResource(R.drawable.round_icon_purple)
                    }
                    Color.COLORLESS -> {
                        binding.root.itemColor.visibility = View.INVISIBLE
                    }
                }
            }
        }

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
       holder.bind(getItem(position))
    }
}

object ElementDiffCallback : DiffUtil.ItemCallback<Element>() {
    override fun areItemsTheSame(oldItem: Element, newItem: Element): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Element, newItem: Element): Boolean {
        return oldItem == newItem
    }

    // Function to convert Color to its human readable name for content description
    fun getColorToContentDescriptionString(context: Context) = mapOf(
        Color.RED to context.getString(R.string.color_red),
        Color.ORANGE to context.getString(R.string.color_orange),
        Color.YELLOW to context.getString(R.string.color_yellow),
        Color.GREEN to context.getString(R.string.color_green),
        Color.LIGHT_BLUE to context.getString(R.string.color_light_blue),
        Color.BLUE to context.getString(R.string.color_blue),
        Color.PURPLE to context.getString(R.string.color_purple),
        Color.COLORLESS to context.getString(R.string.color_colorless)
    )
}