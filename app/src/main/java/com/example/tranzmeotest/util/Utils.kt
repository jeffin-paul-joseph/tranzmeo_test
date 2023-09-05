package com.example.tranzmeotest.util

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.tranzmeotest.data.response.Address
import com.example.tranzmeotest.util.Utils.setRoundedImage
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun ImageView.setRoundedImage(url: String) {
        Glide.with(this).load(url)
            .fitCenter()
            .circleCrop()
            .into(this)
    }

    fun TextView.getDobDate(inputDate: String?) {
        text = try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val date = inputFormat.parse(inputDate)
            outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            inputDate
        }
    }

    fun TextView.setAddress(address: Address?) {
        text = address.toString()
    }
}