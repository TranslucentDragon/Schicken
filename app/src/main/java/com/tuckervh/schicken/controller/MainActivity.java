package com.tuckervh.schicken.controller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import com.tuckervh.schicken.R;
import com.tuckervh.schicken.model.Conversation;
import com.tuckervh.schicken.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context = this;

    final private int REQUEST_PERMISSIONS_READ_CONTACT = 123;

    private List<Conversation> conversationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(context, ConversationActivity.class);
                        intent.putExtra("name", conversationList.get(position).getName());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        Toast.makeText(context, "long", Toast.LENGTH_SHORT).show();
                    }
                })
        );

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        conversationList.add(new Conversation("Initial", "I", new Message("Initial List")));
        // specify an adapter (see also next example)
        mAdapter = new ConversationAdapter(conversationList);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mRecyclerView.setAdapter(mAdapter);

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(getApplicationContext(), "Necessary Permission", Toast.LENGTH_LONG).show();
            } else {
                conversationList.add(new Conversation("Initial2", "I2", new Message("Initial List2")));
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                        REQUEST_PERMISSIONS_READ_CONTACT);
            }
        } else {
            conversationList.add(new Conversation("Permission Worked", "PW", new Message("The permissions worked")));
        }

    }

    private void prepareConversationList() {


        conversationList.add(new Conversation("Will Crawford", "WC", new Message("Test")));
        conversationList.add(new Conversation("Tucker VH", "TV", new Message("Test123")));
        Cursor cur = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (null != cur) {
            cur.moveToFirst();
            conversationList.add(new Conversation(cur.getColumnName(0), "ex", new Message("tucker test")));
            String[] columnNames = cur.getColumnNames();

            Conversation filler = new Conversation("Filler Convo", "FC",
                    new Message("Words that are really long so they take up more than one line!!!"));

            for (int i = 0; i < columnNames.length; i++) {
                conversationList.add(new Conversation(columnNames[i], "", new Message(columnNames[i] + "Message")));
            }
        } else {
            conversationList.add(new Conversation("null", "!n", new Message("cur was null")));
        }




       /* conversationList.add(new Conversation(
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
                Integer.toString(CommonDataKinds.Phone.TYPE_HOME),
                new Message("Etc")));*/

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS_READ_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    prepareConversationList();

                } else {

                    conversationList.add(new Conversation("No Permissions", "NP", new Message("Permissions didn'tt work")));
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


}
