package com.example.androidassist.apps.contacts

class ContactsBinarySearch {
    companion object {
        fun indexToInsert(contactsList: MutableList<ContactInfo>, contactInfo: ContactInfo): Int {
            if(contactsList.isEmpty()) return 0

            val index = contactsList.binarySearch {
                if(it.isFavourite && !contactInfo.isFavourite) return@binarySearch -1
                if(!it.isFavourite && contactInfo.isFavourite) return@binarySearch 1
                String.CASE_INSENSITIVE_ORDER.compare("${it.firstName} ${it.lastName}",
                    "${contactInfo.firstName} ${contactInfo.lastName}")
            }
            if(index < 0) return -index - 1

            return index
        }
    }
}