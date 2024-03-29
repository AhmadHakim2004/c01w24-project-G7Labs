package com.example.androidassist.apps.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.utilities.LayoutUtils

class ContactsEmergencyFragment : Fragment() {


    private lateinit var endButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.call_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        endButton = requireView().findViewById(R.id.button3)

        endButton.setOnClickListener{
            (activity as? ContactsMainActivity)?.apply {
                replaceFragment(ContactMainFragment())
                setState(SharedConstants.AppEnum.CONTACTS)
            }
        }

        setupStyles()
    }

    private fun setupStyles() {

        LayoutUtils.setTextSize(endButton, 0.01f)
    }
}