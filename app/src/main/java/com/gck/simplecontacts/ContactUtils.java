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
        return context.getContentResolver().query(searchUri, PROJECTION, SELECTION, selectionArgs, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
    }

    public static String[] getContactPhoneNumber(Context context, int id) {
        String[] PROJECTION = {ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

        String SELECTION = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?";
        String[] selectionArgs = {id + ""};

        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, SELECTION, selectionArgs, null, null);

        String[] numbers = new String[cursor.getCount()];

        int i = 0;
        while (cursor.moveToNext()) {
            int numId = cursor.getInt(0);
            numbers[i] = cursor.getString(1);
            String name = cursor.getString(2);
            i++;
        }

        cursor.close();

        return numbers;
    }

}
