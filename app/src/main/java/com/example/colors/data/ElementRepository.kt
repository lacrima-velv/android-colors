package com.example.colors.data

import android.util.Log
import androidx.lifecycle.LiveData

class ElementRepository(private val elementDao: ElementDao) {

    suspend fun addInitialElements(elementsList: List<Element>) {
        elementDao.addInitialElements(elementsList)
    }

    suspend fun addElement(element: Element) {
        elementDao.addElement(element)
    }

    suspend fun deleteElementById(id: Long) {
        elementDao.deleteElementById(id)
    }

    suspend fun deleteAllElements() {
        elementDao.deleteAllElements()
    }

    fun getAllElements(): LiveData<List<Element>> = elementDao.getAllElements()

    suspend fun checkIfThereIsAnyData(): Boolean {
        return elementDao.checkIfThereIsAnyData()
    }

    suspend fun getMaxElementId(): Int {
        val maxId = elementDao.getMaxElementId()
        Log.d("ElementRepository", "getMaxElementId() returns $maxId")
        return maxId?: 0
    }

}