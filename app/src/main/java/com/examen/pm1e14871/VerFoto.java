package com.examen.pm1e14871;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.examen.pm1e14871.Configuracion.Operaciones;
import com.examen.pm1e14871.Configuracion.SQLiteConexion;
import com.examen.pm1e14871.R;

import java.io.ByteArrayInputStream;

public class VerFoto extends AppCompatActivity {

    SQLiteConexion conexion = new SQLiteConexion(this, Operaciones.NameDatabase, null, 1);
    ImageView imageViewFoto;
    Button btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ver_foto);

        btnAtras = (Button) findViewById(R.id.btnavfAtras);

        imageViewFoto = (ImageView) findViewById(R.id.imgViewVerFoto);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListadoContacto.class);
                startActivity(intent);
            }
        });
        /*
        Toast.makeText(getApplicationContext(), getIntent().getStringExtra("codigoParaFoto")+" "
                ,Toast.LENGTH_LONG).show();*/

        Bitmap recuperarFoto = buscarImagen(getIntent().getStringExtra("codigoParaFoto"));
        imageViewFoto.setImageBitmap(recuperarFoto);


    }

    public Bitmap buscarImagen(String id) {
        SQLiteDatabase db = conexion.getWritableDatabase();

        String sql = "SELECT foto FROM contactos WHERE id =" + id;
        Cursor cursor = db.rawQuery(sql, new String[] {});
        Bitmap bitmap = null;
        if(cursor.moveToFirst()){
            byte[] blob = cursor.getBlob(0);
            ByteArrayInputStream bais = new ByteArrayInputStream(blob);
            bitmap = BitmapFactory.decodeStream(bais);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        return bitmap;
    }
}