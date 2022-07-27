package com.hazem.homebase.shiftapp.detailscreen

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hazem.homebase.shiftapp.databinding.FragmentCreateShiftBinding
import com.hazem.homebase.shiftapp.models.AppResults
import com.hazem.homebase.shifts.di.ShiftsModule
import java.util.*

class CreateNewShiftFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentCreateShiftBinding
    private val calendar = Calendar.getInstance().apply { time = Date() }
    private var fromDate: Boolean = false
    private var toDate: Boolean = false

    private val vm: CreateNewShiftViewModel by viewModels {
        NewShiftVmFactory(ShiftsModule.shiftInfoUseCase)
    }

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
        vm.apply {
            namesLd.observe(viewLifecycleOwner) {
                when (it) {
                    is AppResults.Success -> {
                        val ad = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            it.result
                        )

                        ad.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item
                        )
                        binding.employeeList.adapter = ad
                    }
                    else -> {
                        Toast.makeText(
                            requireContext(),
                            "An Error Loading names",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            colorsLd.observe(viewLifecycleOwner) {
                when (it) {
                    is AppResults.Success -> {
                        val ad = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            it.result
                        )

                        ad.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item
                        )
                        binding.colorList.adapter = ad
                    }
                    else -> {
                        Toast.makeText(
                            requireContext(),
                            "An Error Loading names",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            rolesLd.observe(viewLifecycleOwner) {
                when (it) {
                    is AppResults.Success -> {
                        val ad = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            it.result
                        )

                        ad.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item
                        )
                        binding.roleList.adapter = ad
                    }
                    else -> {
                        Toast.makeText(
                            requireContext(),
                            "An Error Loading names",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }

        binding.apply {
            employeeList.onItemSelectedListener = this@CreateNewShiftFragment
            colorList.onItemSelectedListener = this@CreateNewShiftFragment
            roleList.onItemSelectedListener = this@CreateNewShiftFragment

        }
        binding.toCalendar.setOnClickListener {
            toDate = true
            createDatePickerDialog().show()
        }

        binding.fromCalendar.setOnClickListener {
            fromDate = true
            createDatePickerDialog().show()
        }
    }

    fun createDatePickerDialog(): DatePickerDialog {

        return DatePickerDialog(
            requireContext(),
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val cal = Calendar.getInstance()
        cal.set(year, month, dayOfMonth)
        val date = cal.time
        if (fromDate) {
            binding.fromDate.text = date.toString()
        } else if (toDate) {
            binding.toDate.text = date.toString()
        }
        fromDate = false
        toDate = false
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (view == null)
            return
        when {
            view.id == binding.employeeList.id -> {

            }
            view.id == binding.colorList.id -> {

            }
            view.id == binding.roleList.id -> {

            }
            else -> return
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}