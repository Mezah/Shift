package com.hazem.homebase.shiftapp.detailscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.hazem.homebase.shiftapp.databinding.FragmentCreateShiftBinding
import com.hazem.homebase.shifts.di.ShiftsModule

class CreateNewShiftFragment : Fragment() {

    private lateinit var binding: FragmentCreateShiftBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCreateShiftBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ad = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            mutableListOf("Name 1", "Name 2", "Name 3")
        )

        val ad2 = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            mutableListOf("Role 1", "Role 2", "Role 3")
        )

        val ad3 = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            mutableListOf("Color 1", "Color 2", "Color")
        )

        ad.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)
        ad2.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)
        ad3.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)

        binding.apply {
            employeeList.adapter = ad
            colorList.adapter = ad2
            roleList.adapter = ad3
        }

//        val itemsList =
//            ShiftsModule.loadShiftViewModelListUseCase.loadShiftsViewModelList().getOrDefault(
//                emptyList()
//            )
//        val names = itemsList.map { it.title }
//        val roles = itemsList.map { it.subtitle }
//        val colors = itemsList.map { it.color.colorName }

    }
}