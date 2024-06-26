package com.example.androidassist.apps.contacts

import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.AndroidAssistApplication
import com.example.androidassist.sharedComponents.utilities.SharedPreferenceUtils
import com.example.androidassist.sharedComponents.utilities.TextToSpeechUtils
import java.util.Locale

class ContactAdapter(private val items: MutableList<ContactInfo>, private val recyclerView: RecyclerView, private val activity: ContactsMainActivity) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>(), TextToSpeech.OnInitListener {

    private val textToSpeech: TextToSpeech = TextToSpeech(AndroidAssistApplication.getAppContext(), this)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contacts_list_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = items[position]
        var displayName: String? = "${contact.firstName ?: ""} ${contact.lastName ?: ""}"
        if(contact.firstName.isNullOrBlank() && contact.lastName.isNullOrBlank()) displayName = contact.number

        holder.contactName.text = displayName

        val heartButtonNeeded = if (contact.isFavourite) R.drawable.heart else R.drawable.empty_heart
        holder.heartButton.setImageResource(heartButtonNeeded)

        if(contact.image != null) {
            holder.contactImage.setImageBitmap(contact.image)
        }
        else {
            holder.contactImage.setImageResource(R.drawable.contact_default_image)
        }

        holder.setupClickingContact(contact, ::onContactClicked)
        holder.setupHeartButton(contact, ::onHeartButtonClicked)
        holder.setupTTC(textToSpeech)
    }

    override fun getItemCount(): Int = items.size

    private fun onHeartButtonClicked(contact: ContactInfo, position: Int) {
        contact.isFavourite = !contact.isFavourite
        items.removeAt(position)
        notifyItemRemoved(position)

        val indexToInsertAt = ContactsBinarySearch.indexToInsert(items, contact)
        items.add(indexToInsertAt, contact)
        notifyItemInserted(indexToInsertAt)

        if(contact.isFavourite) {
            recyclerView.scrollToPosition(0)
            SharedPreferenceUtils.addStringSetElementToDefaultSharedPrefFile(
                AndroidAssistApplication.getAppContext(), "favContacts", contact.id)
        }
        else {
            SharedPreferenceUtils.removeStringSetElementFromDefaultSharedPrefFile(
                AndroidAssistApplication.getAppContext(), "favContacts", contact.id)
        }
    }

    private fun onContactClicked(contact: ContactInfo) {
        activity.selectedContact = contact
        activity.replaceFragment(ContactsSingleContactFragment())
        activity.setState(SharedConstants.PageState.CSINGLECONTACT)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contactName: TextView = view.findViewById(R.id.tv_name)
        val contactImage: ImageView = view.findViewById(R.id.iv_profile)
        val heartButton: ImageButton = view.findViewById(R.id.btn_favourite_contact)

        fun setupHeartButton(contact: ContactInfo, clickHeartButton: (ContactInfo, Int)-> Unit) {
            heartButton.setOnClickListener {
                clickHeartButton(contact, layoutPosition)
            }
        }

        fun setupClickingContact(contact: ContactInfo, clickContact: (ContactInfo)-> Unit) {
            itemView.setOnClickListener {
                clickContact(contact)
            }
        }

        fun setupTTC(textToSpeech: TextToSpeech) {
            TextToSpeechUtils.setupTTS(itemView, textToSpeech, contactName.text)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val lang = AndroidAssistApplication.getAppContext().resources.configuration.locales.get(0)
            val result = textToSpeech.setLanguage(lang)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                textToSpeech.setLanguage(Locale.CANADA)
            }
        }
    }
}