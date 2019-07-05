package com.example.firebase_practica1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase_practica1.models.Persona;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private EditText et_Nombre, et_Apellido, et_Correo, et_Password;
    private ListView lv_datosPersona;
    //declaramos variables de firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_Nombre = (EditText)findViewById(R.id.txt_nombrePersona);
        et_Apellido = (EditText)findViewById(R.id.txt_apellidoPersona);
        et_Correo = (EditText)findViewById(R.id.txt_correoPersona);
        et_Password = (EditText)findViewById(R.id.txt_passwordPersona);
        lv_datosPersona = (ListView) findViewById(R.id.listv_datosPersona);

        InicializarFirebase();
    }

    private void InicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    //METODO PARA CREAR EL MENU CREADO (AGREGAR, GUARDAR, ELIMINAR)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //AGREGAMOS EL MENU CREADO CON SU RUTA
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //METODO PARA REALIZAR ACCION DESPUES DE DAR CLICK EN LOS BOTONES
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String Nombre_String = et_Nombre.getText().toString();
        String Apellido_String = et_Apellido.getText().toString();
        String Correo_String = et_Correo.getText().toString();
        String Password_String = et_Password.getText().toString();

        //Crear switch
        switch (item.getItemId()){
            case R.id.icon_add:{
                if (Nombre_String.equals("") || Apellido_String.equals("") || Correo_String.equals("") || Password_String.equals("") ){
                     validacion();
                } else{
                    Persona p = new Persona();
                    p.setUid(UUID.randomUUID().toString());
                    p.setNombre(Nombre_String);
                    p.setApellido(Apellido_String);
                    p.setCorreo(Correo_String);
                    p.setPassword(Password_String);
                    databaseReference.child("Persona").child(p.getUid()).setValue(p);

                    Toast.makeText(this, "Agregado", Toast.LENGTH_LONG).show();
                    LimpiarCajas();
                }
                break;
                
            }
            case R.id.icon_save:{
                Toast.makeText(this, "Guardar", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.icon_delete:{
                Toast.makeText(this,"Eliminar", Toast.LENGTH_LONG).show();
                break;
            }
            default:break;
        }
        return true;
    }

    private void LimpiarCajas() {
        et_Nombre.setText("");
        et_Apellido.setText("");
        et_Correo.setText("");
        et_Password.setText("");
    }

    private void validacion() {
        String Nombre_String = et_Nombre.getText().toString();
        String Apellido_String = et_Apellido.getText().toString();
        String Correo_String = et_Correo.getText().toString();
        String Password_Sttring = et_Password.getText().toString();
        
        if (Nombre_String.equals("")){
            et_Nombre.setError("Required");
        }
        else if(Apellido_String.equals("")){
            et_Apellido.setError("Required");
        }
        else if(Correo_String.equals("")){
            et_Correo.setError("Required");
        }
        else if (Password_Sttring.equals("")) {
            et_Password.setError("Required");
        }
    }
    
    
}
