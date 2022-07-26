package com.hazem.homebase.shiftapp.listscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hazem.homebase.shiftapp.databinding.FragmentShiftsListLayoutBinding
import com.hazem.homebase.shiftapp.models.AppResults
import com.hazem.homebase.shifts.di.ShiftsModule

class ShiftsListFragment : Fragment() {

    private lateinit var binding: FragmentShiftsListLayoutBinding
    private val shiftAdapter = ShiftsListAdapter()
    private val viewModel: ShiftListViewModel by viewModels {
        ShiftListViewModelFactory(ShiftsModule.loadShiftViewModelListUseCase)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShiftsListLayoutBinding.inflate(inflater, container, false)
        viewModel.loadShiftList()
        viewModel.listLiveData.observe(viewLifecycleOwner) { listResult ->
            when (listResult) {
                AppResults.Failure -> Toast.makeText(
                    requireContext(),
                    "An Error Occurred",
                    Toast.LENGTH_SHORT
                ).show()
                is AppResults.Success -> shiftAdapter.addShiftList(listResult.result)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.createShiftBtn.setOnClickListener {
            findNavController().navigate(ShiftsListFragmentDirections.actionShiftsListFragmentToShiftDetailFragment())
        }
        binding.shiftsList.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = shiftAdapter
        }
    }
}