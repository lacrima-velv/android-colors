package com.example.colors.data

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ElementViewModel(application: Application): AndroidViewModel(application) {

    val allElements: LiveData<List<Element>>
    private val repository: ElementRepository

    // ColorsListFragment should know if initial data has already been added to database
    private val _onInitialElementsAdded = MutableLiveData<Boolean>()

    val onInitialElementsAdded: LiveData<Boolean>
        get() = _onInitialElementsAdded

    private fun doneAddingInitialElements() {
        _onInitialElementsAdded.value = true
    }

    // Use this value to determine screen title and a new element name when creating it
    private val _nextElementId = MutableLiveData<Int>()

    val nextElementId: LiveData<Int>
        get() = _nextElementId

    private fun gotNewElementId(id: Int) {
        _nextElementId.value = id
        Log.d("ElementViewModel", "nextElementId.value is ${nextElementId.value}")
    }

    fun updateNextElementId() {
        viewModelScope.launch(Dispatchers.Main) {
            val maxId = repository.getMaxElementId()
            gotNewElementId(maxId)
        }

    }
    /*
    Used inside AdapterDataObserver to trigger scrolling to bottom
    when a new element has been added
     */
    private val _onNewElementAdded = MutableLiveData<Boolean>()

    val onNewElementAdded: LiveData<Boolean>
        get() = _onNewElementAdded

    fun startAddingNewElement() {
        _onNewElementAdded.value = true
    }

    fun doneAddingNewElement() {
        _onNewElementAdded.value = false
    }
    // Used when adding a new element to database
    private val _onColorRadioButtonChosen = MutableLiveData<Color>()

    val onColorRadioButtonChosen: LiveData<Color>
        get() = _onColorRadioButtonChosen

    fun doneChoosingColorRadioButton(color: Color) {
        _onColorRadioButtonChosen.value = color
    }

    // Must save placeholder view visibility as it resets to default visibility GONE
    private val _isEmptyListPlaceholderVisible = MutableLiveData<Boolean>()

    val isEmptyListPlaceholderVisible: LiveData<Boolean>
    get() = _isEmptyListPlaceholderVisible

    fun setIsEmptyListPlaceholderVisible(viewVisibility: Int) {
        when (viewVisibility) {
            View.VISIBLE -> _isEmptyListPlaceholderVisible.value = true
            View.GONE -> _isEmptyListPlaceholderVisible.value = false
            else -> throw IllegalArgumentException(
                "Provided argument must be one of View visibility states: View.VISIBLE or View.GONE"
            )
        }
    }

    init {
        // Initialize repository
        val elementDao = ElementDatabase.getInstance(application).elementDao
        repository = ElementRepository(elementDao)
        allElements = repository.getAllElements()
        /*
        Set onInitialElementsAdded to false so ColorsListFragment could use it
        to add initial data to DB
         */
        _onInitialElementsAdded.value = false
        /*
        Set onColorRadioButtonChosen to default Color.RED as it's a default color
        and its value shouldn't be null
         */
        _onColorRadioButtonChosen.value = Color.RED
    }

    fun addElement(element: Element) {
        viewModelScope.launch(Dispatchers.IO) {
         repository.addElement(element)
        }
    }

    fun deleteElementById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteElementById(id)
        }
    }

    fun deleteAllElements() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllElements()
        }
    }

    fun addInitialElementsIfDbIsEmpty(elementsList: List<Element>) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.checkIfThereIsAnyData()
            if (!result) {
                repository.addInitialElements(elementsList)
            }
        }
        doneAddingInitialElements()
    }

}