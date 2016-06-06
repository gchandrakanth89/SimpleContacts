package com.gck.simplecontacts;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


public class ContactDetailsFragment extends Fragment {
    private static final String KEY_CONTACT_ID = "param1";
    private static final String KEY_CONTACT_NAME = "param2";
    private static final String TAG = "ContactDetailsFragment";

    private int contactId;
    private String contactName;
    private ListView listView;
    private NumbersAdapter numbersAdapter;


    public ContactDetailsFragment() {
    }

    public static ContactDetailsFragment newInstance(int contactId, String name) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_CONTACT_ID, contactId);
        args.putString(KEY_CONTACT_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contactId = getArguments().getInt(KEY_CONTACT_ID);
            contactName = getArguments().getString(KEY_CONTACT_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Contact id " + contactId);
        View view = inflater.inflate(R.layout.fragment_contact_details, container, false);

        TextView nameTextView = (TextView) view.findViewById(R.id.name_tv);
        nameTextView.setText(contactName);
        listView = (ListView) view.findViewById(R.id.numbers_list_view);
        numbersAdapter = new NumbersAdapter(getActivity(), ContactUtils.getContactPhoneNumber(getActivity(), contactId));
        listView.setAdapter(numbersAdapter);
        return view;
    }

}
