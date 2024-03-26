package com.example.androidassist.apps.photos

import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.FragmentActivity

class PhotosService {
    companion object {
        fun loadPhotos(activity: FragmentActivity?): ArrayList<String> {
            if(activity == null) {
                return ArrayList()
            }

            val photos = ArrayList<String>()

            val sdCard = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
            if (sdCard) {
                val columns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)
                val order = MediaStore.Images.Media.DATE_TAKEN + " DESC"
                val cursor = activity.contentResolver?.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    columns,
                    null,
                    null,
                    order
                )
                val count = cursor!!.count

                for (i in 0 until count) {
                    cursor.moveToPosition(i)
                    val columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                    photos.add(cursor.getString(columnIndex))
                }

                cursor.close()
            }
            return photos
        }
    }
}