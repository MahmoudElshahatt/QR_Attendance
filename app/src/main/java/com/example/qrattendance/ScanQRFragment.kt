package com.example.qrattendance

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.qrattendance.databinding.FragmentScanQRBinding
import com.google.zxing.integration.android.IntentIntegrator


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
            integrator.setCaptureActivity(ScanActivity::class.java)
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
            val trainee = result.contents.toString()
//            if (databaseHelper.addText(trainee, date)) {
//                Toast.makeText(
//                    this@MainActivity,
//                    "Attendance recorded successfully",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
            binding.edtCode.text = trainee
        } else {
            Toast.makeText(requireContext(), "Error in scanning the QR code", Toast.LENGTH_SHORT)
                .show()
        }

    }

}


