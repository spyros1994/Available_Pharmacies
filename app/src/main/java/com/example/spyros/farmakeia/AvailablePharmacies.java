package com.example.spyros.farmakeia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AvailablePharmacies extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    private String url = "https://www.vrisko.gr/efimeries-farmakeion/patra";
    public List<String> mPharmacyNameList = new ArrayList<>();
    private ArrayList<String> mPharmacyPhoneList = new ArrayList<>();
    private ArrayList<String> mPharmacyAddressList = new ArrayList<>();
    private ArrayList<String> mPharmacyDetailsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_pharmacies);

        new Description().execute();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_available_pharmacies);
    }
    //Έναρξη crawler
    public class Description extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Μήνυμα προς τον χρήστη όσο η doinbackground τρέχει
            mProgressDialog = new ProgressDialog(AvailablePharmacies.this);
            mProgressDialog.setTitle(getString(R.string.Loading_title));
            mProgressDialog.setMessage(getString(R.string.Loading_message));
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();

        }
@Override
        protected Void doInBackground(Void... params) {
            try {
                //Σύνδεση με το site
                Document mPharmacyDocument = Jsoup.connect(url).get();
                Document mPharmacyDocument2 = Jsoup.connect(url).get();

                // Elements για να παρουμε τα δεδομένα
                Elements mElementDataSize = mPharmacyDocument.select("div.ResultLeft");
                Elements mElementDataSize1 = mPharmacyDocument2.select("div.ResultRight");
                // Εντοπισμός συγκεκριμένων χαρακτηριστικών
                int mElementSize = mElementDataSize.size();

                for (int i = 0; i < mElementSize; i++) {
                    Elements mElementPharmacy = mPharmacyDocument.select("div[style*=padding-bottom:6px;]").select("a").eq(i);
                    String mAuthorName = mElementPharmacy.text();

                    Elements mElementPharmacyPhone = mPharmacyDocument.select("span.SpPhone").eq(i);
                    String mPharmacyPhone = mElementPharmacyPhone.text();

                    Elements mElementPharmacyName = mPharmacyDocument.select("div.ResultAddr").eq(i);
                    String mPharmacyAddress = mElementPharmacyName.text();

                    Elements mElementPharmacyHour = mPharmacyDocument2.select("div.DutyHours").eq(i);
                    String mPharmacyHour = mElementPharmacyHour.text();

                    Elements mElementPharmacyDay = mPharmacyDocument2.select("div.DutyDay").eq(i);
                    String mPharmacyDay = mElementPharmacyDay.text();

                    Elements mElementPharmacyTimes = mPharmacyDocument2.select("div.DutyTimes").eq(i);
                    String mPharmacyTimes = mElementPharmacyTimes.text();

                    Elements mElementPharmacyActive = mPharmacyDocument2.select("div.DutyActive").eq(i);
                    String mPharmacyActive = mElementPharmacyActive.text();

                    //Εισαγωγή των παραπάνω δεδομένων σε λίστες
                    mPharmacyNameList.add(mAuthorName);
                    mPharmacyPhoneList.add(mPharmacyPhone);
                    mPharmacyAddressList.add(mPharmacyAddress);
                    mPharmacyDetailsList.add(mPharmacyHour + ": " + mPharmacyDay + ", " + mPharmacyTimes + ", " + mPharmacyActive);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //Δημιουργία ανικειμέων και εμφάνισει στον χρήστη
            RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.act_recyclerview);

            DataAdapter mDataAdapter = new DataAdapter(AvailablePharmacies.this, mPharmacyNameList, mPharmacyAddressList, mPharmacyPhoneList, mPharmacyDetailsList);


            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mDataAdapter);


            mProgressDialog.dismiss();
        }
    }
}