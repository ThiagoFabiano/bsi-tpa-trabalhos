# Trabalho 1 - Análise de Complexidade em Estruturas de Listas

## 📌 Sobre o Projeto
Este projeto foi desenvolvido para a disciplina de Técnicas de Programação Avançadas. O objetivo foi implementar uma biblioteca própria de Lista Encadeada Genérica em Java (suportando listas ordenadas e não ordenadas) e realizar a análise matemática e empírica de complexidade de seus métodos. 

Para testar a biblioteca, foi construído um programa interativo que gerencia contatos telefônicos carregados a partir de um arquivo texto, permitindo inserções, buscas e remoções.

## 👥 Componentes do Grupo
* Daniel Pinheiro
* Kaio Henrique da Silva Nezio
* Thiago Fabiano

## 📂 Organização do Código
O código-fonte está estruturado no diretório raiz e dividido nos seguintes pacotes lógicos:

* **`colecao`**: Contém a interface `IColecao.java`, que define os contratos e métodos obrigatórios da estrutura de dados.
* **`listaencadeada`**: É o núcleo da biblioteca. Contém as classes `ListaEncadeada.java` (implementação genérica) e `No.java` (nó da lista).
* **`dominio`**: Contém as regras de negócio e a execução do programa. Inclui a classe `Contato.java`, os comparadores para ordenação (`ComparatorContatoPorNome.java` e `ComparatorContatoPorTelefone.java`) e a classe `Main.java`, que possui o menu interativo de testes.

## ⚙️ Como Rodar o Projeto

### Pré-requisitos
* Java Development Kit (JDK) instalado.
* Um arquivo chamado `entrada.txt` no diretório de execução do projeto. O arquivo deve conter os contatos no formato `Nome;Telefone`, um por linha.

### Executando via Terminal (Linha de Comando)
1. Clone o repositório:
   ```bash
   git clone [https://github.com/ThiagoFabiano/bsi-tpa-trabalhos.git](https://github.com/ThiagoFabiano/bsi-tpa-trabalhos.git)