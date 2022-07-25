package com.hazem.homebase.shiftapp.listscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hazem.homebase.shiftapp.databinding.FragmentShiftsListLayoutBinding
import com.hazem.homebase.shifts.di.ShiftsModule
import com.hazem.homebase.shifts.models.ShiftViewModel

class ShiftsListFragment : Fragment() {

    private lateinit var binding: FragmentShiftsListLayoutBinding
    private val shiftAdapter = ShiftsListAdapter()
    private val list = mutableListOf<ShiftViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val l = ShiftsModule.loadShiftViewModelListUseCase.loadShiftsViewModelList().getOrDefault(
            emptyList())
        list.addAll(l)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShiftsListLayoutBinding.inflate(inflater, container, false)
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
        shiftAdapter.addShiftList(list)
    }
}