import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class main{

    static final ArrayList<String> c = new ArrayList<String>();
    public static void main(String[] args) throws IOException {

        Date date = new Date(); //
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        System.out.println(formatter.format(date));

        setCaracters();
        String senha = "VbbmR4";//x3dCU4 //VbbmR4
        System.out.println("size: " + c.size());

        ThreadBasica thread1, thread2, thread3, thread4,thread5, thread6;
//        thread1 = new ThreadBasica(senha, 0,9, c );
//        thread2 = new ThreadBasica(senha, 10,19, c );
//        thread3 = new ThreadBasica(senha, 20,29, c );
//        thread4 = new ThreadBasica(senha, 30,39, c );
//        thread5 = new ThreadBasica(senha, 40,49, c );
        thread6 = new ThreadBasica(senha, 58,62, c );
//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();
//        thread5.start();
        thread6.start();

    }

    private static void setCaracters() {
        for (int i = 0; i <= 9; i++) {
            c.add(Integer.toString(i));
        }
        for(char d : "qwertyuiopasdfghjklzxcvbnm".toCharArray()) {
            c.add(Character.toString(d));
        }
        for(char d : "qwertyuiopasdfghjklzxcvbnm".toUpperCase().toCharArray()) {
            c.add(Character.toString(d));
        }

    }

}


class ThreadBasica extends Thread {
    String senha;
    int inicio, fim;
    ArrayList<String> c;
    public ThreadBasica(String senha, int inicio, int fim ,ArrayList<String> c) {
        this.senha = senha;
        this.inicio = inicio;
        this.fim = fim;
        this.c = c;
    }


    public void run() {
        System.out.println("Iniciou Thread com inicio: "+ inicio + " e fim: " + fim);
        try {
            forcaBrutaWhile(senha, inicio,fim, c );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //    x3dCU4
//    terminou Thread com inicio: 30 e fim: 39
//    tempo: 16sec
//    qtd: 49793941
//    qtd/sec: 3112121
    public  static void forcaBruta(String senha, int inicio , int fim,List<String> c){
        long tinicial = System.currentTimeMillis();
        int size =  c.size();
        int qtd = 0;
        for (int i = inicio; i < fim; i++) {
            //System.out.println(i);
            for (int j = 0; j < size; j++) {
                //System.out.println(j);
                for (int k = 0; k < size; k++) {
                    for (int l = 0; l < size; l++) {
                        for (int m = 0; m < size; m++) {
                            for (int n = 0; n < size; n++) {
                                String aux = c.get(i) + c.get(j) + c.get(k) + c.get(l) + c.get(m) + c.get(n);
                                //System.out.println(aux);
                                qtd++;
                                if(aux.equals(senha)){
                                    System.out.println("igual");
                                    System.out.println(aux);
                                    long tfinal = System.currentTimeMillis();
                                    System.out.println("terminou Thread com inicio: "+ inicio + " e fim: " + fim);
                                    long auxa = (tfinal-tinicial)/ 1000;
                                    System.out.println("tempo: " + auxa + "sec");
                                    System.out.println("qtd: " + qtd);
                                    System.out.println("qtd/sec: " + qtd/(auxa));
                                    System.exit(0);
                                }
                                //qtd++;
//                                long tfinal = System.currentTimeMillis();
//                                long auxa = (tfinal-tinicial)/1000;
//                                if(auxa == 2){
//                                    System.out.println(qtd);
//                                    System.exit(0);
//                                }
                            }
                        }
                    }

                }
            }
        }
        System.out.println("terminou Thread com inicio: "+ inicio + " e fim: " + fim);
        long tfinal = System.currentTimeMillis();
        long auxa = (tfinal-tinicial)/1000;
        System.out.println("tempo: " + auxa + "sec");
        System.out.println("qtd: " + qtd);
        System.out.println("qtd/sex: " + qtd/(auxa));

    }


    //    igual
//    x3dCU4
//    terminou Thread com inicio: 30 e fim: 39
//    tempo: 16sec
//    qtd: 49793941
//    qtd/sec: 3112121
    public  static void forcaBrutaWhile(String senha, int inicio , int fim,List<String> c) throws IOException {
        long tinicial = System.currentTimeMillis();
        int size =  c.size();
        int qtd = 0;
        int i = inicio;
        int j = 0;
        int k = 0;
        int l = 0;
        int m = 0;
        int n = 0;
        while (i<fim){
            while ( j < size){
                while ( k < size){
                    while ( l < size){
                        while ( m < size){
                            while ( n < size){
                                String auxSenha = c.get(i) + c.get(j) + c.get(k) + c.get(l) + c.get(m) + c.get(n);
                                //System.out.println(auxSenha);
                                verificaSenhaString(auxSenha, senha);
                                verificaSenhaMain(auxSenha);
                                //try {
                                //verificaSenhaJar(auxSenha);
                                //} catch (IOException e) {
                                //e.printStackTrace();
                                //}
                                n++;
                            };
                            n=0;
                            m++;
                        };
                        m = 0;
                        l++;
                    };
                    l = 0;
                    k++;
                };
                k=0;
                j++;
            };
            j = 0;
            i++;
        };
    }


    public static void verificaSenhaJar(String senha) throws IOException {
        Runtime rt = Runtime.getRuntime();
        String[] commands = {"java",  "TesteSenha", "administrador", senha};
        Process proc = rt.exec(commands);
//        try {
//            //proc.waitFor();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println("Saida do comando:" + s);
            if("Acesso concedido!".equals(s)){
                System.out.println("igual");
                System.exit(0);
            }
        }
    }

    public static void verificaSenhaMain(String senha) throws IOException {
        TesteSenha.main(new String[]{"administrador", senha});
    }


    public static void verificaSenhaString(String senha, String chave){
        if(chave.equals(senha)){
            System.out.println("igual");
            System.out.println("A senha é: " + chave);
            System.exit(0);
        }
    }


}

