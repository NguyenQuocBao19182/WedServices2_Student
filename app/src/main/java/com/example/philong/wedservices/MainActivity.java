package com.example.philong.wedservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView lvSinhVien;
    ArrayList<SinhVien> arraySinhVien;
    SinhVienAdapterTest adapter;
    String urlGetData=" http://192.168.56.1:81/androidwebservice/getdata.php";
    String urlDeleteData="http://192.168.56.1:81/androidwebservice/delete.php";
    //Xóa
   public void DeleteStudent(final int idsv){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlDeleteData, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            if(response.trim().equals("success")){
                Toast.makeText(MainActivity.this, "XoaThanhCong", Toast.LENGTH_SHORT).show();
                GetData(urlGetData);
            }
            else Toast.makeText(MainActivity.this, "loi xoa", Toast.LENGTH_SHORT).show();
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Loi xoa", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("idSV", String.valueOf(idsv));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvSinhVien=findViewById(R.id.list_view_main);

        arraySinhVien=new ArrayList<>();
        adapter=new SinhVienAdapterTest(this,R.layout.dong_sinh_vien,arraySinhVien);
        lvSinhVien.setAdapter(adapter);

        GetData(urlGetData);
    }
    private void GetData (String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        //GET để lấy xuống
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
            @Override
            //khi doc duoc json

            public void onResponse(JSONArray response) {
                arraySinhVien.clear();
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        arraySinhVien.add(new SinhVien(
                                object.getInt("ID"),
                                object.getString("HoTen"),
                                object.getInt("NamSinh"),
                                object.getString("DiaChi")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
               adapter.notifyDataSetChanged();
            }
       },
                //khi doc json bi loi
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.add_sinh_vien,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.it_menu)
        {
        startActivity(new Intent(MainActivity.this,Main2Activity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
