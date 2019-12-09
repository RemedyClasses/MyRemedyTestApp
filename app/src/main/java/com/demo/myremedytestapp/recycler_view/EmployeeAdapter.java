package com.demo.myremedytestapp.recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.myremedytestapp.R;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyEmpViewHolder>{
    private Context mContext;
    private ArrayList<Employee> allEmpList;

    //Constructor of adapter class to receive list and context from calling class
    public EmployeeAdapter(Context context, ArrayList<Employee> empList){
        this.mContext = context;
        this.allEmpList = empList;
    }


    @NonNull
    @Override
    public MyEmpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate layout for the view-holder class
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,
                parent, false);
        //Return object of "View-Holder" class
        return new MyEmpViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyEmpViewHolder holder, int position) {
        //Set value of all fields in UI
        //Take data from list that have been received in constructor
        holder.textName.setText(allEmpList.get(position).getName());
        holder.textCity.setText(String.valueOf(allEmpList.get(position).getCity()));
        holder.textDoj.setText(String.valueOf(allEmpList.get(position).getDoj()));
        holder.textSalary.setText(String.valueOf(allEmpList.get(position).getSalary()));
    }

    @Override
    public int getItemCount() {
        //Return total number of data within the list
        return allEmpList.size();
    }

    //View Holder class to hold the design layout for every data/employee
    class MyEmpViewHolder extends RecyclerView.ViewHolder{
        TextView textName, textSalary, textDoj, textCity;

        //Constructor for receiving object of holder class & layout
        public MyEmpViewHolder(@NonNull View itemView) {
            super(itemView);
            //Object creation of layout items
            textName = itemView.findViewById(R.id.textName);
            textSalary = itemView.findViewById(R.id.textSalary);
            textDoj = itemView.findViewById(R.id.textDoj);
            textCity = itemView.findViewById(R.id.textCity);
        }
    }

}
