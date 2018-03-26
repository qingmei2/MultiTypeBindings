# MultiTypeBindings

DataBinding库对RecyclerView列表的一种实现方式。

[一行Java代码实现RecyclerViewAdapter?一行都不需要！](https://blog.csdn.net/mq2553299/article/details/79661821)

## 功能

* xml配置 实现单类型列表
* xml配置 实现多类型列表（sample默认提供了多类型列表的展示demo）

## 使用方式

### 1、依赖配置

该库需要DataBinding和Java8的支持，因此你需要添加对应的配置

```groovy
android {
    //...
    dataBinding {
        enabled = true
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```

添加MultiTypeBindings的依赖：

```groovy

dependencies {
    implementation 'com.github.qingmei2:multitype_bindings:0.1.2'
}

```

请注意，multitype_bindings隐式地依赖了下面2个库.

```groovy
{
    api 'com.android.support:recyclerview-v7:26.1.0'    // recyclerview的依赖
    api 'me.drakeet.multitype:multitype:3.3.0'          // drakeet的multitype库**
}
```
这里提供了gradle添加依赖的配置以供快速开发，但我们依然建议您将源码拉下来，作为工具类在项目中修改使用或添加进行个性化配置。


### 2、初始化Activity

```java
public class MainActivity extends AppCompatActivity {

   //要展示的数据源
    public final ObservableArrayList<Student> showDatas = new ObservableArrayList<>();

    {
        //初始化数据源,实际项目中，被网络请求，数据库读取等操作代替
        for (int i = 0; i < 20; i++) {
            students.add(new Student("学生:" + i));
        }
        showDatas.addAll(students);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DataBinding的初始化
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);
    }

    public void onBindItem(ViewDataBinding binding, Object data, int position) {
        binding.getRoot().setOnClickListener(v -> Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show());
    }

    //数据的实体类
    public class Student {
          public String name;
          public Student(String name) {
            this.name = name;
          }
      }

}
```

### 3、实现布局和RecyclerView的配置：

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="activity"
            type="com.qingmei2.simplerecyclerview.MainActivity" />
    </data>

    <android.support.v7.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:items="@{activity.showDatas}"
        app:layoutManager="@string/linear_layout_manager"
        app:itemLayout="@{@layout/item_student_list}"
        app:onBindItem="@{activity::onBindItem}" />
</layout>
```

### 4、实现item的布局文件和对应的配置：

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="data"
            type="com.qingmei2.simplerecyclerview.Student" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="40dp"
        android:orientation="vertical">

         <!--显示student.name到TextView-->
         <TextView
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1"
            android:gravity="center_vertical"
            android:padding="8dp"
            android:text="@{data.name}"
            tools:text="小明" />
    </LinearLayout>
</layout>
```

### 最后运行app，展示列表

![singleType](https://upload-images.jianshu.io/upload_images/7293029-603b368a243cf449.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


