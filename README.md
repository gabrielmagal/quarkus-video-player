# Guia
Essa aplicação é um servidor onde conseguimos enviar arquivos e também acessar desde o celular até o computador. <br><br>
Temos o endpoint "/videos/upload", esse endpoint é um POST e enviamos o arquivo, também precisamos definir o nome que vai ser salvo. <br><br>
Já o endpoint "/videos/{mediaName}" é utilizado para obter o arquivo e rodar. Caso for um vídeo, irá iniciar automaticamente e se for uma foto também irá abrir... <br><br>
Para acessar do seu celular é bem simples, rode a aplicação no computador: "mvn quarkus:dev" e com o IP da sua máquina em mãos onde você está rodando passe a porta, exemplo: "http://192.168.1.2:8080/videos/nome_do_seu_arquivo.mp4?folder=sua_pasta", dessa forma iriamos buscar por "nome_do_seu_arquivo.mp4" e rodar ele caso exista.

# Como rodar?
Como mencionado anteriormente, basta baixar o projeto e rodar o comando no terminal: mvn quarkus:dev <br>
Também considere criar no seu Disco Local C uma pasta com nome videos, no final ficará: "C:/videos", dentro da pasta videos você cria a estrutura que quiser.

# Visualização da aplicação
Página Inicial:
![1](https://i.imgur.com/zKE4NJP.png)
<br><br>
Página para assistir conteudo armazenado:
![2](https://i.imgur.com/iE9l4fc.png)
<br><br>
Página de envio para armazenamento:
![3](https://i.imgur.com/Y90sHoY.png)
