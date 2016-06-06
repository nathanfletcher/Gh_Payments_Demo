package com.ketecode.ghpaymentsdemo;

import android.util.Log;

import com.mpowerpayments.mpower.MPowerCheckoutInvoice;
import com.mpowerpayments.mpower.MPowerCheckoutStore;
import com.mpowerpayments.mpower.MPowerSetup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KobbyFletcher on 6/6/16.
 */
public class MPower {

    MPowerSetup setup = new MPowerSetup();
    MPowerCheckoutStore store = new MPowerCheckoutStore();
    //This invoice will take you to the Mpower website
    MPowerCheckoutInvoice invoiceCheckout = new MPowerCheckoutInvoice(setup, store);
    List<MPowerCheckoutInvoice> cart = new ArrayList<>();
    private double totalAmount;
    double cartTotal;

    public MPower(){}

    public void mPowerSetupInitTest(String testMasterKey, String testPrivateKey, String testPublicKey, String testToken){
        setup.setMasterKey(testMasterKey);
        setup.setPrivateKey(testPrivateKey);
        setup.setPublicKey(testPublicKey);
        setup.setToken(testToken);
        setup.setMode("test");
    }

    public void mPowerSetupInitLive(String liveMasterKey, String livePrivateKey, String livePublicKey, String liveToken){
        setup.setMasterKey(liveMasterKey);
        setup.setPrivateKey(livePrivateKey);
        setup.setPublicKey(livePublicKey);
        setup.setToken(liveToken);
        setup.setMode("live");
    }

    public void mPowerStoreInit(String storeName, String storeMotto, String storePhoneNumber, String storePostalAddress, String storeWebsiteUrl){
        store.setName(storeName);
        store.setTagline(storeMotto);
        store.setPhoneNumber(storePhoneNumber);
        store.setPostalAddress(storePostalAddress);
        store.setWebsiteUrl(storeWebsiteUrl);
    }

    //This method is to MPower's lack of calculations when adding anything to their cart and returns the total of the items in the cart
    public void addItem(String itemName, int itemQuantity, double itemUnitPrice, String itemDescription){
        totalAmount += (itemQuantity * itemUnitPrice);
        invoiceCheckout.addItem(itemName, itemQuantity, itemUnitPrice, totalAmount, itemDescription);
        invoiceCheckout.setTotalAmount(totalAmount);

        //CREATING INVOICE
        // The code below depicts how to create the checkout invoice on our servers
        // and redirect to the checkout page.
        if (invoiceCheckout.create()) {
            Log.d("Invoice Status ", invoiceCheckout.getStatus());
            Log.d("Invoice Response ",invoiceCheckout.getResponseText());
            Log.d("Invoice URL", invoiceCheckout.getInvoiceUrl());
        } else {
            Log.d("Invoice Response ", invoiceCheckout.getResponseText());
            Log.d("Invoice Status ", invoiceCheckout.getStatus());
        }
    }

    /**
     * This funciton would confirm the Checkout programatically.
     * I'm using the variable 'invoiceCheckout' for this method as an example
     * @return true if the payment was successful and vice versa
     * */
    public boolean confirmCheckout(){
        // Invoice token is returned as a URL query string "token"
        // You are free to also explicitly check the status of an invoice and persist it in your database or part of your logs
        String invoiceToken = invoiceCheckout.getToken();


        if (invoiceCheckout.confirm(invoiceToken)) {

            // Retrieving Invoice Status
            // Status can be either completed, pending, canceled, fail
            Log.d("Status",invoiceCheckout.getStatus());
            Log.d("Response Text",invoiceCheckout.getResponseText());

            // You can retrieve the Name, Email & Phone Number
            // of the customer using the callbacks below
            Log.d("Customer Name:",  invoiceCheckout.getCustomerInfo("name").toString());
            Log.d("Customer Email:" , (String) invoiceCheckout.getCustomerInfo("email"));
            Log.d("Customer Phone:" , (String) invoiceCheckout.getCustomerInfo("phone"));

            // Return the URL to the Invoice Receipt PDF file for download
            Log.d("Receipt URL:",invoiceCheckout.getReceiptUrl());

            // Retrieving any custom data you have added to the invoice
            // Please remember to use the right keys you used to set them earlier
            Log.d("First Name:" , (String) invoiceCheckout.getCustomData("Firstname"));
            Log.d("Cart ID:" , (String) invoiceCheckout.getCustomData("CartId"));
            Log.d("Plan :" , (String) invoiceCheckout.getCustomData("Plan"));

            // You can also callback the total amount set earlier
            Log.d("Total Amount:", String.valueOf(invoiceCheckout.getTotalAmount()));

            return true;

        }else{
            Log.d("Status",invoiceCheckout.getStatus());
            Log.d("Response Text",invoiceCheckout.getResponseText());
            Log.d("Response Code",invoiceCheckout.getResponseCode());
        }
        return false;
    }

}
