package com.example.androidassist.apps.contacts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassist.databinding.ContactChildBinding

class ContactAdapter(private val items: List<ContactDTO>, private val context: Context) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ContactChildBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = items[position]
        with(holder.binding) {
            tvName.text = contact.name
            //tvNumber.text = contact.number
            //ivProfile.setImageBitmap((contact.image ?: ContextCompat.getDrawable(context, R.mipmap.ic_launcher_round)) as Bitmap?)
            //ivProfile.setImageResource(R.drawable.asad)
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val binding: ContactChildBinding) : RecyclerView.ViewHolder(binding.root)
}