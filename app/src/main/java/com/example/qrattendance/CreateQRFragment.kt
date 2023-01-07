package com.example.qrattendance

import android.content.Context.WINDOW_SERVICE
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.qrattendance.databinding.FragmentCreateQRBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter


class CreateQRFragment : Fragment() {

    private lateinit var binding: FragmentCreateQRBinding

    lateinit var bitmap: Bitmap

    private val nameInput by lazy {
        binding.etAttendantName.text
    }

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

            if (nameInput.isEmpty()) {
                Toast.makeText(requireContext(), "Enter a name, please", Toast.LENGTH_SHORT).show()
            } else {
                binding.imgQR.setImageBitmap(generateQR())
            }

        }
    }

    private fun generateQR(): Bitmap {
        val writer = QRCodeWriter()
        try {
            val bitMatrix =
                writer.encode(nameInput.toString(), BarcodeFormat.QR_CODE, 600, 600)
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
