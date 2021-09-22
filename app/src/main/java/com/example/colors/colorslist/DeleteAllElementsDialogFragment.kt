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

class DeleteAllElementsDialogFragment : DialogFragment() {

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
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_all_elements_dialog_title))
            .setPositiveButton(getString(R.string.delete_button_in_dialog)) { _, _ ->
                elementViewModel.deleteAllElements()
            }
            .setNegativeButton(getString(R.string.cancel_in_dialog)) {
                    dialog, _ -> dialog.cancel()
            }
            .show()
    }
}