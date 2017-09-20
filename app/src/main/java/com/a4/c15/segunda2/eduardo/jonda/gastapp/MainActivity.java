package com.a4.c15.segunda2.eduardo.jonda.gastapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REGISTER_FORM_REQUEST = 100;

    private ListView list;
    private TextView sumatoria; //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);
        sumatoria = (TextView) findViewById(R.id.sumatoria);//

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        list.setAdapter(adapter);
    }
    public void addItem(View view){
        startActivityForResult(new Intent(this, RegisterActivity.class), REGISTER_FORM_REQUEST);
    }

    // return from RegisterActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final ArrayAdapter<String> adapter = (ArrayAdapter<String>)list.getAdapter();
        adapter.clear();
        GastoRepository gastoRepository = GastoRepository.getInstance();
        //1
        final List<Gasto> gastos = gastoRepository.getGastos();
        double acumulador=0;

        for (final Gasto gasto : gastos) {

            acumulador = acumulador+gasto.getMonto();
            String c = gasto.getMonto().toString();
            adapter.add(gasto.getDetalle());

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id ) {

                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Detalles :");
                    alertDialog.setMessage(""+gastos.get(position));
                    // Alert dialog button
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            });
            sumatoria.setText("=  S./ "+acumulador);
        }

    }
}
