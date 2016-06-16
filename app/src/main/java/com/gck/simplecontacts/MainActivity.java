package com.gck.simplecontacts;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (BuildConfig.DEBUG) {
            LogUtils.enableStrictMode();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        TabLayout.Tab contacts = tabLayout.newTab().setText("Contacts");
        TabLayout.Tab calls = tabLayout.newTab().setText("Calls");
        tabLayout.addTab(contacts);
        tabLayout.addTab(calls);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setTabTextColors(Color.WHITE, Color.GREEN);



        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
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
