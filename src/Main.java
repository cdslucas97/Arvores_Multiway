public class Main {
    public static void main(String[] args) throws Exception {
        TwoThreeTree tree = new TwoThreeTree();
        tree.insert(50, 5.0f);
        tree.insert(30, 3.0f);
        tree.insert(70, 7.0f);
        tree.insert(20, 2.0f);
        tree.insert(40, 4.0f);
        System.out.println(tree.search(50));
        System.out.println(tree.search(25));
        System.out.println("Git: https://github.com/SEU_USUARIO/SEU_REPO");
        System.out.println("YouTube: https://youtu.be/SEU_VIDEO");
    }
}
