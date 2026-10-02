SRC := $(shell find src -name '*.java')
BIN := bin

.PHONY: all compilar executar gerar limpar

all: compilar

compilar:
	javac -encoding UTF-8 -d $(BIN) $(SRC)

executar: compilar
	java -cp $(BIN) app.Main

gerar: compilar
	java -cp $(BIN) util.GeradorArquivos $(TAMANHOS)

limpar:
	rm -rf $(BIN)
