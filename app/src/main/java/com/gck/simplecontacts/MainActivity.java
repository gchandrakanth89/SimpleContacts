package com.gck.simplecontacts;

import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ListView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(BuildConfig.DEBUG){
            LogUtils.enableStrictMode();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState==null){
            ContactsListFragment contactsListFragment = ContactsListFragment.newInstance();
            android.app.FragmentManager fragmentManager=getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frag_container,contactsListFragment);
            fragmentTransaction.commit();
        }

    }

    private void listAllContacts() {

        final String[] PROJECTION = {
                Contacts._ID,
                Contacts.LOOKUP_KEY,
                Contacts.DISPLAY_NAME_PRIMARY
        };
        Cursor cursor = getContentResolver().query(Contacts.CONTENT_URI, PROJECTION, null, null, null);
        assert cursor != null;
        String[] columnNames = cursor.getColumnNames();
        for (int i = 0; i < columnNames.length; i++) {
            Log.d(TAG, i + " " + columnNames[i]);
        }
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String lookUpKey = cursor.getString(1);
            String name = cursor.getString(2);
            Log.d(TAG, id + "\t" + name + "\t" + lookUpKey);
        }
        cursor.close();
    }

    private void searchContacts(String search) {

        final String[] PROJECTION = {
                Contacts._ID,
                Contacts.LOOKUP_KEY,
                Contacts.DISPLAY_NAME_PRIMARY
        };


        Uri searchUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, search);

        Cursor cursor = getContentResolver().query(searchUri, PROJECTION, null, null, null);

        Log.d(TAG, "Chandu " + cursor.getCount());

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String lookUpKey = cursor.getString(1);
            String name = cursor.getString(2);
            Log.d(TAG, id + "\t" + name + "\t" + lookUpKey);
        }
        cursor.close();
    }

    private void getContactPhoneNumber(int id) {
        String[] PROJECTION = {ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

        String SELECTION = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?";
        String[] selectionArgs = {id + ""};

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, SELECTION, selectionArgs, null, null);
        Log.d(TAG, "Chandu " + cursor.getCount());
        while (cursor.moveToNext()) {
            int numId = cursor.getInt(0);
            String number = cursor.getString(1);
            String name = cursor.getString(2);

            Log.d(TAG, numId + " " + number + " " + name);

        }

        cursor.close();
    }

}
