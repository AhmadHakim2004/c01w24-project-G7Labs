package com.example.androidassist.apps.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.androidassist.R
import com.jsibbold.zoomage.ZoomageView
import java.io.File

class PhotosSinglePhotoFragment : Fragment() {
    private lateinit var zoomageView: ZoomageView
    private lateinit var imageFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("photoFilePath")?.let {
            imageFile = File(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.photos_single_photo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        zoomageView = requireView().findViewById(R.id.single_photo)

        if (imageFile.exists()) {
            Glide.with(this).load(imageFile).into(zoomageView)
        }
    }
}