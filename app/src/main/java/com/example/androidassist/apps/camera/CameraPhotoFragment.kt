package com.example.androidassist.apps.camera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.camera.core.ImageCapture
import androidx.camera.view.PreviewView
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.views.TextToSpeechFragment
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicReference

class CameraPhotoFragment : TextToSpeechFragment() {

    private var imageCaptureRef: AtomicReference<ImageCapture> = AtomicReference()
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var previewViewV: PreviewView

    private val REQUIRED_PERMISSIONS =
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) arrayOf(Manifest.permission.CAMERA)
        else arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.camera_photo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        previewViewV = requireView().findViewById(R.id.viewFinder)
        cameraExecutor = Executors.newSingleThreadExecutor()

        // Check camera permissions if all permission granted
        // start camera else ask for the permission
        if (allPermissionsGranted()) {
            CameraService.startCamera(this, previewViewV, imageCaptureRef)
        } else {
            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
        }

        // set on click listener for the button of capture photo
        // it calls a method which is implemented below
        val takePhotoButton: Button = view.findViewById(R.id.camera_capture_button)
       takePhotoButton.setOnClickListener {
           CameraService.takePhoto(imageCaptureRef, cameraExecutor, requireActivity())
        }

        setupTTS(takePhotoButton, takePhotoButton.text)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (permissions.all { it.value }) {
            CameraService.startCamera(this, previewViewV, imageCaptureRef)
        }
    }
}
