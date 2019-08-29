package com.mayandevelopers.pftp.controllers;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.models.RanchosModel;
import com.mayandevelopers.pftp.views.ArbolesActivity;
import com.mayandevelopers.pftp.views.MuestrasRanchosActivity;

import java.util.List;

public class RvRanchosMuestrasController extends RecyclerView.Adapter<RvRanchosMuestrasController.ViewHolder> {


    private Context mContext;
    private List<RanchosModel> mData;

    public RvRanchosMuestrasController(Context mContext, List<RanchosModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public RvRanchosMuestrasController.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_ranchos_muestras,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvRanchosMuestrasController.ViewHolder viewHolder, int position) {
        final RanchosModel rancho = mData.get(position);

        viewHolder.txt_nombre_rancho.setText(rancho.getNombreRancho());

        viewHolder.btn_muestras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ArbolesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });



        // IR A OTRA ACTIVIDAD //
        viewHolder.btn_muestras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MuestrasRanchosActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {


        TextView txt_nombre_rancho;
        Button btn_muestras;

        public ViewHolder(View view) {
            super(view);

            txt_nombre_rancho= (TextView) view.findViewById(R.id.txtNombreRanchoMuestras);
            btn_muestras= (Button) view.findViewById(R.id.btnMuestrasRanchos);
           // relativeLayout=(RelativeLayout)view.findViewById(R.id.capa__lonuevo);

        }


    }


}
