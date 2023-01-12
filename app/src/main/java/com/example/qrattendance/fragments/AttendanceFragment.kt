package com.example.qrattendance.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qrattendance.MainActivity
import com.example.qrattendance.adapter.AttendeesAdapter
import com.example.qrattendance.databinding.FragmentAttendanceBinding
import com.example.qrattendance.viewModel.AttendeeViewModel

class AttendanceFragment : Fragment() {
    private lateinit var binding: FragmentAttendanceBinding
    private lateinit var attendeeViewModel: AttendeeViewModel
    private lateinit var attendeesAdapter: AttendeesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAttendanceBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        attendeeViewModel = (activity as MainActivity).attendeeViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAttendanceRV()
    }

    private fun setUpAttendanceRV() {
        attendeesAdapter = AttendeesAdapter()
        binding.attendeesRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = attendeesAdapter
            attendeeViewModel.getAllAttendees().observe(viewLifecycleOwner) { attendees ->
                attendeesAdapter.differ.submitList(attendees)
            }
        }

    }


}