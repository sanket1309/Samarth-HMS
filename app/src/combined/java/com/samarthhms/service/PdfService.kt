package com.samarthhms.service

import android.os.Environment
import android.text.Layout.Alignment
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.PageSize
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
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
        document.setMargins(24f, 24f, 32f, 32f)
        document.pageSize = PageSize.LETTER
        return document
    }

    fun setupPdfWriter(document: Document, file: File) {
        val pdf = PdfWriter.getInstance(document, FileOutputStream(file))
        pdf.setFullCompression()
        document.open()
    }

    fun createTable(numberOfColumns: Int, tableWidths: List<Float>, widthPercentage: Float = 100f): PdfPTable {
        val table = PdfPTable(numberOfColumns)
        table.widthPercentage = widthPercentage
        table.setWidths(tableWidths.toFloatArray())
        return table
    }

    fun createCell(data: Paragraph, padding: Float, colSpan: Int = 1, rowSpan: Int = 1): PdfPCell {
        val cell = PdfPCell(data)
        cell.colspan = colSpan
        cell.rowspan = rowSpan
        cell.setPadding(padding)
        return cell
    }

    fun createCell(padding: List<Float>, colSpan: Int = 1, rowSpan: Int = 1): PdfPCell {
        val cell = PdfPCell()
        cell.colspan = colSpan
        cell.rowspan = rowSpan
        if(padding[0] >= 0) cell.paddingTop = padding[0]
        if(padding[1] >= 0) cell.paddingRight = padding[1]
        if(padding[2] >= 0) cell.paddingBottom = padding[2]
        if(padding[3] >= 0) cell.paddingLeft = padding[3]
        return cell
    }

    fun createCellWithHorizontalAlignment(padding: List<Float>, horizontalAlignment: Int, colSpan: Int = 1, rowSpan: Int = 1): PdfPCell {
        val cell = PdfPCell()
        cell.colspan = colSpan
        cell.rowspan = rowSpan
        if(padding[0] >= 0) cell.paddingTop = padding[0]
        if(padding[1] >= 0) cell.paddingRight = padding[1]
        if(padding[2] >= 0) cell.paddingBottom = padding[2]
        if(padding[3] >= 0) cell.paddingLeft = padding[3]
        cell.horizontalAlignment = horizontalAlignment
        return cell
    }

    fun createCellWithVerticalAlignment(padding: List<Float>, verticalAlignment: Int, colSpan: Int = 1, rowSpan: Int = 1): PdfPCell {
        val cell = PdfPCell()
        cell.colspan = colSpan
        cell.rowspan = rowSpan
        if(padding[0] >= 0) cell.paddingTop = padding[0]
        if(padding[1] >= 0) cell.paddingRight = padding[1]
        if(padding[2] >= 0) cell.paddingBottom = padding[2]
        if(padding[3] >= 0) cell.paddingLeft = padding[3]
        cell.verticalAlignment = verticalAlignment
        return cell
    }
}