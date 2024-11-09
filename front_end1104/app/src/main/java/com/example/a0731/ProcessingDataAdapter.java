package com.example.a0731;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProcessingDataAdapter extends RecyclerView.Adapter<ProcessingDataAdapter.ViewHolder> implements Filterable {

    private List<ProcessingData> processingDataList;
    private List<ProcessingData> processingDataListFull;
    private Context context;
    private boolean showCheckBox;
    private CheckBox selectAllCheckBox;

    public ProcessingDataAdapter(Context context, List<ProcessingData> processingDataList, boolean showCheckBox, CheckBox selectAllCheckBox) {
        this.context = context;
        this.processingDataList = processingDataList;
        this.processingDataListFull = new ArrayList<>(processingDataList);
        this.showCheckBox = showCheckBox;
        this.selectAllCheckBox = selectAllCheckBox;

        // 初始化全选框的逻辑
        if (selectAllCheckBox != null) {
            selectAllCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                for (ProcessingData data : processingDataList) {
                    data.setSelected(isChecked);
                }
                notifyDataSetChanged();
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_processing_data, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProcessingData data = processingDataList.get(position);
        holder.processingTitleTextView.setText(data.getFrameNumber());
        holder.processingFloorTextView.setText(data.getFloor());
        holder.processingRoomTextView.setText(data.getLayout());
        holder.processingCheckBox.setOnCheckedChangeListener(null);
        holder.processingCheckBox.setChecked(data.isSelected());

        // 控制 CheckBox 是否顯示
        if (showCheckBox) {
            holder.processingCheckBox.setVisibility(View.VISIBLE);
            // 如果顯示 CheckBox，則禁用 itemView 的點擊事件
            holder.itemView.setOnClickListener(null);
        } else {
            holder.processingCheckBox.setVisibility(View.GONE);
            // 如果不顯示 CheckBox，則啟用 itemView 的點擊事件跳轉至詳細頁面
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ProcessingDataDetailActivity.class);
                intent.putExtra("frameNumber", data.getFrameNumber());
                intent.putExtra("floor", data.getFloor());
                intent.putExtra("layout", data.getLayout());
                context.startActivity(intent);
            });
        }

        // 設置 CheckBox 的點擊事件
        holder.processingCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            data.setSelected(isChecked);
            // 檢查是否需要取消全选框的勾选状态
            if (!isChecked && selectAllCheckBox != null && selectAllCheckBox.isChecked()) {
                selectAllCheckBox.setOnCheckedChangeListener(null);
                selectAllCheckBox.setChecked(false);
                selectAllCheckBox.setOnCheckedChangeListener((buttonView1, isChecked1) -> {
                    for (ProcessingData item : processingDataList) {
                        item.setSelected(isChecked1);
                    }
                    notifyDataSetChanged();
                });
            } else if (isChecked) {
                // 如果所有項目都被選中，則勾選全選框
                boolean allSelected = true;
                for (ProcessingData item : processingDataList) {
                    if (!item.isSelected()) {
                        allSelected = false;
                        break;
                    }
                }
                if (allSelected && selectAllCheckBox != null && !selectAllCheckBox.isChecked()) {
                    selectAllCheckBox.setOnCheckedChangeListener(null);
                    selectAllCheckBox.setChecked(true);
                    selectAllCheckBox.setOnCheckedChangeListener((buttonView1, isChecked1) -> {
                        for (ProcessingData item : processingDataList) {
                            item.setSelected(isChecked1);
                        }
                        notifyDataSetChanged();
                    });
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return processingDataList.size();
    }

    @Override
    public Filter getFilter() {
        return processingDataFilter;
    }

    private Filter processingDataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ProcessingData> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(processingDataListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ProcessingData item : processingDataListFull) {
                    if (item.getFrameNumber().toLowerCase().contains(filterPattern) ||
                            item.getFloor().toLowerCase().contains(filterPattern) ||
                            item.getLayout().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            processingDataList.clear();
            processingDataList.addAll((List<ProcessingData>) results.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView processingTitleTextView;
        public TextView processingFloorTextView;
        public TextView processingRoomTextView;
        public CheckBox processingCheckBox;

        public ViewHolder(View view) {
            super(view);
            processingTitleTextView = view.findViewById(R.id.processingTitleTextView);
            processingFloorTextView = view.findViewById(R.id.processingFloorTextView);
            processingRoomTextView = view.findViewById(R.id.processingRoomTextView);
            processingCheckBox = view.findViewById(R.id.processingCheckBox);
        }
    }
}
