# Docker

Aqui estão algumas informações sobre como criar e utilizar as imagens Docker para a produção do projeto.

### Debian 11 Bullseye

A Dockerfile e .dockerignore para essa imagem estão na raiz do projeto.

Primeiro, construa a imagem. Utilizaremos o nome `jfx`para essa imagem:

```shell
docker build -t jfx
```

Após isso, inicie a imagem e entre nela com a shell interativa. Atenção! Isso deverá ser feito no diretório raiz do projeto.

```shell
docker run --rm -it -v "$(pwd)":/app -v "$(pwd)/docker-dist:/app/target" jfx bash 
```

Nesta shell, você poderá rodar os comandos para fazer a construção do programa, como `mvn clean compile javafx:jlink`.
Para saber qual comando rodar, volte para a página [PRODUCAO.md](PRODUCAO.md), e leia as instruções do formato que você estiver empacotando.

Após fazer a construção, você deverá extrair os arquivos do container para o host.
Para fazer isso, execute, na raiz do projeto:


```shell
sudo docker run --rm -iv "$(pwd)":/app -v docker-dist:/app/target jfx
```

Com isso, os conteudos da pasta `target` do container deverão ser transferidos para a pasta `docker-dist`, da sua máquina.
Se a saída do seu empacotamento não residir na pasta `target`, troque-a no comando pela pasta em que ela estiver. Por exemplo, se o resultado estiver em /build/out, use:

```shell
sudo docker run --rm -iv "$(pwd)":/app -v docker-dist:/app/build/out jfx
```