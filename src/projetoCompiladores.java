public class projetoCompiladores {
    public static void main(String[] args) {
            Scan scn = new Scan("C:\\Users\\danie\\IdeaProjects\\projetoCompiladores\\src\\teste.txt");

            Parser parser = new Parser(scn);
            parser.getTokken();
    }

}