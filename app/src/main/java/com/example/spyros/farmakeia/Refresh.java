package com.example.spyros.farmakeia;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

//Η κλαση αυτη χρησιμοποιήθηκε για την εισαγωγή δεδομένων στη βαση δεδομένων
public class Refresh extends AppCompatActivity {

    public ArrayList<String> mPharmacyNameList = new ArrayList<>();
    private ArrayList<String> mPharmacyPhoneList = new ArrayList<>();
    private ArrayList<String> mPharmacyAddressList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AddToFirebase().execute();

    }


    public class AddToFirebase extends AsyncTask<Void, Void, Void> {
        String desc;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        // Υλοποίηση Crawler παρόμοια με τον προηγούμενο
        @Override
        protected Void doInBackground(Void... params) {
            int pages = 12;
            int j = 0;

            try {
                for (j = 1; j <= pages; j++) {
                    // Connect to the web site
                    Document mPharmacyDocument = Jsoup.connect("https://www.xo.gr/dir-az/F/Farmakeia/Patra/?page=" + j).get();
                    // Using Elements to get the Meta data
                    Elements mElementDataSize = mPharmacyDocument.select("div.listingWhiteArea");
                    // Locate the content attribute
                    int mElementSize = mElementDataSize.size();
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    int i = 0;
                    for (i = 0; i < mElementSize; i++) {
                        Elements mElementPharmacy = mPharmacyDocument.select("span[itemprop=name]").eq(i);
                        String mAuthorName = mElementPharmacy.text();

                        Elements mElementPharmacyPhone = mPharmacyDocument.select("span[itemprop=telephone]").eq(i);
                        String mPharmacyPhone = mElementPharmacyPhone.text();

                        Elements mElementPharmacyName = mPharmacyDocument.select("span.addressProfile").eq(i);
                        String mPharmacyAddress = mElementPharmacyName.text();

                        mPharmacyNameList.add(mAuthorName);
                        mPharmacyPhoneList.add(mPharmacyPhone);
                        mPharmacyAddressList.add(mPharmacyAddress);


                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            addPharmacies();
        }
    }

    //Εισαγωγή δεδομένων στην Firebae
    private void addPharmacies() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        DatabaseReference usersRef = ref.child("All_Patras_Pharmacies");
        DatabaseReference usersRef2 = usersRef.child("Address");
        int i = 0;
        for (i = 0; i < mPharmacyNameList.size(); i++) {
            RefreshConstra name = new RefreshConstra(mPharmacyNameList.get(i), mPharmacyAddressList.get(i), mPharmacyPhoneList.get(i));

            String name1 = name.getmAuthorName();
            String address1 = name.getmPharmacyAddress();

            usersRef.child("").push().setValue(name);
            //usersRef2.child("Address").push().setValue(address1);
            //ref.push().setValue(name1);
            //usersRef.setValue(users);
        }


    }
}
