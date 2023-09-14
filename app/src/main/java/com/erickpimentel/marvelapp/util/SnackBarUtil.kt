package com.erickpimentel.marvelapp.util

import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.erickpimentel.marvelapp.R
import com.google.android.material.snackbar.Snackbar

class SnackBarUtil {

    companion object {
        fun View.showSnackBar(@StringRes resId: Int, length: Int = Snackbar.LENGTH_LONG) {
            val snackBar = Snackbar.make(this, resId, length)
            snackBar.view.setBackgroundColor(ContextCompat.getColor(context, R.color.red))
            snackBar.show()
        }
    }

}