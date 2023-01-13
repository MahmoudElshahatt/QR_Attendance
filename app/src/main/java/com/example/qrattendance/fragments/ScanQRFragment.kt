package com.example.qrattendance.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.qrattendance.MainActivity
import com.example.qrattendance.ScanActivity
import com.example.qrattendance.databinding.FragmentScanQRBinding
import com.example.qrattendance.serialize
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class ScanQRFragment : Fragment() {
    private lateinit var binding: FragmentScanQRBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScanQRBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()

    }

    private fun setOnClickListeners() {
        binding.btnScanQr.setOnClickListener {
            val integrator = IntentIntegrator.forSupportFragment(this)
            integrator.setPrompt("For flash use volume up key")
            integrator.setBeepEnabled(true)
            integrator.setOrientationLocked(true)
            integrator.captureActivity = ScanActivity::class.java
            integrator.initiateScan()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        var result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result.contents != null) {
            addAttendeeInDataBase(result)
            Toast.makeText(
                requireContext(),
                "Attendee is recorded successfully",
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            Toast.makeText(requireContext(), "You didn't scan any QR code", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun addAttendeeInDataBase(result: IntentResult) {
        val attendeeInString = result.contents.toString()
        val attendee = attendeeInString.serialize()
        (activity as MainActivity).attendeeViewModel.addAttendee(attendee)
    }

}


