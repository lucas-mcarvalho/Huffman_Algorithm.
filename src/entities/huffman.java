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

    public huffman(){

    }

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
    public static void generateCodes(huffman root, String code, Map<Character,String>map) {
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

        //CRIANDO A FILA DE PRIORIDADES E USANDO EXPRESSAO LAMBDA ,ELA VAI ORDENAR DE ACORDO COM A FREQUENCIA DO NODE
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
    //METODO PARA RETORNAR UMA STRING DE TEXTO SEM CODIGOS
    public static huffman buildTree(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<huffman> fila = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            fila.add(new huffman(entry.getKey(), entry.getValue()));
        }

        while (fila.size() > 1) {
            huffman node1 = fila.poll();
            huffman node2 = fila.poll();
            huffman merged = new huffman(node1.frequency + node2.frequency, node1, node2);
            fila.add(merged);
        }
        return fila.poll();
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

    // Reconstrói a árvore de Huffman a partir de códigos binários
    public static huffman buildTreeFromCodes(Map<String, Character> codes) {

        //CRIA A ARVORE VAZIA COM NO NULL
        huffman root = new huffman();

        //PERCORRENDO O MAPA E ATRIBUINDO OS VALORES NAS VARIAVEIS
        for (Map.Entry<String, Character> entry : codes.entrySet()) {
            String code = entry.getKey();
            char charactere = entry.getValue();
            huffman current = root;


            //DEPOIS DE PERCORRER VERIFICA OS VALORES E INSERE EM CADA BIT
            for (char bit : code.toCharArray()) {
                //SE FOR ZERO E ESQUERDA ,SE NAO E DIREITA
                if (bit == '0') {
                    if (current.left == null)
                        current.left = new huffman();
                    current = current.left;

                } else {
                    if (current.right == null)
                        current.right = new huffman();
                    current = current.right;
                }
            }

            //DEPOIS DE CRIAR OS NODES ATRIBUI A FOLHA O CARACTERE
            current.character = charactere;
        }
        return root;
    }



    //FUNCAO PRA DECODIFICAR A STRIN BINARIA
    public static  String decode (String encodeText,huffman root){
    StringBuilder builder = new StringBuilder();
    huffman current = root;


        /**
         * PERCORRENDO TODA A ARVORE E DECODIFICANDO A STRING ,
         * SE ENCONTRARMOS UM NO FOLHA ,RETORNAMOS PARA A RAIZ
         */

        for(char bit: encodeText.toCharArray()){
            if(bit == '0'){
                    current = current.left;
            }else{
                current = current.right;

            }
            if(current.left == null && current.right == null){
                    builder.append(current.character);
                    current = root;
            }
        }
    return builder.toString();
    }


    //TRANSFORMA A ARVORE DE HUFFMAN E UM STRING LINEAR ,PARA PODER FAZER O PERCURSO EM PRE ORDEM
    public static void sealizerTree(huffman no, StringBuilder sb){

        if(no == null){
            return;
        }
        if(no.left ==null && no.right ==null){
            sb.append('1');
            sb.append(no.character);
        }else{
            sb.append('0');
            sealizerTree(no.left,sb);
            sealizerTree(no.right,sb);
        }
    }

    public static huffman desserializerTree(String s,int [] index){
    if(index[0]>=s.length()){
        return  null;
    }
    char flag = s.charAt(index[0]++);
    if(flag == '1'){
        char caractere = s.charAt(index[0]++);
        huffman folha  = new huffman();
        folha.character = caractere;
        return  folha;
    }else{
        huffman node = new huffman();
        node.left = desserializerTree(s,index);
        node.right = desserializerTree(s,index);
        return node;
         }
    }

}

