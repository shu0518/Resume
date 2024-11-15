package com.example.a0731;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import org.tensorflow.lite.Interpreter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class TestCameraActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Interpreter interpreter;
    private ImageView originalImageView;
    private ImageView segmentedImageView;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_camera);

        // 初始化 TFLite 解釋器
        try {
            Interpreter.Options options = new Interpreter.Options();
            interpreter = new Interpreter(loadModelFile(), options);
            Log.d("TestCameraActivity", "TensorFlow Lite 模型加載成功");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TestCameraActivity", "TensorFlow Lite 模型加載失敗");
        }

        // 查找 ImageView 和 TextView
        originalImageView = findViewById(R.id.originalImageView);
        segmentedImageView = findViewById(R.id.segmentedImageView);
        resultTextView = findViewById(R.id.resultTextView);

        // 打開相簿讓使用者選擇圖片
        openGallery();
    }

    // 加載模型文件
    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("best_float32.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    // 打開相簿
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                // 調整圖片大小以符合模型要求
                Bitmap resizedImage = resizeImageForModel(selectedImage);

                // 顯示選擇的原始圖片
                originalImageView.setImageBitmap(selectedImage);

                // 在這裡進行圖片處理，例如使用 TensorFlow Lite 進行推論
                if (interpreter != null) {
                    processImageWithTFLite(resizedImage);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // 調整圖片大小為 640x640
    private Bitmap resizeImageForModel(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, 640, 640, true);
    }

    // 使用 TFLite 解釋器處理圖片
    private void processImageWithTFLite(Bitmap bitmap) {
        // 調整圖片大小
        Bitmap resizedBitmap = resizeImageForModel(bitmap);

        // 準備輸入數據
        int inputSize = 640; // 假設模型需要 640x640 大小的圖片
        float[][][][] input = new float[1][inputSize][inputSize][3];
        for (int y = 0; y < inputSize; y++) {
            for (int x = 0; x < inputSize; x++) {
                int pixel = resizedBitmap.getPixel(x, y);
                input[0][y][x][0] = ((pixel >> 16) & 0xFF) / 255.0f; // R
                input[0][y][x][1] = ((pixel >> 8) & 0xFF) / 255.0f;  // G
                input[0][y][x][2] = (pixel & 0xFF) / 255.0f;         // B
            }
        }

        // 假設模型輸出是一個三維的浮點陣列
        float[][][] output = new float[1][37][8400];

        // 使用 TFLite 解釋器進行推論
        try {
            interpreter.run(input, output);
            Log.d("TestCameraActivity", "推論成功");

            // 將推論結果應用於圖片
            Bitmap resultBitmap = drawBoundingBox(bitmap, output);

            // 顯示辨識後的圖片
            segmentedImageView.setImageBitmap(resultBitmap);
            displayResult("辨識結果: " + output[0][0][0]); // 顯示模型輸出
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TestCameraActivity", "推論失敗");
        }
    }

    // 繪製邊界框到圖片上
    private Bitmap drawBoundingBox(Bitmap bitmap, float[][][] output) {
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.RED); // 繪製的顏色
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        // 假設 output 包含邊界框的數據，您需要按照您的模型輸出結構來調整
        for (int i = 0; i < output[0].length; i++) {
            float left = output[0][i][0] * bitmap.getWidth();
            float top = output[0][i][1] * bitmap.getHeight();
            float right = output[0][i][2] * bitmap.getWidth();
            float bottom = output[0][i][3] * bitmap.getHeight();

            canvas.drawRect(left, top, right, bottom, paint);
        }

        return mutableBitmap;
    }

    // 顯示辨識結果
    private void displayResult(String result) {
        resultTextView.setText(result);
    }
}