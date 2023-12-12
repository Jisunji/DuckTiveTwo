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

import Model.HabitData;


public class HabitAdapter extends FirebaseRecyclerAdapter<HabitData,HabitAdapter.habitViewHolder> {
    private List<HabitData> dataList;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public HabitAdapter(@NonNull FirebaseRecyclerOptions<HabitData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull habitViewHolder holder, int position, @NonNull HabitData model) {
        holder.habit.setText(model.getHabitName());
        holder.description.setText(model.getHabitDescription());
        holder.time.setText(model.getHabitTime());
        holder.date.setText(model.getHabitDate());
    }

    @NonNull
    @Override
    public habitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habits_recycler_data,parent,false);
        return new habitViewHolder(view);
    }

    public void setHabitDataList(List<HabitData> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    static class habitViewHolder extends RecyclerView.ViewHolder{
        TextView habit, description, time, date;

        public habitViewHolder(@NonNull View itemView) {
            super(itemView);

            habit = itemView.findViewById(R.id.habitsname_txt_habits);
            description = itemView.findViewById(R.id.desc_txt_habits);
            time = itemView.findViewById(R.id.time_txt_habits);
            date = itemView.findViewById(R.id.date_txt_habits);

        }
    }

}
