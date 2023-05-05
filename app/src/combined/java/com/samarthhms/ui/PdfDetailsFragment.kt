package com.samarthhms.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.samarthhms.BuildConfig
import com.samarthhms.databinding.FragmentPdfDetailsBinding

class PdfDetailsFragment : Fragment() {

    private val viewModel: PdfDetailsViewModel by viewModels()

    private lateinit var binding: FragmentPdfDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPdfDetailsBinding.inflate(layoutInflater, container, false)
        val file = PdfDetailsFragmentArgs.fromBundle(requireArguments()).file
        binding.fileName.text = file.name
        val uriPath = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID +".provider", file)

        binding.openPdfButton.setOnClickListener{
            val pdfIntent = Intent(Intent.ACTION_VIEW)
            pdfIntent.setDataAndType(uriPath, "application/pdf")
            pdfIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(pdfIntent)
        }

        binding.shareFileButton.setOnClickListener{
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "application/pdf"
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uriPath)
            startActivity(Intent.createChooser(sharingIntent, "Share using"))
        }

        return binding.root
    }

}