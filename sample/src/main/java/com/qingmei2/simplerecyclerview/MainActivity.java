package com.qingmei2.simplerecyclerview;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.qingmei2.multitype_binding.binding.Linker;
import com.qingmei2.simplerecyclerview.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final ObservableArrayList<Object> showDatas = new ObservableArrayList<>();

    public final ObservableArrayList<Linker> linkers = new ObservableArrayList<>();

    public final List<Student> students = new ArrayList<>();
    public final List<Teacher> teachers = new ArrayList<>();


    {
        for (int i = 0; i < 20; i++) {
            students.add(new Student("学生:" + i));
        }
        for (int j = 0; j < 5; j++) {
            teachers.add(new Teacher("教师:" + j, "年龄：" + (20 + j)));
        }
        linkers.add(
                new Linker(
                        o -> o instanceof Student,
                        R.layout.item_student_list
                )
        );
        linkers.add(
                new Linker(
                        o -> o instanceof Teacher,
                        R.layout.item_teacher_list
                )
        );
        showDatas.addAll(students);
        showDatas.addAll(teachers);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        initRecyclerView();
    }

    private void initRecyclerView() {
        students.addAll(students);
    }

    public void onBindItem(ViewDataBinding binding, Object data, int position) {
        binding.getRoot().setOnClickListener(v -> Toast.makeText(MainActivity.this, data.toString(), Toast.LENGTH_SHORT).show());
    }
}
