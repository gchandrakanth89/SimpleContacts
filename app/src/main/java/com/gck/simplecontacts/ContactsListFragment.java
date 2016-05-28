package com.gck.simplecontacts;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;


public class ContactsListFragment extends Fragment {


    private static final String TAG = "ContactsListFragment";
    private ListView listView;
    private ContactsAdapter contactsAdapter;
    private Cursor searchCursor;
    private Cursor allContactsCursor;

    public ContactsListFragment() {
    }

    public static ContactsListFragment newInstance() {
        ContactsListFragment fragment = new ContactsListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);

        listView = (ListView) view.findViewById(R.id.list_view);
        allContactsCursor = Utils.getAllContactsCursor(getActivity());
        contactsAdapter = new ContactsAdapter(getActivity(), allContactsCursor);
        listView.setAdapter(contactsAdapter);

        EditText editText = (EditText) view.findViewById(R.id.edit_text);
        editText.addTextChangedListener(textWatcher);

        return view;
    }

    @Override
    public void onDetach() {
        allContactsCursor.close();
        if(searchCursor!=null){
            Log.d(TAG,"Closing cursor");
            searchCursor.close();
        }
        super.onDetach();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s)) {
                searchCursor = Utils.getAllContactsCursor(getActivity());
            } else {
                searchCursor = Utils.getSearchCursor(getActivity(), s.toString());
            }
            contactsAdapter.changeCursor(searchCursor, s.toString());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


}


