package com.example.androidassist.apps.contacts
import android.content.ContentUris
import android.content.ContentValues
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants


public class AddContactFragment : Fragment(){

    //val fragment = ContactMainFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameEditText = view.findViewById<EditText>(R.id.nameEditText)
        val phoneEditText = view.findViewById<EditText>(R.id.phoneEditText)
        val saveContactButton = view.findViewById<Button>(R.id.saveContactButton)

        saveContactButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val phone = phoneEditText.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty()) {
                addContact(name, phone)
                Toast.makeText(requireContext(), "Contact saved", Toast.LENGTH_SHORT).show()

                // To refresh contacts in ContactMainFragment
                (activity as? ContactsMainActivity)?.apply {
                    replaceFragment(ContactMainFragment())
                    setState(SharedConstants.AppEnum.CONTACTS)
                }

            } else {
                Toast.makeText(requireContext(), "Name and phone number are required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addContact(name: String, phone: String) {

        val contentResolver = requireActivity().contentResolver

        // Prepare to insert a new raw contact
        val values = ContentValues()
        val rawContactUri = contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, values)
        val rawContactId = ContentUris.parseId(rawContactUri!!)

        // Insert the contact's name
        values.clear()
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
        values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
        contentResolver.insert(ContactsContract.Data.CONTENT_URI, values)

        // Insert the contact's phone number
        values.clear()
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
        contentResolver.insert(ContactsContract.Data.CONTENT_URI, values)

    }
}
