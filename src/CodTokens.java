public enum CodTokens {
    P_INT("int"),
    P_MAIN("main"),
    P_IF("if"),
    P_ELSE("else"),
    P_WHILE("while"),
    P_FOR("for"),
    P_DO("do"),
    P_CHAR("char"),
    P_FLOAT("float"),
    NUMERO_INT("NÚMERO INT"),
    NUMERO_FLOAT("NÚMERO FLOAT"),
    EOF("END OF FILE"),
    DESCONHECIDO("CARACTER DESCONHECIDO"),
    ID("ID"),
    ATRIBUICAO("ATRIBUIÇÃO (=)"),
    IGUALDADE("IGUALDADE (==)"),
    DIFERENCA("DIFERENÇA (!=)"),
    COMENTARIO("COMENTARIO (//)"),
    MAIOR_QUE("MAIOR QUE (>)"),
    MENOR_QUE("MENOR QUE (<)"),
    MAIOR_IGUAL("MAIOR IGUAL (>=)"),
    MENOR_IGUAL("MENOR IGUAL (<=)"),
    ABRE_PAR("ABRE PARENTESES (' ( ')"),
    FECHA_PAR("FECHA PARENTESES (' ) ')"),
    ABRE_CH("ABRE CHAVES (' { ')"),
    FECHA_CH("FECHA CHAVES (' }')"),
    PONTO_VIRGULA("PONTO E VIRGULA (';')"),
    VIRGULA("VIRGULA (',')"),
    SOMA("SOMA ( + )"),
    MULTIPLICACAO("MULTIPLICAÇÃO ( * )"),
    DIVISAO("DIVISÃO ( / )"),
    SUBTRACAO("SUBTRAÇÃO ( - )"),
    V_CHAR("VALOR CHAR ('x')");

    private String nome;

    CodTokens(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}


