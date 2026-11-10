package arvorebinaria;

import java.util.Comparator;


public class ArvoreAVL<T> extends ArvoreBinaria<T> {

    public ArvoreAVL(Comparator<T> comparador) {
        super(comparador);
    }

    @Override
    public boolean adicionar(T novoValor) {
        if (novoValor == null) {
            return false;
        }

        int quantidadeAntes = this.quantidade;

        this.raiz = adicionar(this.raiz, novoValor);

        return this.quantidade > quantidadeAntes;
    }

    @Override
    public boolean remover(T valor) {
        if (valor == null) {
            return false;
        }

        int quantidadeAntes = this.quantidade;

        this.raiz = remover(this.raiz, valor);

        return this.quantidade < quantidadeAntes;
    }

    private void atualizarAlturaNo(No<T> no){

        int novaAltura = 1 + Math.max(alturaNo(no.getEsq()) , alturaNo(no.getDir()));

        no.setAltura(novaAltura);
    }

    private int alturaNo(No<T> no){

        if (no == null){
            return -1;
        }

        return no.getAltura();
    }

    private int fatorBalanceamento(No<T> no){

        int fb = alturaNo(no.getEsq()) - alturaNo(no.getDir());

        return fb;
    }

    private No<T> rotacaoDireita(No<T> noDesbalanceado){

        No<T> novoNoPai = noDesbalanceado.getEsq();

        noDesbalanceado.setEsq(novoNoPai.getDir());

        novoNoPai.setDir(noDesbalanceado);

        atualizarAlturaNo(noDesbalanceado);
        atualizarAlturaNo(novoNoPai);

        return novoNoPai;
    }

    private No<T> rotacaoEsquerda(No<T> noDesbalanceado){

         No<T> novoNoPai = noDesbalanceado.getDir();

        noDesbalanceado.setDir(novoNoPai.getEsq());

        novoNoPai.setEsq(noDesbalanceado);

        atualizarAlturaNo(noDesbalanceado);
        atualizarAlturaNo(novoNoPai);

        return novoNoPai;
    }

    private No<T> balancear(No<T> no){

        atualizarAlturaNo(no);

        int fb = fatorBalanceamento(no);

        if(fb > 1){
            if(fatorBalanceamento(no.getEsq()) < 0){
                no.setEsq(rotacaoEsquerda(no.getEsq()));
            }
            return rotacaoDireita(no);
        }

        if(fb < -1){
            if(fatorBalanceamento(no.getDir()) > 0){
                no.setDir(rotacaoDireita(no.getDir()));
            }
            return rotacaoEsquerda(no);
        }

        return no;
    }

    private No<T> adicionar(No<T> no, T valor){
        if(no == null){
            No<T> novoNo = new No<>(valor);

            this.quantidade++;

            return novoNo;
        }

        int comp = this.comparador.compare(valor, no.getValor());

        if(comp == 0){
            return no;
        }

        if(comp < 0){
            no.setEsq(adicionar(no.getEsq(), valor));
        } else {
            no.setDir(adicionar(no.getDir(), valor));
        }

        return balancear(no);
    }

    private No<T> remover(No<T> no, T valor){
        if(no == null){
            return null;
        }

        int comp = this.comparador.compare(valor, no.getValor());

        if(comp < 0){
            no.setEsq(remover(no.getEsq(), valor));
        } else if(comp > 0){
            no.setDir(remover(no.getDir(), valor));
        } else {
            if(no.getEsq() == null){
                this.quantidade--;
                return no.getDir();
            }
            if(no.getDir() == null){
                this.quantidade--;
                return no.getEsq();
            }

            No<T> sucessor = no.getDir();
            while(sucessor.getEsq() != null){
                sucessor = sucessor.getEsq();
            }

            no.setValor(sucessor.getValor());
            no.setDir(remover(no.getDir(), sucessor.getValor()));
        }

        return balancear(no);
    }

}
