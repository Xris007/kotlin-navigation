package com.noblecilla.listmaker.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.noblecilla.listmaker.R
import com.noblecilla.listmaker.databinding.FragmentListBinding
import com.noblecilla.listmaker.model.ListEntity
import com.noblecilla.listmaker.view.adapter.ListAdapter
import com.noblecilla.listmaker.view.utils.toast
import com.noblecilla.listmaker.viewmodel.ListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding
        get() = _binding!!

    private val listViewModel: ListViewModel by sharedViewModel()

    private lateinit var listAdapter: ListAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper.SimpleCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)

        activity?.title = getString(R.string.app_name)

        setupViewModel()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupView()
    }

    private fun setupViewModel() {
        listViewModel.list.observe(viewLifecycleOwner, list)
        listViewModel.isSuccessful.observe(viewLifecycleOwner, isSuccessful)
        listViewModel.onMessageError.observe(viewLifecycleOwner, onMessageError)
    }

    private val list = Observer<List<ListEntity>> {
        listAdapter.update(it)
    }

    private val isSuccessful = Observer<Boolean> {
        listViewModel.all()
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
                listViewModel.list.value?.get(viewHolder.adapterPosition)
                    ?.let { listViewModel.delete(it) }
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.listRecycler)

        listAdapter = ListAdapter(emptyList()) {
            findNavController().navigate(ListFragmentDirections.actionListFragmentToItemsFragment(it))
        }

        activity?.let {
            binding.listRecycler.apply {
                layoutManager = LinearLayoutManager(it)
                adapter = listAdapter
            }

            binding.addList.setOnClickListener {
                findNavController().navigate(R.id.listDialogFragment)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        listViewModel.all()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
