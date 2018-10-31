package com.example.philong.wedservices;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SinhVienAdapterTest extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<SinhVien> SinhVienList;


    public SinhVienAdapterTest(MainActivity context, int layout, List<SinhVien> sinhVienList) {
        this.context = context;
        this.layout = layout;
        this.SinhVienList = sinhVienList;
    }

    @Override
    public int getCount() {
        return SinhVienList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
    TextView txtTen,txtNamSinh,txtDiaChi;
    ImageView imgSua,imgXoa;


    }

    //Alert Xác nhận xóa
    private void XacNhanXoa(String ten, final int id){

        AlertDialog.Builder dialogxoa=new AlertDialog.Builder(context);
        dialogxoa.setMessage("Ban co muon xoa sinh vien : "+ten+" khong");
        dialogxoa.setPositiveButton("Co", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            context.DeleteStudent(id);
            }
        });
        dialogxoa.setNegativeButton("khong", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogxoa.show();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(layout,null);

            holder.txtTen=convertView.findViewById(R.id.text_view_ten);
            holder.txtDiaChi=convertView.findViewById(R.id.text_view_diachi);
            holder.txtNamSinh=convertView.findViewById(R.id.text_view_namsinh);
            holder.imgSua= convertView.findViewById(R.id.image_view_edit);
            holder.imgXoa=convertView.findViewById(R.id.image_view_delete);
            convertView.setTag(holder); // nếu else thì giữ ánh xạ vào biến holder
        }
        else{
           holder=(ViewHolder) convertView.getTag();
        }

        final SinhVien SinhVien=SinhVienList.get(position);

        holder.txtTen.setText(SinhVien.getHoTen());
        holder.txtNamSinh.setText("Nam sinh : "+SinhVien.getNamSinh());
        holder.txtDiaChi.setText(SinhVien.getDiaChi());

        holder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            //bắt sự kiện xóa sửa
            public void onClick(View v) {
                Intent intent=new Intent(context,Update.class);   //truyền extra
                intent.putExtra("dataSinhVien",SinhVien);
                context.startActivity(intent);
            }
        });
        //click xóa
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            XacNhanXoa(SinhVien.getHoTen(),SinhVien.getID());
            }
        });
        return convertView;
    }

}
