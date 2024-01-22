# Guia
Essa aplicação é um servidor onde conseguimos enviar arquivos e também acessar desde o celular até o computador. <br>
Temos o endpoint "/videos/upload", esse endpoint é um POST e enviamos o arquivo, também precisamos definir o nome que vai ser salvo. <br>
Já o endpoint "/videos/{mediaName}" é utilizado para obter o arquivo e rodar. Caso for um vídeo, irá iniciar automaticamente e se for uma foto também irá abrir... <br>
Para acessar do seu celular é bem simples, rode a aplicação: "mvn quarkus:dev" e com o IP da sua máquina em mãos onde você está rodando passe a porta, exemplo: "http://192.168.1.2:8080/videos/video.mp4", dessa forma iriamos buscar por "video.mp4" e rodar ele caso exista.
