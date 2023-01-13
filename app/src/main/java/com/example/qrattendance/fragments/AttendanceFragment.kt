package com.example.qrattendance.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qrattendance.MainActivity
import com.example.qrattendance.adapter.AttendeesAdapter
import com.example.qrattendance.databinding.FragmentAttendanceBinding
import com.example.qrattendance.exportCSVFile
import com.example.qrattendance.generateFile
import com.example.qrattendance.getCSVFileName
import com.example.qrattendance.model.Attendee
import com.example.qrattendance.viewModel.AttendeeViewModel
import java.io.File


class AttendanceFragment : Fragment() {
    private lateinit var binding: FragmentAttendanceBinding
    private lateinit var attendeeViewModel: AttendeeViewModel
    private lateinit var attendeesAdapter: AttendeesAdapter
    private lateinit var attendees: List<Attendee>
    private val REQUEST_CODE = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAttendanceBinding.inflate(layoutInflater)
        attendeeViewModel = (activity as MainActivity).attendeeViewModel
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAttendanceRV()
        setOnClickListeners()
    }

    private fun setUpAttendanceRV() {

        attendeesAdapter = AttendeesAdapter()
        binding.attendeesRv.apply {

            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = attendeesAdapter
            attendeeViewModel.getAllAttendees().observe(viewLifecycleOwner) { listOfAttendees ->
                attendees = listOfAttendees
                if (attendees.isEmpty()) {
                    binding.btnDeleteAll.isEnabled = false
                    binding.btnExportCvs.isEnabled = false
                } else {
                    binding.btnDeleteAll.isEnabled = true
                    binding.btnExportCvs.isEnabled = true
                }
                attendeesAdapter.differ.submitList(attendees)
            }

        }

    }


    private fun setOnClickListeners() {

        binding.btnDeleteAll.setOnClickListener {
            showAlert()
        }
        binding.btnExportCvs.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                exportDatabaseToCSVFile()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), REQUEST_CODE
                )
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                exportDatabaseToCSVFile()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please provide required permissions",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun exportDatabaseToCSVFile() {
        val csvFile = generateFile(requireContext(), getCSVFileName())
        if (csvFile != null) {
            exportCSVFile(csvFile, attendees)
            val intent = goToFileIntent(requireContext(), csvFile)
            startActivity(intent)
            Toast.makeText(requireContext(), "CSV File is generated", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(requireContext(), "CSV File can't be generated", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun goToFileIntent(context: Context, file: File): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        val contentUri =
            FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        val mimeType = context.contentResolver.getType(contentUri)
        intent.setDataAndType(contentUri, mimeType)
        intent.flags =
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION

        return intent
    }


    private fun showAlert() {
        val alert = AlertDialog.Builder(requireContext())
        alert.apply {
            setMessage("Are You sure you want to delete all attendees?")
            setPositiveButton("Delete") { dialog, _ ->
                attendeeViewModel.deleteAllAttendees()
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            create()
        }.show()
    }


}