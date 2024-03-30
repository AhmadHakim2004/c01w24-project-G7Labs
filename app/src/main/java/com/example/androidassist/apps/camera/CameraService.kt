package com.example.androidassist.apps.camera

import android.content.ContentValues
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.AndroidAssistApplication
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.atomic.AtomicReference

class CameraService {
    companion object {
        private const val TAG = "CameraXGFG"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

        fun startCamera(lifeCycleOwner: LifecycleOwner,
                        previewView: PreviewView,
                        imageCaptureRef: AtomicReference<ImageCapture>,
                        openFrontCamera: Boolean = false) {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(AndroidAssistApplication.getAppContext())
            cameraProviderFuture.addListener({

                // Used to bind the lifecycle of cameras to the lifecycle owner
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                // Preview
                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.createSurfaceProvider())
                    }

                imageCaptureRef.set(ImageCapture.Builder().build())

                // Select back camera as a default
                val cameraSelector =
                    if(openFrontCamera) CameraSelector.DEFAULT_FRONT_CAMERA
                    else CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    // Unbind use cases before rebinding
                    cameraProvider.unbindAll()

                    // Bind use cases to camera
                    cameraProvider.bindToLifecycle(
                        lifeCycleOwner, cameraSelector, preview, imageCaptureRef.get()
                    )

                } catch (exc: Exception) {
                    Log.e(TAG, "Use case binding failed", exc)
                }

            }, ContextCompat.getMainExecutor(AndroidAssistApplication.getAppContext()))
        }

        fun takePhoto(imageCaptureRef: AtomicReference<ImageCapture>, cameraExecutor: ExecutorService, fragmentActivity: FragmentActivity) {
            imageCaptureRef.get()?.let { imageCapture ->

                // Create time stamped name and MediaStore entry.
                val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis())
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                        val appName = AndroidAssistApplication.getAppContext().resources.getString(R.string.app_name)
                        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/")
                    }
                }

                // Create output options object which contains file + metadata
                val outputOptions = ImageCapture.OutputFileOptions
                    .Builder(AndroidAssistApplication.getAppContext().contentResolver,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues)
                    .build()

                // Setup image capture listener which is triggered after photo has been taken
                imageCapture.takePicture(
                    outputOptions, cameraExecutor, object : ImageCapture.OnImageSavedCallback {
                        override fun onError(exc: ImageCaptureException) {
                            Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                        }

                        override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                            val msg = "Photo capture succeeded"
                            fragmentActivity.runOnUiThread {
                                Toast.makeText(AndroidAssistApplication.getAppContext(), msg, Toast.LENGTH_LONG).show()
                            }
                            Log.d(TAG, msg)
                        }
                    })
            }
        }
    }
}
