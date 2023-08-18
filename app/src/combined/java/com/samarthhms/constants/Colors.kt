package com.samarthhms.constants

import com.samarthhms.R
import com.samarthhms.models.BillItem
import com.samarthhms.models.MedicineTemplate

object Colors {
    object Theme {
        const val MAIN_BLUE = R.color.blue_theme
        const val RED = R.color.red
        const val BLACK = R.color.black
    }

    object Validation {
        const val VALID = Theme.MAIN_BLUE
        const val INVALID = Theme.RED
    }

    object TextColor{
        const val EDITABLE_TEXT = Theme.BLACK
        const val UNEDITABLE_TEXT = R.color.add_visit_edittext_text_color
    }
}