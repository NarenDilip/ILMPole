package com.schnell.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import com.example.ilmpole.R


class EditText(context: Context, attrs: AttributeSet?, defStyle: Int) : AppCompatEditText(context, attrs) {
    constructor(context: Context) : this(context, null) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        init()
    }

    init {
        init()
    }

    private fun init() {
        if (!isInEditMode) {
            typeface = ResourcesCompat.getFont(context, R.font.helvetica_font)
        }
    }
}

