package com.hazem.homebase.shiftapp.detailscreen

import android.R
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
import androidx.navigation.fragment.findNavController
import com.hazem.homebase.shiftapp.databinding.FragmentCreateShiftBinding
import com.hazem.homebase.shiftapp.models.AppResults
import com.hazem.homebase.shifts.di.ShiftsModule
import java.text.SimpleDateFormat
import java.util.*

class CreateNewShiftFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentCreateShiftBinding
    private val calendar = Calendar.getInstance().apply { time = Date() }
    private var fromDate: Boolean = false
    private var toDate: Boolean = false
    private val sdf = SimpleDateFormat("EEE, MMMM d", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("GMT-08:00")
    }

    private val vm: CreateNewShiftViewModel by viewModels {
        NewShiftVmFactory(ShiftsModule.shiftInfoUseCase, ShiftsModule.createNewShiftUseCase)
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
        liveDateObservers()
        selectListeners()
        clickListeners()
    }

    private fun selectListeners() {
        binding.apply {
            employeeList.onItemSelectedListener = this@CreateNewShiftFragment
            colorList.onItemSelectedListener = this@CreateNewShiftFragment
            roleList.onItemSelectedListener = this@CreateNewShiftFragment

        }
    }

    private fun liveDateObservers() {
        vm.apply {
            namesLd.observe(viewLifecycleOwner) {
                when (it) {
                    is AppResults.Success -> {
                        val ad = ArrayAdapter(
                            requireContext(),
                            R.layout.simple_spinner_item,
                            it.result
                        )

                        ad.setDropDownViewResource(
                            R.layout.simple_spinner_dropdown_item
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
                            R.layout.simple_spinner_item,
                            it.result
                        )

                        ad.setDropDownViewResource(
                            R.layout.simple_spinner_dropdown_item
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
                            R.layout.simple_spinner_item,
                            it.result
                        )

                        ad.setDropDownViewResource(
                            R.layout.simple_spinner_dropdown_item
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
            createNewShift.observe(viewLifecycleOwner) {
                when (it) {
                    AppResults.EmptyColor -> Toast.makeText(
                        requireContext(),
                        "Select a Color",
                        Toast.LENGTH_LONG
                    ).show()
                    AppResults.EmptyEndDate -> Toast.makeText(
                        requireContext(),
                        "Select a End Date",
                        Toast.LENGTH_LONG
                    ).show()
                    AppResults.EmptyName -> Toast.makeText(
                        requireContext(),
                        "Select a name",
                        Toast.LENGTH_LONG
                    ).show()
                    AppResults.EmptyRole -> Toast.makeText(
                        requireContext(),
                        "Select a Role",
                        Toast.LENGTH_LONG
                    ).show()
                    AppResults.EmptyStartDate -> Toast.makeText(
                        requireContext(),
                        "Select a Start date",
                        Toast.LENGTH_LONG
                    ).show()
                    else -> return@observe
                }
            }
        }
    }

    private fun clickListeners() {
        binding.toCalendar.setOnClickListener {
            toDate = true
            createDatePickerDialog().show()
        }

        binding.fromCalendar.setOnClickListener {
            fromDate = true
            createDatePickerDialog().show()
        }

        binding.save.setOnClickListener {
            vm.createNewShift()
            Toast.makeText(requireContext(), "Shift added", Toast.LENGTH_SHORT).show()
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun createDatePickerDialog(): DatePickerDialog {

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
            binding.fromDate.text = sdf.format(date)
            vm.addStartDate(date)
        } else if (toDate) {
            binding.toDate.text = sdf.format(date)
            vm.addEndDate(date)
        }
        fromDate = false
        toDate = false
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent == null)
            return
        when (parent.id) {
            binding.employeeList.id -> {
                val name = parent.adapter.getItem(position) as String
                vm.addName(name)
            }
            binding.colorList.id -> {
                val color = parent.adapter.getItem(position) as String
                vm.addColor(color)
            }
            binding.roleList.id -> {
                val role = parent.adapter.getItem(position) as String
                vm.addRole(role)
            }
            else -> return
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}