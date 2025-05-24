package progam;

import entities.huffman;

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
                String[] partes = linha.split(":");
                if (partes.length == 2) {
                    String codigo = partes[0].trim();
                    char caractere = partes[1].trim().charAt(0);
                    mapaDecodificado.put(codigo, caractere);
                }
            }else{
                codificado.append(linha.trim());
            }
        }
        huffman arvore = huffman.buildTreeFromCodes(mapaDecodificado);

        String original = huffman.decode(codificado.toString(),arvore);

        System.out.println("Texto decodificado : "+original);


    }

    catch (IOException e){
        e.printStackTrace();
    }

    }
}
