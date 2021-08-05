package com.alper.orderapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alper.orderapp.Database.OrderContract;

public class OrangeVanillaActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ImageView imageView;
    ImageButton plusquantity, minusquantity;
    TextView quantitynumber, drinnkName, coffeePrice;
    CheckBox addToppings, addExtraCream;
    Button addtoCart;
    int quantity;
    public Uri mCurrentCartUri;
    boolean hasAllRequiredValues = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        imageView = findViewById(R.id.imageViewInfo);
        plusquantity = findViewById(R.id.addquantity);
        minusquantity  = findViewById(R.id.subquantity);
        quantitynumber = findViewById(R.id.quantity);
        drinnkName = findViewById(R.id.drinkNameinInfo);
        coffeePrice = findViewById(R.id.coffeePrice);
        addToppings = findViewById(R.id.addToppings);
        addtoCart = findViewById(R.id.addtocart);
        addExtraCream = findViewById(R.id.addCream);

        //setting name of drink
        drinnkName.setText("Orange Vanilla");
        imageView.setImageResource(R.drawable.orangevanilla);

        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrangeVanillaActivity.this,SummaryActivity.class);
                startActivity(intent);
                //bu butona tiklandiginda degerlerimizi veritabanina kaydedip bu degerleri gondermek istiyoruz.
                //hemen siparis bilgilerini gosterdigimiz özet aktiviteye
                SaveCart();
            }
        });

        plusquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //coffe price
                int basePrice = 5;

                quantity++;
                displayQuantity();
                int coffePrice = basePrice * quantity;
                String setnewPrice = String.valueOf(coffePrice);
                coffeePrice.setText(setnewPrice);

                //CheckBoxes Functionality
                int ifCheckBox = CalculatePrice(addExtraCream,addToppings);
                coffeePrice.setText("$"+ifCheckBox);

            }
        });

        minusquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int basePrice=5;

                //because we dont want the quantitiy go less than 0
                if(quantity == 0){
                    Toast.makeText(OrangeVanillaActivity.this,"Cant decrease quantity < 0",Toast.LENGTH_SHORT).show();
                }else{
                    quantity--;
                    displayQuantity();
                    int coffePrice = basePrice * quantity;
                    String setnewPrice = String.valueOf(coffePrice);
                    coffeePrice.setText(setnewPrice);


                    //CheckBoxes Functionality
                    int ifCheckBox = CalculatePrice(addExtraCream,addToppings);
                    coffeePrice.setText("$"+ifCheckBox);
                }
            }
        });

    }

    private boolean SaveCart() {
        //degerlerimizi gorunumden almak
        String name = drinnkName.getText().toString();
        String price = coffeePrice.getText().toString();
        String quantity =quantitynumber.getText().toString();

        ContentValues values = new ContentValues();
        values.put(OrderContract.OrderEntry.COLUMN_NAME,name);
        values.put(OrderContract.OrderEntry.COLUMN_PRICE,price);
        values.put(OrderContract.OrderEntry.COLUMN_QUANTITY,quantity);

        if(addExtraCream.isChecked()){
            values.put(OrderContract.OrderEntry.COLUMN_CREAM,"Has Cream : Yes");
        }else{
            values.put(OrderContract.OrderEntry.COLUMN_CREAM,"Has Cream : No");
        }

        if(addToppings.isChecked()){
            values.put(OrderContract.OrderEntry.COLUMN_HASTOPPING,"Has Topping : Yes");
        }else{
            values.put(OrderContract.OrderEntry.COLUMN_HASTOPPING,"Has Topping : No");
        }

        if(mCurrentCartUri == null){
            Uri newUri = getContentResolver().insert(OrderContract.OrderEntry.CONTENT_URI,values);
            if(newUri == null){
                Toast.makeText(this,"Failed to add to Cart",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Succes adding to Cart",Toast.LENGTH_SHORT).show();
            }
        }
        hasAllRequiredValues = true;
        return hasAllRequiredValues;
    }

    private int CalculatePrice(CheckBox addExtraCream, CheckBox addToppings) {

        int basePrice=5;

        if(addExtraCream.isChecked()){
            //add cream cost $2
            basePrice= basePrice + 2;
        }
        if(addToppings.isChecked()){
            //topping cost is $3
            basePrice = basePrice + 3;

        }
        return basePrice * quantity;
    }

    private void displayQuantity() {
        quantitynumber.setText(String.valueOf(quantity));

    }



    //Loader Methods
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {

        String[] projection = {OrderContract.OrderEntry._ID,
                OrderContract.OrderEntry.COLUMN_NAME,
                OrderContract.OrderEntry.COLUMN_PRICE,
                OrderContract.OrderEntry.COLUMN_QUANTITY,
                OrderContract.OrderEntry.COLUMN_CREAM,
                OrderContract.OrderEntry.COLUMN_HASTOPPING
        };


        return new CursorLoader(this,mCurrentCartUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

        if(cursor == null || cursor.getCount() < 1){
            return;
        }

        if(cursor.moveToFirst()){
            int name = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_NAME);
            int price = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_PRICE);
            int quantity = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_QUANTITY);
            int hasCream = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_CREAM);
            int hasTopping = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_HASTOPPING);

            String nameofdrink = cursor.getString(name);
            String priceofdrink = cursor.getString(price);
            String quantityofdrink = cursor.getString(quantity);
            String yeshasCream = cursor.getString(hasCream);
            String yeshastopping = cursor.getString(hasTopping);

            drinnkName.setText(nameofdrink);
            coffeePrice.setText(priceofdrink);
            quantitynumber.setText(quantityofdrink);

        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        drinnkName.setText("");
        coffeePrice.setText("");
        quantitynumber.setText("");
    }
}