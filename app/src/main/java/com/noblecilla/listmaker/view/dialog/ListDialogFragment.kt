package com.noblecilla.listmaker.view.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.noblecilla.listmaker.databinding.FragmentListDialogBinding
import com.noblecilla.listmaker.model.ListEntity
import com.noblecilla.listmaker.viewmodel.ListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass.
 */
class ListDialogFragment : DialogFragment() {

    private val listViewModel: ListViewModel by sharedViewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val layoutInflater = requireActivity().layoutInflater
            val binding = FragmentListDialogBinding.inflate(layoutInflater)
            builder.setView(binding.root)
            binding.createList.setOnClickListener {
                listViewModel.insert(ListEntity("${binding.listDialogName.text}"))
                dismiss()
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
