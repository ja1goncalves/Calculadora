package br.com.poli.goncalves.calculao;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Calc_normal extends AppCompatActivity {

    private TextView edit1, result;
    private LeitorDeEquacao leitor = new LeitorDeEquacao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_normal);

        edit1 = (TextView)findViewById(R.id.edit1);
        result = (TextView)findViewById(R.id.result);

        Bundle b= getIntent().getExtras();
        if(b != null) {
            if (b.containsKey("equacao")) {
                edit1.setText(b.getString("equacao"));
            }
            if (b.containsKey("result")) {
                result.setText(b.getString("result"));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_normal, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.item1){
            Intent i = new Intent(this, Calc_cientifica.class);
            i.putExtra("equacao", edit1.getText().toString());
            i.putExtra("result", result.getText().toString());
            Toast.makeText(this, "Calculadora cientifica", Toast.LENGTH_LONG).show();
            startActivity(i);
        }else if (item.getItemId() == R.id.item2){
            AlertDialog.Builder dlg = new AlertDialog.Builder(Calc_normal.this);
            dlg.setMessage("Instruções\n" +
                    "1. Operações de multiplação e divisão na equação são automáticas;\n" +
                    "2. O '.' faz referência a vírgula;\n" +
                    "3. 'BACK' apaga o último caracter;\n" +
                    "4. O sinal de '=' soluciona toda a operação;");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public String resolvendo(String equacao) throws Exception{
        try {
            if ((leitor.ultimaLetra(equacao) == '+') || (leitor.ultimaLetra(equacao) == '-') || (leitor.ultimaLetra(equacao) == '^') ||
                    (leitor.ultimaLetra(equacao) == 'x') || (leitor.ultimaLetra(equacao) == '/') || (leitor.ultimaLetra(equacao) == '.') ||
                    (leitor.ultimaLetra(equacao) == '°')) {
                equacao = equacao.substring(0, equacao.length() - 1);
                equacao = leitor.leitor(equacao);
            } else {
                equacao = leitor.leitor(equacao);
            }
        }catch (Exception e){
            Toast.makeText(this, "Equação incorreta", Toast.LENGTH_LONG).show();
        }
        return equacao;
    }

    //OnClicks
    public void operacao(View v) throws Exception {
        String equacao = edit1.getText().toString();
        String resultad = result.getText().toString();
        if (equacao.length() < 1) {
            //do nothing
        } else {
            if(result.length() > 0){
                if(Double.valueOf(resolvendo(equacao)).equals(Double.valueOf(resultad))) {
                    edit1.setText(result.getText().toString());
                    result.setText("");
                }
            }
            if ((leitor.ultimaLetra(equacao) == '+') || (leitor.ultimaLetra(equacao) == '-') || (leitor.ultimaLetra(equacao) == '^') ||
                    (leitor.ultimaLetra(equacao) == 'x') || (leitor.ultimaLetra(equacao) == '/') || (leitor.ultimaLetra(equacao) == '.')
                    || (leitor.ultimaLetra(equacao) == '°')) {
                equacao = equacao.substring(0, equacao.length() - 1);
                edit1.setText(equacao + v.getTag().toString());
            }else if(!(v.getTag().toString().equals("."))) {
                if(leitor.lastOperation(equacao) == "^") {
                    String semExpoente = equacao;
                    String resultado = leitor.leitorReverso(equacao);
                    if(resultado != "") {
                        semExpoente = leitor.noExpoente(equacao);
                    }
                    edit1.setText(semExpoente + resultado + v.getTag().toString());
                    Toast.makeText(this, "Número mais próximo (caso decimal)", Toast.LENGTH_SHORT).show();
                }else if(leitor.lastOperation(equacao) == "x") {
                    String semExpoente = equacao;
                    String resultado = leitor.leitorReverso(equacao);
                    if(resultado != "") {
                        semExpoente = leitor.noMultiplication(equacao);
                    }
                    edit1.setText(semExpoente + resultado + v.getTag().toString());
                }else if(leitor.lastOperation(equacao) == "/") {
                    String semExpoente = equacao;
                    String resultado = leitor.leitorReverso(equacao);
                    if(resultado != "") {
                        semExpoente = leitor.noDivision(equacao);
                    }
                    edit1.setText(semExpoente + resultado + v.getTag().toString());
                }else if(leitor.lastOperation(equacao) == "°"){
                    String resultado = leitor.leitorReverso(equacao);
                    String semExpoente = leitor.noLog(equacao);
                    edit1.setText(semExpoente + resultado + v.getTag().toString());
                    Toast.makeText(this, "Número mais próximo (caso decimal)", Toast.LENGTH_SHORT).show();
                }else{
                    edit1.setText(edit1.getText().toString() + v.getTag().toString());
                }
            }else {
                edit1.setText(edit1.getText().toString() + v.getTag().toString());
            }
        }
    }

    public void operadorParenteses(View v) {
        String equacao = edit1.getText().toString();
        String parenteses = v.getTag().toString();
        if(result.length() > 0){
            edit1.setText(result.getText().toString());
            equacao = edit1.getText().toString();
            result.setText("");
        }
        if (equacao.length() < 1) {
            if (parenteses.equals(")")) {
                Toast.makeText(this, "Equação incorreta", Toast.LENGTH_LONG).show();
            } else if (parenteses.equals("(")) {
                edit1.setText(equacao + v.getTag().toString());
            }
        } else if ((equacao.length() >= 1)) {
            if ((leitor.ultimaLetra(equacao) == '(') || (leitor.ultimaLetra(equacao) == ')')) {
                equacao = equacao.substring(0, equacao.length() - 1);
                edit1.setText(equacao + v.getTag().toString());
            } else if ((leitor.ultimaLetra(equacao) == '+') || (leitor.ultimaLetra(equacao) == '-') || (leitor.ultimaLetra(equacao) == '°') ||
                    (leitor.ultimaLetra(equacao) == 'x') || (leitor.ultimaLetra(equacao) == '/') || (leitor.ultimaLetra(equacao) == '^')) {
                edit1.setText(equacao + v.getTag().toString());
            } else if ((leitor.ultimaLetra(equacao) == '.')) {
                equacao = equacao.substring(0, equacao.length() - 1);
                edit1.setText(equacao + "x" + v.getTag().toString());
            } else if ((leitor.ultimaLetra(equacao) != '+') && (leitor.ultimaLetra(equacao) != '-') && (leitor.ultimaLetra(equacao) != '^') &&
                    (leitor.ultimaLetra(equacao) != 'x') && (leitor.ultimaLetra(equacao) != '/') && (parenteses.equals(")")) &&
                    (leitor.ultimaLetra(equacao) != '°')) {
                edit1.setText(equacao + v.getTag().toString());
            } else if ((leitor.ultimaLetra(equacao) != '+') && (leitor.ultimaLetra(equacao) != '-') && (leitor.ultimaLetra(equacao) != '^') &&
                    (leitor.ultimaLetra(equacao) != 'x') && (leitor.ultimaLetra(equacao) != '/') && (parenteses.equals("("))&&
                    (leitor.ultimaLetra(equacao) != '°')) {
                edit1.setText(equacao + "x" + v.getTag().toString());
            }
        }
    }

    public void numero(View v) {
        String equacao = edit1.getText().toString();
        if(leitor.ultimaLetra(equacao) == ')'){
            edit1.setText(equacao+"x"+v.getTag().toString());
        }else {
            edit1.setText(edit1.getText().toString() + v.getTag().toString());
        }
    }

    public void clearNum(View v) {
        edit1.setText("");
        result.setText("");
    }

    public void backspace(View v) {
        String equacao = edit1.getText().toString();
        //char lastChar = ultimaLetra(equacao);
        //equacao = equacao.replace(String.valueOf(equacao.charAt(equacao.length() - 1)), "");
        if(equacao.length() > 0) {
            equacao = equacao.substring(0, equacao.length() - 1);
            edit1.setText(equacao);
        }
    }

    public void resolver(View v) throws Exception{
        try {
            String equacao = edit1.getText().toString();
            if ((leitor.ultimaLetra(equacao) == '+') || (leitor.ultimaLetra(equacao) == '-') || (leitor.ultimaLetra(equacao) == '^') ||
                    (leitor.ultimaLetra(equacao) == 'x') || (leitor.ultimaLetra(equacao) == '/') || (leitor.ultimaLetra(equacao) == '.') ||
                    (leitor.ultimaLetra(equacao) == '°')) {
                equacao = equacao.substring(0, equacao.length() - 1);
                result.setText(leitor.leitor(equacao));
            } else {
                result.setText(leitor.leitor(equacao));
            }
        }catch (Exception e){
            Toast.makeText(this, "Equação incorreta", Toast.LENGTH_LONG).show();
        }
    }
}
