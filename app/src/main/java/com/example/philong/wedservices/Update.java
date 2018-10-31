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

public class Update extends AppCompatActivity {
    private EditText edt_suaTen,edt_suaNamSinh,edt_suaDiaChi;
    private Button btn_sua;
    int id=0;
    String urlThem="http://192.168.56.1:81/androidwebservice/update.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        anhXa();

        Intent intent=getIntent();
        SinhVien sinhvien=(SinhVien) intent.getSerializableExtra("dataSinhVien"); //lấy Serializable(tuần tự) từ intent adapter
        id=sinhvien.getID();
        edt_suaTen.setText(sinhvien.getHoTen());   //gán giá trị vào edt
        edt_suaNamSinh.setText(sinhvien.getNamSinh()+"");
        edt_suaDiaChi.setText(sinhvien.getDiaChi());

        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen= edt_suaTen.getText().toString().trim();
                String namSinh= edt_suaNamSinh.getText().toString().trim();
                String diaChi= edt_suaDiaChi.getText().toString().trim();
                if(hoTen.isEmpty()||namSinh.isEmpty()||diaChi.isEmpty()){
                    Toast.makeText(Update.this, "Nhap du thong tin nha", Toast.LENGTH_SHORT).show();

                }
                else {
                    CapNhap(urlThem);
                }
            }
        });
    }

    public void CapNhap(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success")){//success là báo thành công trên php lấy xuống để dùng
                    Toast.makeText(Update.this, "Sua thanh cong", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Update.this,MainActivity.class));
                }
                else {

                    Toast.makeText(Update.this, "có lỗi gì rồi", Toast.LENGTH_SHORT).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update.this, "Loi ", Toast.LENGTH_SHORT).show();
                Log.d("AAA","Loi!\n"+error.toString());//chi tiết lỗi
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //tạo map để đẩy lên
                Map<String,String> params=new HashMap<>();
                params.put("idSV", String.valueOf(id));
                params.put("hotenSV",edt_suaTen.getText().toString().trim());//đẩy lên Json hotenSV với edtTen .trim để dùng khoảng trắng
                params.put("namsinhSV",edt_suaNamSinh.getText().toString().trim());//đẩy lên Json hotenSV với edtTen
                params.put("diachiSV",edt_suaDiaChi.getText().toString().trim());//đẩy lên Json hotenSV với edtTen
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }
   private void anhXa(){
        edt_suaTen=findViewById(R.id.edt_suaten);
        edt_suaNamSinh=findViewById(R.id.edt_suanamsinh);
        edt_suaDiaChi=findViewById(R.id.edt_suadiachi);
        btn_sua=findViewById(R.id.btn_capnhap);
    }
}
