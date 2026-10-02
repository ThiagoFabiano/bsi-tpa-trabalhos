package app;

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
        boolean ehOrdenada = view.perguntarSeOrdenada();

        IColecao<Contato> colecaoNome = new ListaEncadeada<>(new ComparatorContatoPorNome(), ehOrdenada);
        IColecao<Contato> colecaoTelefone = new ListaEncadeada<>(new ComparatorContatoPorTelefone(), ehOrdenada);

        new ContatoController(new ContatoService(colecaoNome, colecaoTelefone), view).executar();
    }
}
