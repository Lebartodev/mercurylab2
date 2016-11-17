package com.lebartodev.mercuryapp2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.lebartodev.mercuryapp2.model.ContactModel;
import com.lebartodev.mercuryapp2.view.ContactAdapter;
import com.lebartodev.mercuryapp2.view.ContactListPage;
import com.lebartodev.mercuryapp2.view.ListFragment;

import java.util.ArrayList;
import java.util.List;

public class ContactsListActivity extends AppCompatActivity  {


    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_contacts_list);

        fragmentManager = getFragmentManager();
        Fragment listFragment = fragmentManager.findFragmentByTag(Consts.FRAGMENT_LIST);
        if (listFragment == null) {
            listFragment = new ListFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, listFragment, Consts.FRAGMENT_LIST)
                    .commit();
        }




    }






}
