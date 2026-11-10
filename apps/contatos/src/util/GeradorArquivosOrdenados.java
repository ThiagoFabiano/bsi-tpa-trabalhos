package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GeradorArquivosOrdenados {

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
                // O último telefone do arquivo é a folha no fim da árvore degenerada
                long telefonePiorCaso = 27900000000L + tamanho - 1;
                System.out.println("Gerado: " + arquivo.getName() + " | Telefone de Pior Caso: " + telefonePiorCaso);
            } catch (IOException e) {
                System.out.println("ERRO ao gerar " + arquivo.getName() + ": " + e.getMessage());
            }
        }
    }

    private static void gerarArquivo(File arquivo, int quantidade) throws IOException {
        GeradorNomes nomes = new GeradorNomes();
        
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))) {
            for (int i = 0; i < quantidade; i++) {
                String nome = nomes.proximo();
                
                // Telefones gerados sequencialmente, SEM EMBARALHAR!
                long telefone = 27900000000L + i;
                
                escritor.write(nome + ";" + telefone);
                escritor.newLine();
            }
        }
    }
}