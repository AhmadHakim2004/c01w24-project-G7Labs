package com.example.androidassist.apps.photos

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidassist.R
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants

class PhotosMainFragment : Fragment() {
    private lateinit var photosList: RecyclerView
    private lateinit var photos: ArrayList<String>
    private var photosAdapter: PhotosAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.photos_main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (allPermissionsGranted()) {
            photos = loadPhotos()
            photosList = requireView().findViewById(R.id.photosList)

            photosAdapter = PhotosAdapter(photos, ::onPhotoClicked)
            photosList.adapter = photosAdapter
            photosList.layoutManager = GridLayoutManager(activity, 3)
        } else {
            requestPhotosPermissions()
        }
    }

    private fun onPhotoClicked(photoFilePath: String) {
        val photosSinglePhotofragment = PhotosSinglePhotoFragment()
        val args = Bundle()
        args.putString("photoFilePath", photoFilePath)
        photosSinglePhotofragment.arguments = args

        val photosActivity = activity as PhotosMainActivity
        photosActivity.replaceFragment(photosSinglePhotofragment)
        photosActivity.setState(SharedConstants.AppEnum.PSINGLEPHOTO)
    }

    companion object {
        private val REQUIRED_PERMISSIONS = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
            else arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPhotosPermissions() {
        ActivityCompat.requestPermissions(requireActivity(), REQUIRED_PERMISSIONS, 123)
    }

    private fun loadPhotos(): ArrayList<String> {
        val photos = ArrayList<String>()

        val SDCard = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        if (SDCard) {
            val columns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)
            val order = MediaStore.Images.Media.DATE_TAKEN + " DESC"
            val cursor = activity?.contentResolver?.query(
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
        }
        return photos
    }
}