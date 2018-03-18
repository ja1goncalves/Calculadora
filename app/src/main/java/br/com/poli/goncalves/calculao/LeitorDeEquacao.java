package br.com.poli.goncalves.calculao;

import java.util.Locale;

/**
 * Created by Goncalves on 15/02/2018.
 */

public class LeitorDeEquacao {

        private String equacao;

        public LeitorDeEquacao(String equacao){
            this.setEquacao(equacao);
        }
        public LeitorDeEquacao(){

        }

        //EQUAÇÃO
        public String getEquacao() {
            return this.equacao;
        }
        public void setEquacao(String equacao) {
            this.equacao = equacao;
        }

//MÉTODOS

        public String leitor(String equacao) throws Exception{
            try {
                for (int i = 0; i < equacao.length() - 1; i++) {
                    if (contadorParenteses(equacao) > 0) {
                        return operador(leitorParenteses(equacao));
                    } else {
                        return operador(equacao);
                    }
                }
            }catch (Exception e){
                System.out.print(e.toString());
            }
            return equacao;
        }

        public String leitorParenteses(String equacao){
            int start = 0, end = 0, vezes = contadorParenteses(equacao);;
            if(this.sentido(equacao)) {
                for (int k = 0; k < vezes; k++) {
                    for (int j = 0; j <= equacao.length() - 1; j++) {
                        if ((equacao.charAt(j) == ')')) {
                            end = j;
                            break;
                        } else if ((j == equacao.length() - 1)) {
                            end = j + 1;
                            break;
                        }
                    }
                    for (int i = end - 1; i >= 0; i--) {
                        if ((equacao.charAt(i) == '(') || (i == 0)) {
                            start = i;
                            break;
                        }
                    }
                    String newEquation = equacao.substring(start + 1, end);
                    String solucao = operador(newEquation);
                    if(start < 0){
                        start = start+1;
                    }
                    String part1 = equacao.substring(0, start);
                    if ((end + 1) > equacao.length()) {
                        end = end - 1;
                    }
                    String part2 = equacao.substring(end + 1, equacao.length());
                    equacao = part1 + solucao + part2;
                    equacao = organizar(equacao);
                }
            }else{
                for (int k = 0; k < vezes+1; k++) {
                    for (int i = equacao.length() - 1; i >= 0; i--) {
                        if ((equacao.charAt(i) == '(')) {
                            start = i;
                            break;
                        }else if((i == 0)){
                            start = i-1;
                            break;
                        }
                    }
                    for (int j = start+1; j <= equacao.length() - 1; j++) {
                        if ((equacao.charAt(j) == ')')) {
                            end = j;
                            break;
                        } else if ((j == equacao.length() - 1)) {
                            end = j + 1;
                            break;
                        }
                    }
                    String newEquation = equacao.substring(start + 1, end);
                    String solucao = operador(newEquation);
                    if(start < 0){
                        start = start+1;
                    }
                    String part1 = equacao.substring(0, start);
                    if ((end + 1) > equacao.length()) {
                        end = end - 1;
                    }
                    equacao = organizar(equacao);
                    String part2 = equacao.substring(end + 1, equacao.length());
                    equacao = part1 + solucao + part2;
                    equacao = organizar(equacao);
                }
            }
            return equacao;
        }

        public int contadorParenteses(String equacao) {
            int parenteses1 = 0, parenteses2 = 0;
            for (int i = 0; i <= equacao.length() - 1; i++) {
                if (equacao.charAt(i) == '(') {
                    parenteses1++;
                } else if (equacao.charAt(i) == ')') {
                    parenteses2++;
                }
            }
            if((parenteses1 >= parenteses2)){
                return parenteses1;
            }else{
                return parenteses2;
            }
        }
        public boolean sentido(String equacao){
            int parenteses1 = 0, parenteses2 = 0;
            for (int i = 0; i <= equacao.length() - 1; i++) {
                if (equacao.charAt(i) == '(') {
                    parenteses1++;
                } else if (equacao.charAt(i) == ')') {
                    parenteses2++;
                }
            }
            if((parenteses1 >= parenteses2)){
                return true;
            }else{
                return false;
            }
        }
        public int contadorOperacoes(String equacao){
            int soma = 0;
            for(int i = 0; i < equacao.length()-1; i++){
                if((equacao.charAt(i) == '+') || (equacao.charAt(i) == '+') || (equacao.charAt(i) == '+') || (equacao.charAt(i) == '-') ||
                        (equacao.charAt(i) == '^') || (equacao.charAt(i) == 'x') || (equacao.charAt(i) == '/')){
                    soma++;
                }
            }
            return soma;
        }

        public String organizar(String equacao){
            for(int i = 0; i < equacao.length(); i++) {
                if ((equacao.charAt(i) == '+') && (equacao.charAt(i + 1) == '-')) {
                    String part1 = equacao.substring(0, i);
                    String part2 = equacao.substring(i + 1, equacao.length());
                    equacao = part1+part2;
                    return equacao;
                }else if ((equacao.charAt(i) == '+') && (equacao.charAt(i + 1) == '+')) {
                    String part1 = equacao.substring(0, i);
                    String part2 = equacao.substring(i + 1, equacao.length());
                    equacao = part1+part2;
                    return equacao;
                }else if ((equacao.charAt(i) == '-') && (equacao.charAt(i + 1) == '+')) {
                    String part1 = equacao.substring(0, i + 1);
                    String part2 = equacao.substring(i + 2, equacao.length());
                    equacao = part1+part2;
                    return equacao;
                }else if ((equacao.charAt(i) == '-') && (equacao.charAt(i + 1) == '-')) {
                    String part1 = equacao.substring(0, i);
                    String part2 = equacao.substring(i + 2, equacao.length());
                    equacao = part1+"+"+part2;
                    return equacao;
                }else if ((equacao.charAt(i) == 'x') && (equacao.charAt(i + 1) == '-')) {
                    for(int j = i; j >= 0; j--){
                        if(equacao.charAt(j) == '+'){
                            String part1 = equacao.substring(0, j);
                            String part2 = equacao.substring(j+1, i+1);
                            String part3 = equacao.substring(i+2, equacao.length());
                            equacao = part1+"-"+part2+part3;
                            return equacao;
                        }else if(equacao.charAt(j) == '-'){
                            String part1 = equacao.substring(0, j);
                            String part2 = equacao.substring(j+1, i+1);
                            String part3 = equacao.substring(i+2, equacao.length());
                            equacao = part1+"+"+part2+part3;
                            return equacao;
                        }
                    }
                }else if ((equacao.charAt(i) == 'x') && (equacao.charAt(i + 1) == '+')) {
                    String part1 = equacao.substring(0, i+1);
                    String part2 = equacao.substring(i + 2, equacao.length());
                    equacao = part1+part2;
                    return equacao;
                }
            }
            return equacao;
        }

        public String operador(String equacao){
            double num1 = 0, num2 = 0, result = 0;
            int end = 0, start = 0, tamanho = contadorOperacoes(equacao);
            for (int i = 0; i < equacao.length() - 1; i++) {
                if ((equacao.charAt(i) == 'x') || (equacao.charAt(i) == '^') || (equacao.charAt(i) == '/')) {
                    for (int q = i - 1; q >= 0; q--) {
                        if ((equacao.charAt(q) == '+') || (equacao.charAt(q) == '-') || (equacao.charAt(q) == '^') ||
                                (equacao.charAt(q) == 'x') || (equacao.charAt(q) == '/')) {
                            num1 = Double.valueOf(equacao.substring(q + 1, i));
                            end = q;
                            break;
                        } else if (q == 0) {
                            num1 = Double.valueOf(equacao.substring(q, i));
                            end = q - 1;
                            break;
                        }
                    }
                    for (int k = i; k <= equacao.length(); k++) {
                        if ((equacao.charAt(k) == '+') || (equacao.charAt(k) == '-')) {
                            num2 = Double.valueOf(equacao.substring(i + 1, k));
                            start = k;
                            break;
                        } else if (k == equacao.length() - 1) {
                            num2 = Double.valueOf(equacao.substring(i + 1, k + 1));
                            start = k + 1;
                            break;
                        }
                    }
                    switch (equacao.charAt(i)) {
                        case 'x':
                            result = num1 * num2;
                            break;
                        case '^':
                            result = elevacao(num1, num2);
                            break;
                        case '/':
                            result = num1 / num2;
                            break;
                    }if(end == 0){
                        end = end - 1;
                    }
                    String part1 = equacao.substring(0, end + 1);
                    String part2 = equacao.substring(start, equacao.length());
                    equacao = part1 + String.valueOf(result) + part2;
                    equacao = this.organizar(equacao);
                }
            }
            for (int j = 0; j < tamanho; j++) {
                equacao = this.organizar(equacao);
                for (int i = 1; i < equacao.length(); i++) {
                    if ((equacao.charAt(i) == '+') || (equacao.charAt(i) == '-')) {
                        for (int q = i - 1; q >= 0; q--) {
                            if (q == 0) {
                                num1 = Double.valueOf(equacao.substring(q, i));
                                break;
                            } else if ((equacao.charAt(q) == '+') || (equacao.charAt(q) == '-') || (equacao.charAt(q) == '^') ||
                                    (equacao.charAt(q) == 'x') || (equacao.charAt(q) == '/')) {
                                num1 = Double.valueOf(equacao.substring(q + 1, i));
                                break;
                            }
                        }
                        for (int k = i + 1; k <= equacao.length(); k++) {
                            if ((equacao.charAt(k) == '+') || (equacao.charAt(k) == '-')) {
                                num2 = Double.valueOf(equacao.substring(i + 1, k));
                                end = k;
                                break;
                            } else if (k == equacao.length() - 1) {
                                num2 = Double.valueOf(equacao.substring(i + 1, k + 1));
                                end = k + 1;
                                break;
                            }
                        }
                        switch (equacao.charAt(i)) {
                            case '+':
                                result = num1 + num2;
                                break;
                            case '-':
                                result = num1 - num2;
                                break;
                        }
                        equacao = equacao.substring(end, equacao.length());
                        equacao = String.valueOf(result) + equacao;
                        break;
                    }
                }
            }
            return equacao;
        }

        public double elevacao(double x, double y) {
            if(x == 0){
                return 0;
            }else if(y == 0){
                return 1;
            }else {
                double base = x;
                for (int i = 1; i < y; i++) {
                    x = x * base;
                }
                return x;
            }
        }

        public double logaritmo(double base, double valor) {
            double x = base, i = 1;
            while (i > 0) {
                base = base * x;
                if (base > valor) {
                    return i;
                }
                if (base == valor) {
                    i++;
                    break;
                }
                i++;
            }
            return i;
        }

        //Pega o ultimo elemento da equação
        public char ultimaLetra(String nome) {
            if ((nome != null) && (nome.length() > 0)) {
                char ultima = nome.charAt(nome.length() - 1);
                return ultima;
            }
            return (char) 0;
        }

        //Pega o ulitmo numero digitado
        public String leitorReverso(String equacao){
            for(int i = equacao.length()-1; i > 0; i--){
                if((equacao.charAt(i) == 'x' && equacao.charAt(i-1) == ')') || (equacao.charAt(i) == '/' && equacao.charAt(i-1) == ')')
                        || (equacao.charAt(i) == '^' && equacao.charAt(i-1) == ')')){
                    return "";
                }else if((equacao.charAt(i) == '^') || ((equacao.charAt(i) == 'x') && !(equacao.charAt(i-1) == ')'))||
                        (equacao.charAt(i) == '°') || (equacao.charAt(i) == '/')){
                    String num1 = equacao.substring(i+1, equacao.length());
                    String sobra = equacao.substring(0, i);
                    for(int j = sobra.length()-1; j >= 0; j--){
                        if((equacao.charAt(j) == '+') || (equacao.charAt(j) == '-') || (equacao.charAt(j) == '/') || (equacao.charAt(j) == 'x')){
                            String num2 = sobra.substring(j+1, sobra.length());
                            double result = 0;
                            switch (equacao.charAt(i)) {
                                case '^':
                                    result = elevacao(Double.valueOf(num2), Double.valueOf(num1));
                                    String resultado1 = String.format(Locale.US, "%.2f", result);
                                    return resultado1;
                                case 'x':
                                    result = Double.valueOf(num2)*Double.valueOf(num1);
                                    String resultado2 = String.format(Locale.US, "%.2f", result);
                                    return resultado2;
                                case '/':
                                    result = Double.valueOf(num2)/Double.valueOf(num1);
                                    String resultado3 = String.format(Locale.US, "%.2f", result);
                                    return resultado3;
                                case '°':
                                    result = this.logaritmo(Double.valueOf(num1),Double.valueOf(num2));
                                    String resultado4 = String.format(Locale.US, "%.2f", result);
                                    return resultado4;
                            }
                        }else if(j == 0){ // a equacao acabar
                            String num2 = sobra.substring(0, sobra.length());
                            double result = 0;
                            switch (equacao.charAt(i)) {
                                case '^':
                                    result = elevacao(Double.valueOf(num2), Double.valueOf(num1));
                                    return String.valueOf(result);
                                case 'x':
                                    result = Double.valueOf(num2)*Double.valueOf(num1);
                                    return String.valueOf(result);
                                case '/':
                                    result = Double.valueOf(num2)/Double.valueOf(num1);
                                    return String.valueOf(result);
                                case '°':
                                    result = this.logaritmo(Double.valueOf(num1),Double.valueOf(num2));
                                    return String.valueOf(result);
                            }
                        }
                    }
                }
                if((equacao.charAt(i) == '+') || (equacao.charAt(i) == '-') || (equacao.charAt(i) == '/') || (equacao.charAt(i) == '°')
                        || (equacao.charAt(i) == 'x') ||(equacao.charAt(i) == ')')|| (equacao.charAt(i) == '(') || (i == 0)){
                    String ultimoNum = equacao.substring(i+1, equacao.length());
                    return ultimoNum;
                }
            }
            return equacao;
        }

        //Elimina o ultimo numero da equacao
        public String deleteLast(String equacao){
            for(int i = equacao.length() -1; i > 0; i--){
                if((equacao.charAt(i) == '+') || (equacao.charAt(i) == '-') || (equacao.charAt(i) == '/') || (equacao.charAt(i) == '°')||
                        (equacao.charAt(i) == 'x') || (equacao.charAt(i) == ')') || (equacao.charAt(i) == '^')) {
                    String newEquation = equacao.substring(0, i + 1); // retorna a equação sem o numero
                    return newEquation;
                }
            }
            return "";// retorna a equacao já que não ouve operaçã0
        }

        //Verfica qualquer outro operador
        public String noExpoente(String equacao){
            for(int i = equacao.length()-1; i > 0; i--) {
                if((equacao.charAt(i) == '+') || (equacao.charAt(i) == '-') || (equacao.charAt(i) == '/') ||
                        (equacao.charAt(i) == 'x') || (equacao.charAt(i) == ')')) {
                    equacao = equacao.substring(0, i+1);
                    return equacao;
                }
            }
            return "";
        }
        public String noMultiplication(String equacao){
            for(int i = equacao.length()-1; i > 0; i--) {
                if((equacao.charAt(i) == '+') || (equacao.charAt(i) == '-') || (equacao.charAt(i) == '/') || (equacao.charAt(i) == ')')) {
                    equacao = equacao.substring(0, i+1);
                    return equacao;
                }
            }
            return "";
        }
        public String noDivision(String equacao){
            for(int i = equacao.length()-1; i > 0; i--) {
                if((equacao.charAt(i) == '+') || (equacao.charAt(i) == '-') || (equacao.charAt(i) == 'x') || (equacao.charAt(i) == ')')) {
                    equacao = equacao.substring(0, i+1);
                    return equacao;
                }
            }
            return "";
        }
        public String noLog(String equacao){
            for(int i = equacao.length()-1; i > 0; i--) {
                if((equacao.charAt(i) == '+') || (equacao.charAt(i) == '-') || (equacao.charAt(i) == 'x') ||
                        (equacao.charAt(i) == ')') || (equacao.charAt(i) == '^')) {
                    equacao = equacao.substring(0, i+1);
                    return equacao;
                }
            }
            return "";
        }

        public String leitorDeSinal(String equacao){
            for(int i = equacao.length() - 1; i >= 0; i--){
                if(equacao.charAt(i) == '+'){
                    String newEquation = equacao.substring(0, i);
                    String numChange = equacao.substring(i+1, equacao.length());
                    return newEquation+"-"+numChange;
                }else if(equacao.charAt(i) == '-'){
                    String newEquation = equacao.substring(0, i);
                    String numChange = equacao.substring(i+1, equacao.length());
                    return newEquation+"+"+numChange;
                }else if(i == 0){
                    return "-"+equacao;
                }else if((equacao.charAt(i) == '(')||(equacao.charAt(i) == ')')){
                    String newEquation = equacao.substring(0, i+1);
                    String numChange = equacao.substring(i+1, equacao.length());
                    return newEquation+"-"+numChange;
                }
            }
            return equacao;
        }

        public String lastOperation(String equacao){
            for(int i = equacao.length()-1; i > 0; i--){
                switch (equacao.charAt(i)){
                    case '+': return "+";
                    case '-': return "-";
                    case 'x': return "x";
                    case '/': return "/";
                    case '^': return "^";
                    case '°': return "°";
                    case '(': return "";
                    case ')': return "";
                }
            }
            return "";
        }

}
