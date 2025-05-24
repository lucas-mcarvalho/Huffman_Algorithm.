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
        String serializedTree = reader.readLine(); // primeira linha
        reader.readLine(); // pula "###"
        String encodedText = reader.readLine(); // texto codificado

        // Reconstruir árvore a partir da serialização
        int[] index = {0}; // precisa ser array para passar por referência
        huffman root = huffman.desserializerTree(serializedTree, index);

        // Decodificar
        String original = huffman.decode(encodedText, root);
        System.out.println("Texto decodificado: " + original);

    }

    catch (IOException e){
        e.printStackTrace();
    }

    }
}
