public class TokLex {
    private CodTokens token;
    private String lexema;
    private TokLex tok;

    public TokLex() {
    }

    public void setToken(CodTokens token){
        this.token = token;
    }
    public CodTokens getToken(){
        return token;
    }
    public void setTok(TokLex tok){
        this.tok = tok;
    }
    public TokLex getTok(){
        return tok;
    }
    public String getLexema() {
        return lexema;
    }
    public void setLexema(String lexema) {
        this.lexema = lexema;
    }
    @Override
    public String toString() {
        return "TokLex{"   +   "token= " + token   +    ", lexema= " + lexema +    "}";
    }

}
