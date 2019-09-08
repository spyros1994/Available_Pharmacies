package com.example.spyros.farmakeia;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
    private List<String> mPharmacyNameList = new ArrayList<>();
    private ArrayList<String> mPharmacyAddressList = new ArrayList<>();
    private ArrayList<String> mPharmacyPhoneList = new ArrayList<>();
    private ArrayList<String> mPharmacyDetailsList = new ArrayList<>();

    private Activity mActivity;
    private int lastPosition = -1;

    // Constractor
    public DataAdapter(AvailablePharmacies activity, List<String> mPharmacyNameList, ArrayList<String> mPharmacyAddressList, ArrayList<String> mPharmacyPhoneList, ArrayList<String> mPharmacyDetailsList) {
        this.mActivity = activity;
        this.mPharmacyNameList = mPharmacyNameList;
        this.mPharmacyAddressList = mPharmacyAddressList;
        this.mPharmacyPhoneList = mPharmacyPhoneList;
        this.mPharmacyDetailsList = mPharmacyDetailsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView pharmacy_name, pharmacy_address, pharmacy_phone, pharmacy_details;
        private Button pharmacy_Directions, pharmacy_Call;


        public MyViewHolder(final View view) {
            super(view);
            pharmacy_name = (TextView) view.findViewById(R.id.Pharmacy_Name);
            pharmacy_address = (TextView) view.findViewById(R.id.Pharmacy_Address);
            pharmacy_details = (TextView) view.findViewById(R.id.Pharmacy_Details);
            pharmacy_Directions = (Button) view.findViewById(R.id.button2);
            pharmacy_Call = (Button) view.findViewById(R.id.button1);


        }


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_data, parent, false);

        return new MyViewHolder(itemView);
    }

    //Εισοδος τιμων στα πεδία που βλέπει ο χρήστης
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.pharmacy_name.setText(mPharmacyNameList.get(position));
        holder.pharmacy_address.setText(mPharmacyAddressList.get(position));
        holder.pharmacy_details.setText(mPharmacyDetailsList.get(position));
        holder.pharmacy_Directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String test = "geo:0,0?q=" + mPharmacyAddressList.get(position);
                Uri gmmIntentUri = Uri.parse(test);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                mActivity.startActivity(mapIntent);
            }
        });
        holder.pharmacy_Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "+6986862225";
                Intent call = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                mActivity.startActivity(call);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPharmacyNameList.size();
    }
}
