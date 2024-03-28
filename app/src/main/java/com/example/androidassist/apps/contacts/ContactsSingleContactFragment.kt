package com.example.androidassist.apps.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.utilities.LayoutUtils

class ContactsSingleContactFragment : Fragment() {
    private lateinit var contactInfo: ContactInfo

    private lateinit var contactImage: ImageView
    private lateinit var contactFirstNameText: TextView
    private lateinit var contactPhoneNumberText: TextView
    private lateinit var editContactBtn: Button
    private lateinit var callContactBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contactInfo = (activity as ContactsMainActivity).selectedContact!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.contacts_view_single_contact_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactImage = requireView().findViewById(R.id.contact_image)
        contactFirstNameText = requireView().findViewById(R.id.contact_name)
        contactPhoneNumberText = requireView().findViewById(R.id.contact_phone_number)
        editContactBtn = requireView().findViewById(R.id.edit_contact_btn)
        callContactBtn = requireView().findViewById(R.id.call_contact_btn)

        if(contactInfo.image != null)contactImage.setImageBitmap(contactInfo.image)
        contactFirstNameText.text = "${contactInfo.firstName} ${contactInfo.lastName}"
        contactPhoneNumberText.text = contactInfo.number

        setupStyles()
    }

    private fun setupStyles() {
        LayoutUtils.setMargins(contactImage, 0f, 0.05f, 0f, 0f)

        LayoutUtils.setMargins(contactFirstNameText, 0f, 0.02f, 0f, 0f)
        LayoutUtils.setTextSize(contactFirstNameText, 0.015f)

        LayoutUtils.setTextSize(contactPhoneNumberText, 0.012f)

        LayoutUtils.setPadding(editContactBtn, 0.02f)
        LayoutUtils.setTextSize(editContactBtn, 0.01f)

        LayoutUtils.setPadding(callContactBtn, 0.02f)
        LayoutUtils.setTextSize(callContactBtn, 0.01f)
    }
}