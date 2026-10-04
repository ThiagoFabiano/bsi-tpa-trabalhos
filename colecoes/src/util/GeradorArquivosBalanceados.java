package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeradorArquivosBalanceados {

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

    static int profundidadeMax = -1;
    static long telefonePiorCaso = -1;

    public static void main(String[] args) {
        File pasta = new File(PASTA_SAIDA);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
        for (int tamanho : TAMANHOS_PADRAO) {
            File arquivo = new File(pasta, "balanceado_" + tamanho + ".txt");
            try {
                gerarArquivo(arquivo, tamanho);
                System.out.println("Gerado: " + arquivo.getName() + " | Telefone de Pior Caso: " + telefonePiorCaso);
            } catch (IOException e) {
                System.out.println("ERRO ao gerar " + arquivo.getName() + ": " + e.getMessage());
            }
        }
    }

    private static void gerarArquivo(File arquivo, int quantidade) throws IOException {
        Random sorteio = new Random();
        List<Long> telefonesBalanceados = new ArrayList<>(quantidade);
        
        profundidadeMax = -1;
        telefonePiorCaso = -1;
        dividirEConquistar(0, quantidade - 1, telefonesBalanceados, 0);

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))) {
            for (Long telefone : telefonesBalanceados) {
                String nome = NOMES[sorteio.nextInt(NOMES.length)]
                        + " " + SOBRENOMES[sorteio.nextInt(SOBRENOMES.length)];
                
                escritor.write(nome + ";" + telefone);
                escritor.newLine();
            }
        }
    }

    private static void dividirEConquistar(int inicio, int fim, List lista, int profundidade) {
        if (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            long telefone = 27900000000L + meio;
            lista.add(telefone);
            
            if (profundidade > profundidadeMax) {
                profundidadeMax = profundidade;
                telefonePiorCaso = telefone;
            }
            
            dividirEConquistar(inicio, meio - 1, lista, profundidade + 1);
            dividirEConquistar(meio + 1, fim, lista, profundidade + 1);
        }
    }
}