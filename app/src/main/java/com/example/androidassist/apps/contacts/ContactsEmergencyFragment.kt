package com.example.androidassist.apps.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.utilities.LayoutUtils
import com.example.androidassist.sharedComponents.views.TextToSpeechFragment

class ContactsEmergencyFragment : TextToSpeechFragment() {
    private lateinit var nameTextView: TextView
    private lateinit var numberTextView: TextView
    private lateinit var speakerImageView: ImageView
    private lateinit var speakerTextView: TextView
    private lateinit var locationImageView: ImageView
    private lateinit var locationTextView: TextView
    private lateinit var muteImageView: ImageView
    private lateinit var muteTextView: TextView
    private lateinit var endCallButton: Button

    private lateinit var actions: List<Pair<ImageView, TextView>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contacts_call_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameTextView = view.findViewById(R.id.contact_name)
        numberTextView = view.findViewById(R.id.contact_phone_number)
        speakerImageView = view.findViewById(R.id.speakerbutton)
        speakerTextView = view.findViewById(R.id.speaker_button_text)
        locationImageView = view.findViewById(R.id.locationbutton)
        locationTextView = view.findViewById(R.id.location_button_text)
        muteImageView = view.findViewById(R.id.mutebutton)
        muteTextView = view.findViewById(R.id.mute_button_text)
        endCallButton = view.findViewById(R.id.button3)

        actions = listOf(
            Pair(speakerImageView, speakerTextView),
            Pair(locationImageView, locationTextView),
            Pair(muteImageView, muteTextView)
        )

        endCallButton.setOnClickListener{
            (activity as? ContactsMainActivity)?.apply {
                replaceFragment(ContactMainFragment())
                setState(SharedConstants.PageState.CONTACTS)
            }
        }

        setupTTSForEverything()
        setupStyles()
    }

    private fun setupTTSForEverything() {
        setupTTS(nameTextView, nameTextView.text)
        setupTTS(numberTextView, numberTextView.text)
        setupTTS(endCallButton, endCallButton.text)

        for (action in actions) {
            setupTTS(action.first, action.second.text)
            setupTTS(action.second, action.second.text)
        }
    }

    private fun setupStyles() {
        LayoutUtils.setTextSize(endCallButton, 0.01f)
    }
}