package progam;

import entities.huffman;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Encoder {

    public static void main(String[] args){
    Map<Character,String> h1;

    try {
        String path  = "/home/lucasmonterio/Downloads/arquivo.txt";
        FileWriter writer = new FileWriter(path);
        String texto = "Batman";

        huffman root = huffman.buildTree(texto);

        Map<Character, String> mapaCodigos = new HashMap<>();
        huffman.generateCodes(root, "", mapaCodigos);

        // Serializar árvore
        StringBuilder sb = new StringBuilder();
        huffman.sealizerTree(root, sb);
        String serializedTree = sb.toString();

        writer.write(serializedTree + "\n###\n");

        // Codificar texto
        String encodedText = huffman.encode(texto, mapaCodigos);
        writer.write(encodedText);
        writer.close();

    }
    catch(IOException e){
        e.printStackTrace();
    }

    }
}
