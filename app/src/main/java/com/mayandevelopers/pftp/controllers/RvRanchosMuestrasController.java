package com.mayandevelopers.pftp.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.models.RanchosModel;
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
    public void onBindViewHolder(@NonNull RvRanchosMuestrasController.ViewHolder viewHolder, int i) {
       // final EspeciesModel Lonuevo = mData.get(position);




        /*holder.precio_nuevo.setText(Lonuevo.getPrecio_nuevo());
        holder.vigencia_nuevo.setText(Lonuevo.getVigencia_nuevo());
        holder.nombre_nuevo.setText(Lonuevo.getNombre_nuevo());
        Glide.with(mContext).load(mData.get(position).getImg_nuevo()).error(R.drawable.default_picture_promo).into(holder.imag_nuevo);*/


        // IR A OTRA ACTIVIDAD //
        viewHolder.btn_detalles.setOnClickListener(new View.OnClickListener() {
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
        Button btn_detalles;

        public ViewHolder(View view) {
            super(view);

            txt_nombre_rancho= (TextView) view.findViewById(R.id.txtNombreRanchoMuestras);
            btn_detalles= (Button) view.findViewById(R.id.btnMuestrasRanchos);
           // relativeLayout=(RelativeLayout)view.findViewById(R.id.capa__lonuevo);

        }


    }


}
