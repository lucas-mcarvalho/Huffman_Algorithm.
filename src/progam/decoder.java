package progam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class decoder {

    public static void main(String[] args){
    Map<String ,Character> mapaDecodificado = new HashMap<>();
    StringBuilder codificado = new StringBuilder();
    try(BufferedReader reader = new BufferedReader(new FileReader("/home/lucasmonterio/Downloads/arquivo.txt"))){
        String linha;
        boolean lendo_codificado = false;

        while((linha = reader.readLine())!=null){
            if(linha.equals("###")){
                lendo_codificado = true;
                continue;
            }
            if(!lendo_codificado){
                String [] partes = linha.split(":");
                mapaDecodificado.put(partes[1],partes[0].charAt(0));
            }else{
                codificado.append(linha);
            }
        }


    }

    catch (IOException e){
        e.printStackTrace();
    }

    }
}
