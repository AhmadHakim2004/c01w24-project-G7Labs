package com.example.androidassist.apps.camera

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import com.example.androidassist.AppsGridAdapter
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.dataClasses.AppsInfo
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import java.util.concurrent.Executors

class CameraMainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var appsGridContainer: GridView
    private lateinit var appsGridAdapter: AppsGridAdapter
    private lateinit var apps: List<AppsInfo>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.camera_main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAppGrid()
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = CameraMainFragment()
    }

    private fun initAppGrid() {
        appsGridContainer = requireView().findViewById(R.id.appsGridContainer)
        apps = getInitialApps()
        appsGridAdapter = AppsGridAdapter(requireContext(), apps)
        appsGridContainer.adapter = appsGridAdapter

        appsGridContainer.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            when(apps[position].appEnum) {
                SharedConstants.AppEnum.CPHOTO -> {
                    val cameraActivity = activity as CameraMainActivity
                    cameraActivity.replaceFragment(CameraPhotoFragment())
                    cameraActivity.setState(SharedConstants.AppEnum.CPHOTO)
                }
                SharedConstants.AppEnum.CPHOTOSELFIE -> {
                    val cameraActivity = activity as CameraMainActivity
                    cameraActivity.replaceFragment(CameraPhotoSelfieFragment())
                    cameraActivity.setState(SharedConstants.AppEnum.CPHOTOSELFIE)
                }
                SharedConstants.AppEnum.CVIDEO -> {
                    val cameraActivity = activity as CameraMainActivity
                    cameraActivity.replaceFragment(CameraVideoFragment())
                    cameraActivity.setState(SharedConstants.AppEnum.CVIDEO)
                }
                SharedConstants.AppEnum.CVIDEOSELFIE -> {
                    val cameraActivity = activity as CameraMainActivity
                    cameraActivity.replaceFragment(CameraVideoSelfieFragment())
                    cameraActivity.setState(SharedConstants.AppEnum.CVIDEOSELFIE)

                }
                else -> {}
            }
        }
    }

    // @Todo Get Apps From DB
    private fun getInitialApps(): List<AppsInfo> {
        return listOf(
            AppsInfo(1, R.mipmap.ic_launcher, "Photo", SharedConstants.AppEnum.CPHOTO),
            AppsInfo(2, R.mipmap.ic_launcher, "Photo Selfie", SharedConstants.AppEnum.CPHOTOSELFIE),
            AppsInfo(3, R.mipmap.ic_launcher, "Video",SharedConstants.AppEnum.CVIDEO),
            AppsInfo(4, R.mipmap.ic_launcher, "Video Selfie", SharedConstants.AppEnum.CVIDEOSELFIE),
        )
    }
}