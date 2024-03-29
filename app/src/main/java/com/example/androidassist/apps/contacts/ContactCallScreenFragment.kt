package com.example.androidassist.apps.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
        val view = inflater.inflate(R.layout.normal_call_screen1, container, false)
        contactInfo = (activity as ContactsMainActivity).selectedContact!!
        // Initialize TextViews with contact information
        val nameTextView: TextView = view.findViewById(R.id.textView8)
        nameTextView.text = "Name: ${contactInfo.firstName} ${contactInfo.lastName}"

        val numberTextView: TextView = view.findViewById(R.id.textView9)
        numberTextView.text = "Number: ${contactInfo.number}"

        // Setup End Call button
        val endCallButton: Button = view.findViewById(R.id.button3)
        endCallButton.setOnClickListener {
            (activity as? ContactsMainActivity)?.apply {
                replaceFragment(ContactMainFragment())
                setState(SharedConstants.AppEnum.CONTACTS)
            }
        }

        return view
    }



}