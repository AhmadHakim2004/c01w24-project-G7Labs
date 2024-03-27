package com.example.androidassist.apps.contacts

import android.Manifest
import android.content.pm.PackageManager
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

class ContactMainFragment : Fragment() {
    private lateinit var contactList: RecyclerView
    private lateinit var contacts: MutableList<ContactInfo>
    private var contactAdapter: ContactAdapter? = null

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

        val addButton = view.findViewById<Button>(R.id.add_contact_button)
        addButton.setOnClickListener {
            (activity as? ContactsMainActivity)?.apply {
                replaceFragment(ContactsAddContactFragment())
                setState(SharedConstants.AppEnum.CADDCONTACTS)
            }
        }

        if (allPermissionsGranted()) {
            setupContacts()
        } else {
            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
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

    fun setupContacts() {
        contacts = loadContacts()
        contactList = requireView().findViewById(R.id.contact_list)

        contactAdapter = ContactAdapter(contacts)
        contactList.adapter = contactAdapter
        contactList.layoutManager = LinearLayoutManager(activity)
    }

    fun loadContacts(): MutableList<ContactInfo> {
        val contactList: MutableList<ContactInfo> = ArrayList()
        val contacts = (activity?.contentResolver)?.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null
        )
        contacts?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val photoUriIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)

            while (cursor.moveToNext()) {
                if (nameIndex < 0 || numberIndex < 0 || photoUriIndex < 0) {
                    continue
                }
                val name = cursor.getString(nameIndex)
                val number = cursor.getString(numberIndex)
                val photoUriString = cursor.getString(photoUriIndex)
                val photoUri = photoUriString?.let { Uri.parse(it) }
                val bitmap = loadContactPhoto(photoUri)

                val contact = ContactInfo(name, number, bitmap)
                contactList.add(contact)
            }
        }

        return contactList
    }

    fun loadContactPhoto(photoUri: Uri?): Bitmap? {
        photoUri?.let {
            val inputStream = activity?.contentResolver?.openInputStream(it)
            return BitmapFactory.decodeStream(inputStream)
        }
        return null
    }
}