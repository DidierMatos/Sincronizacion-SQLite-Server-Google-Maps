package com.mayandevelopers.pftp.controllers;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.models.ArbolesModel;
import com.mayandevelopers.pftp.views.AgregarArbolActivity;
import com.mayandevelopers.pftp.views.VisitasActivity;

import java.util.ArrayList;
import java.util.List;

public class RvArbolesController extends RecyclerView.Adapter<RvArbolesController.ViewHolder> {

    private Context mContext;
    private List<ArbolesModel> mData;
    ArrayList<ArbolesModel> actividadItem;
    private int selectPosition;

    private static final String ARBOL_SELECCIONADO = "ARBOL_SELECCIONADO";

    public RvArbolesController(Context mContext, List<ArbolesModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public RvArbolesController.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_mis_arboles ,viewGroup,false);

        return new RvArbolesController.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvArbolesController.ViewHolder viewHolder, int position) {
        final ArbolesModel misarboles = mData.get(position);

        viewHolder.txtview_folio.setText(misarboles.getFolio());


        viewHolder.btn_visitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, VisitasActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);


            }
        });

        viewHolder.btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AgregarArbolActivity.class);

                SharedPreferences sharedPref = mContext.getSharedPreferences(ARBOL_SELECCIONADO,0);
                SharedPreferences.Editor editor = sharedPref.edit();
                //editor.putString("nombre_empresa", misespecies.getNombreEspecie());
                editor.apply();

                intent.putExtra("update","1");
                intent.putExtra("id_arbol",misarboles.getId());
                intent.putExtra("folio_arbol", misarboles.getFolio());
                //intent.putExtra("numserie_arbol", misarboles.getNum_serie());
                intent.putExtra("latitud_arbol", misarboles.getLatitud());
                intent.putExtra("longitud_arbol", misarboles.getLongitud());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        // eliminar un arbol //
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtview_folio;
        ImageButton btn_visitas, btn_editar, btn_eliminar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtview_folio = itemView.findViewById(R.id.txtviewFolioCVarbol);
            btn_visitas = itemView.findViewById(R.id.btnVistasCVarbol);
            btn_editar = itemView.findViewById(R.id.btnEditarCVarbol);
            btn_eliminar = itemView.findViewById(R.id.btnEliminarCVarbol);


        }
    }
}
