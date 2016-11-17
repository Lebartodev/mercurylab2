package com.lebartodev.mercuryapp2.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.lebartodev.mercuryapp2.R;
import com.lebartodev.mercuryapp2.model.ContactModel;




import java.util.ArrayList;
import java.util.List;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactVH> {
    private Context context;
    private List<ContactModel> contacts;
    //private Cursor cursor;


    public void setContacts(List<ContactModel> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public ContactAdapter(Context context) {
        this.contacts = new ArrayList<>();
        this.context = context;

    }

    public void addContact(ContactModel contactModel) {
        contacts.add(contactModel);
    }

    @Override
    public ContactVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.i_contact, parent, false);
        return new ContactVH(v);
    }


    @Override
    public void onBindViewHolder(final ContactVH holder, int position) {

        String title = contacts.get(position).getName();
        if (title == null)
            title = "<No Name>";
        final String contactid = contacts.get(position).getContactid();
        final String email = contacts.get(position).getEmail();
        final String phone = contacts.get(position).getPhone();
        final String finalTitle = title;
        holder.title.setText(title);


        ColorGenerator generator = ColorGenerator.MATERIAL;
        final int color = generator.getColor(title);
        String letter = title.substring(0, 1);
        TextDrawable drawable = TextDrawable.builder()
                .buildRect(letter.toUpperCase(), color);
        holder.icon.setImageDrawable(drawable);

        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ContactActivity.class);
                intent.putExtra("contactid", contactid);
                intent.putExtra("name", finalTitle);
                intent.putExtra("phone", phone);
                intent.putExtra("color", color);
                intent.putExtra("email", email);
                context.startActivity(intent);

                ((Activity)context).overridePendingTransition(R.anim.slide_from_right,
                       R.anim.slide_to_left);
            }
        });
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactVH extends RecyclerView.ViewHolder {
        private TextView title;

        private ImageView icon;
        private View mainView;
        private RelativeLayout transitionsContainer;


        public ContactVH(View itemView) {


            super(itemView);
            transitionsContainer = (RelativeLayout) itemView.findViewById(R.id.transitionsContainer);
            this.mainView = itemView;


            title = (TextView) itemView.findViewById(R.id.contactName);

            icon = (ImageView) itemView.findViewById(R.id.contactImage);


        }
    }
}
