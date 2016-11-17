package com.lebartodev.mercuryapp2.view;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mrengineer13.snackbar.SnackBar;
import com.google.gson.Gson;
import com.lebartodev.mercuryapp2.R;
import com.lebartodev.mercuryapp2.model.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment implements ContactListPage, LoaderManager.LoaderCallbacks<Cursor> {
    private final String LOG_TAG = "ContactsListActivity";
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView contactList;
    private ContactAdapter adapter;

    public ListFragment() {


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);
        contactList = (RecyclerView) getActivity().findViewById(R.id.contactList);
        Log.d(LOG_TAG, "onActivityCreated");
        mLayoutManager = new LinearLayoutManager(getActivity());
        contactList.setLayoutManager(mLayoutManager);
        contactList.setAdapter(adapter);
        if (adapter == null) {


            adapter = new ContactAdapter(getActivity());
            contactList = (RecyclerView) getActivity().findViewById(R.id.contactList);
            contactList.setLayoutManager(mLayoutManager);
            contactList.setAdapter(adapter);
            initFromDB();

        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        return view;
    }

    private void initFromDB() {
        Log.d(LOG_TAG, "Start update from DB...");
        List<ContactModel> contacts = ContactModel.listAll(ContactModel.class);
        adapter.setContacts(contacts);
        getLoaderManager().initLoader(0, null, this);
        Log.d(LOG_TAG, "Update from DB: Success");
    }

    @Override
    public void onLoadContact(ContactModel contactName) {

        adapter.addContact(contactName);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.d(LOG_TAG, "Cursor onCreateLoader");
        return new CursorLoader(getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor cursor) {
        Log.d(LOG_TAG, "Cursor onLoadFinished");
        new SnackBar.Builder(getActivity())
                .withMessage("Start updating...")
                .show();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                updateList(cursor, getActivity());
            }
        });


    }

    private void updateList(Cursor cursor, Context context) {
        final List<ContactModel> contacts = new ArrayList<>();
        Log.d(LOG_TAG, "cursor count:" + cursor.getCount());
        Log.d(LOG_TAG, "bd count: " + ContactModel.count(ContactModel.class));
        while (cursor.moveToNext()) {

            String title = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            final String contactid = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            List<String> phonesArray = new ArrayList<>();
            List<String> emailsArray = new ArrayList<>();
            Log.d(LOG_TAG, "Start for " + title);
            Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactid, null, null);
            while (phones.moveToNext()) {
                // This would allow you get several email addresses
                String phonesString = phones.getString(
                        phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
                phonesArray.add(phonesString);
                Log.d(LOG_TAG, "add  " + phonesString);
            }
            phones.close();
            Cursor emails = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactid, null, null);

            while (emails.moveToNext()) {
                // This would allow you get several email addresses
                String emailAddress = emails.getString(
                        emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                emailsArray.add(emailAddress);
                Log.d(LOG_TAG, "add  " + emailAddress);
            }

            emails.close();


            if (emailsArray.size() > 0 || phonesArray.size() > 0) {
                Gson gson = new Gson();
                String emailsStr = gson.toJson(emailsArray);
                String phonesStr = gson.toJson(phonesArray);


                ContactModel contactModel = new ContactModel(title, contactid, emailsStr, phonesStr);
                contacts.add(contactModel);


                if (ContactModel.count(ContactModel.class) > 0) {
                    List<ContactModel> items = ContactModel.find(ContactModel.class, "contactid=?", contactModel.getContactid());
                    if (items.size() == 0) {
                        Log.d(LOG_TAG, "cantfind:" + contactModel.getName());
                        contactModel.save();

                    } else {
                        Log.d(LOG_TAG, "find:" + contactModel.getName());

                        if (!equalsContact(items.get(0), contactModel)) {

                            ContactModel updateContact = items.get(0);
                            updateContact.setName(contactModel.getName());
                            updateContact.setEmail(contactModel.getEmail());
                            updateContact.setPhone(contactModel.getPhone());
                            contactModel.update();

                        }
                    }

                } else
                    contactModel.save();
            }


        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new SnackBar.Builder(getActivity())
                        .withMessage("Update complete!")
                        .show();
                adapter.setContacts(contacts);
            }
        });
    }
    private boolean equalsContact(ContactModel model1, ContactModel model2) {
        return model1.getName() != null && model2.getName() != null && model1.getName().equals(model2.getName()) &&
                model1.getContactid().equals(model2.getContactid());


    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(LOG_TAG, "Cursor onLoaderReset");
    }
}
