package com.example.androidassist.apps.photos

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.androidassist.R
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants

class PhotosMainFragment : Fragment() {
    private lateinit var photosList: RecyclerView
    private lateinit var photos: ArrayList<String>
    private var photosAdapter: PhotosAdapter? = null

    private val REQUIRED_PERMISSIONS =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
        else arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

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
            setupPhotos()
        } else {
            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
        }
    }

    private fun setupPhotos() {
        photos = PhotosService.loadPhotos(activity)
        photosList = requireView().findViewById(R.id.photosList)

        photosAdapter = PhotosAdapter(photos, ::onPhotoClicked)
        photosList.adapter = photosAdapter
        photosList.layoutManager = GridLayoutManager(activity, 2)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (permissions.all { it.value }) {
            setupPhotos()
        }
    }

    private fun onPhotoClicked(photoFilePath: String) {
        val photosSinglePhotoFragment = PhotosSinglePhotoFragment()
        val args = Bundle()
        args.putString("photoFilePath", photoFilePath)
        photosSinglePhotoFragment.arguments = args

        val photosActivity = activity as PhotosMainActivity
        photosActivity.replaceFragment(photosSinglePhotoFragment)
        photosActivity.setState(SharedConstants.PageState.PSINGLEPHOTO)
    }
}