# Sistema de Servidor de Arquivos em Java
Este guia irá guiá-lo através da configuração de um sistema de servidor de arquivos em Java. O sistema consistirá em um servidor principal que lida com solicitações de clientes e um conjunto de servidores de arquivos que armazenam arquivos e respondem a consultas do servidor principal.

# Pré-requisitos
Java Development Kit (JDK) instalado
Um Ambiente de Desenvolvimento Integrado (IDE) como IntelliJ IDEA ou Eclipse
Compreensão dos conceitos de rede Java

# Etapa 1: Configure o servidor principal
Crie uma nova classe Java chamada FileServerMain.
Use ServerSocket para ouvir as conexões TCP do cliente de entrada.
Quando um cliente se conecta, aceite a conexão e crie um BufferedReader para ler o nome de arquivo solicitado do cliente.
Envie uma solicitação multicast ou broadcast para todos os servidores de arquivos para verificar se eles contêm o arquivo solicitado.

# Etapa 2: Configurar os servidores de arquivos
Crie uma nova classe Java chamada FileServer.
Use MulticastSocket para ingressar em um grupo multicast e receber mensagens do servidor principal.
Quando uma mensagem for recebida, verifique se o arquivo solicitado existe no diretório do servidor.
Se o arquivo existir, envie uma resposta de volta para o servidor principal indicando sua presença.
# Etapa 3: Lidar com as respostas no servidor principal
Colete respostas de todos os servidores de arquivos dentro de um determinado período de tempo limite.
Quando o tempo limite expirar, compile uma lista de servidores que relataram ter o arquivo.
Envie essa lista de volta para o cliente que fez a solicitação original.
# Etapa 4: Recuperação de arquivos pelo cliente
Depois de receber a lista de servidores do servidor principal, o cliente seleciona um servidor para baixar o arquivo.
Estabelecer uma conexão TCP unicast com o servidor selecionado.
Baixe o arquivo do servidor para a máquina local do cliente.

# Executando o aplicativo
Compile as classes FileServerMain e FileServer.
Execute FileServerMain para iniciar o servidor principal.
Execute uma ou mais instâncias do FileServer para simular servidores de arquivos com diferentes conjuntos de arquivos.
Para testar o sistema, execute um aplicativo cliente que envia uma solicitação de arquivo para o servidor principal.

# Considerações adicionais
Certifique-se de que todos os servidores e clientes estejam na mesma rede e que as portas necessárias estejam abertas e acessíveis.
Implemente o tratamento de erros e o gerenciamento de exceções para lidar com qualquer problema durante a comunicação de rede
Considere o uso de pools de threads para gerenciar várias conexões simultâneas de clientes e respostas de servidor.