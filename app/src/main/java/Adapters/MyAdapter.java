package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ducktivetwo.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

import Model.Data;


public class MyAdapter extends FirebaseRecyclerAdapter<Data,MyAdapter.myViewHolder> {
    private List<Data> dataList;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyAdapter(@NonNull FirebaseRecyclerOptions<Data> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Data model) {
        holder.date.setText(model.getDate());
        holder.type.setText(model.getType());
        holder.note.setText(model.getNote());
        holder.amount.setText(Integer.toString(model.getAmount()));
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_recycler_data,parent,false);
        return new myViewHolder(view);
    }

    public void setExpenseDataList(List<Data> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        TextView date, type, note, amount;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            date =itemView.findViewById(R.id.date_txt_expense);
            type =itemView.findViewById(R.id.type_txt_expense);
            note =itemView.findViewById(R.id.note_txt_expense);
            amount =itemView.findViewById(R.id.amount_txt_expense);

        }
    }

}
