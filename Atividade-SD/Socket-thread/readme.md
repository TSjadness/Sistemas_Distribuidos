# Simples Servidor Web Java

Este projeto contém um servidor web simples escrito em Java. Ele é capaz de servir arquivos estáticos de um diretório específico e aceitar várias conexões simultâneas de clientes.

## Objetivo

O objetivo deste projeto é demonstrar como criar um servidor web simples em Java que possa lidar com várias conexões simultâneas e servir arquivos estáticos de um diretório específico.

## Instalação

1. Clone este repositório para sua máquina local.
2. Certifique-se de ter o JDK (Java Development Kit) instalado em seu sistema. Este projeto requer pelo menos a versão 6 do JDK.
3. Abra um terminal e navegue até o diretório raiz do projeto.
4. Compile os arquivos `.java` usando o compilador `javac` incluído no JDK. Por exemplo, você pode executar o comando `javac *.java` para compilar todos os arquivos `.java` no diretório atual.
5. Execute o programa principal (`WebServer`) usando o interpretador `java` do JDK. Por exemplo, você pode executar o comando `java WebServer`.

## Uso

Após iniciar o servidor, você pode começar a enviar solicitações para ele. Para fazer isso, você precisará de um cliente que possa se conectar ao servidor e solicitar arquivos. Um exemplo de tal cliente é o `Client` fornecido neste projeto.

Para solicitar um arquivo do servidor, você precisará digitar o nome do arquivo quando solicitado pelo cliente. O cliente então se conectará ao servidor, solicitará o arquivo e salvará a resposta em um arquivo local.

Se o servidor não conseguir encontrar o arquivo solicitado, ele retornará um erro 404. Se o servidor não responder dentro de um minuto, o cliente encerrará a conexão.

## Exemplos

Você pode executar o servidor e o cliente em duas janelas de terminal separadas. Primeiro, inicie o servidor executando o comando `java WebServer`. Em seguida, inicie o cliente executando o comando `java Client`.

Quando solicitado, digite o nome de um arquivo no diretório "arquivos". O cliente solicitará esse arquivo ao servidor, e o servidor enviará o arquivo de volta para o cliente. O cliente então salvará o arquivo em seu diretório local.