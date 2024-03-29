package com.example.androidassist.apps.contacts
import android.app.Activity.RESULT_OK
import android.content.ContentUris
import android.content.ContentValues
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
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.utilities.LayoutUtils

private const val PICK_IMAGE = 100

class ContactsAddContactFragment : Fragment() {
    private lateinit var choosePhotoTextView: TextView
    private lateinit var photoImageView: ImageView
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var saveContactButton: Button

    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contacts_add_contact_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        choosePhotoTextView = view.findViewById(R.id.choose_photo_text)
        firstNameEditText = view.findViewById(R.id.firstNameEditText)
        lastNameEditText = view.findViewById(R.id.lastNameEditText)
        phoneEditText = view.findViewById(R.id.phoneEditText)
        photoImageView = view.findViewById(R.id.photoImageView)
        saveContactButton = view.findViewById(R.id.saveContactButton)

        photoImageView.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, PICK_IMAGE)
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
                val fullName = "$first $last"
                addContact(fullName, phone, imageUri)
                Toast.makeText(requireContext(), "Contact saved", Toast.LENGTH_SHORT).show()
                (activity as? ContactsMainActivity)?.apply {
                    replaceFragment(ContactMainFragment())
                    setState(SharedConstants.AppEnum.CONTACTS)
                }
            }
        }

        setupStyles()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data?.data
            view?.findViewById<ImageView>(R.id.photoImageView)?.setImageURI(imageUri)
        }
    }



    private fun addContact(name: String, phone: String, photoUri: Uri?) {
        val contentResolver = requireActivity().contentResolver

        // Insert a new raw contact
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

        // If a photo URI is provided, insert the contact's photo
        photoUri?.let {
            values.clear()
            values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
            values.put(ContactsContract.Data.IS_SUPER_PRIMARY, 1)
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)

            // Convert the photo URI to a byte array
            val photoStream = contentResolver.openInputStream(photoUri) // Ensure to handle nullability and exceptions here
            val photoByteArray = photoStream?.readBytes()

            values.put(ContactsContract.CommonDataKinds.Photo.PHOTO, photoByteArray)
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, values)

            photoStream?.close() // Make sure to close the stream
        }
    }

    fun isNumeric(input: String): Boolean {
        return input.matches(Regex("-?\\d+(\\.\\d+)?"))
    }

    private fun setupStyles() {
        LayoutUtils.setMargins(choosePhotoTextView, 0f, 0.05f, 0f, 0f)
        LayoutUtils.setTextSize(choosePhotoTextView, 0.01f)

        LayoutUtils.setDimensions(photoImageView, 0.2f, 0.2f)
        LayoutUtils.setMargins(photoImageView, 0f, 0.01f, 0f, 0f)

        LayoutUtils.setMargins(firstNameEditText, 0.1f, 0.05f, 0.1f, 0f)
        LayoutUtils.setTextSize(firstNameEditText, 0.01f)

        LayoutUtils.setMargins(lastNameEditText, 0.1f, 0.01f, 0.1f, 0f)
        LayoutUtils.setTextSize(lastNameEditText, 0.01f)

        LayoutUtils.setMargins(phoneEditText, 0.1f, 0.01f, 0.1f, 0f)
        LayoutUtils.setTextSize(phoneEditText, 0.01f)

        LayoutUtils.setMargins(saveContactButton, 0.1f, 0.05f, 0.1f, 0f)
        LayoutUtils.setTextSize(saveContactButton, 0.007f)
    }
}
