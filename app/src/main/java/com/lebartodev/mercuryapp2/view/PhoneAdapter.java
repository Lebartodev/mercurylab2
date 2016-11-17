package com.lebartodev.mercuryapp2.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lebartodev.mercuryapp2.R;
import com.lebartodev.mercuryapp2.model.ContactModel;
import com.lebartodev.mercuryapp2.model.PhoneModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Александр on 15.11.2016.
 */

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneVH> {
    private Context context;
    private List<PhoneModel> phones;
    private Gson gson;
//private Cursor cursor;


    public PhoneAdapter(Context context) {
        this.phones = new ArrayList<>();
        this.context = context;
        gson = new Gson();

    }

    public void addPhone(PhoneModel phone) {
        phones.add(phone);

        notifyDataSetChanged();
    }


    @Override
    public PhoneVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.i_contact_number, parent, false);
        return new PhoneVH(v);
    }


    @Override
    public void onBindViewHolder(final PhoneVH holder, int position) {
        final String phone = phones.get(position).getContent();
        holder.title.setText(phone);
        if (phones.get(position).getType() == 5) {
            holder.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_phone));
        } else {
            holder.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_mail));
        }
        Log.d("Adapter", phone);
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phones.get(holder.getAdapterPosition()).getType() == 5) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, phone);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Some subject");
                    intent.putExtra(Intent.EXTRA_TEXT, "HI!");

                    context.startActivity(Intent.createChooser(intent, "Send Email"));
                }
            }
        });


    }


    /*@Override
     public int getItemCount() {
         return contacts.size();
     }*/
    @Override
    public int getItemCount() {
        return phones.size();
    }

    class PhoneVH extends RecyclerView.ViewHolder {
        private TextView title;

        private ImageView icon;


        public PhoneVH(View itemView) {


            super(itemView);

            title = (TextView) itemView.findViewById(R.id.phoneText);

            icon = (ImageView) itemView.findViewById(R.id.imagePhone);


        }
    }
}
