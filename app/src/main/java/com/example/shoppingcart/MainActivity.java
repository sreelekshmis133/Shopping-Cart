package com.example.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shoppingcart.model.ProductModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private TextView totalAmountTextView, overalTotalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        //for fullscreen
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ProductModel> productList = createDummyData();

        productAdapter = new ProductAdapter(productList, this);

        recyclerView.setAdapter(productAdapter);
        totalAmountTextView = findViewById(R.id.totalAmountValue);
        overalTotalTextView = findViewById(R.id.overallTotalValue);


        updateTotalAmount();

    }



    private List<ProductModel> createDummyData(){
        List<ProductModel> productList = new ArrayList<>();

        productList.add(new ProductModel("Product 1", "Large", 2, 2500));
        productList.add(new ProductModel("Product 2", "Medium", 3, 4000));
        productList.add(new ProductModel("Product 3", "Small", 1, 1000));

        return productList;
    }

    public void updateTotalAmount(){
        double totalAmount = productAdapter.calculateTotalAmount();
        totalAmountTextView.setText("₹" + String.format(Locale.US, "%.2f", totalAmount));

        double overalAmount = totalAmount + 192;
        overalTotalTextView.setText("₹"+ String.format(Locale.US, "%.2f", overalAmount));
    }
}