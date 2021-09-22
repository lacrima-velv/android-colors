package com.example.colors.colorslist

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.example.colors.databinding.FragmentColorsListBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.colors.*
import com.example.colors.data.ElementViewModel
import com.example.colors.data.InitialData
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class ColorsListFragment : Fragment() {

    private lateinit var binding: FragmentColorsListBinding
    private lateinit var listOfColorsAdapter: ColorsAdapter
    private lateinit var elementViewModel: ElementViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("ColorsListFragment", "OnCreateView() is called")

        setHasOptionsMenu(true)

//        binding = DataBindingUtil.inflate(
//            inflater, R.layout.fragment_colors_list, container, false
//        )

        binding = FragmentColorsListBinding.inflate(inflater, container, false)

        // Open screen for creating a new Element by tapping FAB and give it its name.
        binding.floatingActionButton.setOnClickListener {
            /*
            Use Element id as its position and as a part of its name because it is generated by Room
            and therefore always unique
             */
            val position = elementViewModel.nextElementId.value

            val newName = getString(R.string.item, position)
            val action = ColorsListFragmentDirections
                .actionColorsListFragmentToElementCreationFragment(newName)
            findNavController().navigate(action)
        }

        elementViewModel = ViewModelProvider(requireActivity()).get(ElementViewModel::class.java)

        /*
        Lambda is empty because we shouldn't call anything when nextElementId is changing.
        We just use its value inside onClickListener for FAB.
         */
        elementViewModel.nextElementId.observe(viewLifecycleOwner) {}

        /*
       Check if initial data was already added to DB. I can't just check if DB is empty because
       when I delete every item manually, there is be a bug: Initial data is added again.
       By additional using of LiveData object and I can avoid it.
        */
        elementViewModel.onInitialElementsAdded.observe(viewLifecycleOwner) {
            Log.d("ColorsListFragment", "Trying to add initial elements, " +
                    "onInitialElementsAdded == $it")
            if (it == false) {
                elementViewModel.addInitialElementsIfDbIsEmpty(
                    InitialData.createInitialElementsList(requireActivity()).toMutableList()
                )
                Log.d("ColorsListFragment", "Initial elements added, " +
                        "onInitialElementsAdded == $it")
            }
        }

        // Submit List of Elements to RecyclerView List Adapter every time DB is changed
        elementViewModel.allElements.observe(viewLifecycleOwner) { element ->
            listOfColorsAdapter.submitList(element?.toMutableList())
            elementViewModel.updateNextElementId()
        }

        /*
        Lambda is empty because we shouldn't call anything when onNewElementAdded is changing.
        We just use its value inside AdapterDataObserver.
        */
        elementViewModel.onNewElementAdded.observe(viewLifecycleOwner) {}

        // Save placeholder visibility state
        elementViewModel.emptyListPlaceholderVisibility.observe(viewLifecycleOwner) { visibility ->
            binding.placeholder.visibility = visibility
        }

        initRecyclerView(showSnackBarByTappingElement, showAlertDialogByTappingDelete)

        return binding.root
    }

    // Create options menu with a button for deleting all elements
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_list_of_elements, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_elements -> {
                //elementViewModel.deleteAllElements()
                val action = ColorsListFragmentDirections
                    .actionColorsListFragmentToDeleteAllElementsDialogFragment()
                findNavController().navigate(action)
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initRecyclerView(
        onItemClick: (String) -> Unit,
        showAlertDialogByTappingDelete: (Long, String) -> Unit
    ) {
        // Create an instance of List Adapter
        listOfColorsAdapter = ColorsAdapter(onItemClick, showAlertDialogByTappingDelete)
        // Set Adapter for recycler view
        binding.listOfColors.adapter = listOfColorsAdapter
        binding.listOfColors.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        // Save scrolling position when fragment is recreated
        listOfColorsAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy
            .PREVENT_WHEN_EMPTY
        /*
        Register an observer to List to scroll automatically to bottom when a new element is added
        and to set a placeholder when the list is empty
         */
        listOfColorsAdapter.registerAdapterDataObserver(
            elementViewModel.observeList(
                listOfColorsAdapter,
                binding.listOfColors,
                binding.placeholder
            )
        )
    }

    // Lambda expression for onClickListener of Element in Recycler View
    private val showSnackBarByTappingElement: (String) -> Unit = {
        Snackbar
            .make(
                binding.floatingActionButton,
                getString(R.string.message_item_clicked, it),
                BaseTransientBottomBar.LENGTH_SHORT
            )
            .setAnchorView(binding.floatingActionButton)
            .show()
    }

    // Lambda expression for onClickListener of Delete button in Recycler View
    private val showAlertDialogByTappingDelete: (Long, String) -> Unit = {
            elementId: Long,
            elementName: String ->
        // Navigate to DialogFragment and give it elementId and elementName
        val action = ColorsListFragmentDirections
            .actionColorsListFragmentToDeleteElementDialogFragment(elementId, elementName)
        findNavController().navigate(action)
    }

}