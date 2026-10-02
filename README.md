# Trabalho 1 - Análise de Complexidade em Estruturas de Listas

## 📌 Sobre o Projeto
Este projeto foi desenvolvido para a disciplina de Técnicas de Programação Avançadas. O objetivo foi implementar uma biblioteca própria de Lista Encadeada Genérica em Java (suportando listas ordenadas e não ordenadas) e realizar a análise matemática e empírica de complexidade de seus métodos.

Para testar a biblioteca, foi construído um programa interativo que gerencia contatos telefônicos carregados a partir de um arquivo texto, permitindo inserções, buscas e remoções.

## 👥 Componentes do Grupo
* Daniel Pinheiro
* Kaio Henrique da Silva Nezio
* Thiago Fabiano

## 📂 Organização do Código
O código-fonte está em `colecoes/src`, organizado no padrão MVC, com as bibliotecas de estruturas de dados em pacotes próprios:

* **`colecao`**: interface `IColecao.java`, que define os métodos obrigatórios das estruturas.
* **`listaencadeada`**: biblioteca de lista encadeada genérica (`ListaEncadeada.java` e `No.java`).
* **`arvorebinaria`**: biblioteca de árvore binária de busca genérica. `ArvoreBinaria.java` especializa a `ArvoreBinariaBase.java` disponibilizada pelo professor; `No.java` é o nó da árvore.
* **`model`**: classe `Contato.java` e os comparadores `ComparatorContatoPorNome.java` e `ComparatorContatoPorTelefone.java`.
* **`view`**: `ContatoView.java`, responsável por toda a interação com o usuário no terminal (menus, leitura e mensagens).
* **`controller`**: `ContatoController.java`, que recebe a opção escolhida na view, chama o service e manda a view exibir o resultado.
* **`service`**: `ContatoService.java`, com as regras do programa (leitura do arquivo, telefone sem repetição, manter as duas coleções iguais) e a medição dos tempos; `Medicao.java` guarda o resultado de uma operação junto com o tempo gasto.
* **`app`**: `Main.java`, que instancia as coleções escolhidas pelo usuário, o service e o controller.
* **`util`**: `GeradorArquivos.java`, usado para criar os arquivos de entrada dos testes de desempenho.

Os arquivos de entrada ficam em `colecoes/entradas`.

## 📄 Formato do arquivo de entrada
O programa lê o arquivo `entradas/entrada.txt`, com um contato por linha, no formato `Nome;Telefone`:

```
Ana Silva;27900000123
Bruno Lima;27900004567
```

Linhas fora desse formato são ignoradas. Não pode haver dois contatos com o mesmo telefone.

## ⚙️ Como Rodar o Projeto

### Pré-requisitos
* Java Development Kit (JDK) instalado.

### Passo a passo (terminal)

1. Clone o repositório:
   ```bash
   git clone https://github.com/ThiagoFabiano/bsi-tpa-trabalhos.git
   ```

2. Entre na pasta do projeto. **É importante rodar os comandos a partir da pasta `colecoes`**, porque o programa procura o arquivo de entrada em `entradas/entrada.txt`:
   ```bash
   cd bsi-tpa-trabalhos/colecoes
   ```

3. Compile e execute com o `make` (precisa do `make` instalado; no Windows, use o Git Bash ou o WSL):
   ```bash
   make executar
   ```

   Outros comandos do Makefile:
   ```bash
   make                          # só compila
   make gerar                    # gera os arquivos de 50000, 100000, 200000 e 400000 contatos
   make gerar TAMANHOS="1000"    # gera um arquivo com o tamanho que quiser
   make limpar                   # apaga a pasta bin
   ```

   Sem o `make`, compile manualmente. O comando precisa pegar os arquivos de todos os pacotes, por isso muda de um sistema para o outro:

   Linux ou macOS:
   ```bash
   javac -encoding UTF-8 -d bin $(find src -name "*.java")
   ```

   Windows (PowerShell):
   ```powershell
   javac -encoding UTF-8 -d bin (Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
   ```

   Windows (Prompt de Comando):
   ```cmd
   dir /s /B src\*.java > fontes.txt
   javac -encoding UTF-8 -d bin @fontes.txt
   ```

   O `-encoding UTF-8` evita erro de compilação por causa dos acentos que existem nos comentários e nas mensagens do programa.

4. Execute o programa (igual nos três casos):
   ```bash
   java -cp bin app.Main
   ```

   No Windows, se os acentos aparecerem trocados na tela, rode `chcp 65001` antes de executar.

5. O programa pergunta se a lista deve ser ordenada e mostra o menu com as opções de carregar o arquivo, adicionar, pesquisar, remover, alterar e sair.

### Rodando pelo VS Code
Abra a pasta `colecoes` (e não a pasta raiz do repositório), tenha o **Extension Pack for Java** instalado e clique em **Run** acima do `main` da classe `app/Main.java`.

## 📊 Como refazer os testes de desempenho
Os arquivos maiores usados na Seção 3 do relatório são criados pelo gerador. A partir da pasta `colecoes`:

```bash
java -cp bin util.GeradorArquivos                 # gera 100000, 200000, 400000 e 50000 contatos
java -cp bin util.GeradorArquivos 1000            # gera um arquivo com o tamanho que quiser
```

Os arquivos são gravados em `entradas/entrada_<tamanho>.txt`. Para testar um deles, copie-o por cima do `entrada.txt`:

Linux ou macOS:
```bash
cp entradas/entrada_100000.txt entradas/entrada.txt
```

Windows (Prompt de Comando):
```cmd
copy entradas\entrada_100000.txt entradas\entrada.txt
```

Depois é só rodar o programa, escolher o tipo de lista e usar a opção 1 do menu.

> Atenção: o carregamento da lista **ordenada** demora, porque cada contato é inserido na posição
> correta. Com 100.000 contatos leva alguns minutos e com 400.000 passa de uma hora. Na lista não
> ordenada é praticamente instantâneo em qualquer tamanho.
