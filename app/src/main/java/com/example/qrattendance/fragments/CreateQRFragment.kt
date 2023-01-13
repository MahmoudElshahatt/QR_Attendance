package com.example.qrattendance.fragments

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.qrattendance.createBalloon
import com.example.qrattendance.databinding.FragmentCreateQRBinding
import com.example.qrattendance.hideKeypad
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import java.text.SimpleDateFormat
import java.util.*

class CreateQRFragment : Fragment() {

    private lateinit var binding: FragmentCreateQRBinding
    private lateinit var contentToFormat: String
    private lateinit var contentToPresent: String
    private val REQUEST_CODE = 1
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

        binding.btnDownloadQr.setOnClickListener {
            if (binding.imgQR.isVisible) {
                checkPermissionsAndSave()
            }
        }

    }

    private fun checkPermissionsAndSave() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            saveImage()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImage()
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Please provide required permissions",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun saveImage() {
        val contentResolver: ContentResolver = requireContext().contentResolver

        val images: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        val contentValues = ContentValues()
        contentValues.put(
            MediaStore.Images.Media.DISPLAY_NAME,
            "$contentToFormat.jpg"
        )

        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "images/*");
        val uri = contentResolver.insert(images, contentValues);

        try {
            val savedBitmap: Bitmap = bitmap
            val outputStream = uri?.let { contentResolver.openOutputStream(it) }

            savedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            Objects.requireNonNull(outputStream)

            Toast.makeText(requireContext(), "Saved successfully", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Image failed to be saved.", Toast.LENGTH_LONG)
                .show()
            e.printStackTrace()
        }
    }

    private fun createBalloonAndShow(it: View) {
        val balloon = requireActivity().createBalloon(contentToPresent)
        balloon.showAlignTop(it)
    }

    private fun getCurrentData() {
        nameInput = binding.etAttendantName.text.toString()
        notesInput = binding.etAttendantNotes.text.toString()
    }

    private fun formatContent() {

        contentToFormat = "$nameInput/$notesInput"

        contentToPresent = if (notesInput.isEmpty()) {
            "Name: $nameInput"
        } else "Name: $nameInput\nNotes: $notesInput"
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
