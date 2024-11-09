package com.example.a0731;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProcessingDataActivity extends AppCompatActivity {

    private RecyclerView processingRecyclerView;
    private ProcessingDataAdapter processingAdapter;
    private List<ProcessingData> processingDataList;
    private EditText searchEditText;
    private ImageButton clearButton; // 清除按鈕
    private FloatingActionButton addProcessingDataButton; // 新增數據按鈕

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing_data);

        // 獲取從 CustomerFunction 傳過來的地址資訊
        String customer_Address = getIntent().getStringExtra("customer_Address");
        String account = getIntent().getStringExtra("account");

        // 初始化返回按鈕
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // 初始化 RecyclerView
        processingRecyclerView = findViewById(R.id.processingDataRecyclerView);
        processingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 初始化數據列表
        processingDataList = new ArrayList<>();
        processingDataList.add(new ProcessingData("A1", "1樓", "主臥"));
        processingDataList.add(new ProcessingData("A2", "2樓", "客廳"));

        // 設置適配器，傳遞 Context 和數據列表
        CheckBox selectAllCheckBox = findViewById(R.id.selectAllCheckBox); // 獲取全選框
        processingAdapter = new ProcessingDataAdapter(this, processingDataList, false, selectAllCheckBox);
        processingRecyclerView.setAdapter(processingAdapter);

        // 初始化搜尋欄和清除按鈕
        searchEditText = findViewById(R.id.searchEditText);
        clearButton = findViewById(R.id.clearButton);

        // 添加 TextWatcher 監聽輸入變化
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 文字改變前的處理邏輯
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 當輸入欄文字改變時過濾數據
                processingAdapter.getFilter().filter(s.toString());
                if (s.length() > 0) {
                    clearButton.setVisibility(View.VISIBLE);
                } else {
                    clearButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 文字改變後的處理邏輯
            }
        });

        // 清除按鈕的邏輯
        clearButton.setOnClickListener(v -> searchEditText.setText(""));

        // 新增數據按鈕的點擊事件
        addProcessingDataButton = findViewById(R.id.addProcessingDataButton);
        addProcessingDataButton.setOnClickListener(v -> {
            // 跳轉到新增加工數據頁面
            Intent intent = new Intent(ProcessingDataActivity.this, AddProcessingDataActivity.class);
            intent.putExtra("customer_Address", customer_Address);
            intent.putExtra("account", account);
            startActivity(intent);
        });
    }
}
