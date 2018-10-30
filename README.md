# [DEPRECATED] MultiTypeBindings

DataBinding库对RecyclerView列表的一种实现方式。

[零行Java代码实现RecyclerView](https://blog.csdn.net/mq2553299/article/details/79661821)

这里提供了gradle添加依赖的方式以方便快速开发，但个人依然建议您fork源码，并进行个性化配置和修改，因为：

> 这里仅仅是提供了实现列表的一种思路。

## 废弃通知（2018/10/30）

距离发布[这篇博客](https://blog.csdn.net/mq2553299/article/details/79661821)时隔数月，不少的同行针对我的实现方式提出了各种各样的问题，包括但不限于：

> XXXXX(上拉加载更多，错误界面的展示，Header或者Footer)怎么实现？

回顾初心，我写当初这篇博客的目的是 **展示一种不同的实现思路**，但这种实现方式并非是**非常优秀**的，从某种意义上讲，我认为这种方式也存在一些弊端（比如，依赖Databinding库，必须在`Activity`容器实现回调函数的不友好等），因此我一直在强调，**这个仓库只是一个Demo，仅供参考**。

现在我把这个库废弃了，原因有二，**最主要的原因是**：从面向对象的编程思想来看，我个人不赞同将和列表展示无直接关联的功能 放在 RecyclerView的Adapter中（包括上拉加载，下拉刷新，以及异常页面），google官方出了一个单独的SwipeRefreshView而没有出一个SwipeRefreshAdapter，我相信也是有这样一部分的原因，因此我建议将上述不同的功能和`RecyclerView`的`Adapter`进行隔离。

**其次**，半年后的我不再满意于这样的实现方式，我更沉迷于[这个Demo中新的实践](https://github.com/qingmei2/MVVM-Rhine),它达到了我上文中不同功能通过不同控件进行**绑定**，而又没有代码的强耦合掺和在一起的设计理念。

相比star，我更看重学习过程中对 **编程思想** 的逐步理解，因此我决定废弃这个repo，**它不应该被您通过compile依赖在您的项目中**，对于`DataBinding`的学习者，我更希望看到它能加深您对DataBinding的本质即： **观察者模式（数据驱动视图）** 的理解。

当然，考虑到Databinding实际项目的运用，我推荐这个仓库，至少目前为止，它是我比较满意的设计：

> [MVVM-Rhine: MVVM+Jetpack架构的Github客户端](https://github.com/qingmei2/MVVM-Rhine)

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

* 请注意，multitype_bindings隐式地依赖了下面2个库(但并不需要在gradle文件中声明它们).

```groovy
{
    api 'com.android.support:recyclerview-v7:26.1.0'    // recyclerview的依赖
    api 'me.drakeet.multitype:multitype:3.3.0'          // drakeet的multitype库**
}
```


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

<div align="left"><img width="300" height="540" src="https://upload-images.jianshu.io/upload_images/7293029-603b368a243cf449.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240"/></div>


