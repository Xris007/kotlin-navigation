package com.noblecilla.listmaker.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.noblecilla.listmaker.R
import com.noblecilla.listmaker.databinding.FragmentItemsBinding
import com.noblecilla.listmaker.model.ListEntity
import com.noblecilla.listmaker.view.MainActivity
import com.noblecilla.listmaker.view.adapter.ItemAdapter
import com.noblecilla.listmaker.view.utils.toast
import com.noblecilla.listmaker.viewmodel.ListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass.
 */
class ItemsFragment : Fragment() {

    private var _binding: FragmentItemsBinding? = null
    private val binding
        get() = _binding!!

    private val args: ItemsFragmentArgs by navArgs()
    private lateinit var listEntity: ListEntity

    private val listViewModel: ListViewModel by sharedViewModel()

    private lateinit var itemAdapter: ItemAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper.SimpleCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentItemsBinding.inflate(inflater, container, false)

        args.list.let {
            activity?.title = it.name
            listEntity = it
        }

        setupViewModel()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupView()
    }

    private fun setupViewModel() {
        listViewModel.items.observe(viewLifecycleOwner, items)
        listViewModel.listUpdated.observe(viewLifecycleOwner, listUpdated)
        listViewModel.onMessageError.observe(viewLifecycleOwner, onMessageError)
    }

    private val items = Observer<ListEntity> {
        itemAdapter.update(it)
    }

    private val listUpdated = Observer<Boolean> {
        listViewModel.items(listEntity)
    }

    private val onMessageError = Observer<Any> {
        activity?.toast("$it")
    }

    private fun setupView() {
        itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                listViewModel.items.value?.items?.get(viewHolder.adapterPosition)?.let {
                    listViewModel.deleteItem(listEntity, it)
                }
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.itemsRecycler)

        itemAdapter = ItemAdapter(listEntity)

        activity?.let {
            binding.itemsRecycler.apply {
                layoutManager = LinearLayoutManager(it)
                adapter = itemAdapter
            }

            binding.addItem.setOnClickListener {
                val bundle = bundleOf(MainActivity.LIST_KEY to listEntity)
                findNavController().navigate(R.id.itemDialogFragment, bundle)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        listViewModel.items(listEntity)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
