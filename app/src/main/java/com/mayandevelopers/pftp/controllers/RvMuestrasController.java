package com.mayandevelopers.pftp.controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.models.MuestrasModel;
import com.mayandevelopers.pftp.models.VisitasModel;

import java.util.List;

public class RvMuestrasController extends RecyclerView.Adapter<RvMuestrasController.ViewHolder> {


    private Context mContext;
    private List<MuestrasModel> mData;

    public RvMuestrasController(Context mContext, List<MuestrasModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public RvMuestrasController.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_muestras,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvMuestrasController.ViewHolder viewHolder, int i) {
       // final EspeciesModel Lonuevo = mData.get(position);




        /*holder.precio_nuevo.setText(Lonuevo.getPrecio_nuevo());
        holder.vigencia_nuevo.setText(Lonuevo.getVigencia_nuevo());
        holder.nombre_nuevo.setText(Lonuevo.getNombre_nuevo());
        Glide.with(mContext).load(mData.get(position).getImg_nuevo()).error(R.drawable.default_picture_promo).into(holder.imag_nuevo);



        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,PromocionActivity.class);
                intent.putExtra("id_company",Lonuevo.getId_company());
                intent.putExtra("id_promo",Lonuevo.getId_promo());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });*/

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

            txt_fecha_visita= (TextView) view.findViewById(R.id.txtMuestra);
            btn_editar= (ImageButton) view.findViewById(R.id.imgbtnEditarMuestra);
            btn_eliminar= (ImageButton) view.findViewById(R.id.imgbtnEliminarMuestra);
           // relativeLayout=(RelativeLayout)view.findViewById(R.id.capa__lonuevo);

        }


    }


}
