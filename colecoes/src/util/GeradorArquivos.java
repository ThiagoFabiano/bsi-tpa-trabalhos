package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GeradorArquivos {

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

    private static final int[] TAMANHOS_PADRAO = {100000, 200000, 400000, 800000};

    private static final String PASTA_SAIDA = "entradas";

    public static void main(String[] args) {
        int[] tamanhos = lerTamanhos(args);
        File pasta = new File(PASTA_SAIDA);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
        for (int tamanho : tamanhos) {
            File arquivo = new File(pasta, "entrada_" + tamanho + ".txt");
            try {
                gerarArquivo(arquivo, tamanho);
                System.out.println("Gerado: " + arquivo.getAbsolutePath() + " (" + tamanho + " contatos)");
            } catch (IOException e) {
                System.out.println("ERRO ao gerar " + arquivo.getName() + ": " + e.getMessage());
            }
        }
    }

    private static int[] lerTamanhos(String[] args) {
        if (args.length == 0) {
            return TAMANHOS_PADRAO;
        }
        int[] tamanhos = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            tamanhos[i] = Integer.parseInt(args[i]);
        }
        return tamanhos;
    }

    private static void gerarArquivo(File arquivo, int quantidade) throws IOException {
        Random sorteio = new Random();
        long[] telefones = sortearTelefones(quantidade, sorteio);
        BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo));
        try {
            for (int i = 0; i < quantidade; i++) {
                String nome = NOMES[sorteio.nextInt(NOMES.length)]
                        + " " + SOBRENOMES[sorteio.nextInt(SOBRENOMES.length)];
                escritor.write(nome + ";" + telefones[i]);
                escritor.newLine();
            }
        } finally {
            escritor.close();
        }
    }

    private static long[] sortearTelefones(int quantidade, Random sorteio) {
        long[] telefones = new long[quantidade];
        for (int i = 0; i < quantidade; i++) {
            telefones[i] = 27900000000L + i;
        }
        for (int i = quantidade - 1; i > 0; i--) {
            int j = sorteio.nextInt(i + 1);
            long troca = telefones[i];
            telefones[i] = telefones[j];
            telefones[j] = troca;
        }
        return telefones;
    }
}
