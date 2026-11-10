package service;

import colecao.IColecao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import model.Contato;

public class ContatoService {

    private final IColecao<Contato> colecaoNome;
    private final IColecao<Contato> colecaoTelefone;
    private boolean arquivoCarregado;

    public ContatoService(IColecao<Contato> colecaoNome, IColecao<Contato> colecaoTelefone) {
        this.colecaoNome = colecaoNome;
        this.colecaoTelefone = colecaoTelefone;
        this.arquivoCarregado = false;
    }

    public boolean arquivoCarregado() {
        return arquivoCarregado;
    }

    public long carregarArquivo(String caminho) throws IOException {
        long inicio = System.nanoTime();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 2 && !partes[0].trim().isEmpty() && !partes[1].trim().isEmpty()) {
                    inserir(new Contato(partes[0].trim(), partes[1].trim()));
                }
            }
        }
        arquivoCarregado = true;
        return System.nanoTime() - inicio;
    }

    public boolean adicionar(String nome, String telefone) {
        if (buscarPorTelefone(telefone) != null) {
            return false;
        }
        inserir(new Contato(nome, telefone));
        return true;
    }

    public Medicao<Contato> pesquisarPorNome(String nome) {
        Contato chave = new Contato(nome, "");
        return medir(() -> colecaoNome.pesquisar(chave));
    }

    public Medicao<Contato> pesquisarPorTelefone(String telefone) {
        Contato chave = new Contato("", telefone);
        return medir(() -> colecaoTelefone.pesquisar(chave));
    }

    public Medicao<Boolean> removerPorTelefone(String telefone) {
        Contato chaveTelefone = new Contato("", telefone);
        Contato contatoASerRemovido = colecaoTelefone.pesquisar(chaveTelefone);

        if (contatoASerRemovido == null) {
            return new Medicao<>(false, 0);
        }

        Medicao<Boolean> remocao = medir(() -> colecaoTelefone.remover(chaveTelefone));

        if (remocao.resultado()) {
            removerDaColecaoNome(contatoASerRemovido); 
        }
        return remocao;
    }

    public boolean alterar(Contato atual, String novoNome, String novoTelefone) {
        Contato dono = buscarPorTelefone(novoTelefone);
        if (dono != null && dono != atual) {
            return false;
        }

        // Remove das duas coleções usando o objeto atual que possui Nome e Telefone reais
        colecaoTelefone.remover(atual);
        removerDaColecaoNome(atual);

        // Insere o novo contato
        inserir(new Contato(novoNome, novoTelefone));
        return true;
    }

    public int quantidadeNome() {
        return colecaoNome.quantidadeNos();
    }

    public int quantidadeTelefone() {
        return colecaoTelefone.quantidadeNos();
    }

    private Contato buscarPorTelefone(String telefone) {
        return colecaoTelefone.pesquisar(new Contato("", telefone));
    }

    private void inserir(Contato contato) {
        colecaoNome.adicionar(contato);
        colecaoTelefone.adicionar(contato);
    }

    private void removerDaColecaoNome(Contato contato) {
        List<Contato> mesmoNome = new ArrayList<>();
        Contato encontrado = colecaoNome.pesquisar(contato);
        while (encontrado != null && encontrado != contato) {
            colecaoNome.remover(encontrado);
            mesmoNome.add(encontrado);
            encontrado = colecaoNome.pesquisar(contato);
        }
        if (encontrado == contato) {
            colecaoNome.remover(contato);
        }
        for (Contato outro : mesmoNome) {
            colecaoNome.adicionar(outro);
        }
    }

    private <T> Medicao<T> medir(Supplier<T> operacao) {
        long inicio = System.nanoTime();
        T resultado = operacao.get();
        return new Medicao<>(resultado, System.nanoTime() - inicio);
    }
}