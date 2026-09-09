package listaencadeada;

import colecao.IColecao;
import java.util.Comparator;

public class ListaEncadeada<T> implements IColecao<T> {

    private No<T> prim;
    private No<T> ult;
    private int quant;
    private final Comparator<T> comparador;
    private final boolean ehOrdenada;

    public ListaEncadeada() {
        this(null, false);
    }

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
        throw new UnsupportedOperationException("Unimplemented method 'pesquisar'");
    }

    @Override
    public boolean remover(T valor) {
        throw new UnsupportedOperationException("Unimplemented method 'remover'");
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
