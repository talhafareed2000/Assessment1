package com.example.maprentals;
import java.lang.Math;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public  class Db {
    public Connection connect_to_db(String dbname,String user,String pass){
        Connection conn=null;
        try{
            Class.forName("org.postgresql.Driver");
            conn= DriverManager.getConnection("jdbc:postgresql://3.235.170.15/"+dbname,user,pass);
            if(conn!=null){
                System.out.println("Connection Established");
            }
            else{
                System.out.println("Connection Failed");
            }

        }catch (Exception e){
            System.out.println(e);
        }
        return conn;
    }
    public void createTable(Connection conn, String table_name){
        Statement statement;
        try{
            String query="create table "+table_name+"(empid SERIAL,name varchar(200),address varchar(200),primary key(empid));";
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table Created");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void insert_row(Connection conn,String table_name,String name, String address){
        Statement statement;
        try {
            String query=String.format("insert into %s(name,address) values('%s','%s');",table_name,name,address);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row Inserted");
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void read_data(Connection conn, String table_name){
        Statement statement;

        ResultSet rs=null;

        try {
            String query=String.format("select * from %s",table_name);
            statement=conn.createStatement();
            rs=statement.executeQuery(query);
            while(rs.next()){
                System.out.print(rs.getString("mls")+" ");
                System.out.println(rs.getString("rent")+" ");
                System.out.print(rs.getString("lat")+" ");   //fix // not printing full decimal value
                System.out.print(rs.getString("long")+" ");


            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public long GetAvg(Connection conn,double lat, double lng){
        Statement statement;
        ResultSet rs=null;
        double meters = 500; //0.5 km
        double coef = meters * 0.0000089;

        double new_lat = lat + coef;
        double new_long =  lng+ coef / Math.cos(lng * 0.018);

        List<Integer> total = new ArrayList<Integer>();

        try {
            String query=String.format("SELECT rent FROM dallas_rent Where (lat> 32.79 AND lat<34.83) AND (long<-94.23 AND long>-97.44)");
            statement=conn.createStatement();
            rs=statement.executeQuery(query);
            while (rs.next()){

                total.add(rs.getInt(1));
            }

            System.out.println(total);   //total array list
            long intSum = total.stream()   //sum
                    .mapToLong(Integer::longValue)
                    .sum();
            System.out.println(intSum);
            long Avg = intSum/ total.size();
            System.out.println(Avg);
            return Avg;


        }catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }
    public void search_by_id(Connection conn, String table_name,int id){
        Statement statement;
        ResultSet rs=null;
        try {
            String query=String.format("select * from %s where empid= %s",table_name,id);
            statement=conn.createStatement();
            rs=statement.executeQuery(query);
            while (rs.next()){
                System.out.print(rs.getString("empid")+" ");
                System.out.print(rs.getString("name")+" ");
                System.out.println(rs.getString("address"));

            }
        }catch (Exception e){
            System.out.println(e);
        }
    }






}





