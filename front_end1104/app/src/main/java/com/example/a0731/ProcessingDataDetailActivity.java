package com.example.a0731;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

public class ProcessingDataDetailActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private TextView windowMaterialTextView;
    private TextView innerSupportTextView;
    private TextView outerSupportTextView;
    private TextView screenWidthTextView;
    private TextView screenHeightTextView;
    private TextView glassWidthTextView;
    private TextView glassHeightTextView;
    private ImageView completedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing_data_detail);

        // 初始化視圖元件
        windowMaterialTextView = findViewById(R.id.windowMaterialTextView);
        innerSupportTextView = findViewById(R.id.innerSupportTextView);
        outerSupportTextView = findViewById(R.id.outerSupportTextView);
        screenWidthTextView = findViewById(R.id.screenWidthTextView);
        screenHeightTextView = findViewById(R.id.screenHeightTextView);
        glassWidthTextView = findViewById(R.id.glassWidthTextView);
        glassHeightTextView = findViewById(R.id.glassHeightTextView);
        completedImageView = findViewById(R.id.completedImageView);

        // 初始化返回按鈕
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProcessingDataDetailActivity.this, ProcessingDataActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        // 確認上傳圖片的按鈕
        Button uploadImageButton = findViewById(R.id.uploadImageButton);
        uploadImageButton.setOnClickListener(v -> openImagePicker()); // 呼叫圖片選擇器
    }

    // 開啟圖片選擇器的方法
    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "選擇圖片"), PICK_IMAGE_REQUEST);
    }

    // 處理圖片選擇結果的方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                // 將選擇的圖片轉換為 Bitmap 並顯示於 ImageView
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                completedImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "無法載入圖片", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
