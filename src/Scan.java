import java.io.*;

public class Scan {
    private char la;
    private int coluna;
    private int linha;
    private StringBuilder l;
    private String conteudo = "";
    private TokLex tok;
    private int indLinha;
    private int indColuna;

    public Scan (String caminho) {
        l = new StringBuilder();
        this.indColuna = 0;
        this.indLinha = 0;
        this.coluna = 0;
        this.linha = 1;
        this.la = ' ';
        try {
            FileReader arq = new FileReader(caminho);
            BufferedReader lerArq = new BufferedReader(arq);
            try {
                conteudo = lerArq.readLine();
                /*while (conteudo != null) {        //roda o buffer
                    conteudo = lerArq.readLine();
                }*/
            } catch (IOException ex) {
                System.out.println("Erro: Não foi possível ler o arquivo.");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public TokLex verifica() {
        pegaProx();
        if (tok != null) {
            tok.setTok(null);
            l = new StringBuilder();
            verificaLook();
        }
        else {
            verificaLook();
        }
        return tok;
    }

    public char pegaProx() {
        this.la = ' ';
        if (indLinha < conteudo.length()){
            if(indColuna < conteudo.length()){
                this.la = conteudo.charAt(indColuna);
            }
            if (this.la == '\t'){
                coluna+= 4;
            }
            else {
                coluna++;
            }
            indColuna++;
        }
        else if(indColuna == conteudo.length()){
            linha++;
            coluna = 0;
            indColuna = 0;
            indLinha++;
        }
        else {
            la = 0;
        }
        return la;
//        if (coluna < conteudo.length()) {
//            la = conteudo.charAt(coluna);
//            return la;
//        }
//        else if(coluna == conteudo.length()){
//            linha++;
//            return la = ' ';
//        }
//        else {
//            la = 0;
//            return la;
//        }
    }

    public TokLex verificaLook() {
        this.tok = new TokLex();
        if(la == '\t'){
            pegaProx();
        }
        else {
            while (Character.isWhitespace(la)) {
                pegaProx();
            }
        }
        if (Character.isDigit(la) || la == '.') {
            while (Character.isDigit(la)) {
                l.append(la);
                pegaProx();
            }
            if (la == '.') {
                l.append(la);
                pegaProx();
                if (Character.isDigit(la)) {
                    while (Character.isDigit(la)) {
                        l.append(la);
                        pegaProx();
                    }
                    tok.setToken(CodTokens.NUMERO_FLOAT);
                    tok.setLexema(l.toString());
                } else {
                    System.out.println("ERRO: Float mal formado na linha: " + linha + "  |  e coluna: " + coluna);
                    System.exit(0);
                }
                return tok;
            } else {
                tok.setToken(CodTokens.NUMERO_INT);
                tok.setLexema(l.toString());
                return tok;
            }
        } else if (Character.isLetter(la)) {
            while (Character.isLetter(la)) {
                l.append(la);
                pegaProx();
            }
            if (Character.isDigit(la) || la == '_') {
                while (Character.isDigit(la) || Character.isLetter(la)
                        || la == '_') {
                    l.append(la);
                    pegaProx();
                }
                tok.setToken(CodTokens.ID);
                tok.setLexema(l.toString());
            } else {
                if(la == '\''){
                    System.out.println("ERRO: Valor char precisa ser fechado entre apóstrofos. Na linha: " + linha +
                            "    |     coluna: " + coluna + "Ex: 'x'");
                    System.exit(0);
                }
                switch (l.toString()) {
                    case "int":
                        tok.setToken(CodTokens.P_INT);
                        return tok;
                    case "main":
                        tok.setToken(CodTokens.P_MAIN);
                        return tok;
                    case "if":
                        tok.setToken(CodTokens.P_IF);
                        return tok;
                    case "else":
                        tok.setToken(CodTokens.P_ELSE);
                        return tok;
                    case "while":
                        tok.setToken(CodTokens.P_WHILE);
                        return tok;
                    case "for":
                        tok.setToken(CodTokens.P_FOR);
                        return tok;
                    case "do":
                        tok.setToken(CodTokens.P_DO);
                        return tok;
                    case "char":
                        tok.setToken(CodTokens.P_CHAR);
                        return tok;
                    case "float":
                        tok.setToken(CodTokens.P_FLOAT);
                        return tok;
                    default:
                        tok.setToken(CodTokens.ID);
                        tok.setLexema(l.toString());
                        return tok;
                }
            }
        } else if (la == '\'') {
            l.append(la);
            pegaProx();
            if (Character.isLetter(la) || Character.isDigit(la)) {
                l.append(la);
                pegaProx();
                if (la == '\'') {
                    l.append(la);
                    pegaProx();
                    tok.setToken(CodTokens.V_CHAR);
                    tok.setLexema(l.toString());
                    return tok;
                }
            }
            System.out.println("ERRO: Valor char precisa ser fechado entre apóstrofos. Ex: 'x'. Na linha: " + linha + "" +
                    "    |    e coluna: " + coluna);
            System.exit(0);
        } else {
            l.append(la);
            switch (la) {
                case '>':
                    pegaProx();
                    if (la == '=') {
                        l.append(la);
                        pegaProx();
                        tok.setToken(CodTokens.MAIOR_IGUAL);
                        tok.setLexema(l.toString());
                    } else {
                        tok.setToken(CodTokens.MAIOR_QUE);
                        tok.setLexema(l.toString());
                    }
                    return tok;
                case '<':
                    pegaProx();
                    if (la == '=') {
                        l.append(la);
                        pegaProx();
                        tok.setToken(CodTokens.MENOR_IGUAL);
                        tok.setLexema(l.toString());
                    } else {
                        tok.setToken(CodTokens.MENOR_QUE);
                        tok.setLexema(l.toString());
                    }
                    return tok;
                case '=':
                    pegaProx();
                    if (la == '=') {
                        l.append(la);
                        pegaProx();
                        tok.setToken(CodTokens.IGUALDADE);
                        tok.setLexema(l.toString());
                    } else {
                        tok.setToken(CodTokens.ATRIBUICAO);
                        tok.setLexema(l.toString());
                    }
                    return tok;
                case '!':
                    pegaProx();
                    if (la == '=') {
                        l.append(la);
                        pegaProx();
                        tok.setToken(CodTokens.DIFERENCA);
                        tok.setLexema(l.toString());
                    } else {
                        System.out.println("ERRO: Igualdade após a exclamação esperada na linha: " + linha + "" +
                                "  |  e coluna: " + coluna);
                        System.exit(0);
                    }
                    return tok;
                case '-':
                    pegaProx();
                    tok.setToken(CodTokens.SUBTRACAO);
                    return tok;
                case '+':
                    pegaProx();
                    tok.setToken(CodTokens.SOMA);
                    return tok;
                case '*':
                    pegaProx();
                    tok.setToken(CodTokens.MULTIPLICACAO);
                    return tok;
                case '/':
                    Integer linhaLida = linha;
                    pegaProx();
                    if (la == '/') {
                        while (la != 0 && linhaLida == linha) {
                            pegaProx();
                        }
                        return tok;
                    }
                    else if(la == '*'){
                        while(la != 0){
                            pegaProx();
                            if(la == '\u0000'){
                                return null;

                            }
                            if(la == '*'){  pegaProx();
                                if(la == '/'){
                                    //coment multi linha
                                }
                            }
                        }
                    }
                    else {
                        pegaProx();
                        tok.setToken(CodTokens.DIVISAO);
                        return tok;
                    }
                case ',':
                    pegaProx();
                    tok.setToken(CodTokens.VIRGULA);
                    return tok;
                case ';':
                    pegaProx();
                    tok.setToken(CodTokens.PONTO_VIRGULA);
                    return tok;
                case '{':
                    tok.setToken(CodTokens.ABRE_CH);
                    return tok;
                case '}':
                    tok.setToken(CodTokens.FECHA_CH);
                    return tok;
                case '(':
                    tok.setToken(CodTokens.ABRE_PAR);
                    return tok;
                case ')':
                    tok.setToken(CodTokens.FECHA_PAR);
                    return tok;
            }
        }
        return null;
    }
    public TokLex comentMulti(){
        while (la != '*' && la != 0){
            coluna++;
            pegaProx();
        }
        if (la == '*'){
            l.append(la);
            coluna++;
            pegaProx();
            if (la == '/'){
                l.append(la);
                coluna++;
                pegaProx();
            }
            else {
                comentMulti();
                return verificaLook();
            }
        }
        else {
            System.out.println("Erro de fim de arquivo em comentário multilinha " +
                    "na linha: " + linha + "     |     e coluna: " + coluna);
        }
        return null;
    }
}