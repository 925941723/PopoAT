package com.light.zenghaitao.popoat.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ly-zenghaitao on 2016/4/20.
 */
@DatabaseTable(tableName = "databaseBean")
public class DatabaseBean {
    public static final String ID = "id";
    public static final String PEOPLE = "people";
    public static final String AGE = "age";
    public static final String SEX = "sex";
    public static final String ADDRESS = "address";

    @DatabaseField(generatedId = true, useGetSet = true, columnName = ID)
    private int id;
    @DatabaseField(useGetSet = true, columnName = PEOPLE)
    private String people;
    @DatabaseField(useGetSet = true, columnName = AGE)
    private int age;
//    @DatabaseField(useGetSet = true, columnName = SEX)
//    private String sex;
//    @DatabaseField(useGetSet = true, columnName = ADDRESS)
//    private String address;

    public DatabaseBean(){}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }

    public String getPeople() {

        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

//    public String getSex() {
//        return sex;
//    }
//
//    public void setSex(String sex) {
//        this.sex = sex;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//



}
