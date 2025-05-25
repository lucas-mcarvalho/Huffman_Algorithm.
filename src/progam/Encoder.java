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
        String path  = "/home/lucasmonteiro/Downloads/Huffman_Algorithm-master/texto.huff";
        FileWriter writer = new FileWriter(path);
        String texto = "Batman";

        //CONTROI A ARVORE
        huffman root = huffman.buildTree(texto);

        Map<Character, String> mapaCodigos = new HashMap<>();
        //PERCORRE A ARVORE E GERA OS CODIGOS
        huffman.generateCodes(root, "", mapaCodigos);

        //SERIALIZANDO A ARVORE PRA COLOCAR EM EM PRE ORDEM
        StringBuilder sb = new StringBuilder();
        huffman.sealizerTree(root, sb);
        //DEPOIS DE SERIALIZAR,FAZEMOS O STRING BUILDER VIRAR UMA STRING PADRAO DO JAVA
        String serializedTree = sb.toString();

        //ESCREVENDO NO ARQUIVO DE TEXTO
        writer.write(serializedTree + "\n###\n");

        // Codificar text("Transforma o texto em codigo")
        String encodedText = huffman.encode(texto, mapaCodigos);
        //ESCREVE NO ARQUIVO
        writer.write(encodedText);
        writer.close();

    }
    catch(IOException e){
        e.printStackTrace();
    }

    }
}
