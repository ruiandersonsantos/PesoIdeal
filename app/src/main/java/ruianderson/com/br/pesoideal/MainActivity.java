package ruianderson.com.br.pesoideal;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends Activity {
    // Declarando variaveis
    private double peso = 0;
    private double altura = 0;
    private double imc = 0;
    private int idade = 0;
    private int sexo = 0;

    //Declarando objetos com atributos da activity
    EditText edt_peso;
    EditText edt_altura;
    EditText edt_idade;

    //Criando array com os valores da tabela acima de 15 anos
    double imclista[]= {17.00,18.50,25.00,30.00,35.00,40};
    //Criando array com as avaliações para acima de 15 anos
    String imcresult[] = {"Muito abaixo do peso","Abaixo do peso","Peso normal","Acima do peso","Obesidade I","Obesidade II (severa)","Obesidade III (mórbida)"};
    // Array com as avaliação para crianças
    String resultCrianca[]={"Normal","Sobrepeso","Obesidade"};
    // Lista com os valores comforme faixa etaria para meninos
    List<ValoresMeninos> listaMeninos = new LinkedList<ValoresMeninos>();
    // Lista com os valores comforme faixa etaria para meninas
    List<ValoresMeninas> listaMeninas = new LinkedList<ValoresMeninas>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      // Vinculando objetos com a tela
      edt_peso = (EditText) findViewById(R.id.peso);
      edt_altura = (EditText) findViewById(R.id.altura);
      edt_idade = (EditText) findViewById(R.id.idade);
      final RadioGroup group = (RadioGroup)findViewById(R.id.rg_sexo);

       // Capturando a marcação do radio button
       group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {

                if(id == R.id.rb_masculino){
                    sexo = 0;
                }else if(id == R.id.rb_feminino){
                    sexo = 1;
                }

            }
        });
        //Carregando as listas preenchidas.
        carregaListaMeninos();
        carregaListaMeninas();
    }

//Metodo do click do botão calcular
public void calcularIMC(View view) {

    //Verificando se os campos foram preenchidos.
    if (edt_altura.getText().toString().equals("") || edt_peso.getText().toString().equals("")) {
        Toast.makeText(this, "Altura e peso devem estar preenchidos!", Toast.LENGTH_LONG).show();
    } else {
        //atribuindo os valores preenchidos as variaveis
        altura = Double.parseDouble(edt_altura.getText().toString());
        peso = Double.parseDouble(edt_peso.getText().toString());
        idade = Integer.parseInt(edt_idade.getText().toString());
        String resultado = "";

        //Verificando se todos os preenchimentos estam corretos conforme a regra.
        if (altura > 0 && peso > 0 && idade > 5) {
            //calculando IMC
            imc = retornaImc(peso, altura);
            if(idade > 15){
                //Atribuindo resultado quando idade for maior que 15.
                resultado = "Olá, seu IMC é "+ajustaRetornaValor(imc)+" e sua situação é "+retornaResultado(imc)+".";
                //Toast.makeText(this,resultado, Toast.LENGTH_LONG).show();
            }else{
                //Atribuindo resultado quando for criança.
                resultado = "Olá, seu IMC é "+ajustaRetornaValor(imc)+" e sua situação é "+retornaResultCrianca(sexo, idade, imc)+".";
               // Toast.makeText(this, resultado, Toast.LENGTH_LONG).show();
            }

        } else {

            Toast.makeText(this, "Altura e peso não podem ser 0!"+"\n"+"Idade tem que ser maior que 5 anos.", Toast.LENGTH_LONG).show();
        }

        // verificando se houve resultado.
        if(!resultado.equals("")){
            //Criando Intent para chamada de nova activity
            Intent res = new Intent(getBaseContext(),Result_activity.class);

            Bundle parametros = new Bundle();

            //atribuindo resultado como parametro para outra activity
            parametros.putString("resultado",resultado);
            res.putExtras(parametros);
            //gerando notificação.
            gerarNotificacao(this, res, "Calculo IMC efetuado.", "Calculo IMC", "Verifique seu IMC!");

        }

    }
}
// metodo retorna a avaliação para maiore de 15.
private String retornaResultado(double imc) {

    String resultado = "";

    if(imc >= 40){
        resultado = imcresult[imcresult.length-1];
    }else{

        for (int i = 0; i < imclista.length; i++) {

            if (imc < imclista[i]) {

                resultado = imcresult[i];
                break;
            }
        }

    }



    return resultado;
}

// metodo que ajusta o valor para apresenta somente 2 casas decimais.
public String ajustaRetornaValor(double imc){

    DecimalFormat df = new DecimalFormat("0.##");
    String dx = df.format(imc);
    return dx;
}

// metodo que calcula o IMC
private double retornaImc(double peso, double altura){
    return (peso/(altura * altura));
}

//Metodo que preenche a lista com os valores para resultado da situação dos meninos de 6 a 15 anos
private void carregaListaMeninos(){

    ValoresMeninos obj6 = new ValoresMeninos(6,14.5,16.6,18.00);
    listaMeninos.add(obj6);

    ValoresMeninos obj7 = new ValoresMeninos(7,15,17.3,19.1);
    listaMeninos.add(obj7);

    ValoresMeninos obj8 = new ValoresMeninos(8,15.6,16.7,20.3);
    listaMeninos.add(obj8);

    ValoresMeninos obj9 = new ValoresMeninos(9,16.1,18.8,21.4);
    listaMeninos.add(obj9);

    ValoresMeninos obj10 = new ValoresMeninos(10,16.7,19.6,22.5);
    listaMeninos.add(obj10);

    ValoresMeninos obj11 = new ValoresMeninos(11,17.2,20.3,23.7);
    listaMeninos.add(obj11);

    ValoresMeninos obj12 = new ValoresMeninos(12,17.8,21.1,24.8);
    listaMeninos.add(obj12);

    ValoresMeninos obj13 = new ValoresMeninos(13,18.5,21.9,25.9);
    listaMeninos.add(obj13);

    ValoresMeninos obj14 = new ValoresMeninos(14,19.2,22.7,26.9);
    listaMeninos.add(obj14);

    ValoresMeninos obj15 = new ValoresMeninos(15,19.9,23.6,27.7);
    listaMeninos.add(obj15);

}

    //Metodo que preenche a lista com os valores para resultado da situação das meninas de 6 a 15 anos
private void carregaListaMeninas(){

    ValoresMeninas obj6 = new ValoresMeninas(6,14.3,16.1,17.4);
    listaMeninas.add(obj6);

    ValoresMeninas obj7 = new ValoresMeninas(7,14.9,17.1,18.9);
    listaMeninas.add(obj7);

    ValoresMeninas obj8 = new ValoresMeninas(8,15.6,18.1,20.3);
    listaMeninas.add(obj8);

    ValoresMeninas obj9 = new ValoresMeninas(9,16.3,19.1,21.7);
    listaMeninas.add(obj9);

    ValoresMeninas obj10 = new ValoresMeninas(10,17,20.1,23.2);
    listaMeninas.add(obj10);

    ValoresMeninas obj11 = new ValoresMeninas(11,17.6,21.1,24.5);
    listaMeninas.add(obj11);

    ValoresMeninas obj12 = new ValoresMeninas(12,17.8,21.1,24.8);
    listaMeninas.add(obj12);

    ValoresMeninas obj13 = new ValoresMeninas(13,18.9,23,27.7);
    listaMeninas.add(obj13);

    ValoresMeninas obj14 = new ValoresMeninas(14,19.3,23.8,27.9);
    listaMeninas.add(obj14);

    ValoresMeninas obj15 = new ValoresMeninas(15,19.6,24.2,28.8);
    listaMeninas.add(obj15);

}

// metodo que retorna o resultado quando for criança
private String retornaResultCrianca(int sexo, int idade, double imc){

    String resultado = "";

    if(sexo == 0){

        for(ValoresMeninos obj: listaMeninos){

            if(obj.getIdade() == idade){

              if(imc > obj.getObesidade()){
                  resultado = resultCrianca[2];
              }else if(imc > obj.getSobrepeso()){
                  resultado = resultCrianca[1];
              }else if(imc > obj.getNormal()){
                  resultado = resultCrianca[0];
              }else{
                  resultado = "Abaixo da tabela";
              }

            }
        }
    }else{

        for(ValoresMeninas obj: listaMeninas){

            if(obj.getIdade() == idade){

                if(imc > obj.getObesidade()){
                    resultado = resultCrianca[2];
                }else if(imc > obj.getSobrepeso()){
                    resultado = resultCrianca[1];
                }else if(imc > obj.getNormal()){
                    resultado = resultCrianca[0];
                }else{
                    resultado = "Abaixo da tabela";
                }

            }
        }
    }



    return resultado;

}

// metodo que gera a notificação.
public void gerarNotificacao(Context context, Intent intent, CharSequence ticker, CharSequence titulo, CharSequence descricao){

    NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

    PendingIntent p = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);


    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
    builder.setTicker(ticker);
    builder.setContentTitle(titulo);
    builder.setContentText(descricao);
    builder.setSmallIcon(R.mipmap.ic_launcher);
    builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
    builder.setContentIntent(p);

    Notification n = builder.build();
    n.vibrate = new long[]{150, 300, 150, 600};
    n.flags = Notification.FLAG_AUTO_CANCEL;
    nm.notify(R.mipmap.ic_launcher, n);

    try{
        Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone toque = RingtoneManager.getRingtone(context, som);
        toque.play();
    }
    catch(Exception e){}
}

}
