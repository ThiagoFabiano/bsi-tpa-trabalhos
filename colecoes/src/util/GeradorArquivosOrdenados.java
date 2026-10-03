package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GeradorArquivosOrdenados {

    private static final String[] NOMES = {
        "Ana", "Bruno", "Carlos", "Daniela", "Eduardo", "Fernanda", "Gabriel",
        "Helena", "Igor", "Juliana", "Kleber", "Larissa", "Marcos", "Natalia",
        "Otavio", "Paula", "Rafael", "Sabrina", "Thiago", "Vanessa", "William", "Yasmin"
    };

    private static final String[] SOBRENOMES = {
        "Silva", "Souza", "Oliveira", "Santos", "Pereira", "Costa", "Almeida",
        "Rodrigues", "Ferreira", "Gomes", "Martins", "Barbosa", "Ribeiro",
        "Carvalho", "Lima", "Araujo", "Moreira", "Nunes", "Teixeira", "Cardoso"
    };

    private static final int[] TAMANHOS_PADRAO = {50000, 100000, 200000, 400000};
    private static final String PASTA_SAIDA = "entradas";

    public static void main(String[] args) {
        File pasta = new File(PASTA_SAIDA);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
        for (int tamanho : TAMANHOS_PADRAO) {
            // Nome do arquivo indicando que é ordenado
            File arquivo = new File(pasta, "ordenado_" + tamanho + ".txt");
            try {
                gerarArquivo(arquivo, tamanho);
                System.out.println("Gerado: " + arquivo.getAbsolutePath() + " (" + tamanho + " contatos ordenados)");
            } catch (IOException e) {
                System.out.println("ERRO ao gerar " + arquivo.getName() + ": " + e.getMessage());
            }
        }
    }

    private static void gerarArquivo(File arquivo, int quantidade) throws IOException {
        Random sorteio = new Random();
        
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))) {
            for (int i = 0; i < quantidade; i++) {
                String nome = NOMES[sorteio.nextInt(NOMES.length)]
                        + " " + SOBRENOMES[sorteio.nextInt(SOBRENOMES.length)];
                
                // Telefones gerados sequencialmente, SEM EMBARALHAR!
                long telefone = 27900000000L + i;
                
                escritor.write(nome + ";" + telefone);
                escritor.newLine();
            }
        }
    }
}