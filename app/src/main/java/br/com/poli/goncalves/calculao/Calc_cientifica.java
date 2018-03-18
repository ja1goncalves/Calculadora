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

import java.util.Locale;

public class Calc_cientifica extends AppCompatActivity {


    private TextView edit1, result;
    private LeitorDeEquacao leitor = new LeitorDeEquacao();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_cientifica);

        edit1 = (TextView) findViewById(R.id.edit1);
        result = (TextView) findViewById(R.id.result);

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

    //MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item1) {
            Intent i = new Intent(this, Calc_normal.class);
            i.putExtra("equacao", edit1.getText().toString());
            i.putExtra("result", result.getText().toString());
            Toast.makeText(this, "Calculadora normal", Toast.LENGTH_LONG).show();
            startActivity(i);
        } else if (item.getItemId() == R.id.item2) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(Calc_cientifica.this);
            dlg.setMessage("Instruções\n" +
                    "1. Operações de multiplação, divisão, expoente, log, sen, cos, ect... na equação são automáticas;\n" +
                    "2. O '.' faz referência a vírgula;\n" +
                    "3. 'BACK' apaga o último caracter;\n" +
                    "4. O sinal de '=' soluciona toda a operação;\n" +
                    "5. A mudança de sinal é feita apenas no útimo número;\n" +
                    "6. O log é de base 10;\n" +
                    "7. Módulo é o valor real de conceito análitico;");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

        return super.onOptionsItemSelected(item);
    }

    //Metodos acessorios
    public int fatoracao(int x) {
        if (x % 1 == 0) {
            int resultado = x;
            for (int i = 1; i < x; i++) {
                resultado = resultado * (x - i);
            }
            if (resultado < 0) {
                return resultado * (-1);
            } else {
                return resultado;
            }
        } else {
            Toast.makeText(this, "Apenas numeros inteiros", Toast.LENGTH_LONG).show();
            return x;
        }
    }

    public double modulo(String y) {
        double x = Double.valueOf(y);
        if (x < 0) {
            x = -x;
        } else if (x > 0) {
            x = x;
        }
        return x;
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

    //onClicks
    public void raizQuadrada(View v) {
        String equacao = edit1.getText().toString();
        if (equacao.length() > 0) {
            double num = Math.sqrt(Double.valueOf(leitor.leitorReverso(equacao)));
            equacao = leitor.deleteLast(equacao);
            String valor = String.format(Locale.US, "%.2f", num);
            if (num < 0) {
                if (equacao.length() > 0) {
                    if (leitor.ultimaLetra(equacao) == '+') {
                        equacao = equacao.substring(0, equacao.length() - 1);
                        edit1.setText(equacao + valor);
                    } else if (leitor.ultimaLetra(equacao) == '-') {
                        equacao = equacao.substring(0, equacao.length() - 1);
                        valor = valor.substring(1, valor.length());
                        edit1.setText(equacao + "+" + valor);
                    }
                } else {
                    edit1.setText(equacao + valor);
                }
            } else {
                edit1.setText(equacao + valor);
            }
        } else {
            Toast.makeText(this, "Digite um número antes da operação", Toast.LENGTH_LONG).show();
        }
    }

    public void fator(View v) {
        String equacao = edit1.getText().toString();
        if (equacao.length() > 0) {
            if ((leitor.ultimaLetra(equacao) == '+') || (leitor.ultimaLetra(equacao) == '-') || (leitor.ultimaLetra(equacao) == '^') ||
                    (leitor.ultimaLetra(equacao) == 'x') || (leitor.ultimaLetra(equacao) == '/') || (leitor.ultimaLetra(equacao) == '.') ||
                    (leitor.ultimaLetra(equacao) == '°')) {
                equacao = equacao.substring(0, equacao.length() - 1);
            }
            if ((leitor.ultimaLetra(equacao) == '.')) {
                if ((edit1.getText() != null) || (equacao.length() > 0)) {
                    leitor = new LeitorDeEquacao(equacao);
                    String ultimoNum = leitor.leitorReverso(equacao);
                    if (Double.valueOf(ultimoNum) % 1 == 0) {
                        equacao = leitor.deleteLast(equacao);
                        edit1.setText(equacao + String.valueOf(fatoracao(Integer.valueOf(ultimoNum))));
                    } else {
                        Toast.makeText(this, "Apenas numeros inteiros", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Nenhum valor encontrado", Toast.LENGTH_LONG).show();
                }
            } else {
                if ((edit1.getText() != null) || (equacao.length() > 0)) {
                    leitor = new LeitorDeEquacao(equacao);
                    String ultimoNum = leitor.leitorReverso(equacao);
                    if (Double.valueOf(ultimoNum) % 1 == 0) {
                        equacao = leitor.deleteLast(equacao);
                        edit1.setText(equacao + String.valueOf(fatoracao(Integer.valueOf(ultimoNum))));
                    } else {
                        Toast.makeText(this, "Apenas numeros inteiros", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Nenhum valor encontrado", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Toast.makeText(this, "Digite um número antes da operação", Toast.LENGTH_LONG).show();
        }
    } //

    public void e(View v) {
        double e = 2.71;
        String equacao = edit1.getText().toString();
        if (equacao.length() < 1) {
            edit1.setText(equacao + String.valueOf(e));
        } else if (leitor.ultimaLetra(equacao) == ')') {
            edit1.setText(equacao + "x" + String.valueOf(e));
        } else if (leitor.ultimaLetra(equacao) == '.') {
            Toast.makeText(this, "Não é possível adicionar o 'e' com '.'", Toast.LENGTH_LONG).show();
        } else if ((leitor.ultimaLetra(equacao) == '+') || (leitor.ultimaLetra(equacao) == '-') || (leitor.ultimaLetra(equacao) == '^') ||
                (leitor.ultimaLetra(equacao) == 'x') || (leitor.ultimaLetra(equacao) == '/') || (leitor.ultimaLetra(equacao) == '°')) {
            edit1.setText(equacao + String.valueOf(e));
        } else {
            Toast.makeText(this, "Digite a operação e em seguida o e", Toast.LENGTH_LONG).show();
        }
    } //

    public void pi(View v) {
        double e = 3.14;
        String equacao = edit1.getText().toString();
        if (equacao.length() < 1) {
            edit1.setText(equacao + String.valueOf(e));
        } else if (leitor.ultimaLetra(equacao) == ')') {
            edit1.setText(equacao + "x" + String.valueOf(e));
        } else if (leitor.ultimaLetra(equacao) == '.') {
            Toast.makeText(this, "Não é possível adicionar o 'pi' com '.'", Toast.LENGTH_LONG).show();
        } else if ((leitor.ultimaLetra(equacao) == '+') || (leitor.ultimaLetra(equacao) == '-') || (leitor.ultimaLetra(equacao) == '^') ||
                (leitor.ultimaLetra(equacao) == 'x') || (leitor.ultimaLetra(equacao) == '/') || (leitor.ultimaLetra(equacao) == '°')) {
            edit1.setText(equacao + String.valueOf(e));
        } else {
            Toast.makeText(this, "Digite a operação e em seguida o pi", Toast.LENGTH_LONG).show();
        }
    } //

    public void changeSignal(View v) throws Exception {
        try {
            String equacao = edit1.getText().toString();
            if (equacao.length() < 1) {
                Toast.makeText(this, "Não é possível inverter o sinal", Toast.LENGTH_LONG).show();
            } else {
                if ((leitor.ultimaLetra(equacao) == '+') || (leitor.ultimaLetra(equacao) == '-') || (leitor.ultimaLetra(equacao) == '^') ||
                        (leitor.ultimaLetra(equacao) == 'x') || (leitor.ultimaLetra(equacao) == '/') || (leitor.ultimaLetra(equacao) == '.') ||
                        (leitor.ultimaLetra(equacao) == '°')) {
                    equacao = equacao.substring(0, equacao.length() - 1);
                }
                edit1.setText(leitor.leitorDeSinal(equacao));
            }
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    } //

    public void modulo(View v) {
        String equacao = edit1.getText().toString();
        if ((edit1.getText() != null) || (equacao.length() > 0)) {
            leitor = new LeitorDeEquacao(equacao);
            String modulo = leitor.leitorReverso(equacao);
            edit1.setText(leitor.deleteLast(equacao) + String.valueOf(modulo(modulo)));
        } else {
            Toast.makeText(this, "Nenhum valor encontrado", Toast.LENGTH_LONG).show();
        }
    } //

    public void inverter(View v) {
        String equacao = edit1.getText().toString();
        if (equacao.length() >= 1) {
            if((leitor.ultimaLetra(equacao) == 'x')){
                edit1.setText(equacao + "1/");
            }else if ((leitor.ultimaLetra(equacao) != '+') || (leitor.ultimaLetra(equacao) != '-') || (leitor.ultimaLetra(equacao) != '^') ||
                    (leitor.ultimaLetra(equacao) != 'x') || (leitor.ultimaLetra(equacao) != '/') || (leitor.ultimaLetra(equacao) == '.') ||
                    (leitor.ultimaLetra(equacao) == '°')) {
                edit1.setText(equacao + "x1/");
            } else if (leitor.ultimaLetra(equacao) == '^') {
                Toast.makeText(this, "Equação incorreta", Toast.LENGTH_LONG).show();
            }
        } else if (equacao.length() < 1) {
            edit1.setText("1/");
        }
    } //

    public void seno(View v) throws Exception {
        try {
            String equacao = edit1.getText().toString();
            if (equacao.length() > 0) {
                double num = Math.sin(Double.valueOf(leitor.leitorReverso(equacao)));
                equacao = leitor.deleteLast(equacao);
                String valor = String.format(Locale.US, "%.2f", num);
                if (num < 0) {
                    if (equacao.length() > 0) {
                        if (leitor.ultimaLetra(equacao) == '+') {
                            equacao = equacao.substring(0, equacao.length() - 1);
                            edit1.setText(equacao + valor);
                        } else if (leitor.ultimaLetra(equacao) == '-') {
                            equacao = equacao.substring(0, equacao.length() - 1);
                            valor = valor.substring(1, valor.length());
                            edit1.setText(equacao + "+" + valor);
                        }
                    } else {
                        edit1.setText(equacao + valor);
                    }
                } else {
                    edit1.setText(equacao + valor);
                }
            } else {
                Toast.makeText(this, "Digite um número antes da operação", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "Tente novamente", Toast.LENGTH_LONG).show();
        }
    }

    public void cos(View v) throws Exception {
        try {
            String equacao = edit1.getText().toString();
            if (equacao.length() > 0) {
                double num = Math.cos(Double.valueOf(leitor.leitorReverso(equacao)));
                equacao = leitor.deleteLast(equacao);
                String valor = String.format(Locale.US, "%.2f", num);
                if (num < 0) {
                    if (equacao.length() > 0) {
                        if (leitor.ultimaLetra(equacao) == '+') {
                            equacao = equacao.substring(0, equacao.length() - 1);
                            edit1.setText(equacao + valor);
                        } else if (leitor.ultimaLetra(equacao) == '-') {
                            equacao = equacao.substring(0, equacao.length() - 1);
                            valor = valor.substring(1, valor.length());
                            edit1.setText(equacao + "+" + valor);
                        }
                    } else {
                        edit1.setText(equacao + valor);
                    }
                } else {
                    edit1.setText(equacao + valor);
                }
            } else {
                Toast.makeText(this, "Digite um número antes da operação", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "Tente novamente", Toast.LENGTH_LONG).show();
        }
    }

    public void tan(View v) throws Exception {
        try {
            String equacao = edit1.getText().toString();
            if (equacao.length() > 0) {
                double num = Math.tan(Double.valueOf(leitor.leitorReverso(equacao)));
                equacao = leitor.deleteLast(equacao);
                String valor = String.format(Locale.US, "%.2f", num);
                if (num < 0) {
                    if (equacao.length() > 0) {
                        if (leitor.ultimaLetra(equacao) == '+') {
                            equacao = equacao.substring(0, equacao.length() - 1);
                            edit1.setText(equacao + valor);
                        } else if (leitor.ultimaLetra(equacao) == '-') {
                            equacao = equacao.substring(0, equacao.length() - 1);
                            valor = valor.substring(1, valor.length());
                            edit1.setText(equacao + "+" + valor);
                        }
                    } else {
                        edit1.setText(equacao + valor);
                    }
                } else {
                    edit1.setText(equacao + valor);
                }
            } else {
                Toast.makeText(this, "Digite um número antes da operação", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "Tente novamente", Toast.LENGTH_LONG).show();
        }
    }

    public void log(View v) throws Exception {
        try {
            String equacao = edit1.getText().toString();
            if (equacao.length() > 0) {
                double num = Math.log10(Double.valueOf(leitor.leitorReverso(equacao)));
                equacao = leitor.deleteLast(equacao);
                String valor = String.format(Locale.US, "%.2f", num);
                edit1.setText(equacao + valor);
            } else {
                Toast.makeText(this, "Digite um número antes da operação", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "Tente novamente", Toast.LENGTH_LONG).show();
        }
    }

    //OnClicks
    public void operacao(View v) throws Exception{
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
            } else {
                edit1.setText(edit1.getText().toString() + v.getTag().toString());
            }
        }
    }

    public void operadorParenteses(View v) {
        String equacao = edit1.getText().toString();
        String parenteses = v.getTag().toString();
        if(result.length() > 0){
            edit1.setText(result.getText().toString());
            result.setText("");
        }
        if (equacao.length() < 1) {
            if (parenteses.equals(")")) {
                Toast.makeText(this, "Modo incorreto de iniciar uma operação", Toast.LENGTH_LONG).show();
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
                    (leitor.ultimaLetra(equacao) != 'x') && (leitor.ultimaLetra(equacao) != '/') && (parenteses.equals(")"))) {
                edit1.setText(equacao + v.getTag().toString());
            } else if ((leitor.ultimaLetra(equacao) != '+') && (leitor.ultimaLetra(equacao) != '-') && (leitor.ultimaLetra(equacao) != '^') &&
                    (leitor.ultimaLetra(equacao) != 'x') && (leitor.ultimaLetra(equacao) != '/') && (parenteses.equals("("))) {
                edit1.setText(equacao + "x" + v.getTag().toString());
            }
        }
    }

    public void numero(View v) {
        String equacao = edit1.getText().toString();
        if (leitor.ultimaLetra(equacao) == ')') {
            edit1.setText(equacao + "x" + v.getTag().toString());
        } else {
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
