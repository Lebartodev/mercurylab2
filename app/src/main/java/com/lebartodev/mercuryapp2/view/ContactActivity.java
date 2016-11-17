package com.lebartodev.mercuryapp2.view;

import android.os.Bundle;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lebartodev.mercuryapp2.R;
import com.lebartodev.mercuryapp2.model.ContactModel;
import com.lebartodev.mercuryapp2.model.PhoneModel;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {
    private String contactid;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView listPhones;
    private PhoneAdapter adapter;
    private CollapsingToolbarLayout toolbar_layout;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.a_contact);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listPhones = (RecyclerView) findViewById(R.id.listPhones);
        adapter = new PhoneAdapter(this);
        mLayoutManager = new LinearLayoutManager(this);
        listPhones.setLayoutManager(mLayoutManager);
        listPhones.setAdapter(adapter);

        initContact();


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_from_left,
                    R.anim.slide_to_right);
        }
        return super.onOptionsItemSelected(menuItem);
    }
    private void initContact() {
        String name = getIntent().getExtras().getString("name");
        String contactid = getIntent().getExtras().getString("contactid");
        String email = getIntent().getExtras().getString("email");
        String phone = getIntent().getExtras().getString("phone");
        int color = getIntent().getExtras().getInt("color");

        ContactModel contactModel = new ContactModel(name, contactid, email, phone);


        onContactLoad(contactModel, color);
    }

    public void onContactLoad(ContactModel model, int color) {

        toolbar_layout.setContentScrimColor(color);
        toolbar_layout.setStatusBarScrimColor(color);
        toolbar_layout.setBackgroundColor(color);

        getSupportActionBar().setTitle(model.getName());
        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        List<String> phoneList = new Gson().fromJson(model.getPhone(), listType);
        for (String phone : phoneList) {
            adapter.addPhone(new PhoneModel(phone, 5));
        }

        List<String> emailList = new Gson().fromJson(model.getEmail(), listType);
        for (String phone : emailList) {
            adapter.addPhone(new PhoneModel(phone, 7));
        }


    }
}
