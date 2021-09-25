package com.example.colors.data

import android.content.Context
import com.example.colors.R

object InitialData {

    private const val LAST_ELEMENT_INDEX = 49

    fun createInitialElementsList(context: Context): List<Element> {
        val names = mutableListOf<String>()
        val colors = mutableListOf<Color>()
        val initialElements = mutableListOf<Element>()

        for (i in 0..LAST_ELEMENT_INDEX) {
            names.add(context.getString(R.string.item, i))
        }

        for (name in names) {
            for (color in Color.values()) {
                colors.add(color)
            }
        }

        for (i in 0..LAST_ELEMENT_INDEX) {
            initialElements.add(
                Element(
                    id = 0L,
                    elementName = names[i],
                    elementColor = colors[i],
                )
            )
        }
        return initialElements
    }
}