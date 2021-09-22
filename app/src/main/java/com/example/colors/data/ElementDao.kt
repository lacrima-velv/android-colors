package com.example.colors.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ElementDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addInitialElements(elementsList: List<Element>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addElement(element: Element)

    @Query("DELETE FROM colors_table WHERE id = :id")
    suspend fun deleteElementById(id: Long)

    @Query("DELETE FROM colors_table")
    suspend fun deleteAllElements()

    @Query("SELECT * FROM colors_table ORDER BY id ASC")
    fun getAllElements(): LiveData<List<Element>>

    @Query("SELECT EXISTS (SELECT 1 FROM colors_table)")
    suspend fun checkIfThereIsAnyData(): Boolean

    @Query("SELECT MAX(id) FROM colors_table")
    suspend fun getMaxElementId(): Int?

}