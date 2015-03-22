package ruianderson.com.br.pesoideal;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class Result_activity extends Activity {

    TextView txt_resultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_activity);

        txt_resultado = (TextView) findViewById(R.id.txt_result);
        Intent intent = getIntent();

        String mostraTexto = "";

        Bundle params = intent.getExtras();

        if(params!=null) {
            mostraTexto = params.getString("resultado");
            txt_resultado.setText(mostraTexto);
            params.clear();

        }

    }


}
