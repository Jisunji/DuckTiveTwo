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

import Model.TaskData;


public class TaskAdapter extends FirebaseRecyclerAdapter<TaskData,TaskAdapter.taskViewHolder> {
    private List<TaskData> dataList;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TaskAdapter(@NonNull FirebaseRecyclerOptions<TaskData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull taskViewHolder holder, int position, @NonNull TaskData model) {
        holder.task.setText(model.getTask());
        holder.category.setText(model.getCategory());
        holder.description.setText(model.getDescription());
        holder.priority.setText(model.getPriority());
        holder.date.setText(model.getDate());
    }

    @NonNull
    @Override
    public taskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_recycler_data,parent,false);
        return new taskViewHolder(view);
    }

    public void setTaskDataList(List<TaskData> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    static class taskViewHolder extends RecyclerView.ViewHolder{
        TextView task, category, description, priority, date;

        public taskViewHolder(@NonNull View itemView) {
            super(itemView);

            date = (TextView)itemView.findViewById(R.id.date_txt_task);
            task = (TextView)itemView.findViewById(R.id.taskname_txt_tasks);
            category = (TextView)itemView.findViewById(R.id.category_txt_tasks);
            description = (TextView) itemView.findViewById(R.id.desc_txt_tasks);
            priority = (TextView) itemView.findViewById(R.id.priority_txt_tasks);

        }
    }

}
