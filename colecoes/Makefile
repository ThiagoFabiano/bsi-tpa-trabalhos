SRC := $(shell find src -name '*.java')
BIN := bin

.PHONY: all compilar executar gerar gerar-ordenados gerar-balanceados limpar

all: compilar

compilar:
	javac -encoding UTF-8 -d $(BIN) $(SRC)

executar: compilar
	java -cp $(BIN) app.Main

gerar: compilar
	java -cp $(BIN) util.GeradorArquivos $(TAMANHOS)

gerar-ordenados: compilar
	java -cp $(BIN) util.GeradorArquivosOrdenados

gerar-balanceados: compilar
	java -cp $(BIN) util.GeradorArquivosBalanceados

limpar:
	rm -rf $(BIN)
