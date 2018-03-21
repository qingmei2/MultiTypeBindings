package com.qingmei2.simplerecyclerview;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.qingmei2.simplerecyclerview.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final ObservableArrayList<Student> students = new ObservableArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        initRecyclerView();
    }

    private void initRecyclerView() {
        students.addAll(mockStudents());
    }

    public void onBindItem(ViewDataBinding binding, Object data, int position) {
        binding.getRoot().setOnClickListener(__ ->
                Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show());
    }

    private List<Student> mockStudents() {
        //本地假数据，代替网络请求
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            students.add(new Student("学生" + i));
        }
        return students;
    }

    public static class Student {

        public String name;

        public Student(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
