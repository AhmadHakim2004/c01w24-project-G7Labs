package com.example.androidassist.apps.contacts
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidassist.R
import com.example.androidassist.databinding.ContactsMainBinding
import android.provider.ContactsContract
import androidx.recyclerview.widget.RecyclerView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [ContactMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactMainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: ContactsMainBinding
    private lateinit var contactList: RecyclerView
    private lateinit var contacts: MutableList<ContactDTO>
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.contacts_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactList = requireView().findViewById(R.id.contact_list)
        contacts = loadContacts()

        contactAdapter = ContactAdapter(contacts, activity as ContactsMainActivity)
        contactList.adapter = contactAdapter
    }
    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = ContactMainFragment()

    }
    private fun loadContacts() : MutableList<ContactDTO> {
        val contactList: MutableList<ContactDTO> = ArrayList()
        val contacts = (activity?.contentResolver)?.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null
        )
        contacts?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            //val photoUriIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)

            while (cursor.moveToNext()) {
                val name = cursor.getString(nameIndex)
                val number = cursor.getString(numberIndex)
                //val photoUri = if (photoUriIndex != -1) cursor.getString(photoUriIndex) else null
                //val image = photoUri?.let { uri -> loadContactPhoto(Uri.parse(uri)) }

                val contact = ContactDTO(name)
                contactList.add(contact)
            }
        }

        //binding.contactList.adapter = ContactAdapter(contactList, activity as ContactsMainActivity)
        return contactList
    }
}