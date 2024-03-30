package com.example.androidassist.apps.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants

class ContactCallScreenFragment: Fragment(){

    private lateinit var contactInfo: ContactInfo
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.contacts_call_screen, container, false)
        contactInfo = (activity as ContactsMainActivity).selectedContact!!

        val contactImage: ImageView = view.findViewById(R.id.contactImage)
        if(contactInfo.image != null) {
            contactImage.setImageBitmap(contactInfo.image)
        }
        else {
            contactImage.setImageResource(R.mipmap.ic_launcher)
        }

        // Initialize TextViews with contact information
        val nameTextView: TextView = view.findViewById(R.id.contact_name)

        var displayName: String? = "${contactInfo.firstName ?: ""} ${contactInfo.lastName ?: ""}"
        if(contactInfo.firstName.isNullOrBlank() && contactInfo.lastName.isNullOrBlank()) displayName = contactInfo.number
        nameTextView.text = displayName

        val numberTextView: TextView = view.findViewById(R.id.contact_phone_number)
        numberTextView.text = contactInfo.number

        // Setup End Call button
        val endCallButton: Button = view.findViewById(R.id.button3)
        endCallButton.setOnClickListener {
            (activity as? ContactsMainActivity)?.apply {
                replaceFragment(ContactMainFragment())
                setState(SharedConstants.PageState.CONTACTS)
            }
        }

        return view
    }
}