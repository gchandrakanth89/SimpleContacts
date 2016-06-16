package com.gck.simplecontacts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class ContactDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("id");
        String name = extras.getString("name");

        if (savedInstanceState == null) {
            ContactDetailsFragment contactDetailsFragment = ContactDetailsFragment.newInstance(id, name);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frag_container, contactDetailsFragment);
            fragmentTransaction.commit();
        }
    }

    public static void startActivity(Context context, int contactId, String name) {
        Intent intent = new Intent(context, ContactDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", contactId);
        bundle.putString("name", name);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
