package controller;

import java.io.File;
import java.io.IOException;
import model.Contato;
import service.ContatoService;
import service.Medicao;
import view.ContatoView;

public class ContatoController {

    private final ContatoService service;
    private final ContatoView view;

    public ContatoController(ContatoService service, ContatoView view) {
        this.service = service;
        this.view = view;
    }

    public void executar() {
        int opcao;
        do {
            opcao = view.mostrarMenu();
            switch (opcao) {
                case 1 -> carregarArquivo();
                case 2 -> adicionarContato();
                case 3 -> pesquisarPorNome();
                case 4 -> pesquisarPorTelefone();
                case 5 -> removerPorTelefone();
                case 6 -> alterarContato();
                case 7 -> sair();
                default -> view.mostrar("Opção inválida!");
            }
        } while (opcao != 7);
        view.fechar();
    }

    private void carregarArquivo() {
        if (service.arquivoCarregado()) {
            view.mostrar("Os dados do arquivo já foram carregados!");
            return;
        }

        String nomeInformado = view.lerTexto("Digite o nome do arquivo (ex: balanceado_50000.txt): ").trim();

        if (nomeInformado.isEmpty()) {
            view.mostrar("Caminho inválido.");
            return;
        }

        File arquivo = resolverArquivo(nomeInformado);
        view.mostrarSemQuebra("Lendo o arquivo '" + arquivo.getAbsolutePath() + "'... ");
        try {
            long nanos = service.carregarArquivo(arquivo.getPath());
            view.mostrar("\nArquivo lido e estruturas montadas!");
            view.mostrarTempo("Tempo gasto", nanos);
            view.mostrar("Tamanho Estrutura Nome: " + service.quantidadeNome());
            view.mostrar("Tamanho Estrutura Telefone: " + service.quantidadeTelefone());
        } catch (IOException e) {
            view.mostrar("\nErro ao ler o arquivo: " + e.getMessage());
        }
    }

    // Procura o arquivo como foi digitado e, se não achar, nas pastas "entradas" e "../entradas".
    private File resolverArquivo(String nome) {
        File direto = new File(nome);
        if (direto.isFile()) {
            return direto;
        }
        for (String pasta : new String[] { "entradas", "../entradas" }) {
            File candidato = new File(pasta, nome);
            if (candidato.isFile()) {
                return candidato;
            }
        }
        return direto; // não achou: o erro mostrará o caminho que foi tentado
    }

    private void adicionarContato() {
        String nome = view.lerTexto("Digite o nome: ").trim();
        String telefone = view.lerTexto("Digite o telefone: ").trim();
        if (nome.isEmpty() || telefone.isEmpty()) {
            view.mostrar("Erro: Nome e telefone não podem ficar em branco!");
        } else if (service.adicionar(nome, telefone)) {
            view.mostrar("Contato adicionado com sucesso!");
        } else {
            view.mostrar("Erro: Já existe um contato com esse telefone!");
        }
    }

    private void pesquisarPorNome() {
        String nome = view.lerTexto("Digite o nome a pesquisar: ").trim();
        Medicao<Contato> busca = service.pesquisarPorNome(nome);
        view.mostrar(busca.resultado() != null ? "Telefone: " + busca.resultado().getTelefone() : "Contato não existe.");
        view.mostrarTempo("Tempo de busca", busca.nanos());
    }

    private void pesquisarPorTelefone() {
        String telefone = view.lerTexto("Digite o telefone a pesquisar: ").trim();
        Medicao<Contato> busca = service.pesquisarPorTelefone(telefone);
        view.mostrar(busca.resultado() != null ? "Nome: " + busca.resultado().getNome() : "Contato não existe.");
        view.mostrarTempo("Tempo de busca", busca.nanos());
    }

    private void removerPorTelefone() {
        String telefone = view.lerTexto("Digite o telefone a remover: ").trim();
        Medicao<Boolean> remocao = service.removerPorTelefone(telefone);
        view.mostrar(remocao.resultado() ? "Contato excluído com sucesso." : "O contato não existia.");
        view.mostrarTempo("Tempo de remoção", remocao.nanos());
    }

    private void alterarContato() {
        String nome = view.lerTexto("Digite o nome do contato a alterar: ").trim();
        Contato atual = service.pesquisarPorNome(nome).resultado();
        if (atual == null) {
            view.mostrar("Contato não existe.");
            return;
        }
        view.mostrar("Telefone atual: " + atual.getTelefone());
        String novoNome = view.lerTexto("Novo nome: ").trim();
        String novoTelefone = view.lerTexto("Novo telefone: ").trim();
        if (novoNome.isEmpty() || novoTelefone.isEmpty()) {
            view.mostrar("Erro: Nome e telefone não podem ficar em branco!");
        } else if (service.alterar(atual, novoNome, novoTelefone)) {
            view.mostrar("Dados alterados com sucesso!");
        } else {
            view.mostrar("Erro: Já existe outro contato com esse telefone!");
        }
    }

    private void sair() {
        view.mostrar("Encerrando programa...");
        view.mostrar("Quantidade total de contatos: " + service.quantidadeTelefone());
    }
}
