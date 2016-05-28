package com.gck.simplecontacts;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * Created by Chandrakanth on 26-05-2016.
 */
public class ContactUtils {

    private final static String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            /*ContactsContract.SearchSnippets.DEFERRED_SNIPPETING_KEY*/
    };

    public static Cursor getAllContactsCursor(Context context) {

        return context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, null, null, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
    }

    public static Cursor getSearchCursor(Context context, String search) {

        Uri searchUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode(search));
        String SELECTION = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " like ?";
        String[] selectionArgs = {'%' + search + '%'};
        return context.getContentResolver().query(searchUri, PROJECTION,SELECTION , selectionArgs, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
    }

}
