# Produção

O projeto pode ser distribuído em dois formatos principais: como JAR, ou como um instalador.

Para a produção das versões estáveis do projeto, será utilizado a produção com um instalador, dado as mudanças na forma de distribuição de JARs desde o Java 9 pela Oracle.
Para versões com propósito de testagem, pode ser usado o empacotamento em JAR com dependências.

## Empacotamento em JAR

Se você estiverno Linux, poderá utilizar [este script](build/package_jar_linux.sh), que fará a criação do JAR em build/out/JurAI<Versão>.jar. Se não, siga os seguintes passos:

Execute as etapas `clean` e `package` do ciclo de vida do maven. Isso pode ser feito por meio de uma interface gráfica ou pela linha de comando:

```shell
mvn clean package
```

Isso gerará um arquivo jar em target/JurAI-<versão>-jar-with-dependencies.jar. Esse arquivo pode ser distribuído para testers e devs, e poderá ser executado por quem tiver instalado em sua máquina a JDK do Java 22.
Esse arquivo não deverá ser distribuído para os clientes, ou ser publicado nos binários das versões estáveis do projeto, visto que não contém a JVM adequada para sua execução integrada no executável, e apenas poderá ser executado por quem tiver a SDK do java instalado em sua máquina.

## Empacotamento em Instalador

Execute as etapas `clean` e `compile` do ciclo de vida do maven.
Se não houverem erros, execute o jlink com o plugin do javafx para maven, com o comando `mvn javafx:jlink`. Isso deverá criar uma imagem do programa com uma JRE embutia, no diretório target/JurAI. Teste a imagem, e se tudo estiver funcionando, você pode continuar para a proxima etapa.

NOTA: A plataforma para a qual você for empacotar o projeto deverá ser a mesma que você estiver usando. Por exemplo: Se você for empacotar um instalador para Windows x86_64, isso deverá ser feito em uma máqiuna Windows x86_64.

Para criar o instalador ou pacote, execute:

```shell
mvn jpackage:jpackage@<OS>
```

Aqui, o OS deverá ser um dos seguintes:
`mac_pkg`, `mac_dmg`, `win_exe`, `win_msi`, `linux_deb` ou `linux_appimage`.

Abaixo está uma pequena lista dos formatos, juntamente com algunmas observações.
```text
Windows: 
    .msi (MSI);
    .exe (EXE);
    
Linux: 
    .deb (DEB), para distribuições baseadas em Debian;
    .rpm (RPM), para distribuições baseadas em Redhat (Fedora, CentOS, openSuSE).
    .AppImage (APP_IMAGE), para as demais distribuições;
    
Mac: 
    .pkg (PKG);
    .dmg (DMG);
```

Em geral, prefira os seguintes formatos:

No windows, prefira o EXE, pela maior faciliade de distribuição
No Linux, prefira o AppImage para compatibilidade em todas as distros, e, se possível, também faca um pacote .deb, para as distros baseadas em debian;
No Mac, prefira o PKG, pela melhor experiência do usuário na instalação.

# Instruções específicas para as plataformas


### Empacotamento de EXE e MSI (Windows):


Para fazer o empacotamento nestes formatos, basta executar o comando jpackage com o formato a ser empacotado, como descrito na seção Empacotamento em Instalador.


### empacotamento em deb (linux):


Para empacotar um pacote deb, basta executrar o comando `mvn jpackage:jpackag@linux_deb`, como descrito na seção Empacotamento em Instalador.


### Empacotamento de AppImage (Linux):

O empacotamento em AppImage é um pouco mais complexo que os outros, então leia as instruções com atenção.

Você poderá fazer o empacotamento em AppImage de duas formas. A primeira é rodando [Este script](build/package_appimage_linux.sh), que irá produzir uma AppImage padrão automaticamente para você. Entretanto, para maior controle e customização, é recomendado que você faça a criação da mesma manualmente, como descrito abaixo.

Após ter criado um executável com o JPackage do tipo AppImage, você deverá criar um diretório do tipo AppDir, e criar um AppImage dele com algum programa criador de AppImages. Pessoalmente, eu recomendo o `appimagetool`. Para instalá-lo, execute os seguintes comandos:
```shell
sudo wget -c "https://github.com/AppImage/AppImageKit/releases/download/continuous/appimagetool-x86_64.AppImage" -O /usr/local/bin/appimagetool
```
Isso irá fazer o download do appimagekit (appimagetool), e colocá-lo em /usr/local/bin.
Para mais instruções de download e documentação, vide Github do [AppImageKit](https://github.com/AppImage/AppImageKit).

Após isso, você deverá criar um diretório AppDir. [Aqui](https://github.com/AppImage/AppImageKit/wiki/AppDir) está uma referência de como este diretório deverá ser, mas há aqui também a estrutura básica do diretório:
```text
JurAI.AppDir
└── AppRun
└── JurAI.desktop (opcionalmente um Symlink)
└── JurAI.png (opcionalmente um Symlink)
└── usr
    ├── bin
    │   └── JurAI
    ├── lib
    └── share
        ├── applications
        │   └── JurAI.Desktop
        └── icons
            └── hicolor
                └── 256x256
                    └── JurAI.png
```

As pastas bin e lib deverão ser as mesmas criadas pelo JPackage.
O AppRun poderá ter este conteúdo ([padrão fornecido pelo AppImageKit](https://github.com/AppImage/AppImageKit/blob/master/resources/AppRun)):
```shell
#!/bin/sh
SELF=$(readlink -f "$0")
HERE=${SELF%/*}
export PATH="${HERE}/usr/bin/:${HERE}/usr/sbin/:${HERE}/usr/games/:${HERE}/bin/:${HERE}/sbin/${PATH:+:$PATH}"
export LD_LIBRARY_PATH="${HERE}/usr/lib/:${HERE}/usr/lib/i386-linux-gnu/:${HERE}/usr/lib/x86_64-linux-gnu/:${HERE}/usr/lib32/:${HERE}/usr/lib64/:${HERE}/lib/:${HERE}/lib/i386-linux-gnu/:${HERE}/lib/x86_64-linux-gnu/:${HERE}/lib32/:${HERE}/lib64/${LD_LIBRARY_PATH:+:$LD_LIBRARY_PATH}"
export PYTHONPATH="${HERE}/usr/share/pyshared/${PYTHONPATH:+:$PYTHONPATH}"
export XDG_DATA_DIRS="${HERE}/usr/share/${XDG_DATA_DIRS:+:$XDG_DATA_DIRS}"
export PERLLIB="${HERE}/usr/share/perl5/:${HERE}/usr/lib/perl5/${PERLLIB:+:$PERLLIB}"
export GSETTINGS_SCHEMA_DIR="${HERE}/usr/share/glib-2.0/schemas/${GSETTINGS_SCHEMA_DIR:+:$GSETTINGS_SCHEMA_DIR}"
export QT_PLUGIN_PATH="${HERE}/usr/lib/qt4/plugins/:${HERE}/usr/lib/i386-linux-gnu/qt4/plugins/:${HERE}/usr/lib/x86_64-linux-gnu/qt4/plugins/:${HERE}/usr/lib32/qt4/plugins/:${HERE}/usr/lib64/qt4/plugins/:${HERE}/usr/lib/qt5/plugins/:${HERE}/usr/lib/i386-linux-gnu/qt5/plugins/:${HERE}/usr/lib/x86_64-linux-gnu/qt5/plugins/:${HERE}/usr/lib32/qt5/plugins/:${HERE}/usr/lib64/qt5/plugins/${QT_PLUGIN_PATH:+:$QT_PLUGIN_PATH}"
EXEC=$(grep -e '^Exec=.*' "${HERE}"/*.desktop | head -n 1 | cut -d "=" -f 2 | cut -d " " -f 1)
exec "${EXEC}" "$@"
```
Crie-o e adicione permissões de execução com `chmod a+x AppRun`.
Apenas modifique-o se você tiver certeza do que está fazendo.

O Arquivo .desktop deverá ter este formato:
```text
[Desktop Entry]
Name=JurAI
Exec=JurAI %F
Icon=JurAI
Type=Application
Categories=Utility;
Comment=Auxiliador jurídico
Terminal=false
StartupNotify=true
```
Modifique-o conforme necessário.

Após ter criado este diretório, crie um AppImage com `appimagetool JurAI.AppDir JurAI_x86_64.AppImage`.
Verifique se o AppImage está funcionando, e o seu pacote estará pronto.

#### Troubleshooting

Caso você encontre problemas na criação de uma AppImage, você pode consultar o github do [AppImageKit](https://github.com/AppImage/AppImageKit).
Há também um modelo do [AppDir pronto neste github](resources/Sample.AppDir). Você pode checá-lo para ter uma ideia de como deve ser o arquivo.
Você também pode criar uma Issue no GitHub, caso não ache nenhuma solução para o seu problema.

