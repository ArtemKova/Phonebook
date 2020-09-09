package com.ka.phonebook3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.acl.Group;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;

    RecyclerView recyclerView;
    Cursor cursor;
    String name, contactNumber;
    int img;
    Bitmap selectedImage;
   String im;
    ArrayList<PhoneD> contacts = new ArrayList<PhoneD>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkPermission()) {
            Log.e("permission", "Permission already granted.");
        } else {
            //If the app doesn’t have the CALL_PHONE permission, request it//
            requestPermission();
        }

        // Присваиваем переменной наш Recyclerview
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        //Теперь нашему компоненту RecyclerView необходимо указать, отображать список вертикально или горизонтально
        //Для этого устанавливаем с нужным атрибутом LayoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        ArrayList<PhoneD> contacts = new ArrayList<PhoneD>();
        getContacts();
        


//Создаем объект адаптера и передаем ему список данных
        ContactAdapter contactAdapter = new ContactAdapter(contacts);
        //передаем в RecyclerView наш объект адаптера с данными
//        Collections.sort((List) contactAdapter, new Comparator() {
//                    @Override
//                    public int compare(Object o1, Object o2) {
//                        return 0;
//            }
//            @Override
//            public int compare(final Group object1, final Group object2) {
//                String obj1 =object1.getName();
//                String obj2 =object2.getName();
//                return obj1.compareTo(obj2);
//            }
//        });
        recyclerView.setAdapter(contactAdapter);

    }
    @SuppressLint("ResourceType")
    private void getContacts(){
int i = 0;
//        Bitmap bp = BitmapFactory.decodeResource(context.getResources(),
//
//                R.drawable.contact_default_picture);
//




//        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(address));
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
//                i++;
                name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                contactNumber =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cursor,);
//                return BitmapFactory.decodeStream(input);
                img =
                        cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_ID));
                im =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_THUMBNAIL_URI));
                Uri catURI;
                if (img==0){
                    img = (R.drawable.net_foto);
                    catURI = Uri.parse((""+img));
                }else{

                catURI = Uri.parse(""+im);}
                System.out.println(img +"   "+im);
            try {
                InputStream imageStream = getContentResolver().openInputStream(catURI);
                 selectedImage = BitmapFactory.decodeStream(imageStream);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }



                contacts.add(new PhoneD(selectedImage,name+"" , contactNumber+""));
                    selectedImage = null;
                    }

                }
        if (cursor != null) {
            cursor.close();
        }

        Collections.sort(contacts, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }

//            @Override
//            public int compare(final Group object1, final Group object2) {
//                String obj1 =object1.getName();
//                String obj2 =object2.getName();
//                return obj1.compareTo(obj2);
//            }
        });
    }


    public static Bitmap getContactPhoto(Context context, long  contactId) {
        ContentResolver cr=context.getContentResolver();
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
        return BitmapFactory.decodeStream(input);
    }
    public boolean checkPermission() {
//            int CallPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(),
//                    Manifest.permission.CALL_PHONE);
        int ContactPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_CONTACTS);
        return  ContactPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {
//                            Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_CONTACTS
                }, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case PERMISSION_REQUEST_CODE:
//                    callButton = findViewById(R.id.button_call);

                if (grantResults.length > 0) {
                    boolean callPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean readContactsPermission =
                            grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if ( readContactsPermission) {
                        Toast.makeText(MainActivity.this,
                                "Permission accepted", Toast.LENGTH_LONG).show();
                        //If the permission is denied….//
                    } else {
                        Toast.makeText(MainActivity.this,
                                "Permission denied", Toast.LENGTH_LONG).show();
                        // disable the call button.//
//                            callButton.setEnabled(false);
//                        contactsButton.setEnabled(false);
                    }
                    break;
                }
        }
    }

}