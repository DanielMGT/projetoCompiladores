import java.sql.SQLOutput;

public class Parser {
    Scan scanner;
    TokLex la;

    public Parser(Scan scanner) {
        this.scanner = scanner;
    }

    public void getTokken() {
        la = this.scanner.verifica();
        if (la.getToken() == CodTokens.P_INT) {
            la = this.scanner.verifica();
            if (la.getToken() == CodTokens.P_MAIN) {
                la = this.scanner.verifica();
                if (la.getToken() == CodTokens.ABRE_PAR) {
                    la = this.scanner.verifica();
                    if (la.getToken() == CodTokens.FECHA_PAR) {
                        la = scanner.verifica();
                        bloco();
                        la = scanner.verifica();
                        if (la.getToken() != CodTokens.EOF) {
                            System.out.println("ERRO: 'End Of File' era esperado.");
                            System.exit(0);
                        }
                    } else {
                        System.out.println("ERRO: Símbolo ')' era esperado.");
                        System.exit(0);
                    }
                } else {
                    System.out.println("ERRO: Símbolo '(' era esperado.");
                    System.exit(0);
                }
            } else {
                System.out.println("ERRO: Palavra 'main' era esperada.");
                System.exit(0);
            }
        } else {
            System.out.println("ERRO: Palavra 'int' era esperada.");
            System.exit(0);
        }
    }

    private boolean isAbreChaves() {
        la = scanner.verifica();
        if (la.getToken() == CodTokens.ABRE_CH) {
            return true;
        }
        return false;
    }

    private boolean isVarValido() {
        if (la.getToken() == CodTokens.P_INT || la.getToken() == CodTokens.P_FLOAT || la.getToken() == CodTokens.P_CHAR) {
            return true;
        }
        return false;
    }

    private void declaraVariavel() {
        la = scanner.verifica();
        if (la.getToken() == CodTokens.ID) {
            la = scanner.verifica();
            if (la.getToken() == CodTokens.VIRGULA) {
                declaraVariavel();
            } else if (la.getToken() == CodTokens.PONTO_VIRGULA) {
                if (la.getToken() == CodTokens.P_INT || la.getToken() == CodTokens.P_FLOAT || la.getToken() == CodTokens.P_CHAR) {
                    declaraVariavel();
                }
            } else {
                System.out.println("ERRO: 'Ponto e vírgula' era esperado.");
                System.exit(0);
            }
        } else {
            System.out.println("ERRO: 'ID' era esperado.");
            System.exit(0);
        }
    }

    private boolean ehComando() {
        if (ehComandoBasico()) {
            return true;
        } else if (ehIteracao()) {
            return true;
        } else if (la.getToken() == CodTokens.P_IF) {
            return true;
        } else {
            return false;
        }
    }

    private void bloco() {
        if (!isAbreChaves()) {
            System.out.println("ERRO: Símbolo '{' era esperado.");
            System.exit(0);
        }
        la = scanner.verifica();
        while (isVarValido()) {
            declaraVariavel();
        }
        while (ehComando()) {
            comando();
        }
        if (la.getToken() == CodTokens.FECHA_CH) {
            la = scanner.verifica();
        } else {
            System.out.println("ERRO: Símbolo '}' era esperado.");
        }
    }

    private void comando() {
        if (ehComandoBasico()) {
            comandoBasico();
        } else if (la.getToken() == CodTokens.P_WHILE || la.getToken() == CodTokens.P_DO) {
            iteracao();
        } else if (la.getToken() == CodTokens.P_IF) {
            condicional();
        }
    }

    private void atribuicao() {
        if (ehAtribuicao()) {
            la = scanner.verifica();
            if (la.getToken() == CodTokens.ATRIBUICAO) {
                la = scanner.verifica();
                expAritm();
                if (la.getToken() == CodTokens.PONTO_VIRGULA) {
                    la = scanner.verifica();
                    if (la.getToken() == CodTokens.ID) {
                        atribuicao();
                    }
                } else {
                    System.out.println("'Ponto e Vírgula' era esperado.");
                    System.exit(0);
                }
            } else {
                System.out.println("Símbolo '=' era esperado.");
                System.exit(0);
            }
        }
    }

    private void comandoBasico() {
        if (ehAtribuicao()) {
            atribuicao();
        } else if (isAbreChaves()) {
            bloco();
        }
    }

    private void condicional() {
        la = scanner.verifica();
        if(la.getToken() == CodTokens.ABRE_PAR){
            la = scanner.verifica();
            expRelac();
            if(la.getToken() == CodTokens.FECHA_PAR){
                la = scanner.verifica();
                comando();
                if(la.getToken() == CodTokens.P_ELSE){
                    la = scanner.verifica();
                    comando();
                }
            }
            else {
                System.out.println("ERRO: Símbolo ')' era esperado.");
                System.exit(0);
            }
        }
        else {
            System.out.println("ERRO: Símbolo '(' era esperado.");
            System.exit(0);
        }
    }

    private void expAritm() {

    }

    private boolean ehExpAritm() {
    }

    private boolean ehOpRelac(){
        if(la.getToken() == CodTokens.DIFERENCA){
            return true;
        }
        else if(la.getToken() == CodTokens.IGUALDADE){
            return true;
        }
        else if(la.getToken() == CodTokens.MAIOR_IGUAL){
            return true;
        }
        else if(la.getToken() == CodTokens.MAIOR_QUE){
            return true;
        }
        else if(la.getToken() == CodTokens.MENOR_IGUAL){
            return true;
        }
        else if(la.getToken() == CodTokens.MENOR_QUE){
            return true;
        }
        else {
            return false;
        }
    }
    private void expRelac() {
        expAritm();
        if(ehOpRelac()){
            la = scanner.verifica();
            expAritm();
        }
        else {
            System.out.println("ERRO: Operador relacional era esperado.");
            System.exit(0);
        }
    }
    private void termo(){

    }

    private void fator(){

    }

    private void plvDo() {
        la = scanner.verifica();
        if (la.getToken() == CodTokens.P_WHILE) {
            la = scanner.verifica();
            if (la.getToken() == CodTokens.ABRE_PAR) {
                expRelac();
                if (la.getToken() == CodTokens.FECHA_PAR) {
                    la = scanner.verifica();
                    if (la.getToken() == CodTokens.PONTO_VIRGULA) {
                        la = scanner.verifica();
                    }
                    else {
                        System.out.println("ERRO: 'Ponto e Vírgula' era esperado.");
                        System.exit(0);
                    }
                }
                else {
                    System.out.println("ERRO: Símbolo ')' era esperado.");
                    System.exit(0);
                }
            }
            else {
                System.out.println("ERRO: Símbolo '(' era esperado.");
                System.exit(0);
            }
        }
        else {
            System.out.println("ERRO: Palavra 'while' era esperado.");
            System.exit(0);
        }
    }

    private void plvWhile() {
        la = scanner.verifica();
        if (la.getToken() == CodTokens.ABRE_PAR) {
            la = scanner.verifica();
            expRelac();
            if (la.getToken() != CodTokens.FECHA_PAR) {
                System.out.println("ERRO: Símbolo ')' era esperado.");
                System.exit(0);
            }
            la = scanner.verifica();
            comando();
        }
        else {
            System.out.println("ERRO: Símbolo ')' era esperado.");
            System.exit(0);
        }
    }

    private void iteracao() {
        if(ehIteracao()){
            if (la.getToken() == CodTokens.P_WHILE) {
                plvWhile();
            } else if (la.getToken() == CodTokens.P_DO) {
                plvDo();
            }
        }
        else {
            System.out.println("Loop precisa ser inicializado.");
            System.exit(0);
        }

    }

    private boolean ehIteracao() {
        if(la.getToken() == CodTokens.P_WHILE || la.getToken() == CodTokens.P_DO){
            return true;
        }
        return false;
    }

    private boolean ehAtribuicao() {
        if (la.getToken() == CodTokens.ID) {
            return true;
        }
        return false;
    }

    private boolean ehComandoBasico() {
        if (ehAtribuicao() || isAbreChaves()) {
            return true;
        }
        return false;
    }
}
