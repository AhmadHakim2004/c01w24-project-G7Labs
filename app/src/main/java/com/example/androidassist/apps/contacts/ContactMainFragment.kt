package com.example.androidassist.apps.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.AndroidAssistApplication
import com.example.androidassist.sharedComponents.utilities.SharedPreferenceUtils

class ContactMainFragment : Fragment() {
    private lateinit var contacts: MutableList<ContactInfo>
    private var contactAdapter: ContactAdapter? = null
    private lateinit var favouredContacts: Set<String>

    private lateinit var contactList: RecyclerView
    private lateinit var addButton: Button
    private lateinit var emergencyButton: Button

    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.contacts_main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactList = requireView().findViewById(R.id.contact_list)
        emergencyButton = requireView().findViewById(R.id.emergency_call_button)

        addButton = view.findViewById(R.id.add_contact_button)

        if (allPermissionsGranted()) {
            setupContacts()
        } else {
            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
        }

        addButton.setOnClickListener {
            (activity as? ContactsMainActivity)?.apply {
                replaceFragment(ContactsAddContactFragment())
                setState(SharedConstants.AppEnum.CADDCONTACTS)
            }
        }

        emergencyButton.setOnClickListener {
            val contactsActivity = activity as ContactsMainActivity
            contactsActivity.replaceFragment(ContactsEmergencyCallConfirmationFragment())
            contactsActivity.setState(SharedConstants.AppEnum.CEMERGENCYCONFIRM)
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                setupContacts()
            }
        }

    private fun setupContacts() {
        favouredContacts = SharedPreferenceUtils.getStringSetFromDefaultSharedPrefFile(
            AndroidAssistApplication.getAppContext(), "favContacts", setOf()) ?: setOf()
        contacts = loadContacts()

        contactAdapter = ContactAdapter(contacts, contactList, activity as ContactsMainActivity)
        contactList.adapter = contactAdapter
        contactList.layoutManager = LinearLayoutManager(activity)
    }

    private fun loadContacts() : MutableList<ContactInfo> {
        val contactList: MutableList<ContactInfo> = ArrayList()
        val contacts = (activity?.contentResolver)?.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null
        )
        contacts?.use { cursor ->
            val idIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val photoUriIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)

            while (cursor.moveToNext()) {
                if (idIndex < 0 || numberIndex < 0 || photoUriIndex < 0) {
                    continue
                }

                val id = cursor.getString(idIndex)

                val firstAndLastName = getContactFirstAndLastName(id)
                val firstName: String? = firstAndLastName.first
                val lastName: String? = firstAndLastName.second

                val number = cursor.getString(numberIndex)

                val photoUriString = cursor.getString(photoUriIndex)
                val photoUri = photoUriString?.let { Uri.parse(it) }
                val bitmap = loadContactPhoto(photoUri)

                val contact = ContactInfo(id, firstName, lastName, number, bitmap)
                contact.isFavourite = favouredContacts.contains(contact.id)

                val indexToInsert = ContactsBinarySearch.indexToInsert(contactList, contact)
                contactList.add(indexToInsert, contact)
            }
        }

        return contactList
    }

    private fun getContactFirstAndLastName(id: String): Pair<String?, String?> {
        val contactCursor: Cursor? = activity?.contentResolver?.query(
            ContactsContract.Data.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID + " = ?",
            arrayOf(id),
            null
        )

        contactCursor?.use {
            val firstNameIndex = contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME)
            val lastNameIndex = contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME)
            if (contactCursor.moveToFirst()) {
                val firstName = contactCursor.getString(firstNameIndex)
                val lastName = contactCursor.getString(lastNameIndex)
                return Pair(firstName, lastName)
            }
        }

        return Pair(null, null)
    }

    private fun loadContactPhoto(photoUri: Uri?): Bitmap? {
        photoUri?.let {
            val inputStream = activity?.contentResolver?.openInputStream(it)
            return BitmapFactory.decodeStream(inputStream)
        }
        return null
    }
}