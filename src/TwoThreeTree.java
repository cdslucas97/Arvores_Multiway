public class TwoThreeTree {
    private Node root;
    public void insert(int key, float value) throws Exception {
        if (root == null) {
            root = new Node(key, value);
        } else {
            insertRec(root, key, value);
        }
    }
    private void insertRec(Node node, int key, float value) throws Exception {
        if (node.child1 == null && node.child2 == null && node.child3 == null) {
            if (node.numKeys == 1) {
                if (key < node.key1) {
                    node.key2 = node.key1;
                    node.val2 = node.val1;
                    node.key1 = key;
                    node.val1 = value;
                } else {
                    node.key2 = key;
                    node.val2 = value;
                }
                node.numKeys = 2;
            } else {
                splitRoot(node, key, value);
            }
        } else {
            if (key < node.key1) {
                insertRec(node.child1, key, value);
            } else if (node.numKeys == 1 || key < node.key2) {
                insertRec(node.child2, key, value);
            } else {
                insertRec(node.child3, key, value);
            }
        }
    }
    private void splitRoot(Node node, int key, float value) {
        int a = node.key1;
        float va = node.val1;
        int b = node.key2;
        float vb = node.val2;
        int c = key;
        float vc = value;
        if (b > c) {
            int t = c; c = b; b = t;
            float tv = vc; vc = vb; vb = tv;
        }
        if (a > b) {
            int t = a; a = b; b = t;
            float tv = va; va = vb; vb = tv;
        }
        Node left = new Node(a, va);
        Node right = new Node(c, vc);
        root = new Node(b, vb);
        root.child1 = left;
        root.child2 = right;
        left.parent = root;
        right.parent = root;
    }
    public float search(int key) {
        return searchRec(root, key);
    }
    private float searchRec(Node node, int key) {
        if (node == null) return -1f;
        if (key == node.key1) return node.val1;
        if (node.numKeys == 2 && key == node.key2) return node.val2;
        if (key < node.key1) return searchRec(node.child1, key);
        if (node.numKeys == 1 || key < node.key2) return searchRec(node.child2, key);
        return searchRec(node.child3, key);
    }
}
