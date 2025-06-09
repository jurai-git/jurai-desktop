# Contribuição

## Issues

Se você achar algum bug no projeto, poderá fazer duas coias:

1- Criar um fork ou branch, arrumar o bug, e fazer uma pull request
2- Criar uma issue.

Procure não notificar os bugs via Whatsapp, DMs, Emails ou outros meios a não ser o GitHub. Isso ajudará a deixar o projeto mais organizado.

## Implementação de recursos novos

Para contribuir com código para o projeto, crie uma nova branch ou dê um fork, e faça uma Pull Request.

É recomendável que você utilize alguma IDE para desenvolvimento em Java.
A classe que você deverá executar ao desenvolver o projeto é a com.jurai.Launcher.java, que deverá funcionar sem problemas com o maven.

O projeto é separado em algumas partes.
Aqui está uma estrutura geral dos diretórios, juntamente com uma breve descrição de cada um:

```text
com
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

### Arquitetura do projeto

Aqui está o que você deverá tomar como a arquitetura do projeto.

Primeiramente, este projeto seguirá o padrão MVC. Isso significa que existirão views (código da parte visual), models/services (parte lógica) e controllers (parte que junta a lógica com o visual).

#### Views

As views deste projeto são divididas em 3 partes principais: panes, menus e controls.

Os panes são os principais "pedaços" que compõem a UI. Exemplos são a header, e todas as principais telas do programa (tela de dashboard, tela com os planos, etc).
Se você for implementar uma tela totalmente nova ao programa, que ocupará um espaço considerável, e terá diveras funções próprias, considere criar um _pane_.

Já os menus são partes menores da UI, e estão dentro dos panes. Em geral, cada elemento principal dentro de um pane é um Menu. Por exemplo: Na tela de dashboard, cada tabela com informações, barras de pesquisa e botões poderá ser um menu.
Se você for implementar um conjunto de componentes visuais que fazem parte de um mesmo elemento dentro de um _pane_, considere criar um menu.

Finalmente, os controls são os componentes mais básicos da UI. Eles são totalmente individualizados, e servem apenas uma função. Assim como os menus compõem parte dos panes, os controls compõem parte dos menus.
Se você precisar de um componente que fará uma coisa bem específica no sistema, considere criar um componente.
Como eles são partes individuais da UI, todo control extende, diretamente ou indiretamente, a classe `Node`. Ele pode extender `Button`, `TableView`, ou qualquer outro controle.
Antes de criar o seu controle, verifique se alguém na internet ja não criou algo parecido. A principal biblioteca de controles extras é o ControlsFX.

#### Controllers

Os controllers deste projeto seguirão em parte o design pattern de Dependency Injection.
Isso significa que os controllers terão suas dependências (views que serão controladas por eles) injetadas em seus códigos.
A injeção será feita por Method Injection, ou seja, as views serão passadas pelos métodos da classe.
Aqui está um modelo básico de um controller:

```java
import com.jurai.data.AppState;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MeuController {
    private UsuarioService usuarioService;

    public MeuController() {
        usuarioService = new UsuarioService();
    }

    public void initialize(MeuPane pane) {
        attachEvents(pane);
        attachNotifiers(pane);
    }

    private void attachEvents(MeuPane pane) {
        pane.getCriarUsuarioBtn().setOnAction(e -> {
            // fazer a ação do botão aqui
            usuarioService.createUsuario(/*dados do usuário*/);
        });
    }

    private void attachNotifiers(MeuPane pane) {
        AppState.listen(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                //fazer algo quando alguma propriedade do aplicativo mudar
            }
        });
    }
}
```

Como você pode perceber, o MeuPane (um pane qualquer) é injetado através do método initialize() do controlador.
O controlador terá três funcões: 

1 - detectar quando algum evento for feito na view que ele está controlando (através do attachEvents)

2 - detectar quando alguma propriedade do aplicativo mudar, e atualizer sua view conforme necessário (falarei sobre isso mais para a frente)

3 - atualizar os modelos do projeto (criar um usuário através de um serviço, por exemplo). Isso também será discutido mais para frente.


#### Models e Services

Finalmente, os models e services são as partes do projeto que controlam o fluxo de dados dele.
Os modelos são POJO's (Plain Old Java Objects), com as informações dos modelos do projeto (advogados, processos, etc).
Já os services são classes que deverão oferecer uma interface para que os controllers possam criar, deletar, modificar, e vizualizar os modelos de maneira rica.
Os services podem ter métodos para fazer buscas avançadas, filtragens, e devem sincronizar os dados com o banco de dados.

#### Manipulação do estado da aplicação

Uma parte importante de um programa desktop é manter todos os seus componentes sincronizados em relação ao estado da aplicação.

Imagine, por exemplo, que, no HomePane (home) do programa, existe uma mensagem assim: "Bem-vindo(a), ${nome do usuário logado}!".
Imagine, então, que o usuário, através do pane AccountPane do programa, modifica o seu nome de usuário. De alguma maneira, o HomePane do deverá perceber essa mudança, e alterar o nome de usuário de maneira responsiva.
Em geral, podemos fazer isso de duas maneiras:

A primeira é usando um observador na classe do AccountPane, que notifica diretamente para o pane Home a mudança do estado do usuário.

Já a segunda maneira é utilizando uma classe centralizadora, que recebe e envia todas as modificações. No nosso exemplo, a diferença seria a seguinte: Ao invés do AccountPane falar diretamente para o HomePane (e para todas as outras classes que precisarem) que o usuário mudou, ele fala apenas para o ApplicationState, e este ApplicationState notifica todo mundo que quiser saber.

<img src="/resources/images/observer_architecture.jpg" width="50%" display="block">

Enquanto para aplicações simples a primeira opção funciona bem, no nosso caso a segunda é muito mais viável, visto que quando temos dezenas e dezenas de propriedades e eventos, a primeira opção cria uma complexa rede de observadores e propriedades que deixa o código completamente caótico, enquanto a segunda opção centraliza tudo em apenas uma classe, deixando o código mais limpo.

Aqui está uma simples implementação da classe ApplicationState, e o seu uso nos Panes, para melhor entendimento.

```java
public class ApplicationState {
    private static final PropertyChangeSupport support = new PropertyChangeSupport(new ApplicationState());
    
    private static Advogado currentUser; // propriedade do usuário atualmente logado
    
    public static Advogado getCurrentUser() { return currentUser; }

    public static void setCurrentUser(Advogado newUser) {
        // guardar usuário antigo e atualizar o atual
        Advogado old = ApplicationState.currentUser;
        ApplicationState.currentUser = newUser;
        
        /*
         * isso fará o `support` "notificar" todo mundo que adicionou um listener, 
         * ou seja, todo mundo que observa a propriedade currentUser.
         */
        support.firePropertyChange("currentUser", old, newUser);
    }

    // método que adicionará um listener para quem quiser observar alguma propriedade
    public static void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
```

```java
//Home Pane
public class HomePane {
    private Label welcomeLabel;

    public HomePane() {
        welcomeLabel = new Label("Nenhum usuário logado!");
    }

    public Label getWelcomeLabel() {
        return welcomeLabel;
    }
}

// Controlador do HomePane
public class HomePaneController {

    public HomePaneController() {
    }

    public initialize(HomePane homePane) {
        attachNotifiers(homePane);
    }

    private void attachNotifiers(HomePane homePane) {
        ApplicationState.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                //este método será chamado pelo support.firePropertyChange() no ApplicationState
                if(propertyChangeEvent.getPropertyName() != "currentUser") {
                    return; // se a propriedade não for currentUser, não fazemos nada
                }
                
                if(propertyChangeEvent.getNewValue() == null) {
                    homePane.getWelcomeLabel().setText("Nenhum usuário logado!");
                } else {
                    homePane.getWelcomeLabel().setText("Bem-vindo(a), " + propertyChangeEvent.getNewValue().getNomeUsuario());
                }
            }
        });
    }
}
```

```java
// não farei o AccountPane por simplicidade
public class AccountPaneController {
    public AccountPaneController() {
    }

    public void initialize(AccountPane accountPane) {
        attachEvents(accountPane);
    }

    private void attachEvents(AccountPane accountPane) {
        accountPane.getChangeUsernameBtn().setOnAction(e -> {
            Advogado advogado = ApplicationState.getCurrentUser();
            advogado.setNomeUsuario(accountPane.getNomeUsuarioTextField().getText());
            ApplicationState.setCurrentUser(old); // isso vai inforar ao HomePaneController para atualizar o HomePane
        });
    }
}
```

Para mais facilidade na implementação, você poderá extender a classe AbstractPane e AbstractController em seus panes e controllers, que oferecem uma estrutura base para o desenvolvimento.

### Logging

O projeto também conta com um sistema básico de registro (logging).

Por enquanto, ele conta com dois registradores: o registrador de eventos relacionados à UI (UILogger) e o registrador de eventos relacionados ao ApplicationState (StateLogger).
Você deverá utilizá-los para registrar informações úteis sobre o aplicativo para debugging, como a mudança de algum estado na UI. 
O registro das mudanças na classe ApplicationState é feita de maneira automática, então você não precisa se preocupar com isso.

Além disso, você também pode logar erros ou avisos, utilizando os métodos adequados nas classes.

### Controle via linha de comando

Através da classe CommandListener, o programa também pode receber comandos pelo terminal, se for executado com a flag '--debug': `java -jar JurAI.jar --debug`.
Para rodar estes comandos, utilize `fxctl`.

Os dois comandos atualmente suportados são:

`export | --export | -e`: exporta alguma variável para o ApplicationState. Exemplos:

`fxctl export loggedin true`: seta o ApplicationState.loggedIn como true;
    
`fxctl export currentuser default` seta o ApplicationState.currentUser como um usuário padrão.


`stop-debug | -s | --stop-debug`: para o debugging.


#### Adição de comandos ao ctl:

Se você quiser adicionar comandos ao ctl, cheque a classe CommandListener.
Se for trocar alguma propriedade do JavaFX, utilize `Platform.runLater()`, para evitar problemas de threading.
