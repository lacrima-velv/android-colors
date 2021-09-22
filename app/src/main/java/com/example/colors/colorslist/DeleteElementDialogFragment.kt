package com.example.colors.colorslist

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.colors.R
import com.example.colors.data.ElementViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DeleteElementDialogFragment : DialogFragment() {

    private lateinit var elementViewModel: ElementViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        elementViewModel = ViewModelProvider(requireActivity()).get(ElementViewModel::class.java)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val args = DeleteElementDialogFragmentArgs.fromBundle(requireArguments())
        val elementId = args.elementId
        val elementName = args.elementName
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_element_dialog_title, elementName))
            .setPositiveButton(getString(R.string.delete_button_in_dialog)) { _, _ ->
                elementViewModel.deleteElementById(elementId)
            }
            .setNegativeButton(getString(R.string.cancel_in_dialog)) {
                    dialog, _ -> dialog.cancel()
            }
            .show()

    }
}