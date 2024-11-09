package com.example.a0731;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.widget.LinearLayout;
import android.widget.FrameLayout;

public class CustomerFunctionActivity extends AppCompatActivity {

    private LinearLayout customerInfoEditLayout;
    private CardView windowFrameButton, documentButton, processingDataButton;
    private ImageButton cameraButton, galleryButton, homeButton;
    private FrameLayout exampleItem1, exampleItem2, exampleItem3, exampleItem4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_function);

        // 獲取從 CustomerAdapter 傳過來的地址資訊
        Intent intent = getIntent();
        String customer_Address = intent.getStringExtra("customer_Address");
        String customer_name = intent.getStringExtra("customer_name");
        String account = intent.getStringExtra("account");
        // 初始化返回按鈕
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 返回上一頁
            }
        });

        // 初始化 Example Item 區塊
        exampleItem1 = findViewById(R.id.exampleItem1);
        exampleItem2 = findViewById(R.id.exampleItem2);
        exampleItem3 = findViewById(R.id.exampleItem3);
        exampleItem4 = findViewById(R.id.exampleItem4);

        cameraButton = findViewById(R.id.cameraButton);  // 初始化 cameraButton
        galleryButton = findViewById(R.id.galleryButton);  // 初始化 galleryButton
        homeButton = findViewById(R.id.homeButton);  // 初始化 homeButton

        // 測量數據點擊事件
        exampleItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerFunctionActivity.this, MeasuredDataActivity.class);
                intent.putExtra("customer_Address", customer_Address);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });

        // 加工數據 點擊事件
        exampleItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerFunctionActivity.this, ProcessingDataActivity.class);
                intent.putExtra("customer_Address", customer_Address);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });

        // 單據管理 點擊事件
        exampleItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerFunctionActivity.this, HistoryRecordsActivity.class);
                startActivity(intent);
            }
        });

        // 客戶資訊點擊事件
        exampleItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerFunctionActivity.this, CustomerProfileActivity.class);
                intent.putExtra("customer_name", customer_name);
                startActivity(intent);
            }
        });

        // Camera 按鈕點擊事件
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Camera 功能邏輯
            }
        });

        // Gallery 按鈕點擊事件
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gallery 功能邏輯
            }
        });

        // 返回首頁按鈕點擊事件
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerFunctionActivity.this, HomepageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
