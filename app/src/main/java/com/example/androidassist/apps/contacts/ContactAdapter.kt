package com.example.androidassist.apps.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants

class ContactAdapter(private val items: List<ContactInfo>, private val activity: ContactsMainActivity) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contacts_list_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = items[position]
        holder.contactName.text = "${contact.firstName} ${contact.lastName}"
        if(contact.image != null) {
            holder.contactImage.setImageBitmap(contact.image)
        }
        else {
            holder.contactImage.setImageResource(R.mipmap.ic_launcher)
        }

        holder.setupClickingContact(contact, ::onContactClicked)
    }

    override fun getItemCount(): Int = items.size

    private fun onContactClicked(contact: ContactInfo) {
        activity.selectedContact = contact
        activity.replaceFragment(ContactsSingleContactFragment())
        activity.setState(SharedConstants.AppEnum.CSINGLECONTACT)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contactName: TextView = view.findViewById(R.id.tv_name)
        val contactImage: ImageView = view.findViewById(R.id.iv_profile)

        fun setupClickingContact(contact: ContactInfo, clickContact: (ContactInfo)-> Unit) {
            itemView.setOnClickListener {
                clickContact(contact)
            }
        }
    }
}