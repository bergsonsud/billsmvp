package com.example.billsmvp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.billsmvp.R
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


fun ImageView.loadImage(uri: String?) {
    val options = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)
        .circleCrop()
        .error(R.mipmap.ic_launcher_round)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

fun ImageView.loadImageFromStorage(storageReference : StorageReference) {
    val options = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)
        .circleCrop()
        .error(R.mipmap.ic_launcher_round)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(storageReference.root)
        .into(this)
}

fun Uri.bitmap(context: Context): Bitmap {
    return MediaStore.Images.Media.getBitmap(context.contentResolver,this)
}

fun Uri.rotateBitmapOrientation(photoFilePath: String?): Bitmap? {
    // Create and configure BitmapFactory
    val bounds = BitmapFactory.Options()
    bounds.inJustDecodeBounds = true
    BitmapFactory.decodeFile(photoFilePath, bounds)
    val opts = BitmapFactory.Options()
    val bm = BitmapFactory.decodeFile(photoFilePath, opts)
    // Read EXIF Data
    var exif: ExifInterface? = null
    try {
        exif = ExifInterface(photoFilePath)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    val orientString: String = exif?.let {
        it.getAttribute(ExifInterface.TAG_ORIENTATION)
    }.toString()

    val orientation =
        orientString?.toInt() ?: ExifInterface.ORIENTATION_NORMAL
    var rotationAngle = 0f
    if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90f
    if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180f
    if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270f
    // Rotate Bitmap
    val matrix = Matrix()
    matrix.setRotate(
        rotationAngle,
        bm.width.toFloat() / 2,
        bm.height.toFloat() / 2
    )
    // Return result
    return Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true)
}

fun Date.getString(format : String) : String {
    return SimpleDateFormat(format).format(this)
}

fun Date.set(field: Int, value :  Int) : Date {

    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(field, value )

    return calendar.time

}