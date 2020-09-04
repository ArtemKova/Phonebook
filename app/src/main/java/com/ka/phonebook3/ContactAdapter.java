package com.ka.phonebook3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    ArrayList<PhoneD>contacts;

    public ContactAdapter (ArrayList<PhoneD> contacts){
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.number_contact.setText(contacts.get(position).getNumber_contact());
        holder.name_contact.setText(contacts.get(position).getName_contact());
        holder.img.setImageResource(contacts.get(position).getImg());

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name_contact;
        TextView number_contact;
        public ContactViewHolder (View view){
            super(view);
            img = (ImageView) view.findViewById(R.id.photo);
            name_contact = (TextView) view.findViewById(R.id.name_contacts);
            number_contact = (TextView) view.findViewById(R.id.number_contacts);
        }
    }
}
