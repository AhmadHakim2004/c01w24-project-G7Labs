package com.example.androidassist.apps.contacts

import android.app.Activity
import android.content.ContentProviderOperation
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.telephony.PhoneNumberUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.views.TextToSpeechFragment
import java.util.Locale

class ContactsEditContactFragment : TextToSpeechFragment() {
    private var imageUri: Uri? = null
    private lateinit var contactInfo: ContactInfo

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var photoImageView: ImageView
    private lateinit var saveContactButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.contacts_edit_contacts, container, false)
        contactInfo = (activity as ContactsMainActivity).selectedContact!!

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firstNameEditText = view.findViewById(R.id.editFirstNameEditText)
        lastNameEditText = view.findViewById(R.id.editLastNameEditText)
        phoneEditText = view.findViewById(R.id.editPhoneEditText)
        photoImageView = view.findViewById(R.id.editPhotoImageView)
        saveContactButton = view.findViewById(R.id.saveEditedContactButton)

        loadContactDetails(firstNameEditText, lastNameEditText, phoneEditText, photoImageView)

        photoImageView.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, PICK_IMAGE)
        }

        saveContactButton.setOnClickListener {
            val first = firstNameEditText.text.toString()
            val last = lastNameEditText.text.toString()
            val phone = phoneEditText.text.toString()
            if(phone.isEmpty()) {
                Toast.makeText(requireContext(), "Enter a Phone Number", Toast.LENGTH_SHORT).show()
            }
            if(!isNumeric(phone)) {
                Toast.makeText(requireContext(), "Enter a Correct Phone Number", Toast.LENGTH_SHORT).show()
            }
            else {
                updateContact(first, last, phone, imageUri)
                Toast.makeText(requireContext(), "Contact updated", Toast.LENGTH_SHORT).show()
                (activity as? ContactsMainActivity)?.apply {
                    replaceFragment(ContactMainFragment())
                    setState(SharedConstants.PageState.CONTACTS)
                }
            }
        }

        setupTTSForFields()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data?.data
            view?.findViewById<ImageView>(R.id.editPhotoImageView)?.setImageURI(imageUri)
        }
    }

    private fun loadContactDetails(firstNameEditText: EditText, lastNameEditText: EditText, phoneEditText: EditText, photoImageView: ImageView) {
        firstNameEditText.setText(contactInfo.firstName)
        lastNameEditText.setText(contactInfo.lastName)
        phoneEditText.setText(contactInfo.number)
        if(contactInfo.image != null) photoImageView.setImageBitmap(contactInfo.image)

    }

    private fun updateContact(firstName: String, lastName: String, phoneNumber: String, photoUri: Uri?) {
        val ops = ArrayList<ContentProviderOperation>().apply {
            add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection("${ContactsContract.Data.CONTACT_ID}=? AND ${ContactsContract.Data.MIMETYPE}=?", arrayOf(contactInfo.id, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE))
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, firstName)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, lastName)
                .build())

            add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection("${ContactsContract.Data.CONTACT_ID}=? AND ${ContactsContract.Data.MIMETYPE}=?", arrayOf(contactInfo.id, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE))
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)
                .build())
        }

        // Check if a photo entry exists and insert or update accordingly
        val photoIdCursor = requireActivity().contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            arrayOf(ContactsContract.Data._ID),
            "${ContactsContract.Data.CONTACT_ID} = ? AND ${ContactsContract.Data.MIMETYPE} = ?",
            arrayOf(contactInfo.id.toString(), ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE),
            null
        )

        val photoExists = photoIdCursor?.moveToFirst() ?: false
        photoIdCursor?.close()

        photoUri?.let {
            val photoStream = requireActivity().contentResolver.openInputStream(photoUri)
            val photoBytes = photoStream?.readBytes()

            if (photoExists) {
                // If photo exists, update
                ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection("${ContactsContract.Data.CONTACT_ID} = ? AND ${ContactsContract.Data.MIMETYPE} = ?", arrayOf(contactInfo.id.toString(), ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE))
                    .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, photoBytes)
                    .build())
            } else {
                // If no photo exists, insert
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValue(ContactsContract.Data.RAW_CONTACT_ID, contactInfo.id)
                    .withValue(ContactsContract.Data.IS_SUPER_PRIMARY, 1)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, photoBytes)
                    .build())
            }

            photoStream?.close()
        }

        try {
            requireActivity().contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
            // Notify the user of success
            Toast.makeText(requireContext(), "Contact updated", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // Handle any errors
            Toast.makeText(requireContext(), "Failed to update contact", Toast.LENGTH_SHORT).show()
        }
    }


    private fun isNumeric(input: String): Boolean {
        return input.matches(Regex("-?\\d+(\\.\\d+)?"))
    }

    private fun setupTTSForFields() {
        setupTTS(firstNameEditText, firstNameEditText.text)
        setupTTS(lastNameEditText, lastNameEditText.text)

        val formattedNumber = PhoneNumberUtils.formatNumber(phoneEditText.text.toString(), Locale.CANADA.country)
        setupTTS(phoneEditText, formattedNumber ?: phoneEditText.text)

        setupTTS(saveContactButton, saveContactButton.text)
    }

    companion object {
        private const val PICK_IMAGE = 100
    }
}
