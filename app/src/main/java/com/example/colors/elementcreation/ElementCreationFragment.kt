package com.example.colors.elementcreation

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.colors.Color
import com.example.colors.R
import com.example.colors.data.Element
import com.example.colors.data.ElementViewModel
import com.example.colors.databinding.FragmentElementCreationBinding

class ElementCreationFragment : Fragment() {

    private lateinit var binding: FragmentElementCreationBinding
    private lateinit var elementViewModel: ElementViewModel
    private lateinit var newElementName: String
    private lateinit var args: ElementCreationFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        args = ElementCreationFragmentArgs.fromBundle(requireArguments())

        // This label is automatically set as title of this fragment
        args.label

        // Use the same argument for setting a new element name
        newElementName = args.label

        binding = FragmentElementCreationBinding.inflate(layoutInflater)

//        binding = DataBindingUtil.inflate(
//            inflater, R.layout.fragment_element_creation, container, false
//        )



        val colorsGroup = binding.colorsGroup

        colorsGroup.setOnCheckedChangeListener { group, _ ->

            val chosenColor: Color = when (group.checkedRadioButtonId) {
                binding.red.id -> Color.RED
                binding.orange.id -> Color.ORANGE
                binding.yellow.id -> Color.YELLOW
                binding.green.id -> Color.GREEN
                binding.lightBlue.id -> Color.LIGHT_BLUE
                binding.blue.id -> Color.BLUE
                binding.purple.id -> Color.PURPLE
                binding.colorless.id -> Color.COLORLESS
                /*
                Color cannot be not chosen as it's at least red by default.
                So  just leave it red in any other case:
                 */
                else -> Color.RED
            }
            elementViewModel.doneChoosingColorRadioButton(chosenColor)

        }


        val createElementButton = binding.createElement

        elementViewModel = ViewModelProvider(requireActivity()).get(ElementViewModel::class.java)

        elementViewModel.onColorRadioButtonChosen.observe(viewLifecycleOwner) {}

        createElementButton.setOnClickListener {
            addElementToDatabase(newElementName, elementViewModel.onColorRadioButtonChosen.value?: Color.RED)
            /*
            Set onNewElementAdded.value to true to trigger onItemRangeInserted() function
            to scroll to the last element when ColorListFragment is opened
             */
            elementViewModel.startAddingNewElement()
            Log.d("ElementCreationFragment", "startAddingNewElement() is called")
            val action = ElementCreationFragmentDirections
                .actionElementCreationFragmentToColorsListFragment()
            findNavController().navigate(action)
            /*
            Update next element id to use it as a screen and element title
            when user clicks FAB next time
             */
            elementViewModel.updateNextElementId()
        }

        return binding.root
    }

    private fun addElementToDatabase(newElementName: String, color: Color) {
        elementViewModel.addElement(
            Element(
                id = 0,
                elementName = newElementName,
                elementColor = color
            )
        )
    }
}