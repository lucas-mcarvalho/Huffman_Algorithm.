package progam;

import entities.huffman;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class main {

    public static void main(String[] args){
    Map<Character,String> h1;

    try {
        //CAMINHO DO ARQUIVO QUE SERA SALVO
    String path  = "/home/lucasmonterio/Downloads/arquivo.txt";
    //ESCRITOR DO JAVA
    FileWriter writer = new FileWriter(path);
    //CRIANDO UM OBJETO
    huffman huff = new huffman();
        h1 = huffman.buildHuffmanTree("Lego");

        //ITERANDO SOBRE O MAP E GRAVANDO NO ARQUIVO
        for (Map.Entry<Character, String> entry : h1.entrySet()) {
            String line = " ' "+entry.getValue()+" : " +entry.getKey()+" \n";
            writer.write(line);
        }
        String encode = huff.encode("Lego",h1);
        writer.write(encode);
        writer.close();
    }
    catch(IOException e){
        e.printStackTrace();
    }

    }
}
