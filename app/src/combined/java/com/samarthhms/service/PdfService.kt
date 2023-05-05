package com.samarthhms.service

import android.os.Environment
import com.itextpdf.text.Document
import com.itextpdf.text.PageSize
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfPageEventHelper
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream

class PdfService {
    fun createFile(fileName: String): File {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(path, fileName)
        if (!file.exists()) file.createNewFile()
        return file
    }

    fun createDocument(): Document {
        val document = Document()
        document.setMargins(30f, 30f, 15f, 30f)
        document.pageSize = PageSize.LETTER
        return document
    }

    fun setupPdfWriter(document: Document, file: File, pdfPageEventHelper: PdfPageEventHelper) {
        val pdf = PdfWriter.getInstance(document, FileOutputStream(file))
        pdf.setFullCompression()
        pdf.pageEvent = pdfPageEventHelper
        document.open()
    }

    fun createTable(numberOfColumns: Int, tableWidths: List<Float>, widthPercentage: Float = 100f): PdfPTable {
        val table = PdfPTable(numberOfColumns)
        table.widthPercentage = widthPercentage
        table.setWidths(tableWidths.toFloatArray())
        return table
    }
}