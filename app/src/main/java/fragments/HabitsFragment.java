package fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ducktivetwo.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;


public class HabitsFragment extends Fragment {

    private FloatingActionButton habitplusfab;
    private FloatingActionButton habitsbtn;

    private TextView fab_habits_txt;

    private boolean isOpen = false;

    private Animation FadeOpen, FadeClose;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vvv = inflater.inflate(R.layout.fragment_habits, container, false);

        habitplusfab = vvv.findViewById(R.id.habits_plus_btn);
        habitsbtn = vvv.findViewById(R.id.habits_ft_btn);

        fab_habits_txt = vvv.findViewById(R.id.habits_ft_text);

        FadeOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_open);
        FadeClose = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_close);

        habitplusfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHabits();

                if (isOpen) {
                    habitsbtn.startAnimation(FadeClose);
                    habitsbtn.setClickable(false);

                    fab_habits_txt.startAnimation(FadeClose);
                    fab_habits_txt.setClickable(false);
                    isOpen = false;
                } else {
                    habitsbtn.startAnimation(FadeOpen);
                    habitsbtn.setClickable(true);

                    fab_habits_txt.startAnimation(FadeOpen);
                    fab_habits_txt.setClickable(true);

                    isOpen = true;
                }
            }
        });
        return vvv;
    }

    private void addHabits() {
        habitsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habitsDataInsert();
            }
        });
    }

    private void ftAnimation() {
        if (isOpen) {
            habitsbtn.startAnimation(FadeClose);

            habitsbtn.setClickable(false);


            fab_habits_txt.startAnimation(FadeClose);

            fab_habits_txt.setClickable(false);

            isOpen = false;
        } else {
            habitsbtn.startAnimation(FadeOpen);

            habitsbtn.setClickable(true);


            fab_habits_txt.startAnimation(FadeOpen);

            fab_habits_txt.setClickable(true);

            isOpen = true;
        }
    }

    public void habitsDataInsert() {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View myview = inflater.inflate(R.layout.custom_layout_for_creatinghabits, null);
        mydialog.setView(myview);
        final AlertDialog dialog = mydialog.create();

        dialog.setCancelable(false);

        EditText edthabitsname = myview.findViewById(R.id.habitsname_edt);
        EditText edthabitsDescription = myview.findViewById(R.id.habitsdescription_edt);

        Button btnSave = myview.findViewById(R.id.btnSave);
        Button btnCancel = myview.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String habitsname = edthabitsname.getText().toString().trim();
                String notes = edthabitsDescription.getText().toString().trim();

                if (TextUtils.isEmpty(habitsname)) {
                    edthabitsname.setError("Required Field..");
                    return;
                }
                if (TextUtils.isEmpty(notes)) {
                    edthabitsDescription.setError("Required Field..");
                    return;
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftAnimation();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}