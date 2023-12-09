package fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ducktivetwo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class TasksFragment extends Fragment {
    private FloatingActionButton fabplusfab;
    private FloatingActionButton fabcreatetasks;


    private TextView fab_tasks1_txt;


    //boolean
    private boolean isOpen = false;

    //animation

    private Animation FadeOpen, FadeClose;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vv = inflater.inflate(R.layout.fragment_tasks, container, false);
        // Inflate the layout for this fragment


        fabplusfab = vv.findViewById(R.id.tasks_plus_btn);
        fabcreatetasks = vv.findViewById(R.id.tasks1_ft_btn);

        fab_tasks1_txt = vv.findViewById(R.id.tasks1_ft_text);

        FadeOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_open);
        FadeClose = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_close);

        fabplusfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                if (isOpen) {
                    fabcreatetasks.startAnimation(FadeClose);
                    fabcreatetasks.setClickable(false);

                    fab_tasks1_txt.startAnimation(FadeClose);
                    fab_tasks1_txt.setClickable(false);
                    isOpen = false;
                } else {
                    fabcreatetasks.startAnimation(FadeOpen);
                    fabcreatetasks.setClickable(true);

                    fab_tasks1_txt.startAnimation(FadeOpen);
                    fab_tasks1_txt.setClickable(true);

                    isOpen = true;
                }
            }
        });
        return vv;
    }

    private void ftAnimation() {
        if (isOpen) {
            fabcreatetasks.startAnimation(FadeClose);

            fabcreatetasks.setClickable(false);


            fab_tasks1_txt.startAnimation(FadeClose);

            fab_tasks1_txt.setClickable(false);

            isOpen = false;
        } else {
            fabcreatetasks.startAnimation(FadeOpen);

            fabcreatetasks.setClickable(true);


            fab_tasks1_txt.startAnimation(FadeOpen);

            fab_tasks1_txt.setClickable(true);

            isOpen = true;
        }
    }

    private void addData() {
        //Fab Button task...
        fabcreatetasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            taskDataInsert();

            }
        });
    }

    public void taskDataInsert() {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View myview = inflater.inflate(R.layout.custom_layout_for_creatingtasks, null);
        mydialog.setView(myview);
        final AlertDialog dialog = mydialog.create();

        dialog.setCancelable(false);

        EditText edtTaskName = myview.findViewById(R.id.taskname_edt);
        EditText edtCategory = myview.findViewById(R.id.category_edt);
        EditText edtDescription = myview.findViewById(R.id.description_edt);

        Button btnSave = myview.findViewById(R.id.btnSave);
        Button btnCancel = myview.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = edtCategory.getText().toString().trim();
                String taskName = edtTaskName.getText().toString().trim();
                String note = edtDescription.getText().toString().trim();

                if (TextUtils.isEmpty(category)) {
                    edtCategory.setError("Required Field..");
                    return;
                }

                if (TextUtils.isEmpty(taskName)) {
                    edtTaskName.setError("Required Field..");
                    return;
                }

                if (TextUtils.isEmpty(note)) {
                    edtDescription.setError("Required Field..");
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