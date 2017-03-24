package com.vigneshtraining.customlistex;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.vigneshtraining.customlistex.adapter.SelectUserAdapter;
import com.vigneshtraining.customlistex.model.SelectUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    public static final int RequestPermissionCode = 1;
    public static final int RequestCallPermissionCode = 2;
    private ListView listView;

    private LoadContact loadContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.contacts_list);

        listView.setOnItemLongClickListener(this);
        //
        EnableRuntimePermission();

        loadContacts();
        registerForContextMenu(listView);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle(R.string.contextMenu);

        getMenuInflater().inflate(R.menu.custom_menu, menu);


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {


        Toast.makeText(MainActivity.this,":::Selected::"+item.getTitle(),Toast.LENGTH_LONG).show();
        return super.onContextItemSelected(item);
    }



    private void loadContacts() {
        loadContact = new LoadContact();
        loadContact.setPhones(getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null));
        loadContact.setResolver(this.getContentResolver());

        loadContact.setSelectUsers(new ArrayList<SelectUser>());
        loadContact.setListView(listView);
        loadContact.setAdapter(new SelectUserAdapter(loadContact.getSelectUsers(), this));
        loadContact.execute();
    }

    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {

            Toast.makeText(MainActivity.this, "CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {

        }else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.CALL_PHONE}, RequestCallPermissionCode);
        }

    }



    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(MainActivity.this,loadContact.getSelectUsers().get(position).getName() , Toast.LENGTH_LONG).show();
        return false;
    }
}
