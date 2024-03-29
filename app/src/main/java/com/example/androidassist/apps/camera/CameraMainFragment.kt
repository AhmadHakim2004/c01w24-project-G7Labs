package com.example.androidassist.apps.camera

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import com.example.androidassist.GridAdapter
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.dataClasses.ActionItem
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants

class CameraMainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var actionItemsContainer: GridView
    private lateinit var actionItemsAdapter: GridAdapter
    private lateinit var actionItems: List<ActionItem>

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

    private fun initAppGrid() {
        actionItemsContainer = requireView().findViewById(R.id.appsGridContainer)
        actionItems = getActionItems()
        actionItemsAdapter = GridAdapter(requireContext(), actionItems)
        actionItemsContainer.adapter = actionItemsAdapter

        actionItemsContainer.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            when(actionItems[position].pageState) {
                SharedConstants.PageState.CPHOTO -> {
                    val cameraActivity = activity as CameraMainActivity
                    cameraActivity.replaceFragment(CameraPhotoFragment())
                    cameraActivity.setState(SharedConstants.PageState.CPHOTO)
                }
                SharedConstants.PageState.CPHOTOSELFIE -> {
                    val cameraActivity = activity as CameraMainActivity
                    cameraActivity.replaceFragment(CameraPhotoSelfieFragment())
                    cameraActivity.setState(SharedConstants.PageState.CPHOTOSELFIE)
                }
                SharedConstants.PageState.CVIDEO -> {
                    val cameraActivity = activity as CameraMainActivity
                    cameraActivity.replaceFragment(CameraVideoFragment())
                    cameraActivity.setState(SharedConstants.PageState.CVIDEO)
                }
                SharedConstants.PageState.CVIDEOSELFIE -> {
                    val cameraActivity = activity as CameraMainActivity
                    cameraActivity.replaceFragment(CameraVideoSelfieFragment())
                    cameraActivity.setState(SharedConstants.PageState.CVIDEOSELFIE)
                }
                else -> {}
            }
        }
    }

    // @Todo Get Apps From DB
    private fun getActionItems(): List<ActionItem> {
        return listOf(
            ActionItem("1", R.string.c_photo, R.mipmap.camera_rear, SharedConstants.PageState.CPHOTO),
            ActionItem("2", R.string.c_photo_selfie, R.mipmap.camera_front, SharedConstants.PageState.CPHOTOSELFIE),
            ActionItem("3", R.string.c_video, R.mipmap.video_rear, SharedConstants.PageState.CVIDEO),
            ActionItem("4", R.string.c_video_selfie, R.mipmap.video_front, SharedConstants.PageState.CVIDEO),
        )
    }
}