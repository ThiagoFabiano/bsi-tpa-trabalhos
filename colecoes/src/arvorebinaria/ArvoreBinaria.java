package arvorebinaria;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;

public class ArvoreBinaria<T> extends ArvoreBinariaBase<T> {

    protected No<T> raiz;
    protected int quantidade;

    public ArvoreBinaria(Comparator<T> comparador) {
        super(comparador);
        this.raiz = null;
        this.quantidade = 0;
    }

    @Override
    public boolean adicionar(T novoValor) {
        if (novoValor == null) {
            return false;
        }
        No<T> novo = new No<>(novoValor);
        if (this.raiz == null) {
            this.raiz = novo;
            this.quantidade++;
            return true;
        }
        No<T> atual = this.raiz;
        while (true) {
            int comp = this.comparador.compare(novoValor, atual.getValor());
            if (comp == 0) {
                return false;
            }
            if (comp < 0) {
                if (atual.getEsq() == null) {
                    atual.setEsq(novo);
                    break;
                }
                atual = atual.getEsq();
            } else {
                if (atual.getDir() == null) {
                    atual.setDir(novo);
                    break;
                }
                atual = atual.getDir();
            }
        }
        this.quantidade++;
        return true;
    }

    @Override
    public T pesquisar(T valor) {
        if (valor == null) {
            return null;
        }
        
        No<T> atual = this.raiz;
        
        while (atual != null) {
            int comp = this.comparador.compare(valor, atual.getValor());
            
            if (comp == 0) {
                return atual.getValor(); // Encontrou o elemento
            } else if (comp < 0) {
                atual = atual.getEsq(); // Busca na subárvore esquerda
            } else {
                atual = atual.getDir(); // Busca na subárvore direita
            }
        }
        
        return null; // Não encontrou
    }

    @Override
    public boolean remover(T valor) {
        if (valor == null) {
            return false;
        }
        No<T> pai = null;
        No<T> atual = this.raiz;
        while (atual != null) {
            int comp = this.comparador.compare(valor, atual.getValor());
            if (comp == 0) {
                break;
            }
            pai = atual;
            atual = (comp < 0) ? atual.getEsq() : atual.getDir();
        }
        if (atual == null) {
            return false;
        }

        if (atual.getEsq() == null && atual.getDir() == null) {
            trocarFilho(pai, atual, null);
        } else if (atual.getEsq() == null || atual.getDir() == null) {
            No<T> filho = (atual.getEsq() != null) ? atual.getEsq() : atual.getDir();
            trocarFilho(pai, atual, filho);
        } else {
            No<T> paiSucessor = atual;
            No<T> sucessor = atual.getDir();
            while (sucessor.getEsq() != null) {
                paiSucessor = sucessor;
                sucessor = sucessor.getEsq();
            }
            atual.setValor(sucessor.getValor());
            trocarFilho(paiSucessor, sucessor, sucessor.getDir());
        }
        this.quantidade--;
        return true;
    }

    private void trocarFilho(No<T> pai, No<T> antigo, No<T> novo) {
        if (pai == null) {
            this.raiz = novo;
        } else if (pai.getEsq() == antigo) {
            pai.setEsq(novo);
        } else {
            pai.setDir(novo);
        }
    }

    @Override
    public int quantidadeNos() {
        return this.quantidade; // Retorna a variável já gerenciada pela classe
    }

    @Override
    public int altura() {
        if (this.raiz == null) {
            return -1;
        }
        Deque<No<T>> fila = new ArrayDeque<>();
        fila.add(this.raiz);
        int altura = -1;
        while (!fila.isEmpty()) {
            int tamanhoNivel = fila.size();
            for (int i = 0; i < tamanhoNivel; i++) {
                No<T> no = fila.poll();
                if (no.getEsq() != null) {
                    fila.add(no.getEsq());
                }
                if (no.getDir() != null) {
                    fila.add(no.getDir());
                }
            }
            altura++;
        }
        return altura;
    }

    @Override
    public String caminharEmOrdem() {
        StringBuilder s = new StringBuilder("[");
        Deque<No<T>> pilha = new ArrayDeque<>();
        No<T> atual = this.raiz;
        boolean primeiro = true;
        while (atual != null || !pilha.isEmpty()) {
            while (atual != null) {
                pilha.push(atual);
                atual = atual.getEsq();
            }
            atual = pilha.pop();
            if (!primeiro) {
                s.append(",");
            }
            s.append(atual.getValor());
            primeiro = false;
            atual = atual.getDir();
        }
        s.append("]");
        return s.toString();
    }

    @Override
    public String caminharEmNivel() {
        StringBuilder s = new StringBuilder("[");
        if (this.raiz != null) {
            Deque<No<T>> fila = new ArrayDeque<>();
            fila.add(this.raiz);
            while (!fila.isEmpty()) {
                int tamanhoNivel = fila.size();
                for (int i = 0; i < tamanhoNivel; i++) {
                    No<T> no = fila.poll();
                    if (i > 0) {
                        s.append(",");
                    }
                    s.append(no.getValor());
                    if (no.getEsq() != null) {
                        fila.add(no.getEsq());
                    }
                    if (no.getDir() != null) {
                        fila.add(no.getDir());
                    }
                }
                if (!fila.isEmpty()) {
                    s.append("\n");
                }
            }
        }
        s.append("]");
        return s.toString();
    }
}
