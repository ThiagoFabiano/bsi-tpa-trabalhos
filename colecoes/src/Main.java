/*package dominio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import colecao.IColecao;
import listaencadeada.ListaEncadeada;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Pergunta inicial conforme exigência do trabalho
        System.out.println("Deseja criar as listas ordenadas? (1 - Sim / 2 - Não)");
        int opcaoOrdem = scanner.nextInt();
        scanner.nextLine(); // limpa buffer
        boolean ehOrdenada = (opcaoOrdem == 1);

        // Duas listas armazenadas em variáveis do tipo IColecao
        IColecao<Contato> listaNome = new ListaEncadeada<>(new ComparatorContatoPorNome(), ehOrdenada);
        IColecao<Contato> listaTelefone = new ListaEncadeada<>(new ComparatorContatoPorTelefone(), ehOrdenada);

        int opcao = 0;

        do {
            System.out.println("\n========== MENU ==========");
            System.out.println("1 - Carregar dados de arquivo");
            System.out.println("2 - Adicionar contato");
            System.out.println("3 - Pesquisar contato por nome");
            System.out.println("4 - Pesquisar contato por telefone");
            System.out.println("5 - Remover contato por telefone");
            System.out.println("6 - Alterar dados de contato");
            System.out.println("7 - Sair");
            System.out.print("Escolha uma opcao: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa buffer

            switch (opcao) {
                case 1:
                    System.out.print("Lendo o arquivo 'entrada.txt'... ");
                    long inicioLeitura = System.nanoTime();
                    
                    try (BufferedReader br = new BufferedReader(new FileReader("entrada.txt"))) {
                        String linha;
                        while ((linha = br.readLine()) != null) {
                            // Assume formato CSV simples: Nome;Telefone
                            String[] partes = linha.split(";");
                            if (partes.length == 2) {
                                Contato novo = new Contato(partes[0].trim(), partes[1].trim());
                                listaNome.adicionar(novo);
                                listaTelefone.adicionar(novo);
                            }
                        }
                        long fimLeitura = System.nanoTime();
                        System.out.println("\nArquivo lido e listas montadas!");
                        System.out.println("Tempo gasto: " + (fimLeitura - inicioLeitura) + " ns");
                    } catch (Exception e) {
                        System.out.println("\nErro ao ler o arquivo: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.print("Digite o nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite o telefone: ");
                    String tel = scanner.nextLine();
                    
                    Contato tempTel = new Contato("", tel);
                    if (listaTelefone.pesquisar(tempTel) != null) {
                        System.out.println("Erro: Já existe um contato com esse telefone!");
                    } else {
                        Contato novoContato = new Contato(nome, tel);
                        listaNome.adicionar(novoContato);
                        listaTelefone.adicionar(novoContato);
                        System.out.println("Contato adicionado com sucesso!");
                    }
                    break;

                case 3:
                    System.out.print("Digite o nome a pesquisar: ");
                    String nomeBusca = scanner.nextLine();
                    Contato tempNome = new Contato(nomeBusca, "");
                    
                    long inicioBuscaNome = System.nanoTime();
                    Contato achadoNome = listaNome.pesquisar(tempNome);
                    long fimBuscaNome = System.nanoTime();
                    
                    if (achadoNome != null) {
                        System.out.println("Telefone: " + achadoNome.getTelefone());
                    } else {
                        System.out.println("Contato não existe.");
                    }
                    System.out.println("Tempo de busca: " + (fimBuscaNome - inicioBuscaNome) + " ns");
                    break;

                case 4:
                    System.out.print("Digite o telefone a pesquisar: ");
                    String telBusca = scanner.nextLine();
                    Contato tempBuscaTel = new Contato("", telBusca);
                    
                    long inicioBuscaTel = System.nanoTime();
                    Contato achadoTel = listaTelefone.pesquisar(tempBuscaTel);
                    long fimBuscaTel = System.nanoTime();
                    
                    if (achadoTel != null) {
                        System.out.println("Nome: " + achadoTel.getNome());
                    } else {
                        System.out.println("Contato não existe.");
                    }
                    System.out.println("Tempo de busca: " + (fimBuscaTel - inicioBuscaTel) + " ns");
                    break;

                case 5:
                    System.out.print("Digite o telefone a remover: ");
                    String telRemover = scanner.nextLine();
                    Contato dummyTel = new Contato("", telRemover);
                    
                    long inicioRemocao = System.nanoTime();
                    // Pesquisamos primeiro para obter a referência completa do objeto (nome e telefone)
                    Contato contatoRemover = listaTelefone.pesquisar(dummyTel);
                    boolean removeu = false;
                    
                    if (contatoRemover != null) {
                        // Removemos a MESMA referência de ambas as listas para manter consistência
                        listaTelefone.remover(contatoRemover);
                        listaNome.remover(contatoRemover);
                        removeu = true;
                    }
                    long fimRemocao = System.nanoTime();
                    
                    if (removeu) {
                        System.out.println("Contato excluído com sucesso.");
                    } else {
                        System.out.println("O contato não existia.");
                    }
                    System.out.println("Tempo de remoção: " + (fimRemocao - inicioRemocao) + " ns");
                    break;

                case 6:
                    System.out.print("Digite o nome do contato a alterar: ");
                    String nomeAlt = scanner.nextLine();
                    Contato achadoAlt = listaNome.pesquisar(new Contato(nomeAlt, ""));
                    
                    if (achadoAlt != null) {
                        System.out.println("Telefone atual: " + achadoAlt.getTelefone());
                        System.out.print("Novo nome: ");
                        String novoNome = scanner.nextLine();
                        System.out.print("Novo telefone: ");
                        String novoTel = scanner.nextLine();
                        
                        // Para alterar chaves em listas encadeadas ordenadas, devemos remover e readicionar 
                        // caso contrário quebraríamos a estrutura de ordenação da lista
                        listaNome.remover(achadoAlt);
                        listaTelefone.remover(achadoAlt);
                        
                        Contato atualizado = new Contato(novoNome, novoTel);
                        listaNome.adicionar(atualizado);
                        listaTelefone.adicionar(atualizado);
                        
                        System.out.println("Dados alterados com sucesso!");
                    } else {
                        System.out.println("Contato não existe.");
                    }
                    break;

                case 7:
                    System.out.println("Encerrando programa...");
                    // Ambas as listas têm o mesmo tamanho, exibimos a partir de uma delas
                    System.out.println("Quantidade total de contatos: " + listaNome.quantidadeNos());
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 7);
        
        scanner.close();
    }
}*/