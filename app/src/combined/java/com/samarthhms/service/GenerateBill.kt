package com.samarthhms.service

import android.content.Context
import android.graphics.Bitmap.CompressFormat
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.content.res.AppCompatResources
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfPageEventHelper
import com.itextpdf.text.pdf.PdfWriter
import com.samarthhms.R
import com.samarthhms.models.Bill
import com.samarthhms.utils.DateTimeUtils
import com.samarthhms.utils.StringUtils
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

class GenerateBill @Inject constructor(){

    @Inject
    lateinit var context: Context

    var file: File? = null

    private val bodyFont = Font(Font.FontFamily.TIMES_ROMAN, 12f, Font.BOLD)
    private val contentBodyFont = Font(Font.FontFamily.TIMES_ROMAN, 13f, Font.NORMAL)
    private val contentTitleFont = Font(Font.FontFamily.TIMES_ROMAN, 13f, Font.BOLD)
//    private val titleFont = Font(Font.FontFamily.TIMES_ROMAN, 15f, Font.NORMAL)

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

//            cell = getCell("")
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

    fun generatePdf(bill: Bill){
        val pdfService = PdfService()
        val document = pdfService.createDocument()
        file = pdfService.createFile(getFileName(bill))
        pdfService.setupPdfWriter(document, file!!, HeaderPageEvent())


        document.add(Paragraph("",bodyFont))

        var tableTop = pdfService.createTable(2, listOf(80f,20f))
        var data: String?

        data = "Bill Number : " + bill.billNumber
        var cell = getCell(data)
        cell.paddingLeft = 25f
        cell.border = Rectangle.NO_BORDER
        tableTop.addCell(cell)

        data = "Date : " + DateTimeUtils.getDateFormat(bill.dateOfDischarge)
        cell = getCell(data)
        cell.border = Rectangle.NO_BORDER
        tableTop.addCell(cell)

        document.add(tableTop)
        document.add(Paragraph(" ",bodyFont))

        var table = pdfService.createTable(2, listOf(50f,50f), 90f)

//        data = "Patient ID : " + bill.patientId
//        cell = getCell(data, 2)
//        cell.border = Rectangle.NO_BORDER
//        table.addCell(cell)

        data = "Patient Name : " + bill.firstName + " " + bill.middleName + " " + bill.lastName
        cell = getCell(data)
        cell.border = Rectangle.NO_BORDER
        table.addCell(cell)

        data = "Age / Sex : " + bill.age + " / " + bill.gender.value
        cell = getCell(data,)
        cell.border = Rectangle.NO_BORDER
        table.addCell(cell)

        data = "Diagnosis : " + bill.diagnosis
        cell = getCell(data, 2)
        cell.border = Rectangle.NO_BORDER
        table.addCell(cell)

        data = "Date Of Admission : " + DateTimeUtils.getDateFormat(bill.dateOfAdmission)
        cell = getCell(data)
        cell.border = Rectangle.NO_BORDER
        table.addCell(cell)

        data = "Date Of Discharge : " + DateTimeUtils.getDateFormat(bill.dateOfDischarge)
        cell = getCell(data)
        cell.border = Rectangle.NO_BORDER
        table.addCell(cell)

        data = "Address : " + bill.address
        cell = getCell(data, 2)
        cell.border = Rectangle.NO_BORDER
        table.addCell(cell)

        tableTop = pdfService.createTable(1, listOf(100f), 95f)
        tableTop.addCell(table)
        document.add(tableTop)

        document.add(Paragraph("", bodyFont))
        document.add(Paragraph("", bodyFont))

        table = pdfService.createTable(2, listOf(60f, 40f), 80f)

        data = "PARTICULARS"
        cell = getCellBody(data, true, 35f)
        cell.horizontalAlignment = Element.ALIGN_CENTER
        table.addCell(cell)

        data = "CHARGES"
        cell = getCellBody(data, true, 35f)
        cell.horizontalAlignment = Element.ALIGN_CENTER
        table.addCell(cell)

        for(billItem in bill.treatmentCharges){
            data = billItem.itemName
            cell = getCellBody(data, false, 35f)
            table.addCell(cell)

            data = if(billItem.rate * billItem.quantity == 0){
                "-"
            } else {
                billItem.rate.toString() + " X " + billItem.quantity + " = " + (billItem.rate * billItem.quantity).toString()
            }
            if(billItem.itemName == "Registration")
                data = billItem.rate.toString()
            cell = getCellBody(data, false, 35f)
            cell.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(cell)
        }

        var names = "Management of\n"
        var charges = " \n"
        for(billItem in bill.managementCharges){
            names += " - "+billItem.itemName+"..........\n"
            charges += billItem.rate.toString().padStart(5,' ')+" X "+billItem.quantity.toString().padStart(2,' ')+" = "+(billItem.rate * billItem.quantity).toString().padStart(5,' ')+"\n"
        }
        cell = getCellBody(names, false, 35f)
        table.addCell(cell)
        cell = getCellBody(charges, false, 35f)
        cell.horizontalAlignment = Element.ALIGN_CENTER
        table.addCell(cell)

        cell = getCellBody(bill.otherCharges.itemName, false, 35f)
        table.addCell(cell)
        data = bill.otherCharges.rate.toString()+" X "+bill.otherCharges.quantity+" = "+(bill.otherCharges.rate * bill.otherCharges.quantity).toString()
        if(bill.otherCharges.rate * bill.otherCharges.quantity == 0) data = "-"
        cell = getCellBody(data, false, 35f)
        cell.horizontalAlignment = Element.ALIGN_CENTER
        table.addCell(cell)

        cell = getCellBody("Total", true, 35f)
        cell.horizontalAlignment = Element.ALIGN_CENTER
        table.addCell(cell)
        cell = getCellBody(StringUtils.formatPrice(bill.sum)+" /-", true, 35f)
        cell.horizontalAlignment = Element.ALIGN_CENTER
        table.addCell(cell)

        val tableInfo: PdfPTable = pdfService.createTable(2, listOf(60f, 40f))
        cell = getCellBody("In Words :- "+StringUtils.getAmountInWords(bill.sum)+" RUPEES ONLY", false, 70f)
        cell.border = Rectangle.NO_BORDER
        tableInfo.addCell(cell)
        cell = getCellBody("Authority sign / stamp", true, 70f)
        cell.verticalAlignment = Element.ALIGN_BOTTOM
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.paddingBottom = 5f
        cell.border = Rectangle.NO_BORDER
        tableInfo.addCell(cell)
        cell = PdfPCell(tableInfo)
        cell.colspan = 2
        cell.borderWidth = 1.7f
        table.addCell(cell)

        document.add(Paragraph(" ", bodyFont))
        document.add(Paragraph(" ", bodyFont))
        document.add(table)

        document.close()
    }

//    private fun getFilePath(): String{
//        return file!!.path
//    }

    private fun getFileName(bill: Bill): String{
//        return "Generated_Bill_"+bill.patientId+"_"+bill.lastName+".pdf"
        return "Generated_Bill_"+bill.firstName+"_"+bill.lastName+".pdf"
    }

//    private fun getCell(title: String,data: String, isTitleOnNewLine: Boolean, colspan: Int = 1): PdfPCell{
//        val pdfCell = PdfPCell()
//        val lines = data.split('\n')
//
//        val chunk = Chunk(title)
//        chunk.setUnderline(0.8f, -1f)
//        chunk.font = bodyFont
//        if(isTitleOnNewLine){
//            pdfCell.addElement(chunk)
//        }
//
//        var i=0
//        for(line in lines){
//            i++
//            var pg = Paragraph(15f, line, bodyFont)
//            if(i==1 && !isTitleOnNewLine){
//                val ph = Phrase(chunk)
//                pg = Paragraph(15f, chunk.toString()+" "+line, bodyFont)
//            }
//            pdfCell.addElement(pg)
//        }
////        pdfCell.minimumHeight = 25f
//        pdfCell.setPadding(5f)
//        pdfCell.paddingTop = 1f
//        pdfCell.borderWidth = 1.7f
//        pdfCell.colspan = colspan
//        return pdfCell
//    }

    private fun getCell(data: String, colspan: Int = 1): PdfPCell{
        val pdfCell = PdfPCell()
        val lines = data.split('\n')
        for(line in lines){
            val pg = Paragraph(15f, line, bodyFont)
            pdfCell.addElement(pg)
        }
//        pdfCell.minimumHeight = 25f
        pdfCell.setPadding(2f)
        pdfCell.paddingTop = 1f
        pdfCell.borderWidth = 1.7f
        pdfCell.colspan = colspan
        return pdfCell
    }

    private fun getCellBody(data: String,isTitle: Boolean,minimumHeight: Float, colspan: Int = 1): PdfPCell{
        val font = if(isTitle) contentTitleFont else contentBodyFont
        val pdfCell = PdfPCell(Paragraph(15f,data, font))
//        val lines = data.split('\n')
//        var i=0
//        val font = if(isTitle) contentTitleFont else contentBodyFont
//        for(line in lines){
//            val pg = Paragraph(15f, line, font)
//            pdfCell.addElement(pg)
//        }
        pdfCell.minimumHeight = minimumHeight
        pdfCell.setPadding(5f)
        pdfCell.paddingTop = 1f
        pdfCell.borderWidth = 1.7f
        pdfCell.colspan = colspan
        return pdfCell
    }

//    private fun getCell(data: List<String>, colspan: Int = 1): PdfPCell{
//        val pdfCell = PdfPCell()
//        for(line in data){
//            val pg = Paragraph(15f, line, bodyFont)
//            pdfCell.addElement(pg)
//        }
////        pdfCell.minimumHeight = 25f
//        pdfCell.setPadding(5f)
//        pdfCell.paddingTop = 1f
//        pdfCell.borderWidth = 1.7f
//        pdfCell.colspan = colspan
//        return pdfCell
//    }

//    private fun getCell(title: String, data: List<String>,isTitleOnNewLine: Boolean, colspan: Int = 1): PdfPCell{
//        val pdfCell = PdfPCell()
//
//        val chunk = Chunk(title)
//        chunk.setUnderline(0.8f, -1f)
//        chunk.font = bodyFont
//        var ph = Phrase(chunk)
//        if(isTitleOnNewLine){
//            pdfCell.addElement(ph)
//        }
//
//        var i=0
//        for(line in data){
//            i++
//            var pg = Paragraph(15f, i.toString()+") "+line, bodyFont)
//            if(i==1 && !isTitleOnNewLine){
//                ph = Phrase(chunk)
//                pg = Paragraph(15f, chunk.toString()+" "+line, bodyFont)
//            }
//            pdfCell.addElement(pg)
//        }
////        pdfCell.minimumHeight = 25f
//        pdfCell.setPadding(5f)
//        pdfCell.paddingTop = 1f
//        pdfCell.borderWidth = 1.7f
//        pdfCell.colspan = colspan
//        return pdfCell
//    }
}