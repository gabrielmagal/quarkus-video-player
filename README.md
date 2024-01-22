# Guia
Essa aplicação é um servidor onde conseguimos enviar arquivos e também acessar desde o celular até o computador. <br><br>
Temos o endpoint "/videos/upload", esse endpoint é um POST e enviamos o arquivo, também precisamos definir o nome que vai ser salvo. <br><br>
Já o endpoint "/videos/{mediaName}" é utilizado para obter o arquivo e rodar. Caso for um vídeo, irá iniciar automaticamente e se for uma foto também irá abrir... <br><br>
Para acessar do seu celular é bem simples, rode a aplicação no computador: "mvn quarkus:dev" e com o IP da sua máquina em mãos onde você está rodando passe a porta, exemplo: "http://192.168.1.2:8080/videos/nome_do_seu_arquivo.mp4?folder=sua_pasta", dessa forma iriamos buscar por "nome_do_seu_arquivo.mp4" e rodar ele caso exista.

# Como rodar?
Como mencionado anteriormente, basta baixar o projeto e rodar o comando no terminal: mvn quarkus:dev <br>
Também considere criar no seu Disco Local C uma pasta com nome videos, no final ficará: "C:/videos", dentro da pasta videos você cria a estrutura que quiser.

# Como rodar no Docker?
Primeiramente vá no seu Disco C e crie uma pasta chamada volumes, essa pasta será utilizada para utilizar volume no Docker. <br>
Após essa pasta estar criada, acesse a pasta do projeto onde você extraiu os arquivos, e estando na mesma pasta que o pom.xml, abra um Prompt de comando. <br>
Com o Prompt de comando aberto, execute os seguintes comandos: "mvn clean install -DskipTests", isso será utilizado para criar os artefatos do projeto. <br>
Depois desse comando, já com o Docker rodando, utilize ainda no terminal: "docker volume create volume_video_player", esse comando criará o volume no Docker. <br>
Após esses comandos, utilize este: "docker build -f src/main/docker/Dockerfile.jvm -t quarkus/video_player .", desta forma iremos criar uma imagem no Docker para nossa aplicação. <br>
Agora por último e o principal de todos, vamos rodar o container: "docker run -v volume_video_player:/home/jboss/videos/volume_video_player -v C:\git\volumes:/home/jboss/videos -it -i -p 8080:8080 quarkus/video_player". <br> <br>
Pronto, se seguiu corretamente todas essas etapas agora abra o navegador com: "http://localhost:8080/" e o Front da aplicação será exibido!!!

# Visualização da aplicação
Página Inicial:
![1](https://i.imgur.com/zKE4NJP.png)
<br><br>
Página para assistir conteudo armazenado:
![2](https://i.imgur.com/iE9l4fc.png)
<br><br>
Página de envio para armazenamento:
![3](https://i.imgur.com/Y90sHoY.png)
