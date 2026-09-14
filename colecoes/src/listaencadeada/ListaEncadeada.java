package listaencadeada;

import colecao.IColecao;
import java.util.Comparator;

public class ListaEncadeada<T> implements IColecao<T> {

    private No<T> prim;
    private No<T> ult;
    private int quant;
    private final Comparator<T> comparador;
    private final boolean ehOrdenada;

    public ListaEncadeada(Comparator<T> comparador, boolean ehOrdenada) {
        this.prim = null;
        this.ult = null;
        this.quant = 0;
        this.comparador = comparador;
        this.ehOrdenada = ehOrdenada;
    }

    @Override
    public boolean adicionar(T novoValor) {
        if (this.ehOrdenada) {
            adicionarOrdenado(novoValor);
        } else {
            adicionarNaoOrdenado(novoValor);
        }
        this.quant++;
        return true;
    }

    private void adicionarNaoOrdenado(T novoValor) {
        No<T> novo = new No<>(novoValor);
        if (this.prim == null) {
            this.prim = novo;
            this.ult = novo;
        } else {
            this.ult.setProx(novo);
            this.ult = novo;
        }
    }

    private void adicionarOrdenado(T novoValor) {
        No<T> novo = new No<>(novoValor);
        if (this.prim == null) {
            this.prim = novo;
            this.ult = novo;
            return;
        }
        No<T> ant = null;
        No<T> atual = this.prim;
        while (atual != null && this.comparador.compare(atual.getValor(), novoValor) < 0) {
            ant = atual;
            atual = atual.getProx();
        }
        if (ant == null) {
            novo.setProx(this.prim);
            this.prim = novo;
        } else if (atual == null) {
            this.ult.setProx(novo);
            this.ult = novo;
        } else {
            ant.setProx(novo);
            novo.setProx(atual);
        }
    }

    @Override
    public T pesquisar(T valor) {
        if (valor == null || this.prim == null) {
            return null;
        }

        No<T> atual = this.prim;

        while (atual != null){
            int comp = this.comparador.compare(atual.getValor(), valor);

            // Elemento encontrado
            if (comp == 0) {
                return atual.getValor();
            }

            // Se a lista for ordenada e o elemento atual for maior que o procurado temos a certeza de que ele não está adiante.
            if (this.ehOrdenada && comp > 0){
                return null;
            }

            atual = atual.getProx();
        }

        return null;
        
    }

    @Override
    public boolean remover(T valor) {
        return remover(valor, this.comparador);
    }

    // Remove usando outro criterio de comparacao. Serve para quem usa a lista
    // poder achar o elemento exato quando a lista esta ordenada por outro campo.
    public boolean remover(T valor, Comparator<T> criterio) {
        if (valor == null || this.prim == null){
            return false;
        }

        No<T> ant = null;
        No<T> atual = this.prim;

        while (atual != null) {
            int comp = criterio.compare(atual.getValor(), valor);

            if(comp == 0){
                // Caso 1: O elemento a ser removido é o primeiro da lista
                if (ant == null){
                    this.prim = atual.getProx();
                    // Se a lista só tinha 1 elemento, o ult também deve virar null
                    if (this.prim == null){
                        this.ult = null;
                    }
                } else{
                    // Caso 2: O elemento está no meio ou no fim
                    ant.setProx(atual.getProx());
                    // Se removeu o último, atualiza o ponteiro 'ult' para o anterior
                    if (atual == this.ult){
                        this.ult = ant;
                    }
                }
                
                this.quant--;
                return true;
            }

            ant = atual;
            atual = atual.getProx();
        }
        
        return false;
    }

    @Override
    public int quantidadeNos() {
        return this.quant;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        No<T> aux = this.prim;
        while (aux != null) {
            s.append(aux.getValor());
            if (aux != this.ult) {
                s.append(",");
            }
            aux = aux.getProx();
        }
        s.append("]");
        return s.toString();
    }
}
