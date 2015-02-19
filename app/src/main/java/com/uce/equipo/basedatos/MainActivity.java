package com.uce.equipo.basedatos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    public EditText etci, etna, etap, etce;
    public TextView a, b, c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etci = (EditText) findViewById(R.id.gfeditci);
        etna = (EditText) findViewById(R.id.gfeditnom);
        etap = (EditText) findViewById(R.id.gfeditape);
        etce = (EditText) findViewById(R.id.gfeditcel);
        a = (TextView) findViewById(R.id.textView1);
        b = (TextView) findViewById(R.id.textView2);
        c = (TextView) findViewById(R.id.textView3);
    }

    // Inicio Código
    public void gfAgregar(View v) {
        BaseDatos dbobject = new BaseDatos(this, "administracion", null, 1);
        SQLiteDatabase bd = dbobject.getWritableDatabase();
        String ced = etci.getText().toString();
        String nom = etna.getText().toString();
        String ape = etap.getText().toString();
        String cel = etce.getText().toString();
        a.setTextColor(Color.BLACK);
        b.setTextColor(Color.BLACK);
        c.setTextColor(Color.BLACK);

        if (ced.length() != 0 && nom.length() != 0 && ape.length() != 0) {
            if (validadorDeCedula(ced)) {
                Cursor fila = bd.rawQuery(
                        "select nombre,apellido,celular from alumnos where ci="
                                + ced, null);
                if (fila.moveToFirst()) {
                    Toast.makeText(this, "Ya existe la CEDULA",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (cel.length() == 0 || cel.length() == 10) {
                        ContentValues registro = new ContentValues();
                        registro.put("ci", ced);
                        registro.put("nombre", nom);
                        registro.put("apellido", ape);
                        registro.put("celular", cel);
                        bd.insert("alumnos", null, registro);
                        bd.close();
                        etci.setText("");
                        etna.setText("");
                        etap.setText("");
                        etce.setText("");
                        Toast.makeText(this, "Alumno AGREGADO",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this,
                                "El numero celular esta incorrecto",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "No ingreso una cedula válida en ECUADOR",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Los campos con (*) son OBLIGATORIOS",
                    Toast.LENGTH_SHORT).show();
            if (ced.length() == 0) {
                a.setTextColor(Color.RED);
            }
            if (nom.length() == 0) {
                b.setTextColor(Color.RED);
            }
            if (ape.length() == 0) {
                c.setTextColor(Color.RED);
            }
        }
    }

    public void gfConsultar(View v) {
        BaseDatos dbobject = new BaseDatos(this, "administracion", null, 1);
        SQLiteDatabase bd = dbobject.getWritableDatabase();
        String ced = etci.getText().toString();
        if (ced.length() != 0) {
            Cursor fila = bd.rawQuery(
                    "select nombre,apellido,celular from alumnos where ci="
                            + ced, null);
            if (fila.moveToFirst()) {
                etna.setText(fila.getString(0));
                etap.setText(fila.getString(1));
                etce.setText("0" + fila.getString(2));
            } else
                Toast.makeText(this, "No existe la CEDULA", Toast.LENGTH_SHORT)
                        .show();

            bd.close();
        } else
            Toast.makeText(this, "Ingrese una cedula para CONSULTAR",
                    Toast.LENGTH_SHORT).show();
    }

    public void gfModificar(View v) {
        BaseDatos dbobject = new BaseDatos(this, "administracion", null, 1);
        SQLiteDatabase bd = dbobject.getWritableDatabase();
        String ced = etci.getText().toString();
        String nom = etna.getText().toString();
        String ape = etap.getText().toString();
        String cel = etce.getText().toString();
        a.setTextColor(Color.BLACK);
        b.setTextColor(Color.BLACK);
        c.setTextColor(Color.BLACK);
        if (ced.length() != 0) {
            if (ced.length() != 0 && nom.length() != 0 && ape.length() != 0) {
                if (cel.length() == 0 || cel.length() == 10) {
                    ContentValues registro = new ContentValues();
                    registro.put("nombre", nom);
                    registro.put("apellido", ape);
                    registro.put("celular", cel);
                    int cant = bd
                            .update("alumnos", registro, "ci=" + ced, null);
                    bd.close();
                    if (cant == 1)
                        Toast.makeText(this, "Datos ACTUALIZADOS",
                                Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "Alumno NO EXISTE",
                                Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "El numero celular esta incorrecto",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Los campos con (*) son OBLIGATORIOS",
                        Toast.LENGTH_SHORT).show();
                if (ced.length() == 0) {
                    a.setTextColor(Color.RED);
                }
                if (nom.length() == 0) {
                    b.setTextColor(Color.RED);
                }
                if (ape.length() == 0) {
                    c.setTextColor(Color.RED);
                }
            }
        } else
            Toast.makeText(this, "Ingrese una cedula para MODIFICAR",
                    Toast.LENGTH_SHORT).show();
    }

    public void gfBorrar(View v) {
        BaseDatos dbobject = new BaseDatos(this, "administracion", null, 1);
        SQLiteDatabase bd = dbobject.getWritableDatabase();
        String ced = etci.getText().toString();

        if (ced.length() != 0) {
            int cant = bd.delete("alumnos", "ci=" + ced, null);
            bd.close();
            etci.setText("");
            etna.setText("");
            etap.setText("");
            etce.setText("");
            if (cant == 1)
                Toast.makeText(this, "Alumno BORRADO", Toast.LENGTH_SHORT)
                        .show();
            else
                Toast.makeText(this, "Alumno NO EXISTE", Toast.LENGTH_SHORT)
                        .show();
        } else
            Toast.makeText(this, "Ingrese una cedula para BORRAR",
                    Toast.LENGTH_SHORT).show();
    }

    public void gfLimpiar(View v) {
        etci.setText("");
        etna.setText("");
        etap.setText("");
        etce.setText("");
        a.setTextColor(Color.BLACK);
        b.setTextColor(Color.BLACK);
        c.setTextColor(Color.BLACK);
        Toast.makeText(this, "Campos Limpios", Toast.LENGTH_SHORT).show();
    }

    public boolean validadorDeCedula(String cedula) {
        boolean cedulaCorrecta = false;

        try {

            if (cedula.length() == 10) // ConstantesApp.LongitudCedula
            {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
                    // Coeficientes de validación cédula
                    // El decimo digito se lo considera dígito verificador
                    int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
                    int verificador = Integer.parseInt(cedula.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1))
                                * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            System.out
                    .println("Una excepcion ocurrio en el proceso de validadcion");
            cedulaCorrecta = false;
        }

        if (!cedulaCorrecta) {
            System.out.println("La Cédula ingresada es Incorrecta");
        }
        return cedulaCorrecta;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
