package com.example.spyros.farmakeia;

//Κατασκευαστης για την κλάση All_Pharmacies
public class AllPharmaciesContra {
    private String mAuthorName;
    private String mPharmacyAddress;
    private String mPharmacyPhone;

    public AllPharmaciesContra(String mAuthorName, String mPharmacyAddress, String mPharmacyPhone) {
        this.mAuthorName = mAuthorName;
        this.mPharmacyAddress = mPharmacyAddress;
        this.mPharmacyPhone = mPharmacyPhone;


    }

    public String getmAuthorName() {
        return mAuthorName;
    }

    public void setmAuthorName(String mAuthorName) {
        this.mAuthorName = mAuthorName;
    }

    public String getmPharmacyAddress() {
        return mPharmacyAddress;
    }

    public void setmPharmacyAddress(String mPharmacyAddress) {
        this.mPharmacyAddress = mPharmacyAddress;
    }

    public String getmPharmacyPhone() {
        return mPharmacyPhone;
    }

    public void setmPharmacyPhone(String mPharmacyPhone) {
        this.mPharmacyPhone = mPharmacyPhone;
    }

    public AllPharmaciesContra() {

    }

}