package com.demo.myremedytestapp.recycler_view;

public class Employee {
    private String name;
    private int salary;
    private String doj;
    private String city;

    //Setter for emp-name
    public void setName(String empName){
        this.name = empName;
    }

    //Getter for emp-Name
    public String getName(){
        return this.name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", doj='" + doj + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
