package com.ketecode.ghpaymentsdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    EditText mpowerUser;
    EditText mpowerConfirmCode;
    Button mpowerConfirmBtn;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpower);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mpowerUser = (EditText) findViewById(R.id.mpower_username_number);
        mpowerConfirmCode = (EditText) findViewById(R.id.mpower_confirm_code);
        mpowerConfirmBtn = (Button) findViewById(R.id.mpower_confirm_btn);

        mPowerSetupInit();
        mPowerStoreInit();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Buying item using MPower Payments", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                new PaymentThread().execute("check");
            }
        });

        mpowerConfirmBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(!mpowerConfirmCode.getText().toString().isEmpty())
                    new PaymentThread().execute(mpowerConfirmCode.getText().toString());
                else
                    mpowerConfirmCode.setHint("Enter confirmation code here from your email");
            }
        });



/*

        productList.add(new Product(R.drawable.p1,"R.drawable.p1"));

        final GridView gridView = (GridView) findViewById(R.id.activity_gridview_gv);

        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(new GridViewAdapter(this,productList));
        swingBottomInAnimationAdapter.setAbsListView(gridView);

        assert swingBottomInAnimationAdapter.getViewAnimator() != null;
        swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(INITIAL_DELAY_MILLIS);



        gridView.setAdapter(swingBottomInAnimationAdapter);

        //gridView.findViewById()
        gridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        */
/*Article selected = new Article();
                        selected = (Article) parent.getItemAtPosition(position);
                        Intent i;
                        i = new Intent(view.getContext(), ArticleDetailActivity.class);
                        i.putExtra("title", selected.getTitlePlain());
                        i.putExtra("date", selected.date);
                        i.putExtra("body", selected.getContent());
                        i.putExtra("author", selected.author);
                        i.putExtra("url", selected.url);  imageView.getResources().getResourceName(imageResId)


                        startActivity(i);*//*


                        //ImageView imageView = (ImageView) parent.getItemAtPosition(position);
                        //Object emoji =  parent.getItemAtPosition(position);
                        //imageView.getResources().getResourceName(imageView.getId());

                        Product onePic = (Product) parent.getItemAtPosition(position);
                        //onePic.getTheImage().ge

                        //Toast.makeText(getApplicationContext(), "Selected - " + getResources().getResourceName(onePic.getLocalResourceId()).replace(":", "/"), Toast.LENGTH_LONG).show();
*/
/*
                        Intent share = new Intent(android.content.Intent.ACTION_SEND);
                        share.setType("image*//*
*/
/**//*
*/
/*");
                        share.putExtra(Intent.EXTRA_STREAM, Uri.parse()); // Add image path - android.resource://com.ketecode.imojitest/drawable/image1

                        startActivity(Intent.createChooser(share, "Share image using"));*//*


                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        Uri screenshotUri = Uri.parse("android.resource://" + getResources().getResourceName(onePic.getLocalResourceId()).replace(":", "/"));

                        //getResources().getResourceName(onePic.getLocalResourceId())

                        sharingIntent.setType("image*/
/*");
                        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                        startActivity(Intent.createChooser(sharingIntent, "Share image using"));


                    }
                }
        );

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

    private void mPowerSetupInit(){
        setup.setMasterKey("de00cdf8-2859-4c3a-a1ef-640685b28d97");
        setup.setPrivateKey("test_private_sEv-gK5XOI4bVWFmwTLK1v2J_l0");
        setup.setPublicKey("test_private_sEv-gK5XOI4bVWFmwTLK1v2J_l0");
        setup.setToken("79616e81edd48fcdb778");
        setup.setMode("test");
    }

    private void mPowerStoreInit(){
        store.setName("Gh Payments Demo Store");
        store.setTagline("This is an awesome Java store setup.");
        store.setPhoneNumber("0209708141");
        store.setPostalAddress("606 Memorylane Chokor no.1 Road.");
        store.setWebsiteUrl("http://ketecode.com/");
    }

    private void mPowerCheckoutInvoice(){
        invoiceCheckout.addItem("13' Apple Retina 500 HDD", 1, 10.99, 10.99);
        invoiceCheckout.addItem("Case Logic laptop Bag", 2, 100.50, 201, "Optional description");
        invoiceCheckout.setTotalAmount(555.23);

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
        invoiceOnSite.setTotalAmount(200.56);

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
        invoiceOnSite.addItem("13' Apple Retina 500 HDD",1,10.99,10.99);
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


    public class PaymentThread extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            if(params[0].equals("check")) {
                mPowerPay();
                oprToken = invoiceOnSite.getToken();
                return "check";
            }
            else if(!params[0].equals("confirm")){
                onsiteRequestCharge(oprToken, params[0]);
                return "confirm";
            }
            return invoiceOnSite.getStatus();
        }

        @Override
        protected void onPostExecute(String result) {
            switch (result){
                case "check":
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
            }


        }
    }

}
