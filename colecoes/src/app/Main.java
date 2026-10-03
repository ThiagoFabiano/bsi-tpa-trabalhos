package app;

import arvorebinaria.ArvoreBinaria; 
import colecao.IColecao;
import controller.ContatoController;
import listaencadeada.ListaEncadeada;
import model.ComparatorContatoPorNome;
import model.ComparatorContatoPorTelefone;
import model.Contato;
import service.ContatoService;
import view.ContatoView;

public class Main {
    public static void main(String[] args) {
        ContatoView view = new ContatoView();
        
        // Recebe a opcao 1, 2 ou 3 da View atualizada
        int opcaoEstrutura = view.escolherEstruturaDados();

        IColecao<Contato> colecaoNome = null;
        IColecao<Contato> colecaoTelefone = null;

        // Instancia as colecoes corretas com base na escolha do usuario
        if (opcaoEstrutura == 1) {
            colecaoNome = new ListaEncadeada<>(new ComparatorContatoPorNome(), false);
            colecaoTelefone = new ListaEncadeada<>(new ComparatorContatoPorTelefone(), false);
        } else if (opcaoEstrutura == 2) {
            colecaoNome = new ListaEncadeada<>(new ComparatorContatoPorNome(), true);
            colecaoTelefone = new ListaEncadeada<>(new ComparatorContatoPorTelefone(), true);
        } else if (opcaoEstrutura == 3) {
            colecaoNome = new ArvoreBinaria<>(new ComparatorContatoPorNome());
            colecaoTelefone = new ArvoreBinaria<>(new ComparatorContatoPorTelefone());
        } else {
            view.mostrar("Opção inválida! Encerrando o programa.");
            view.fechar();
            return;
        }

        new ContatoController(new ContatoService(colecaoNome, colecaoTelefone), view).executar();
    }
}