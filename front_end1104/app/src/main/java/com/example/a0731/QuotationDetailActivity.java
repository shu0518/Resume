package com.example.a0731;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.example.a0731.QuotationAdapter;
import com.example.a0731.Quotation;

public class QuotationDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuotationAdapter quotationAdapter;
    private List<Quotation> quotationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_detail);

        // 初始化返回按鈕
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            // 返回到 HistoryRecordsActivity 並清除中間的 Activity
            Intent intent = new Intent(QuotationDetailActivity.this, HistoryRecordsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        // 初始化 RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 初始化假報價單項目列表
        quotationList = new ArrayList<>();
        quotationList.add(new Quotation("施工項目A", 10, 100.0, 1000.0));
        quotationList.add(new Quotation("施工項目B", 5, 200.0, 1000.0));
        quotationList.add(new Quotation("施工項目C", 5, 200.0, 1000.0));
        quotationList.add(new Quotation("施工項目D", 5, 200.0, 1000.0));
        quotationList.add(new Quotation("施工項目E", 5, 200.0, 1000.0));
        quotationList.add(new Quotation("施工項目F", 5, 200.0, 1000.0));

        // 設置適配器
        quotationAdapter = new QuotationAdapter(quotationList);
        recyclerView.setAdapter(quotationAdapter);
    }
}
