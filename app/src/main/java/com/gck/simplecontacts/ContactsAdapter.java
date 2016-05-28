package com.gck.simplecontacts;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Pervacio on 26-05-2016.
 */
public class ContactsAdapter extends CursorAdapter {

    private static final String TAG = "ContactsAdapter";
    private int contactNameIndex = -1;
    private String spanString;

    public ContactsAdapter(Context context, Cursor cursor) {
        super(context, cursor, true);
        contactNameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.list_item, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String contactName = cursor.getString(contactNameIndex);
        TextView nameTv = (TextView) view.findViewById(R.id.name);
        //This code is to see where the content is matched
        //        if(cursor.getColumnCount()>3){
        //            String snippet = cursor.getString(3);
        //            Log.d(TAG,snippet);
        //        }

        if (!TextUtils.isEmpty(spanString)) {

            Spannable spannable = new SpannableString(contactName);
            int i = contactName.toLowerCase().indexOf(spanString);
            if (i >= 0) {
                spannable.setSpan(new BackgroundColorSpan(Color.GREEN), i, i + spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            nameTv.setText(spannable);

        } else {
            nameTv.setText(contactName, TextView.BufferType.SPANNABLE);

        }
        nameTv.setText(contactName);
    }

    public void changeCursor(Cursor cursor, String spanString) {
        this.spanString = spanString;
        super.changeCursor(cursor);
    }
}
