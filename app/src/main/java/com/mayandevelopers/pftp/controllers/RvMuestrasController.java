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
import android.widget.Toast;

import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.databaseHelper.DatabaseAccessMuestras;
import com.mayandevelopers.pftp.models.ModeloPrueba;
import com.mayandevelopers.pftp.models.MuestrasModel;
import com.mayandevelopers.pftp.models.RanchosModel;
import com.mayandevelopers.pftp.views.ArbolesActivity;
import com.mayandevelopers.pftp.views.EstablecerMuestraActivity;

import java.util.List;

public class RvMuestrasController extends RecyclerView.Adapter<RvMuestrasController.ViewHolder> {


    private Context mContext;
    private List<MuestrasModel> mData;
    //List<ModeloPrueba> prueba;

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
    public void onBindViewHolder(@NonNull final RvMuestrasController.ViewHolder viewHolder, final int position) {
        final MuestrasModel muestra = mData.get(position);

        String nombre = muestra.getNombreMuestra();
        String fecha = muestra.getFechaMuestra();
        String nombre_fecha = nombre +" "+fecha;

        viewHolder.txt_fecha_visita.setText(nombre_fecha);


        /*holder.precio_nuevo.setText(Lonuevo.getPrecio_nuevo());
        holder.vigencia_nuevo.setText(Lonuevo.getVigencia_nuevo());
        holder.nombre_nuevo.setText(Lonuevo.getNombre_nuevo());
        Glide.with(mContext).load(mData.get(position).getImg_nuevo()).error(R.drawable.default_picture_promo).into(holder.imag_nuevo);*/


        viewHolder.btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_muestra = muestra.getIdMuestra();
                Intent intent = new Intent(mContext, EstablecerMuestraActivity.class);
                intent.putExtra("accion","update");
                intent.putExtra("id_muestra",id_muestra);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        viewHolder.btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mostrar un alert dialog personalizado //
                LayoutInflater mInflater = LayoutInflater.from(mContext);
                final DatabaseAccessMuestras databaseAccessMuestras = DatabaseAccessMuestras.getInstance(mContext);

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

                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String idMuestra = muestra.getIdMuestra();
                        // ELIMINAR DE LA BASE DE DATOS //
                        databaseAccessMuestras.eliminarMuestra(idMuestra);
                        dialog.dismiss();
                        mData.remove(viewHolder.getLayoutPosition());
                        notifyItemRemoved(viewHolder.getLayoutPosition());
                        Toast.makeText(mContext, "La muestra ha sido eliminada", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        /*viewHolder.btn_prueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseAccessMuestras databaseAccessMuestras = DatabaseAccessMuestras.getInstance(mContext);
                String idMuestra = "7";
                Toast.makeText(mContext, idMuestra, Toast.LENGTH_LONG).show();
               prueba = databaseAccessMuestras.obtenerIdArbolesMuestra(idMuestra);

               for (int i=0;i<prueba.size();i++){

                   ModeloPrueba muestra = prueba.get(i);
                   String id = muestra.getId_arbol();
                   Toast.makeText(mContext, id, Toast.LENGTH_SHORT).show();
               }

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
        //ImageButton btn_prueba;

        public ViewHolder(View view) {
            super(view);

            txt_fecha_visita= (TextView) view.findViewById(R.id.txtMuestra);
            btn_editar= (ImageButton) view.findViewById(R.id.imgbtnEditarMuestra);
            btn_eliminar= (ImageButton) view.findViewById(R.id.imgbtnEliminarMuestra);
            //btn_prueba= (ImageButton) view.findViewById(R.id.imgbtnPrueba);


           // relativeLayout=(RelativeLayout)view.findViewById(R.id.capa__lonuevo);

        }


    }


}
