package com.example.admin.contentproviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static android.Manifest.permission.CALL_PHONE;

class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolderClass> {
    Context context;

    ArrayList<Pojo> arr1;

    public RecycleAdapter( Context context,  ArrayList<Pojo> arr1) {
        this.context=context;
        this.arr1=arr1;
    }

    @Override
    public ViewHolderClass onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_recycler_view,viewGroup,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(ViewHolderClass holder, final int i1) {
        holder.txt1.setText(arr1.get(i1).getName());
        holder.txt2.setText(arr1.get(i1).getPhoneNumber());
       holder.txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:" + arr1.get(i1).getPhoneNumber()));
                    context.startActivity(i);
                }
                else {
                    requestPermission();
                }
            }
        });

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions((Activity) context,new String[]
                {android.Manifest.permission.CALL_PHONE},100);
    }

    private boolean checkPermission() {
        int call= ContextCompat.checkSelfPermission(context,CALL_PHONE);
        if (call== PackageManager.PERMISSION_GRANTED){
            return true;
        }else {
            return false;
        }

    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {

            case 100:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED

                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {



                    Log.e("value", "Permission Granted, Now you can use local drive .");

                } else {

                    Log.e("value", "Permission Denied, You cannot use local drive .");

                }

                break;

        }
    }

    @Override
    public int getItemCount() {
        return arr1.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder {
        TextView txt1;
        TextView txt2;
        public ViewHolderClass(View itemView) {
            super(itemView);
            txt1=itemView.findViewById(R.id.txt1);
            txt2=itemView.findViewById(R.id.txt2);
        }
    }
}
