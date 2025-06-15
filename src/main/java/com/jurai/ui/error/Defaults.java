package com.jurai.ui.error;

public class Defaults {
    public static final String DEFAULT_400 = "400: Parece que seu cliente do JurAI está desatualizado. Atualize-o e tente novamente.";
    public static final String DEFAULT_401 = "Ocorreu um erro com sua autenticação. Tente sair e entrar novamente.";
    public static final String DEFAULT_500 = "500: Ocorreu um erro interno no nosso servidor. Tente novamente mais tarde.";
    public static final String DEFAULT_403 = "Você não tem acesso a este recurso.";
    public static final String DEFAULT_402 = "Você precisa de um plano melhor para acessar este recurso.";
    public static final String DEFAULT_600 = "Parece que você não está conectado com a internet. Verifique sua conexão e tente novamente.";
    public static String unknown(int code) {
        return code + ": Ocorreu um erro desconhecido!";
    }
}
