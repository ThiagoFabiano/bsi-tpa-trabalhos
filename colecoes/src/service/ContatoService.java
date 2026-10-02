package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Supplier;
import colecao.IColecao;
import listaencadeada.ListaEncadeada;
import model.ComparatorContatoPorTelefone;
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
        Contato chave = new Contato("", telefone);
        Medicao<Boolean> remocao = medir(() -> colecaoTelefone.remover(chave));
        if (remocao.resultado()) {
            removerDaColecaoNome(chave);
        }
        return remocao;
    }

    public boolean alterar(Contato atual, String novoNome, String novoTelefone) {
        Contato dono = buscarPorTelefone(novoTelefone);
        if (dono != null && dono != atual) {
            return false;
        }
        Contato chaveAntiga = new Contato("", atual.getTelefone());
        colecaoTelefone.remover(chaveAntiga);
        removerDaColecaoNome(chaveAntiga);
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

    private void removerDaColecaoNome(Contato chave) {
        ((ListaEncadeada<Contato>) colecaoNome).remover(chave, new ComparatorContatoPorTelefone());
    }

    private <T> Medicao<T> medir(Supplier<T> operacao) {
        long inicio = System.nanoTime();
        T resultado = operacao.get();
        return new Medicao<>(resultado, System.nanoTime() - inicio);
    }
}
