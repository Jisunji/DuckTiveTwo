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

import Model.TaskHistoryData;


public class TaskHistoryAdapter extends FirebaseRecyclerAdapter<TaskHistoryData,TaskHistoryAdapter.taskHistoryViewHolder> {
    private List<TaskHistoryData> dataList;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TaskHistoryAdapter(@NonNull FirebaseRecyclerOptions<TaskHistoryData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull taskHistoryViewHolder holder, int position, @NonNull TaskHistoryData model) {
        holder.task.setText(model.getTask());
        holder.category.setText(model.getCategory());
        holder.description.setText(model.getDescription());
        holder.priority.setText(model.getPriority());
        holder.date.setText(model.getDate());
        holder.status.setText(model.getStatus());
        //holder.time.setText(model.getTime());
    }

    @NonNull
    @Override
    public taskHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_recycler_data,parent,false);
        return new taskHistoryViewHolder(view);
    }

    public void setTaskHistoryDataList(List<TaskHistoryData> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    static class taskHistoryViewHolder extends RecyclerView.ViewHolder{
        TextView task, category, description, priority, date, status,time;

        public taskHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            date = (TextView)itemView.findViewById(R.id.hdate_txt_task);
            task = (TextView)itemView.findViewById(R.id.htaskname_txt_tasks);
            category = (TextView)itemView.findViewById(R.id.hcategory_txt_tasks);
            description = (TextView) itemView.findViewById(R.id.desc_txt_tasks);
            priority = (TextView) itemView.findViewById(R.id.hpriority_txt_tasks);
            status = (TextView) itemView.findViewById(R.id.hstatus_txt_tasks);
            time = (TextView) itemView.findViewById(R.id.time_txt_task);
        }
    }
}
