package com.example.a0731;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import android.view.ViewGroup;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.content.Context;

public class ImagePreviewActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1; // 圖片選擇請求碼
    private RecyclerView modelRecyclerView; // 顯示模型列表的 RecyclerView
    private ImageView previewImageView; // 預覽背景圖片
    private FrameLayout imageContainer; // 容納模型的 FrameLayout
    private ScaleGestureDetector scaleGestureDetector; // 縮放手勢偵測器
    private float dX, dY; // 用於拖曳計算的位移值
    private float initialDistance = 0f; // 初始的兩指距離
    private float scaleFactor = 1.0f; // 初始縮放比例
    private float scaleSpeedFactor = 1.0f; // 縮放速度因子，調整為適當值以控制速度
    private float currentScaleFactor = 0.8f;// 儲存當前的縮放比例，在手指離開螢幕後記錄最新縮放值
    private ImageView currentModelView; // 當前拖曳的模型
    private boolean isScalingOrRotating = false; // 用於檢查是否進行縮放或旋轉
    private Handler handler = new Handler(); // 用於延遲任務的處理
    private int activePointerId = MotionEvent.INVALID_POINTER_ID; // 當前活動手指 ID
    private FrameLayout trashContainer; // 垃圾桶和圓圈的容器
    private ImageView trashIcon, trashCircle; // 垃圾桶和圓圈的圖標
    private Button style2Button, style3Button, style4Button; // 切換模型樣式的按鈕
    private LinearLayout headerLayout, modelSelectionLayout; // 頂部和底部的 UI 區塊
    private boolean hasTriggeredVibration = false; // 用於檢查是否已觸發震動效果
    private boolean isInTrashZone = false; // 追蹤模型是否已在垃圾桶範圍內
    private static final float TRASH_SCALE_FACTOR = 1.3f;// 增加垃圾桶放大比例
    private static final float SCALE_THRESHOLD = 5f; // 縮放閾值
    private static final float SMOOTHING_FACTOR = 0.5f; // 平滑因子
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        // 初始化 UI 元件
        modelRecyclerView = findViewById(R.id.modelRecyclerView);
        imageContainer = findViewById(R.id.imageContainer);
        previewImageView = findViewById(R.id.previewImageView);
        ImageButton backButton = findViewById(R.id.backButton);
        headerLayout = findViewById(R.id.headerLayout);
        modelSelectionLayout = findViewById(R.id.modelSelectionLayout);

        // 返回按鈕事件
        backButton.setOnClickListener(v -> finish());

        // 設置 RecyclerView 顯示方向為橫向
        modelRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // 初始化垃圾桶容器和圖標
        trashContainer = findViewById(R.id.trashContainer);
        trashIcon = findViewById(R.id.trashIcon);
        trashCircle = findViewById(R.id.trashCircle);
        trashContainer.setVisibility(View.GONE); // 初始狀態為隱藏

        // 初始化模型樣式選擇按鈕
        style2Button = findViewById(R.id.style2Button);
        style3Button = findViewById(R.id.style3Button);
        style4Button = findViewById(R.id.style4Button);

        // 點擊不同樣式按鈕載入對應模型
        style2Button.setOnClickListener(v -> updateModelRecyclerView("models/2la"));
        style3Button.setOnClickListener(v -> updateModelRecyclerView("models/3la"));
        style4Button.setOnClickListener(v -> updateModelRecyclerView("models/4la"));

        // 預設載入初始模型圖片
        updateModelRecyclerView("models/2la");

        // 接收背景圖片的 Intent 並顯示
        Intent intent = getIntent();
        String imageUriString = intent.getStringExtra("imageUri");
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            displayImage(imageUri);
        }

        // 初始化縮放手勢偵測器
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
    }

    // 顯示背景圖片
    private void displayImage(Uri imageUri) {
        try (InputStream imageStream = getContentResolver().openInputStream(imageUri)) {
            Bitmap backgroundImage = BitmapFactory.decodeStream(imageStream);
            previewImageView.setImageBitmap(backgroundImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 更新模型 RecyclerView 中顯示的圖片
    private void updateModelRecyclerView(String folder) {
        List<Bitmap> modelImages = loadImagesFromAssets(folder);
        ModelAdapter modelAdapter = new ModelAdapter(modelImages, this::addModelToImage);
        modelRecyclerView.setAdapter(modelAdapter);
    }

    // 將模型添加到 imageContainer 中並支持拖曳和縮放
    private void addModelToImage(Bitmap modelImage) {
        if (currentModelView != null && currentModelView.getParent() != null) {
            ((ViewGroup) currentModelView.getParent()).removeView(currentModelView);
            currentModelView = null;
        }

        currentModelView = new ImageView(this);
        currentModelView.setImageBitmap(modelImage);
        currentModelView.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        ));
        currentModelView.setTranslationZ(100f); // 保證拖曳時模型位於最上層

        // 設置拖曳和縮放行為
        currentModelView.setOnTouchListener((v, event) -> {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    // 設置活動手指 ID 並初始化位移
                    activePointerId = event.getPointerId(0);
                    dX = v.getX() - event.getRawX();
                    dY = v.getY() - event.getRawY();
                    trashContainer.setVisibility(View.VISIBLE); // 顯示垃圾桶容器
                    headerLayout.setVisibility(View.GONE); // 隱藏頂部
                    modelSelectionLayout.setVisibility(View.GONE); // 隱藏底部
                    hasTriggeredVibration = false; // 重置震動狀態
                    isInTrashZone = false; // 重置垃圾桶區域狀態
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    if (event.getPointerCount() == 2) {
                        initialDistance = getFingerSpacing(event); // 初始化兩指距離
                        // 鎖定初始位置，避免在縮放時模型位置發生改變
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                    }
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (event.getPointerCount() == 2) {
                        float newDistance = getFingerSpacing(event);

                        // 判斷距離變化是否超過閾值
                        if (initialDistance != 0 && Math.abs(newDistance - initialDistance) > SCALE_THRESHOLD) {
                            float scaleChange = newDistance / initialDistance;
                            scaleFactor = scaleFactor * (1 + SMOOTHING_FACTOR * (scaleChange - 1)); // 引入平滑因子
                            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f)); // 限制縮放範圍

                            // 更新模型縮放比例
                            currentModelView.setScaleX(scaleFactor);
                            currentModelView.setScaleY(scaleFactor);

                            initialDistance = newDistance; // 更新初始距離
                        }
                    } else if (event.getPointerCount() == 1 && !isScalingOrRotating) {
                        int pointerIndex = event.findPointerIndex(activePointerId);
                        if (pointerIndex != -1) {
                            float newX = event.getRawX() + dX;
                            float newY = event.getRawY() + dY;
                            v.setX(newX);
                            v.setY(newY);

                            if (isFingerOverTrashContainer(event.getRawX(), event.getRawY())) {
                                if (!isInTrashZone) {
                                    triggerVibration();
                                    trashContainer.setScaleX(1.3f);
                                    trashContainer.setScaleY(1.3f);
                                    v.setScaleX(0.2f); // 只縮小一次
                                    v.setScaleY(0.2f);
                                    isInTrashZone = true;
                                }
                                v.setScaleX(0.4f);
                                v.setScaleY(0.4f);
                            } else {
                                if (isInTrashZone) {
                                    trashContainer.setScaleX(1.0f);
                                    trashContainer.setScaleY(1.0f);
                                    v.setScaleX(scaleFactor);
                                    v.setScaleY(scaleFactor);
                                    isInTrashZone = false;
                                }
                            }
                        }
                    }
                    break;

                case MotionEvent.ACTION_POINTER_UP:
                    // 如果縮放結束，重置初始距離並更新拖曳基準點
                    if (event.getPointerCount() == 2) {
                        initialDistance = 0;
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                    }
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // 所有手指抬起後重置拖曳變數，確保模型位置不會亂跳
                    activePointerId = MotionEvent.INVALID_POINTER_ID;
                    dX = 0;
                    dY = 0;
                    resetViewAfterDrag();
                    break;
            }
            return true;
        });

        imageContainer.addView(currentModelView); // 將模型添加到 imageContainer
    }

    // 計算兩指之間的距離
    private float getFingerSpacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private boolean isFingerOverTrashContainer(float fingerX, float fingerY) {
        if (trashContainer == null) return false;

        int[] containerLocation = new int[2];
        trashContainer.getLocationOnScreen(containerLocation);

        int containerLeft = containerLocation[0];
        int containerRight = containerLeft + trashContainer.getWidth();
        int containerTop = containerLocation[1];
        int containerBottom = containerTop + trashContainer.getHeight();

        // 判斷手指是否碰到垃圾桶範圍的任意部分
        return fingerX >= containerLeft && fingerX <= containerRight &&
                fingerY >= containerTop && fingerY <= containerBottom;
    }

    // 檢查手指是否在 trashContainer 範圍內
    private boolean isOverTrashContainer(View v) {
        if (trashContainer == null || v == null) return false;

        int[] containerLocation = new int[2];
        trashContainer.getLocationOnScreen(containerLocation);

        int[] viewLocation = new int[2];
        v.getLocationOnScreen(viewLocation);

        // 根據垃圾桶的放大比例來動態計算範圍
        int trashCenterX = containerLocation[0] + (int) (trashContainer.getWidth() * TRASH_SCALE_FACTOR) / 2;
        int trashCenterY = containerLocation[1] + (int) (trashContainer.getHeight() * TRASH_SCALE_FACTOR) / 2;

        int scaledWidth = (int) (trashContainer.getWidth() * TRASH_SCALE_FACTOR);
        int scaledHeight = (int) (trashContainer.getHeight() * TRASH_SCALE_FACTOR);

        // 檢查模型中心點是否在放大後的垃圾桶區域內
        return viewLocation[0] + v.getWidth() / 2 > trashCenterX - scaledWidth / 2 &&
                viewLocation[0] + v.getWidth() / 2 < trashCenterX + scaledWidth / 2 &&
                viewLocation[1] + v.getHeight() / 2 > trashCenterY - scaledHeight / 2 &&
                viewLocation[1] + v.getHeight() / 2 < trashCenterY + scaledHeight / 2;
    }

    // 觸發震動效果
    private void triggerVibration() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }

    // 重置拖曳完成後的 UI 狀態
    private void resetViewAfterDrag() {
        trashContainer.setVisibility(View.GONE);
        trashContainer.setScaleX(1.0f);
        trashContainer.setScaleY(1.0f);
        headerLayout.setVisibility(View.VISIBLE);
        modelSelectionLayout.setVisibility(View.VISIBLE);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private float accumulatedScaleFactor = 1.0f; // 初始比例

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (currentModelView != null) {
                // 獲取此次縮放的增量並乘以當前比例
                float scaleFactorIncrement = detector.getScaleFactor();
                accumulatedScaleFactor *= scaleFactorIncrement;

                // 限制縮放比例的最小值為
                accumulatedScaleFactor = Math.max(5.0f, accumulatedScaleFactor);
                // 限制縮放比例的最大值為
                accumulatedScaleFactor = Math.min(5.0f, accumulatedScaleFactor);

                // 更新模型縮放比例
                currentModelView.setScaleX(accumulatedScaleFactor);
                currentModelView.setScaleY(accumulatedScaleFactor);
            }
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true; // 允許縮放手勢開始
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            super.onScaleEnd(detector);
            // 手勢結束後，保存當前縮放比例
            scaleFactor = accumulatedScaleFactor;
            // 添加0.5秒的延遲
            handler.postDelayed(() -> {
                // 延遲結束後可繼續操作
            }, 500);
        }
    }

    // 從 assets 中載入指定資料夾的模型圖片
    private List<Bitmap> loadImagesFromAssets(String folder) {
        List<Bitmap> images = new ArrayList<>();
        try {
            String[] files = getAssets().list(folder);
            if (files != null) {
                for (String filename : files) {
                    try (InputStream is = getAssets().open(folder + "/" + filename)) {
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        images.add(bitmap);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return images;
    }

    // 接收從其他頁面傳遞的結果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            displayImage(selectedImageUri);
        }
    }
}
