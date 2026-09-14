package dominio;

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
        int opcaoOrdem = lerNumero(scanner);
        boolean ehOrdenada = (opcaoOrdem == 1);

        // Duas listas armazenadas em variáveis do tipo IColecao
        IColecao<Contato> listaNome = new ListaEncadeada<>(new ComparatorContatoPorNome(), ehOrdenada);
        IColecao<Contato> listaTelefone = new ListaEncadeada<>(new ComparatorContatoPorTelefone(), ehOrdenada);

        int opcao = 0;
        boolean arquivoCarregado = false;

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
            
            opcao = lerNumero(scanner);

            switch (opcao) {
                case 1:
                    if (arquivoCarregado) {
                        System.out.println("Os dados do arquivo já foram carregados!");
                        break;
                    }

                    System.out.print("Lendo o arquivo 'entrada.txt'... ");
                    long inicioLeitura = System.nanoTime();

                    try (BufferedReader br = new BufferedReader(new FileReader("entradas/entrada.txt"))) {
                        String linha;
                        while ((linha = br.readLine()) != null) {
                            // Assume formato CSV simples: Nome;Telefone
                            String[] partes = linha.split(";");
                            if (partes.length == 2 && !partes[0].trim().isEmpty() && !partes[1].trim().isEmpty()) {
                                Contato novo = new Contato(partes[0].trim(), partes[1].trim());
                                listaNome.adicionar(novo);
                                listaTelefone.adicionar(novo);
                            }
                        }
                        long fimLeitura = System.nanoTime();
                        arquivoCarregado = true;
                        System.out.println("\nArquivo lido e listas montadas!");
                        System.out.println("Tempo gasto: " + (fimLeitura - inicioLeitura) + " ns");

                        System.out.println("Tamanho Lista Nome: " + listaNome.quantidadeNos());
                        System.out.println("Tamanho Lista Telefone: " + listaTelefone.quantidadeNos());
                    } catch (Exception e) {
                        System.out.println("\nErro ao ler o arquivo: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.print("Digite o nome: ");
                    String nome = scanner.nextLine().trim();
                    System.out.print("Digite o telefone: ");
                    String tel = scanner.nextLine().trim();

                    Contato tempTel = new Contato("", tel);
                    if (nome.isEmpty() || tel.isEmpty()) {
                        System.out.println("Erro: Nome e telefone não podem ficar em branco!");
                    } else if (listaTelefone.pesquisar(tempTel) != null) {
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
                    String nomeBusca = scanner.nextLine().trim();
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
                    String telBusca = scanner.nextLine().trim();
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
                    String telRemover = scanner.nextLine().trim();
                    Contato dummyTel = new Contato("", telRemover);
                    
                    long inicioRemocao = System.nanoTime();
                    boolean removeu = listaTelefone.remover(dummyTel);
                    long fimRemocao = System.nanoTime();

                    if (removeu) {
                        ((ListaEncadeada<Contato>) listaNome).remover(dummyTel, new ComparatorContatoPorTelefone());
                    }

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
                        
                        Contato donoDoTelefone = listaTelefone.pesquisar(new Contato("", novoTel.trim()));

                        if (novoNome.trim().isEmpty() || novoTel.trim().isEmpty()) {
                            System.out.println("Erro: Nome e telefone não podem ficar em branco!");
                        } else if (donoDoTelefone != null && donoDoTelefone != achadoAlt) {
                            System.out.println("Erro: Já existe outro contato com esse telefone!");
                        } else {
                            Contato chaveAntiga = new Contato("", achadoAlt.getTelefone());
                            listaTelefone.remover(chaveAntiga);
                            ((ListaEncadeada<Contato>) listaNome).remover(chaveAntiga, new ComparatorContatoPorTelefone());

                            Contato atualizado = new Contato(novoNome.trim(), novoTel.trim());
                            listaNome.adicionar(atualizado);
                            listaTelefone.adicionar(atualizado);

                            System.out.println("Dados alterados com sucesso!");
                        }
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

    private static int lerNumero(Scanner scanner) {
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