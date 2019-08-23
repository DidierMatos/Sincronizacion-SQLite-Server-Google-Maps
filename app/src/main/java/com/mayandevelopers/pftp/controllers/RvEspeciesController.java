package com.mayandevelopers.pftp.controllers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.models.EspeciesModel;

import java.util.List;

public class RvEspeciesController extends RecyclerView.Adapter<RvEspeciesController.ViewHolder> {


    Context mContext;
    private List<EspeciesModel> mData;

    public RvEspeciesController(Context mContext, List<EspeciesModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public RvEspeciesController.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_especies,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvEspeciesController.ViewHolder viewHolder, int i) {
       // final EspeciesModel Lonuevo = mData.get(position);

        /*holder.precio_nuevo.setText(Lonuevo.getPrecio_nuevo());
        holder.vigencia_nuevo.setText(Lonuevo.getVigencia_nuevo());
        holder.nombre_nuevo.setText(Lonuevo.getNombre_nuevo());
        Glide.with(mContext).load(mData.get(position).getImg_nuevo()).error(R.drawable.default_picture_promo).into(holder.imag_nuevo);*/



        viewHolder.eliminar_arboles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mostrar un alert dialog personalizado //
                LayoutInflater mInflater = LayoutInflater.from(mContext);

                androidx.appcompat.app.AlertDialog.Builder mBuilder = new androidx.appcompat.app.AlertDialog.Builder(mContext);
                View mView = mInflater.inflate(R.layout.popup_eliminar,null);

                Button btn_cancelar = (Button) mView.findViewById(R.id.btnCancelarDelete);
                Button btnEliminar = (Button) mView.findViewById(R.id.btnEliminar);

                mBuilder.setView(mView);
                final androidx.appcompat.app.AlertDialog dialog = mBuilder.create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                btn_cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {

        TextView nombre_especie;
        ImageButton ver_arboles;
        ImageButton editar_arboles;
        ImageButton eliminar_arboles;

        public ViewHolder(View view) {
            super(view);

            nombre_especie= (TextView) view.findViewById(R.id.txtNombreEspecie);
            ver_arboles= (ImageButton) view.findViewById(R.id.imgbtnVerArboles);
            editar_arboles=(ImageButton) view.findViewById(R.id.imgbtnEditarArboles);
            eliminar_arboles=(ImageButton) view.findViewById(R.id.imgbtnEliminarArboles);
           // relativeLayout=(RelativeLayout)view.findViewById(R.id.capa__lonuevo);

        }
    }
}
