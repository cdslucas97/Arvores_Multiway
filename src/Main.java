import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        TwoThreeTree tree = new TwoThreeTree();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1 Inserir");
            System.out.println("2 Buscar");
            System.out.println("3 Remover");
            System.out.println("4 Exibir árvore");
            System.out.println("5 Sair");
            System.out.print("Opção: ");
            int op = sc.nextInt();
            if (op == 1) {
                System.out.print("Chave (int): ");
                int key = sc.nextInt();
                System.out.print("Valor (float): ");
                float val = sc.nextFloat();
                tree.insert(key, val);
            } else if (op == 2) {
                System.out.print("Chave (int): ");
                int key = sc.nextInt();
                System.out.println("Resultado: " + tree.search(key));
            } else if (op == 3) {
                System.out.print("Chave (int): ");
                int key = sc.nextInt();
                tree.remove(key);
            } else if (op == 4) {
                tree.printTree();
            } else if (op == 5) {
                break;
            }
        }
        sc.close();
    }
}
