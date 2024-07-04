# Contribuição

## Issues

Se você achar algum bug no projeto, poderá fazer duas coias:

1- Criar um fork ou branch, arrumar o bug, e fazer uma pull request
2- Criar uma issue.

Procure não notificar os bugs via Whatsapp, DMs, Emails ou outros meios a não ser o GitHub. Isso ajudará a deixar o projeto mais organizado.

## Implementação de recursos novos

Para contribuir com código para o projeto, crie uma nova branch ou dê um fork, e faça uma Pull Request.

É recomendável que você utilize alguma IDE para desenvolvimento em Java.
A classe que você deverá executar ao desenvolver o projeto é a io.jurai.Launcher.java, que deverá funcionar sem problemas com o maven.

O projeto é separado em algumas partes.
Aqui está uma estrutura geral dos diretórios, juntamente com uma breve descrição de cada um:

```text
io
└── jurai
    ├── data
    │   ├── model
    │   ├── notifier
    │   └── service
    ├── ui
    │   ├── controller
    │   ├── controls
    │   ├── menus
    │   ├── panes
    │   └── util
    └── util
```
data: diretório com arquivos referentes aos dados da aplicação.
data -> model: Modelos (POJOs) do projeto.
data -> notifier: Notificadores/Observers do projeto.
data -> service: Servicos para os modelos do projeto.

ui: diretório com arquivos referentes à interface gráfica (GUI) do projeto.
ui -> panes: painéis da aplicação. Exemplos: DashboardPane (painel da dashboard), AccountPane.Java (painel da tela de login).
ui -> menus: menus da aplicação: Exemplos: LoginMenu (menu para login), Navbar (barra de navegação).
ui -> controller: controladores dos menus e painéis, contendo todos os listeners para ações (clique em botão, por exemplo).
ui -> controls: controles (Nodes) personalizados para aplicação.
ui -> util: utiliários referentes à GUI.

util: diretório com arquivos utilitários em geral.

Além disso também há o diretório resources, que deve ser bem autoexplicativo.