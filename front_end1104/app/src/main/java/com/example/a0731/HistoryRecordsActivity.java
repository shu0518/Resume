package com.example.a0731;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import android.widget.FrameLayout;

public class HistoryRecordsActivity extends AppCompatActivity {

    private RecyclerView historyRecyclerView;
    private HistoryRecordsAdapter historyRecordsAdapter;
    private List<HistoryRecord> historyRecordsList;
    private List<HistoryRecord> filteredHistoryRecordsList; // 用於搜尋結果
    private EditText searchEditText;
    private ImageButton clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_records);

        // 初始化返回按鈕
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // 初始化RecyclerView
        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 初始化搜尋欄和清除按鈕
        searchEditText = findViewById(R.id.searchEditText);
        clearButton = findViewById(R.id.clearButton);

        // 初始化歷史單據數據列表
        historyRecordsList = new ArrayList<>();
        historyRecordsList.add(new HistoryRecord("報價單", "李大明", "2024-09-01"));
        historyRecordsList.add(new HistoryRecord("報價單", "陳美芳", "2024-09-02"));
        historyRecordsList.add(new HistoryRecord("報價單", "王小華", "2024-09-03"));
        historyRecordsList.add(new HistoryRecord("報價單", "林志成", "2024-09-04"));
        historyRecordsList.add(new HistoryRecord("報價單", "張家豪", "2024-09-05"));

        // 複製原始數據
        filteredHistoryRecordsList = new ArrayList<>(historyRecordsList);

        // 設置適配器
        historyRecordsAdapter = new HistoryRecordsAdapter(filteredHistoryRecordsList);
        historyRecyclerView.setAdapter(historyRecordsAdapter);

        // 點擊歷史單據列表中的單筆資料時，跳轉到報價單詳細頁面
        historyRecordsAdapter.setOnItemClickListener(historyRecord -> {
            Intent intent = new Intent(HistoryRecordsActivity.this, QuotationDetailActivity.class);
            startActivity(intent);
        });

        // 搜尋功能
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    clearButton.setVisibility(View.VISIBLE);
                } else {
                    clearButton.setVisibility(View.GONE);
                }
                filterHistoryRecordsData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // 清除按鈕的邏輯
        clearButton.setOnClickListener(v -> searchEditText.setText(""));

        // 新增加單據的按鈕
        FloatingActionButton addDocumentButton = findViewById(R.id.addDocumentButton);
        addDocumentButton.setOnClickListener(v -> showBottomSheetDialog());
    }

    private void showBottomSheetDialog() {
        // 創建 BottomSheetDialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(HistoryRecordsActivity.this);

        // 加載自定義布局
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.layout_add_document_bottom_sheet,
                null
        );

        // 綁定報價單按鈕
        LinearLayout quotationButton = bottomSheetView.findViewById(R.id.quotationButton);
        quotationButton.setOnClickListener(v -> {
            // 跳轉到新增報價單的頁面
            Intent intent = new Intent(HistoryRecordsActivity.this, AddQuotationActivity.class);
            startActivity(intent);
            bottomSheetDialog.dismiss();
        });

        // 設置為擴展模式
        bottomSheetDialog.setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            FrameLayout bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setPeekHeight(600);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        // 設置視圖並顯示 BottomSheetDialog
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    // 搜尋過濾功能
    private void filterHistoryRecordsData(String query) {
        filteredHistoryRecordsList.clear();
        if (query.isEmpty()) {
            filteredHistoryRecordsList.addAll(historyRecordsList);
        } else {
            for (HistoryRecord record : historyRecordsList) {
                if (record.getRecordType().toLowerCase().contains(query.toLowerCase()) ||
                        record.getDate().toLowerCase().contains(query.toLowerCase()) ||
                        record.getCustomerName().toLowerCase().contains(query.toLowerCase())) {
                    filteredHistoryRecordsList.add(record);
                }
            }
        }
        historyRecordsAdapter.notifyDataSetChanged();
    }
}