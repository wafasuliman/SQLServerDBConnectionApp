package com.example.mymssqlserverdatabaseconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private TextView txetView2;
    private EditText id;
    private EditText name;
    private Button Addbutton;
    private static final String ip="192.168.100.187";
    private static final String port="60557";
    private static final String Classes="net.sourceforge.jtds.jdbc.Driver";
    private static final String database = "testDatabase";
    private static final String username="wafacon";
    private static final String password="1234";
    private static final String url="jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        textView=findViewById(R.id.txetView);
        txetView2=findViewById(R.id.txetView2);
        Addbutton=findViewById(R.id.Addbutton);
        id=findViewById(R.id.editTextId);
        name=findViewById(R.id.editTextTextName);

        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        txetView2.setVisibility(View.GONE);

        try {
            Class.forName(Classes);
            connection= DriverManager.getConnection(url,username,password);
            textView.setText("Connection successful");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            textView.setText("Connection failure");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            textView.setText("SQL Connection failure");
        }

    Addbutton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (connection != null) {
                Statement statement = null;

                try {

                    statement = connection.createStatement();
                    statement.executeQuery("INSERT INTO TEST_TABLE2 (id,name) VALUES ("+id.getText().toString()+",'"+name.getText().toString()+"')");
                    //INSERT INTO TEST_TABLE2 (id,name) VALUES (1223,'wafa');

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                txetView2.setVisibility(View.VISIBLE);
                txetView2.setText(name.getText().toString()+" is added!");
                textView.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();

            }
            else{
                textView.setText("Connection is null");
            }

        }
    });


    }
   /* This method worked well but I disable it to try (setOnClickListener)
   public void sqlButton (View view) {
        textView.setVisibility(View.GONE);
        if (connection != null) {
            Statement statement = null;

            try {
                statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("INSERT INTO TEST_TABLE2 (id,name) VALUES ('"+id.getText().toString()+"','"+name.getText().toString()+"')");

               while(resultSet.next()){
                  // textView.setText(resultSet.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            txetView2.setText(name.getText().toString()+" is added!");
        }
        else{
            textView.setText("Connection is null");
        }
    }*/

    public void deletebutton (View view){
        textView.setVisibility(View.GONE);
            if (connection != null) {
                Statement statement = null;

                try {
                    statement = connection.createStatement();
                    statement.executeQuery("delete from TEST_TABLE2 where id ="+id.getText().toString());

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                txetView2.setVisibility(View.VISIBLE);
                txetView2.setText(name.getText().toString()+" is no more available!");
            }
            else{
                textView.setText("Connection is null");
            }

    }

    public void updatebutton (View view){
        textView.setVisibility(View.GONE);
        if (connection != null) {
            Statement statement = null;
            txetView2.setVisibility(View.VISIBLE);
            txetView2.setText("Name with ID "+id.getText().toString()+" is Updated!");
            try {
                statement = connection.createStatement();
                statement.executeQuery("update TEST_TABLE2 set name='"+name.getText().toString()+"'where id="+id.getText().toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            textView.setText("Connection is null");
        }

    }
}