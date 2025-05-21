package entities;


import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

//REPRESENTANDO OS NOS E AS CLASSES DO HUFFMAN
public class huffman {
    private char character;
    private int frequency;
    private huffman left, right;

    public huffman(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    // Construtor para nós internos
    public huffman(int frequency, huffman left, huffman right) {
        this.character = '\0'; // caractere nulo para nós internos
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    // Método para gerar os códigos a partir da árvore
    public static void generateCodes(huffman root, String code, Map<Character, String> map) {
        //VERIFICANDO SE EXISTE A RAIZ
        if (root == null)
            return;

        //SE ENCONTRARMOS UMA FOLHA DA ARVORE ,ARMAZENAMOS NO MAP
        if (root.left == null && root.right == null && root.character != '\0') {
            map.put(root.character, code);
        }
        //FAZEMOS A CHAMADA RECURSIVA ,SE FOR PRA ESQUERDA ADICIONAMOS UM 0
        generateCodes(root.left, code + "0", map);
        //PRA DIREITA ADICIONAMOS 1
        generateCodes(root.right, code + "1", map);
    }


    // Método para construir a árvore de Huffman e gerar os códigos
    public static Map<Character, String> buildHuffmanTree(String text) {
        // VARIAVEL DO TIPO MAP PARA ARMAZENAR CARACTERES
        Map<Character, Integer> frequencyMap = new HashMap<>();
        //USANDO UM FOR PRA PERCORRER A STRING QUEBRADA POR UM VETOR DE CARACTERES
        //USANDO O  CHARARRAY
        for (char c : text.toCharArray()) {
            //GET ORDEFAULT VERIFICA SE A FUNCAO ESTA NO MAPA, SE ESTIVER SOMA +1
            //CASO CONTRARIO COMECA COM 0
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        //CRIANDO A FILA DE PRIORIDADES E USANDO EXPRESSAO LAMBDA
        PriorityQueue<huffman> fila = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));


        //ELE PERCORRE O NO E DEPOIS CRIA UM NOVO NO DE HUFFMAN
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            /*
             * CRIA UM NOVO OBJETO E DEPOIS ADICIONA ELE NA FILA DE PRIORIDADES
             * getkey:caractere, getValue:Frequencia*/
            fila.add(new huffman(entry.getKey(), entry.getValue()));
        }

        //CONSTRUINDO A ARVORE
        while (fila.size() > 1) {

            //PEGANDO OS 2 ELEMENTOS COM MENOR FREQUENCIA DA FILA
            huffman node1 = fila.poll();
            huffman node2 = fila.poll();
            //CRIO A MERGED ,COM A SOMA DAS FREQUENCIAS ENTRA NODE1 E NODE2
            huffman merged = new huffman(node1.frequency + node2.frequency, node1, node2);
            //ADICIONA A FILA DE PRIORIDADES
            fila.add(merged);
        }
        //O ULTIMO ELEMENTO E A RAIZ DA ARVORE
        huffman root = fila.poll();

        //CRIANDO UMA MAP PARA ARMAZENAR OS CODIGOS BINARIOS DA ARVORE DE HUFFMAN
        Map<Character, String> huffmancode = new HashMap<>();
        //ARMAZENANDO NO HUFFMANCODE
        generateCodes(root, "", huffmancode);
        return huffmancode;
    }

    // Método para codificar o texto original usando os códigos
    public static String encode(String text, Map<Character, String> huffmanCodes) {

        //SERVE PARA CONSTRUIR A STRING
        StringBuilder encoder = new StringBuilder();
        //CONVERTE CADA CARACTERE EM UM ARRAY
        for (char c : text.toCharArray()) {
            //ADICIONA NO ENCODER A STRING E O CODIGO BINARIO CORRESPONDENTE ARMAZENADO NO MAP
            encoder.append(huffmanCodes.get(c));
        }
        //RETORNA A STRING CODIFICADA EM BINARIOS
        return encoder.toString();

    }
}

