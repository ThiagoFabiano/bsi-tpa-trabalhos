package view;

import java.util.Scanner;

public class ContatoView {

    private final Scanner scanner;

    public ContatoView() {
        this.scanner = new Scanner(System.in);
    }

    public boolean perguntarSeOrdenada() {
        System.out.println("Deseja criar as listas ordenadas? (1 - Sim / 2 - Não)");
        return lerNumero() == 1;
    }

    public int mostrarMenu() {
        System.out.println("\n========== MENU ==========");
        System.out.println("1 - Carregar dados de arquivo");
        System.out.println("2 - Adicionar contato");
        System.out.println("3 - Pesquisar contato por nome");
        System.out.println("4 - Pesquisar contato por telefone");
        System.out.println("5 - Remover contato por telefone");
        System.out.println("6 - Alterar dados de contato");
        System.out.println("7 - Sair");
        System.out.print("Escolha uma opcao: ");
        return lerNumero();
    }

    public String lerTexto(String rotulo) {
        System.out.print(rotulo);
        return scanner.nextLine();
    }

    public void mostrar(String mensagem) {
        System.out.println(mensagem);
    }

    public void mostrarSemQuebra(String mensagem) {
        System.out.print(mensagem);
    }

    public void mostrarTempo(String rotulo, long nanos) {
        System.out.println(rotulo + ": " + nanos + " ns");
    }

    public void fechar() {
        scanner.close();
    }

    private int lerNumero() {
        while (!scanner.hasNextInt()) {
            if (!scanner.hasNext()) {
                return 7;
            }
            scanner.next();
            System.out.print("Digite um número: ");
        }
        int numero = scanner.nextInt();
        scanner.nextLine();
        return numero;
    }
}
