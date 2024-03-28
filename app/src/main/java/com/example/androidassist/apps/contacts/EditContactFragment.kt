package com.example.androidassist.apps.contacts

import android.app.Activity
import android.content.ContentProviderOperation
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import java.io.ByteArrayOutputStream
import java.io.IOException

class EditContactFragment : Fragment() {
    private var imageUri: Uri? = null
    // Assuming you pass the contactId correctly to the fragment. You might need to use arguments bundle for that.
    private var contactId: Long = 0
    private lateinit var contactInfo: ContactInfo

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.edit_contacts, container, false)
        contactInfo = (activity as ContactsMainActivity).selectedContact!!


        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val firstNameEditText = view.findViewById<EditText>(R.id.editFirstNameEditText)
        val lastNameEditText = view.findViewById<EditText>(R.id.editLastNameEditText)
        val phoneEditText = view.findViewById<EditText>(R.id.editPhoneEditText)
        val photoImageView = view.findViewById<ImageView>(R.id.editPhotoImageView)
        val saveContactButton = view.findViewById<Button>(R.id.saveEditedContactButton)

        // Load the contact's current details
        /*
        contactId = arguments?.getLong("contactId") ?: 0 // Make sure to pass "contactId" correctly
        if (contactId > 0) {
            loadContactDetails(contactId, firstNameEditText, lastNameEditText, phoneEditText, photoImageView)
        }*/
        context?.let { safeContext ->
            val foundContactId = findContactIdByPhoneNumber(contactInfo.number, safeContext)
            foundContactId?.let {
                contactId = it
            }
        }

        photoImageView.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, PICK_IMAGE)
        }

        saveContactButton.setOnClickListener {
            val first = firstNameEditText.text.toString()
            val last = lastNameEditText.text.toString()
            val phone = phoneEditText.text.toString()
            if (first.isNotEmpty() && last.isNotEmpty() && phone.isNotEmpty()) {
                updateContact(contactId, first, last, phone, imageUri)
                Toast.makeText(requireContext(), "Contact updated", Toast.LENGTH_SHORT).show()
                (activity as? ContactsMainActivity)?.apply {
                    replaceFragment(ContactMainFragment())
                    setState(SharedConstants.AppEnum.CONTACTS)
                }
            } else {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun findContactIdByPhoneNumber(phoneNumber: String, context: Context): Long? {
        val uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber))
        val projection = arrayOf(ContactsContract.PhoneLookup._ID)

        context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val contactIdIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup._ID)
                if (contactIdIndex > -1) {
                    return cursor.getLong(contactIdIndex)
                }
            }
        }
        return null
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data?.data
            view?.findViewById<ImageView>(R.id.editPhotoImageView)?.setImageURI(imageUri)
        }
    }

    private fun loadContactDetails(contactId: Long, firstNameEditText: EditText, lastNameEditText: EditText, phoneEditText: EditText, photoImageView: ImageView) {
        // Define the URI to fetch the contact's details
        val contactUri: Uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId)
        val dataUri: Uri = ContactsContract.Data.CONTENT_URI

        // Define the columns to fetch
        val projection: Array<String> = arrayOf(
            ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
            ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Photo.PHOTO_URI
        )

        // Define the selection criteria (filter by contact ID and MimeType for StructuredName and Phone)
        val selection: String = "${ContactsContract.Data.CONTACT_ID} = ? AND (${ContactsContract.Data.MIMETYPE} = ? OR ${ContactsContract.Data.MIMETYPE} = ? OR ${ContactsContract.Data.MIMETYPE} = ?)"
        val selectionArgs: Array<String> = arrayOf(
            contactId.toString(),
            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
        )

        // Perform the query
        requireActivity().contentResolver.query(dataUri, projection, selection, selectionArgs, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                // Retrieve the column indexes of the data we're interested in
                val givenNameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME)
                val familyNameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME)
                val phoneNumberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val photoUriIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI)

                // Extract the data from the cursor
                val givenName = if (givenNameIndex != -1) cursor.getString(givenNameIndex) else ""
                val familyName = if (familyNameIndex != -1) cursor.getString(familyNameIndex) else ""
                val phoneNumber = if (phoneNumberIndex != -1) cursor.getString(phoneNumberIndex) else ""
                val photoUri = if (photoUriIndex != -1) cursor.getString(photoUriIndex) else null

                // Update the UI elements with the retrieved data
                firstNameEditText.setText(givenName)
                lastNameEditText.setText(familyName)
                phoneEditText.setText(phoneNumber)
                if (photoUri != null) photoImageView.setImageURI(Uri.parse(photoUri))
            } else {
                // Handle the case where the contact details were not found
                Toast.makeText(requireContext(), "Contact details not found", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun updateContact(contactId: Long, firstName: String, lastName: String, phoneNumber: String, photoUri: Uri?) {
        val ops = ArrayList<ContentProviderOperation>().apply {
            add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection("${ContactsContract.Data.CONTACT_ID}=? AND ${ContactsContract.Data.MIMETYPE}=?", arrayOf(contactId.toString(), ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE))
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, firstName)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, lastName)
                .build())

            add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection("${ContactsContract.Data.CONTACT_ID}=? AND ${ContactsContract.Data.MIMETYPE}=?", arrayOf(contactId.toString(), ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE))
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)
                .build())

            photoUri?.let {
                val photoBytes = getBytesFromUri(it)
                add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection("${ContactsContract.Data.CONTACT_ID}=? AND ${ContactsContract.Data.MIMETYPE}=?", arrayOf(contactId.toString(), ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE))
                    .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, photoBytes)
                    .build())
            }
        }

        try {
            requireActivity().contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
            // Notify the user of success
        } catch (e: Exception) {
            // Handle any errors
        }
    }

    @Throws(IOException::class)
    private fun getBytesFromUri(uri: Uri): ByteArray =
        requireContext().contentResolver.openInputStream(uri)?.use { inputStream ->
            ByteArrayOutputStream().use { output ->
                val buffer = ByteArray(1024)
                var length: Int
                while (inputStream.read(buffer).also { length = it } != -1) {
                    output.write(buffer, 0, length)
                }
                output.toByteArray()
            }
        } ?: byteArrayOf()

    companion object {
        private const val PICK_IMAGE = 100
    }
}
