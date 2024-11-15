package com.example.a0731;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ViewHolder> {

    private List<Bitmap> modelImages;  // 用於儲存模型圖片的列表
    private OnModelClickListener listener;  // 點擊事件的回調介面

    // 修改構造函數，接受 List<Bitmap> 和 OnModelClickListener
    public ModelAdapter(List<Bitmap> modelImages, OnModelClickListener listener) {
        this.modelImages = modelImages;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bitmap modelImage = modelImages.get(position);
        holder.modelImageView.setImageBitmap(modelImage);

        // 設置點擊事件，將選中的圖片回調給調用者
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onModelClick(modelImage);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelImages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView modelImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            modelImageView = itemView.findViewById(R.id.modelImageView);
        }
    }

    // 定義回調介面，用於點擊事件的回調
    public interface OnModelClickListener {
        void onModelClick(Bitmap modelImage);
    }
}
