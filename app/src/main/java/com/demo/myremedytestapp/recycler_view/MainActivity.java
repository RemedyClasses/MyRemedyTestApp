package com.demo.myremedytestapp.recycler_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.demo.myremedytestapp.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Employee> employeesList = new ArrayList<>();
    private EmployeeAdapter mEmployeeAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create object of recycler-view
        recyclerView = findViewById(R.id.recyclerView);

        //Create layout-manager to align list-item in horizontal/vertical order
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);

        //Create adapter object
        mEmployeeAdapter = new EmployeeAdapter(this, employeesList);

        //Set layout-manager
        recyclerView.setLayoutManager(layoutManager);

        //Set adapter for recycler-view
        recyclerView.setAdapter(mEmployeeAdapter);

        //Call method to get employee data
        generateDataOfEmployee();

    }

    //Method to create and add Employee within the list
    private void generateDataOfEmployee() {
        //Object of First-Employee
        Employee employee1 = new Employee();
        employee1.setName("Remedy Classes");
        employee1.setSalary(5000);

        //Object of Second-Employee
        Employee employee2 = new Employee();
        employee2.setName("Surjeet Kumar");
        employee2.setSalary(6000);

        //Object of Third-Employee
        Employee employee3 = new Employee();
        employee3.setName("Ajeet Kumar");
        employee3.setSalary(10000);

        //Add all employee within the list
        employeesList.add(employee1); //index in arraylist = 0
        employeesList.add(employee2);//index in arraylist = 1
        employeesList.add(employee3);//index in arraylist = 2

        //Notify Adapter for data-change
        mEmployeeAdapter.notifyDataSetChanged();

        //To show data within Log
        for(int i = 0; i<employeesList.size(); i++){
            String name = employeesList.get(i).getName();
            Log.i("@@Name = ", name);
            String city = employeesList.get(i).getCity();
            Log.i("@@City = ", String.valueOf(city));
            int salary = employeesList.get(i).getSalary();
            Log.i("@@Salary = ", String.valueOf(salary));


        }

    }
}
