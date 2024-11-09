package com.example.a0731;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoryRecordsAdapter extends RecyclerView.Adapter<HistoryRecordsAdapter.ViewHolder> {

    private List<HistoryRecord> historyRecordsList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(HistoryRecord historyRecord);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public HistoryRecordsAdapter(List<HistoryRecord> historyRecordsList) {
        this.historyRecordsList = historyRecordsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryRecord record = historyRecordsList.get(position);
        holder.typeTextView.setText(record.getRecordType());
        holder.customerNameTextView.setText(record.getCustomerName());
        holder.dateTextView.setText(record.getDate());

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(record);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyRecordsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView typeTextView;
        public TextView customerNameTextView;
        public TextView dateTextView;

        public ViewHolder(View view) {
            super(view);
            typeTextView = view.findViewById(R.id.recordTypeTextView);
            customerNameTextView = view.findViewById(R.id.customerNameTextView);
            dateTextView = view.findViewById(R.id.recordDateTextView);
        }
    }
}