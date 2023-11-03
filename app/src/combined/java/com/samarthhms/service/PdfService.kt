package com.samarthhms.service

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.itextpdf.text.Document
import com.itextpdf.text.PageSize
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfPageEventHelper
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URL

class PdfService {
    fun createFile(fileName: String, context: Context): File {
        val path = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        Log.i("","PATH $path")
        val file = File(path, fileName)
        if (!file.exists()) file.createNewFile()
        return file
//        val values = ContentValues()
//
//        values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName) //file name
//        values.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf") //file extension, will automatically add to file
//        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS) //end "/" is not mandatory
//        val uri: Uri = context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)!! //important!
//
//
//        Log.i("","PATH ${uri.path} - ${MediaStore.Downloads.EXTERNAL_CONTENT_URI}")
//        val file = File(uri.path!!)
//        if(!file.exists()) file.mkdirs()
//
//        return file
    }

//    fun createFile(fileName: String, context: Context): File {
//        val path = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath
//        val contentValues = ContentValues().apply {
//            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
//            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
//            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
//        }
//        val resolver = context.contentResolver
//        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
//        if (uri != null) {
//            URL(path).openStream().use { input ->
//                resolver.openOutputStream(uri).use { output ->
//                    input.copyTo(output!!, DEFAULT_BUFFER_SIZE)
//                }
//            }
//        }
//    }

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