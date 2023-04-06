package com.samarthhms.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.room.util.StringUtil
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Chunk
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Image
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Phrase
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPageEventHelper
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.StringUtils
import com.samarthhms.R
import com.samarthhms.models.DischargeCard
import com.samarthhms.utils.DateTimeUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject
import kotlin.math.min

class GenerateDischargeCard @Inject constructor(){

    @Inject
    lateinit var context: Context

    var file: File? = null

    private val bodyFont = Font(Font.FontFamily.TIMES_ROMAN, 12f, Font.BOLD)
    private val titleFont = Font(Font.FontFamily.TIMES_ROMAN, 15f, Font.NORMAL)

    inner class HeaderPageEvent : PdfPageEventHelper(){
        override fun onStartPage(writer: PdfWriter?, document: Document?) {
            val pdfService = PdfService()
            val table = pdfService.createTable(3, listOf(20f,65f,15f))

            table.addCell(getCell(getImage(R.drawable.logo_colored, CompressFormat.JPEG,50f), padding = listOf(-1f, -1f, 5f, -1f)))
            var cell = getCell(getImage(R.drawable.pdf_sub_title_only, CompressFormat.PNG, 60f), padding = listOf(-1f,-1f,12f,-1f))
            cell.verticalAlignment = Element.ALIGN_BOTTOM
            table.addCell(cell)
            table.addCell(getCell(getImage(R.drawable.mother_child_symbol, CompressFormat.PNG,50f)))

            cell = getCell("", 6f, colspan = 3)
            cell.backgroundColor = BaseColor(130,92,158)
            table.addCell(cell)

            cell = getCell("")
//            table.addCell(cell)
            cell = getCell(getImage(R.drawable.pdf_address,CompressFormat.PNG, 10f) , padding = listOf(3f, 70f, 3f, 70f), colspan = 3)
            cell.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(cell)

            cell = getCell("", 15f, colspan = 3)
            table.addCell(cell)

            document?.add(table)
        }

        private fun getImage(id: Int, compressFormat: CompressFormat, height: Float): Image{
            val drawable = AppCompatResources.getDrawable(context, id)
            val bitmap = (drawable as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(compressFormat, 100, stream)
            val image = Image.getInstance(stream.toByteArray())
            image.scaleAbsoluteHeight(height)
            return image
        }

        private fun getCell(data: String, minimumHeight: Float = -1f,padding: List<Float> = listOf(-1f,-1f,-1f,-1f), colspan: Int = 1): PdfPCell{
            val pdfCell = PdfPCell(Paragraph(data, bodyFont))
            if(padding[0]!=-1f) pdfCell.paddingTop = padding[0]
            if(padding[1]!=-1f) pdfCell.paddingRight = padding[1]
            if(padding[2]!=-1f) pdfCell.paddingBottom = padding[2]
            if(padding[3]!=-1f) pdfCell.paddingLeft = padding[3]
            if(minimumHeight!=-1f) pdfCell.minimumHeight = minimumHeight
            pdfCell.colspan = colspan
            pdfCell.border = Rectangle.NO_BORDER
            return pdfCell
        }

        private fun getCell(data: Image, minimumHeight: Float = -1f, padding: List<Float> = listOf(-1f,-1f,-1f,-1f),colspan: Int = 1): PdfPCell{
            val pdfCell = PdfPCell(data, true)
            if(padding[0]!=-1f) pdfCell.paddingTop = padding[0]
            if(padding[1]!=-1f) pdfCell.paddingRight = padding[1]
            if(padding[2]!=-1f) pdfCell.paddingBottom = padding[2]
            if(padding[3]!=-1f) pdfCell.paddingLeft = padding[3]
            if(minimumHeight!=-1f) pdfCell.minimumHeight = minimumHeight
            pdfCell.colspan = colspan
            pdfCell.border = Rectangle.NO_BORDER
            return pdfCell
        }
    }

    fun generatePdf(dischargeCard: DischargeCard){
        val pdfService = PdfService()
        val document = pdfService.createDocument()
        file = pdfService.createFile(getFileName(dischargeCard))
        pdfService.setupPdfWriter(document, file!!, HeaderPageEvent())


        var table = pdfService.createTable(1, listOf(100f), 50f)
        var data: String? = null

        data = "DISCHARGE CARD"
        var cell = PdfPCell(Paragraph(data, titleFont))
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.paddingTop = 5f
        cell.paddingBottom = 7f
        val table_ = pdfService.createTable(1, listOf(100f), 100f)
        table_.addCell(cell)
        val cell_ = PdfPCell(table_)
        cell_.setPadding(1f)
        cell_.borderWidth = 2.5f
        table.addCell(cell_)
//        document.add(Paragraph(" ",bodyFont))
        document.add(table)
        document.add(Paragraph(" ",bodyFont))
        table = pdfService.createTable(2, listOf(70f,30f))
        cell = getCell(" ")
        cell.border = Rectangle.NO_BORDER
        table.addCell(cell)
        cell = getCell("Date : "+DateTimeUtils.getDateFormat(dischargeCard.dateOfDischarge))
        cell.border = Rectangle.NO_BORDER
        table.addCell(cell)
        document.add(table)
        document.add(Paragraph(" ",bodyFont))

        table = pdfService.createTable(2, listOf(42f,58f))

        table.isSplitLate = false

//        data = "Patient ID : " + dischargeCard.patientId
//        table.addCell(getCell(data, 2))

        data = "Patient Name : " + dischargeCard.firstName + " " + dischargeCard.middleName + " " + dischargeCard.lastName
        table.addCell(getCell(data, 2))

        data = "Age / Sex : " + dischargeCard.ageFormat + " / " + dischargeCard.gender.value + "\n" +
               "Weight    : " + dischargeCard.weight + "Kg"
        table.addCell(getCell(data))

        data = "Address : " + dischargeCard.address + "\n" +
               "Mobile Number    : " + dischargeCard.contactNumber
        table.addCell(getCell(data))

        data = "Date Of Admission : " + DateTimeUtils.getDateTime(dischargeCard.dateOfAdmission)
        table.addCell(getCell(data))

        data = "Date Of Discharge : " + DateTimeUtils.getDateTime(dischargeCard.dateOfDischarge)
        table.addCell(getCell(data))

        data = "IPD Number : " + dischargeCard.ipdNumber
        table.addCell(getCell(data, 2))

        data = "Diagnosis : " + dischargeCard.diagnosis
        table.addCell(getCell(data, 2))

        var title = "History Of Present Illness :- "
        data = dischargeCard.patientHistory
        table.addCell(getCell(title,data,true, 2))

        title = "Past H/o :- "
        data = dischargeCard.pastHistory
        table.addCell(getCell(title,data, false,2))

        title = "Family H/o :- "
        data = dischargeCard.familyHistory
        table.addCell(getCell(title,data, false,2))

        title = "Course in Hospital (Treatments) :- "
        table.addCell(getCell(title, dischargeCard.course,true, 2))

        title = "Investigations :- "
        data = dischargeCard.investigations
        table.addCell(getCell(title, data,false, 2))

        title = "Medications on Discharge :- "
        table.addCell(getCell(title, dischargeCard.medicationsOnDischarge,true, 2))

        title = "Advice :- "
        table.addCell(getCell(title, dischargeCard.advice, true,2))

        document.add(table)

        document.close()
    }

    private fun getFilePath(): String{
        return file!!.path
    }

    private fun getFileName(dischargeCard: DischargeCard): String{
//        return "Generated_Card_"+dischargeCard.patientId+"_"+dischargeCard.lastName+".pdf"
        return "Generated_Card_"+dischargeCard.firstName+"_"+dischargeCard.lastName+".pdf"
    }

    private fun getCell(title: String,data: String, isTitleOnNewLine: Boolean, colspan: Int = 1): PdfPCell{
        val pdfCell = PdfPCell()
        val lines = data.split('\n')

        val chunk = Chunk(title)
        chunk.setUnderline(0.8f, -1f)
        chunk.font = bodyFont
        if(isTitleOnNewLine){
            pdfCell.addElement(chunk)
        }

        var i=0
        for(line in lines){
            i++
            var pg = Paragraph(15f, line, bodyFont)
            if(i==1 && !isTitleOnNewLine){
                val ph = Phrase(chunk)
                pg = Paragraph(15f, chunk.toString()+" "+line, bodyFont)
            }
            pdfCell.addElement(pg)
        }
//        pdfCell.minimumHeight = 25f
        pdfCell.setPadding(5f)
        pdfCell.paddingTop = 1f
        pdfCell.borderWidth = 1.7f
        pdfCell.colspan = colspan
        return pdfCell
    }

    private fun getCell(data: String, colspan: Int = 1): PdfPCell{
        val pdfCell = PdfPCell()
        val lines = data.split('\n')
        var i=0
        for(line in lines){
            val pg = Paragraph(15f, line, bodyFont)
            pdfCell.addElement(pg)
        }
//        pdfCell.minimumHeight = 25f
        pdfCell.setPadding(5f)
        pdfCell.paddingTop = 1f
        pdfCell.borderWidth = 1.7f
        pdfCell.colspan = colspan
        return pdfCell
    }

    private fun getCell(data: List<String>, colspan: Int = 1): PdfPCell{
        val pdfCell = PdfPCell()
        for(line in data){
            val pg = Paragraph(15f, line, bodyFont)
            pdfCell.addElement(pg)
        }
//        pdfCell.minimumHeight = 25f
        pdfCell.setPadding(5f)
        pdfCell.paddingTop = 1f
        pdfCell.borderWidth = 1.7f
        pdfCell.colspan = colspan
        return pdfCell
    }

    private fun getCell(title: String, data: List<String>,isTitleOnNewLine: Boolean, colspan: Int = 1): PdfPCell{
        val pdfCell = PdfPCell()

        val chunk = Chunk(title)
        chunk.setUnderline(0.8f, -1f)
        chunk.font = bodyFont
        var ph = Phrase(chunk)
        if(isTitleOnNewLine){
            pdfCell.addElement(ph)
        }

        var i=0
        for(line in data){
            i++
            var pg = Paragraph(15f, i.toString()+") "+line, bodyFont)
            if(i==1 && !isTitleOnNewLine){
                ph = Phrase(chunk)
                pg = Paragraph(15f, chunk.toString()+" "+line, bodyFont)
            }
            pdfCell.addElement(pg)
        }
//        pdfCell.minimumHeight = 25f
        pdfCell.setPadding(5f)
        pdfCell.paddingTop = 1f
        pdfCell.borderWidth = 1.7f
        pdfCell.colspan = colspan
        return pdfCell
    }
}