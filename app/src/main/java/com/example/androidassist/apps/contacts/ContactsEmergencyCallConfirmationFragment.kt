package com.example.androidassist.apps.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.utilities.LayoutUtils

class ContactsEmergencyCallConfirmationFragment : Fragment() {
    private lateinit var constraintLayout: ConstraintLayout

    private lateinit var confirmationText: TextView
    private lateinit var callButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contacts_emergency_call_confirmation_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        constraintLayout = requireView().findViewById(R.id.emergency_call_confirmation_fragment_holder)
        confirmationText = requireView().findViewById(R.id.emergency_call_confirmation_text)
        callButton = requireView().findViewById(R.id.call911)

        callButton.setOnClickListener{
            (activity as? ContactsMainActivity)?.apply {
                replaceFragment(ContactsEmergencyFragment())
                setState(SharedConstants.AppEnum.CEMERGENCY)
            }
        }

        setupStyles()
    }

    private fun setupStyles() {
        LayoutUtils.setPadding(constraintLayout, 0.025F, 0.01F, 0.025F, 0.01F)
        LayoutUtils.setMargins(confirmationText, 0.075f, 0.05f, 0.075f, 0f)
        LayoutUtils.setTextSize(confirmationText, 0.01f)
        LayoutUtils.setTextSize(callButton, 0.01f)
    }
}