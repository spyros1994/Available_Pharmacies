package com.example.spyros.farmakeia;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class All_Pharmacies extends AppCompatActivity {
    private RecyclerView mPharmacyList;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__pharmacies);
        //Ορίσμος του Database θα το χρειαστούμε στην συνεχεια για να παρουμε συγκεκριμενα δεδομενα απο την βαση δεδομένων
        mDatabase = FirebaseDatabase.getInstance().getReference().child("All_Patras_Pharmacies");
        mDatabase.keepSynced(true);

        mPharmacyList = (RecyclerView) findViewById(R.id.AllPharmaciesRV);
        mPharmacyList.setHasFixedSize(true);
        mPharmacyList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        //Ορίζουμε έναν Adapter
        FirebaseRecyclerAdapter<AllPharmaciesContra, PharmaciesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<AllPharmaciesContra, PharmaciesViewHolder>
                (AllPharmaciesContra.class, R.layout.row_data_allparmacies, PharmaciesViewHolder.class, mDatabase) {
            @Override
            public void populateViewHolder(PharmaciesViewHolder viewHolder, final AllPharmaciesContra model, final int position) {
                viewHolder.setmAuthorName(model.getmAuthorName());
                viewHolder.setmPharmacyAddress(model.getmPharmacyAddress());
                viewHolder.pharmacy_Directions.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String test = "geo:0,0?q=" + model.getmPharmacyAddress();
                        Uri gmmIntentUri = Uri.parse(test);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);

                    }
                });

                viewHolder.pharmacy_Call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phone = model.getmPharmacyPhone();
                        Intent call = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        startActivity(call);
                    }
                });


            }


        };
        mPharmacyList.setAdapter(firebaseRecyclerAdapter);
    }

    //Δημιουργια  View και είσαγωγη δεδομενων σε καθε στοιχειο της λίστας
    public static class PharmaciesViewHolder extends RecyclerView.ViewHolder {
        private Button pharmacy_Directions, pharmacy_Call;
        View mView;

        public PharmaciesViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            pharmacy_Directions = (Button) mView.findViewById(R.id.button2);
            pharmacy_Call = (Button) mView.findViewById(R.id.button1);
        }

        public void setmAuthorName(String mAuthorName) {
            TextView post_name = (TextView) mView.findViewById(R.id.Pharmacies_Name);
            post_name.setText(mAuthorName);

        }

        public void setmPharmacyAddress(String mPharmacyAddress) {
            TextView post_address = (TextView) mView.findViewById(R.id.Pharmacies_Address);
            post_address.setText(mPharmacyAddress);

        }


    }


}
