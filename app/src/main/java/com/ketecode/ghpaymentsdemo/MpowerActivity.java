package com.ketecode.ghpaymentsdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.mpowerpayments.mpower.MPowerCheckoutInvoice;
import com.mpowerpayments.mpower.MPowerCheckoutStore;
import com.mpowerpayments.mpower.MPowerOnsiteInvoice;
import com.mpowerpayments.mpower.MPowerSetup;

import java.util.ArrayList;
import java.util.List;



public class MpowerActivity extends AppCompatActivity {

    private static final int INITIAL_DELAY_MILLIS = 300;
    public List<Product> productList = new ArrayList<>();
    //public Context appContext = this.getActivity().getApplication().getApplicationContext();

    MPowerSetup setup = new MPowerSetup();
    MPowerCheckoutStore store = new MPowerCheckoutStore();
    String oprToken="";

    //This invoice will take you to the Mpower website
    MPowerCheckoutInvoice invoiceCheckout = new MPowerCheckoutInvoice(setup, store);

    //This invoice will not take you to the MPower website but bill the customer on the app or site.
    MPowerOnsiteInvoice invoiceOnSite = new MPowerOnsiteInvoice (setup, store);

    //Integrating MPower store
    MPower payments = new MPower();

    EditText mpowerUser;
    EditText mpowerConfirmCode;
    Button mpowerConfirmBtn;
    FloatingActionButton fabOPR; //floating action button for Onsite Payment Request
    FloatingActionButton fabCheckout; //floating action button for checkout



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpower);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mpowerUser = (EditText) findViewById(R.id.mpower_username_number);
        mpowerConfirmCode = (EditText) findViewById(R.id.mpower_confirm_code);
        mpowerConfirmBtn = (Button) findViewById(R.id.mpower_confirm_btn);


        mPowerSetupInitTest();
        //mPowerSetupInitLive();
        mPowerStoreInit();
        fabOPR = (FloatingActionButton) findViewById(R.id.fabOPR);
        fabOPR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!showSettingsAlert()){
                }
                else {
                    Snackbar.make(view, "In App purchace using MPower Payments", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    new PaymentThread().execute("checkOPR");
                }
            }
        });

        fabCheckout = (FloatingActionButton) findViewById(R.id.fabCheckout);
        fabCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!showSettingsAlert()){
                }
                else {
                    Snackbar.make(view, "Buying item on MPower Payments site", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    new PaymentThread().execute("checkCheckout");
                }
            }
        });

        mpowerConfirmBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!mpowerConfirmCode.getText().toString().isEmpty())
                    new PaymentThread().execute(mpowerConfirmCode.getText().toString());
                else
                    mpowerConfirmCode.setHint("Enter confirmation code here from your email");
            }
        });
/*

        if(!isConnected){
            showSettingsAlert();
        }
*/



    }

    private String mPowerPay() {
        //Setting up the On Site Invoice


        if(mpowerUser.getText().toString() != null){
            return mPowerOnSiteInvoice(mpowerUser.getText().toString());
        }
        else {
            Toast.makeText(this,"Please input your Mpower username or phone number", Toast.LENGTH_LONG).show();
            return null;
        }

        /*Adding items to cart and setting total amount
        * Note: You'll have to do the calculations of the items you've added to the cart yourself in the code
        * I have not done that. I've set the total price manually*/
        //mPowerCheckoutOnSiteInvoice();



        //SETUP STYLE 2
        //mPowerCheckoutInvoice();

        //confirmCheckout();


    }

    private void mPowerSetupInitTest(){
        setup.setMasterKey("de00cdf8-2859-4c3a-a1ef-640685b28d97");
        setup.setPrivateKey("test_private_sEv-gK5XOI4bVWFmwTLK1v2J_l0");
        setup.setPublicKey("test_private_sEv-gK5XOI4bVWFmwTLK1v2J_l0");
        setup.setToken("79616e81edd48fcdb778");
        setup.setMode("test");

    }

    private void mPowerSetupInitLive(){
        setup.setMasterKey("de00cdf8-2859-4c3a-a1ef-640685b28d97");
        setup.setPrivateKey("live_private_RQFQGtWoix5nG5Gui-HrYh2om74");
        setup.setPublicKey("live_public_n7IYHyKYA6GYVJL7BBASajhRXws");
        setup.setToken("f53430f7b7aafb67320e");
        setup.setMode("live");
    }

    private void mPowerStoreInit(){
        store.setName("Gh Payments Demo Store");
        store.setTagline("This is an awesome Java store to buy me waache.");
        store.setPhoneNumber("0209708141");
        store.setPostalAddress("606 Memorylane Chokor no.1 Road.");
        store.setWebsiteUrl("http://ketecode.com/");
    }

    private void mPowerInvoiceCheckout(){
        invoiceCheckout.addItem("13' Apple Retina 500 HDD", 1, 10.99, 10.99);
        invoiceCheckout.addItem("Case Logic laptop Bag", 2, 100.50, 201, "Optional description");
        invoiceCheckout.setTotalAmount(2.50);
        //invoiceCheckout.setTotalAmount(invoiceCheckout.getTotalAmount());

        //Log.d("All Items",invoiceCheckout.getItems());
        //Log.d("Get toal Amt", String.valueOf(invoiceCheckout.getTotalAmount()));


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

    private String mPowerOnSiteInvoice(String mpower_customer_username_or_phoneNo){
        invoiceOnSite.addItem("13' Apple Retina 500 HDD", 1, 10.99, 10.99);
        invoiceOnSite.addItem("Case Logic laptop Bag", 2, 100.50, 201, "Optional description");
        invoiceOnSite.setTotalAmount(3.56);

        //CREATING INVOICE
        // Onsite Payment Request requires the mpower customer account alias as the parameter
        if (invoiceOnSite.create(mpower_customer_username_or_phoneNo)) {
            Log.d ("OPR Token" , invoiceOnSite.getToken());
            Log.d ("Status" , invoiceOnSite.getStatus());
            Log.d ("Response Message" , invoiceOnSite.getResponseText());
        } else {
            Log.d("Status (else statement)" , invoiceOnSite.getStatus());
            Log.d("Response msg " , invoiceOnSite.getResponseText());
        }
        return  invoiceOnSite.getToken();
    }


    // Onsite Payment Request Charge requires bother the OPR_Token & Customers Confirmation Token
    private void onsiteRequestCharge(String oprToken, String customerConfirmToken) {
        if (invoiceOnSite.charge(oprToken, customerConfirmToken)) {
            Log.d("Confirm trans Status" , invoiceOnSite.getStatus());
            Log.d("Response Message" , invoiceOnSite.getResponseText());
            Log.d("Receipt URL" , invoiceOnSite.getReceiptUrl());
            Log.d("Customer Name" , (String) invoiceOnSite.getCustomerInfo("name"));
            Log.d("Customer Email" , (String) invoiceOnSite.getCustomerInfo("email"));
        } else {
            Log.d("Status" , invoiceOnSite.getStatus());
            Log.d("Response Message" , invoiceOnSite.getResponseText());
        }
    }


    private void mPowerCheckoutOnSiteInvoice(){
        invoiceOnSite.addItem("13' Apple Retina 500 HDD", 1, 10.99, 10.99);
        invoiceOnSite.addItem("Case Logic laptop Bag", 2, 100.50, 201, "Optional description");
        invoiceOnSite.setTotalAmount(555.23);

        //CREATING INVOICE
        // The code below depicts how to create the checkout invoice on our servers
        // and redirect to the checkout page.
        if (invoiceOnSite.create()) {
            Log.d("Invoice Status ", invoiceOnSite.getStatus());
            Log.d("Invoice Response ",invoiceOnSite.getResponseText());
            Log.d("Invoice URL", invoiceOnSite.getInvoiceUrl());
        } else {
            Log.d("Invoice Response ", invoiceOnSite.getResponseText());
            Log.d("Invoice Status ", invoiceOnSite.getStatus());
        }

    }

/**
 * Note that you may to do the calculations for this yourself.
 * MPower does not state in their documentation if they do anything with these methods */
    private void taxInfoOnSite(){
        invoiceOnSite.addTax("VAT (15%)", 30);
        invoiceOnSite.addTax("NHIL (5%)", 10);
    }

    /**
     * Note that you may to do the calculations for this yourself.
     * MPower does not state in their documentation if they do anything with these methods */
    private void taxInfoCheckout(){
        invoiceCheckout.addTax("VAT (15%)",30);
        invoiceCheckout.addTax("NHIL (5%)",10);
    }

    /**
     * This funciton would confirm the Checkout programatically.
     * I'm using the variable 'invoiceCheckout' for this method as an example
     * */
    private boolean confirmCheckout(){
        // Invoice token is returned as a URL query string "token"
        // You are free to also explicitly check the status of an invoice and persist it in your database or part of your logs
        String invoiceToken = "sjklsdll21-ms0w";

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

    /**
     * This funciton would confirm the Checkout programatically.
     * I'm using the variable 'invoiceOnsite' for this method as an example
     * */
    private boolean confirmOnSiteCheckout(){
        // Invoice token is returned as a URL query string "token"
        // You are free to also explicitly check the status of an invoice and persist it in your database or part of your logs
        String invoiceToken = "sjklsdll21-ms0w";

        if (invoiceOnSite.confirm(invoiceToken)) {

            // Retrieving Invoice Status
            // Status can be either completed, pending, canceled, fail
            Log.d("Status",invoiceOnSite.getStatus());
            Log.d("Response Text",invoiceOnSite.getResponseText());

            // You can retrieve the Name, Email & Phone Number
            // of the customer using the callbacks below
            Log.d("Customer Name:", (String) invoiceOnSite.getCustomerInfo("name"));
            Log.d("Customer Email:" , (String) invoiceOnSite.getCustomerInfo("email"));
            Log.d("Customer Phone:" , (String) invoiceOnSite.getCustomerInfo("phone"));

            // Return the URL to the Invoice Receipt PDF file for download
            Log.d("Receipt URL:",invoiceOnSite.getReceiptUrl());

            // Retrieving any custom data you have added to the invoice
            // Please remember to use the right keys you used to set them earlier
            Log.d("First Name:" , (String) invoiceOnSite.getCustomData("Firstname"));
            Log.d("Cart ID:" , (String) invoiceOnSite.getCustomData("CartId"));
            Log.d("Plan :" , (String) invoiceOnSite.getCustomData("Plan"));

            // You can also callback the total amount set earlier
            Log.d("Total Amount:", String.valueOf(invoiceOnSite.getTotalAmount()));

            return true;

        }else{
            Log.d("Status",invoiceOnSite.getStatus());
            Log.d("Response Text",invoiceOnSite.getResponseText());
            Log.d("Response Code",invoiceOnSite.getResponseCode());
        }
        return false;
    }

    public void openPaymentBrowserActivity(){
        Intent browserActivity = new Intent(MpowerActivity.this,MPowerWebsiteActivity.class);
        browserActivity.putExtra("URL", invoiceCheckout.getInvoiceUrl());
        startActivityForResult(browserActivity, 1);
    }

    public void showBrowserPopup(){
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        PopupWindow pw = new PopupWindow(
                inflater.inflate(R.layout.activity_mpower_website, null, false),
                10000,
                10000,
                true);
        // The code below assumes that the root container has an id called 'main'
        pw.showAtLocation(this.findViewById(R.id.mpowerContext), Gravity.CENTER, 0, 0);
    }

    /**
     * Function to show settings alert dialog
     * */
    public boolean showSettingsAlert() {
        //Internet connectivity
        //To check the Internet connection
        ConnectivityManager cm;
        NetworkInfo activeNetwork;
        boolean isConnected;

        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Internet settings");

        // Setting Dialog Message
        alertDialog.setMessage("Internet is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(!isConnected){
            // Showing Alert Message
            alertDialog.show();
        }

        return isConnected;

    }




    public class PaymentThread extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            switch (params[0]){
                case "checkOPR":
                        mPowerPay();
                        oprToken = invoiceOnSite.getToken();

                        //showSettingsAlert();
                        return "checkOPR";


                case "checkCheckout":
                        //Adding all the items to the cart and opening the mpower site to checkout
                        mPowerInvoiceCheckout();
                        //This opens the Mpower site in an activity successfully
                        openPaymentBrowserActivity();
                        return "checkCheckout";


                case "confirm":
                    onsiteRequestCharge(oprToken, params[0]);
                    return "confirm";
            }
            return invoiceOnSite.getStatus();
        }

        @Override
        protected void onPostExecute(String result) {
            switch (result){
                case "checkOPR":
                    mpowerConfirmCode.setVisibility(View.VISIBLE);
                    mpowerConfirmBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"Input your confirmation code from your email. ",Toast.LENGTH_LONG).show();
                    break;

                case "confirm":
                    //You could put a function here to show an activity to thank the user
                    if(invoiceOnSite.getStatus().equalsIgnoreCase("completed")){
                        mpowerConfirmCode.setText("");
                        mpowerConfirmCode.setVisibility(View.INVISIBLE);
                        mpowerConfirmBtn.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "TRANSACTION SUCCESSFUL !!!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "something went wrong with confirmation :( ", Toast.LENGTH_LONG).show();
                    }
                    break;
                case "error":
                    //showSettingsAlert(getBaseContext());
                    Toast.makeText(getApplicationContext(), "something went wrong with the app :( ", Toast.LENGTH_LONG).show();
                    break;
            }


        }
    }

}
