package com.example.chatbluetooth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter myBluetoothAdapter;

    Intent btEnablingIntent;
    private static final int REQUEST_ENABLE_BT = 1;

    boolean conexao = false;

    Button btnConectar, btnEnviar;
    EditText tf_comandos;
    TextView lbl_texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConectar = findViewById(R.id.btnConectar);
        btnEnviar = findViewById(R.id.btnEnviar);

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        btEnablingIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

        bluetoothOnMethod();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_ENABLE_BT) {
            if (resultCode==RESULT_OK) {
                Toast.makeText(getApplicationContext(), "bluetooth foi ativado", Toast.LENGTH_LONG).show();
            } else if (resultCode==RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "Bluetooth não foi ativado", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void bluetoothOnMethod() {
        btnConectar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (myBluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(), "O dispositivo não possui bluetooth", Toast.LENGTH_LONG).show();
                } else {
                    if (!myBluetoothAdapter.isEnabled()) {
                        startActivityForResult(btEnablingIntent, REQUEST_ENABLE_BT);
                        conexao = true;
                        btnConectar.setText("Desconectar");
                    } else {
                        myBluetoothAdapter.disable();
                        Toast.makeText(getApplicationContext(), "Bluetooth foi desligado", Toast.LENGTH_LONG).show();
                        btnConectar.setText("Conectar");
                        conexao = false;
                    }
                }
            }
        });
    }
}