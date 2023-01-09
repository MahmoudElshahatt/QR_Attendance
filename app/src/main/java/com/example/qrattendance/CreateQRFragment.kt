package com.example.qrattendance

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.qrattendance.databinding.FragmentCreateQRBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.createBalloon
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*


class CreateQRFragment : Fragment() {

    private lateinit var binding: FragmentCreateQRBinding
    private lateinit var contentToFormat: String
    private lateinit var contentToPresent: String
    lateinit var bitmap: Bitmap

    private lateinit var nameInput: String

    private lateinit var notesInput: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateQRBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnCreateQr.setOnClickListener {
            getCurrentData()
            if (nameInput.isEmpty()) {
                Toast.makeText(requireContext(), "Enter a name, please", Toast.LENGTH_SHORT).show()
            } else {
                formatContent()
                binding.imgQR.setImageBitmap(generateQR(contentToFormat))
                binding.imgQR.isVisible = true
                requireActivity().hideKeypad()
            }

        }
        binding.imgQR.setOnClickListener {
            if (it.isVisible) {
                createBalloonAndShow(it)
            }
        }
    }

    private fun createBalloonAndShow(it: View) {
        val balloon = createBalloon(requireContext()) {
            setWidthRatio(1.0f)
            setHeight(BalloonSizeSpec.WRAP)
            setText(contentToPresent)
            setTextColorResource(R.color.white)
            setTextSize(15f)
            setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            setArrowSize(10)
            setArrowPosition(0.5f)
            setPadding(12)
            setCornerRadius(8f)
            setBackgroundColorResource(R.color.colorAccent)
            setBalloonAnimation(BalloonAnimation.ELASTIC)
            setLifecycleOwner(viewLifecycleOwner)
            build()
        }
        balloon.showAlignTop(it)
    }

    private fun getCurrentData() {
        nameInput = binding.etAttendantName.text.toString()
        notesInput = binding.etAttendantNotes.text.toString()
    }


    private fun formatContent() {
        val dateFormatter = SimpleDateFormat("dd-MMM hh.mm aa")
        val dateFormatted = (dateFormatter.format(Date())).toString()
        contentToFormat = "$nameInput/$notesInput/$dateFormatted"
        contentToPresent = "Name: $nameInput\nNotes: $notesInput \nTime: $dateFormatted"
    }

    private fun generateQR(content: String): Bitmap {
        val writer = QRCodeWriter()
        try {
            val bitMatrix =
                writer.encode(content, BarcodeFormat.QR_CODE, 600, 600)
            val width = bitMatrix.width
            val height = bitMatrix.height
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return bitmap
    }

}
