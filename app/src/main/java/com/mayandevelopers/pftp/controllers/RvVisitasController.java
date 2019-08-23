package com.mayandevelopers.pftp.controllers;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.models.VisitasModel;
import com.mayandevelopers.pftp.views.AgregarArbolActivity;
import com.mayandevelopers.pftp.views.AgregarVisitaActivity;

import java.util.List;

public class RvVisitasController extends RecyclerView.Adapter<RvVisitasController.ViewHolder> {


    private Context mContext;
    private List<VisitasModel> mData;

    public RvVisitasController(Context mContext, List<VisitasModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public RvVisitasController.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_visitas,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvVisitasController.ViewHolder viewHolder, int i) {
       // final EspeciesModel Lonuevo = mData.get(position);




        /*holder.precio_nuevo.setText(Lonuevo.getPrecio_nuevo());
        holder.vigencia_nuevo.setText(Lonuevo.getVigencia_nuevo());
        holder.nombre_nuevo.setText(Lonuevo.getNombre_nuevo());
        Glide.with(mContext).load(mData.get(position).getImg_nuevo()).error(R.drawable.default_picture_promo).into(holder.imag_nuevo);*/

        viewHolder.btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AgregarVisitaActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        viewHolder.btn_eliminar.setOnClickListener(new View.OnClickListener() {
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


        TextView txt_fecha_visita;
        ImageButton btn_editar;
        ImageButton btn_eliminar;

        public ViewHolder(View view) {
            super(view);

            txt_fecha_visita= (TextView) view.findViewById(R.id.txtVisita);
            btn_editar= (ImageButton) view.findViewById(R.id.imgbtnEditarVisita);
            btn_eliminar= (ImageButton) view.findViewById(R.id.imgbtnEliminarVisita);
           // relativeLayout=(RelativeLayout)view.findViewById(R.id.capa__lonuevo);

        }


    }


}
