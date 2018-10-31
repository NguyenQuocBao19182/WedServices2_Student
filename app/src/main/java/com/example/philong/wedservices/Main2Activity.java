package com.example.philong.wedservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {
    EditText edtTen,edtNamSinh,edtDiaChi;
    Button btThem;
    String urlInsert="http://192.168.56.1:81/androidwebservice/insert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        anhXa();

        btThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen=edtTen.getText().toString().trim();
                String namSinh=edtNamSinh.getText().toString().trim();
                String diaChi=edtDiaChi.getText().toString().trim();
                if(hoTen.isEmpty()||namSinh.isEmpty()||diaChi.isEmpty()){
                    Toast.makeText(Main2Activity.this, "Nhap du thong tin nha", Toast.LENGTH_SHORT).show();

                }
                else {
                    themSinhVien(urlInsert);
                }
            }
        });
    }


    private void themSinhVien(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        //POST để đấy lên
       StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {//khi insert thành công
            if(response.trim().equals("success")){//success là báo thành công trên php lấy xuống để dùng
                Toast.makeText(Main2Activity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main2Activity.this,MainActivity.class));
            }
            else {

                Toast.makeText(Main2Activity.this, "có lỗi gì rồi", Toast.LENGTH_SHORT).show();
            }
           }
       }, new Response.ErrorListener() {//khi insert thất bại
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(Main2Activity.this, "Loi ", Toast.LENGTH_SHORT).show();
               Log.d("AAA","Loi!\n"+error.toString());//chi tiết lỗi
           }
       }
       ){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               //tạo map để đẩy lên
               Map<String,String> params=new HashMap<>();

               params.put("hotenSV",edtTen.getText().toString().trim());//đẩy lên Json hotenSV với edtTen .trim để xóa khoảng trắng đầu và cuối
               params.put("namsinhSV",edtNamSinh.getText().toString().trim());//đẩy lên Json hotenSV với edtTen
               params.put("diachiSV",edtDiaChi.getText().toString().trim());//đẩy lên Json hotenSV với edtTen
               return params;
           }
       };

       requestQueue.add(stringRequest);//add vao

    }


    private void anhXa(){

        edtTen=findViewById(R.id.edt_ten);
        edtNamSinh=findViewById(R.id.edt_namsinh);
        edtDiaChi=findViewById(R.id.edt_diachi);
        btThem=findViewById(R.id.btn_them);

    }

}
