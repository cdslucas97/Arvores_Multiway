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
                split(node, key, value, null, null);
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
    private void split(Node node, int key, float value, Node leftChild, Node rightChild) throws Exception {
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
        if (node.parent == null) {
            root = new Node(b, vb);
            root.child1 = left;
            root.child2 = right;
            left.parent = root;
            right.parent = root;
        } else {
            Node p = node.parent;
            if (p.numKeys == 1) {
                if (b < p.key1) {
                    p.key2 = p.key1;
                    p.val2 = p.val1;
                    p.key1 = b;
                    p.val1 = vb;
                    p.child3 = p.child2;
                    p.child1 = left;
                    p.child2 = right;
                } else {
                    p.key2 = b;
                    p.val2 = vb;
                    p.child2 = left;
                    p.child3 = right;
                }
                left.parent = p;
                right.parent = p;
                p.numKeys = 2;
            } else {
                split(p, b, vb, left, right);
            }
        }
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
    public void remove(int key) throws Exception {
        removeRec(root, key);
        if (root != null && root.numKeys == 0) {
            if (root.child1 != null) {
                root = root.child1;
                root.parent = null;
            } else {
                root = null;
            }
        }
    }
    private void removeRec(Node node, int key) throws Exception {
        if (node == null) return;
        if (node.child1 == null) {
            if (node.numKeys == 2) {
                if (key == node.key1) {
                    node.key1 = node.key2;
                    node.val1 = node.val2;
                    node.numKeys = 1;
                } else if (key == node.key2) {
                    node.numKeys = 1;
                }
                return;
            }
            if (node.numKeys == 1 && key == node.key1) {
                node.numKeys = 0;
            }
            if (node.numKeys == 0) balance(node);
        } else {
            if (key == node.key1) {
                Node pred = getMax(node.child1);
                node.key1 = pred.numKeys == 2 ? pred.key2 : pred.key1;
                node.val1 = pred.numKeys == 2 ? pred.val2 : pred.val1;
                removeRec(node.child1, node.key1);
            } else if (node.numKeys == 2 && key == node.key2) {
                Node pred = getMax(node.child2);
                node.key2 = pred.numKeys == 2 ? pred.key2 : pred.key1;
                node.val2 = pred.numKeys == 2 ? pred.val2 : pred.val1;
                removeRec(node.child2, node.key2);
            } else if (key < node.key1) {
                removeRec(node.child1, key);
            } else if (node.numKeys == 1 || key < node.key2) {
                removeRec(node.child2, key);
            } else {
                removeRec(node.child3, key);
            }
            if (node.child1.numKeys == 0) balance(node.child1);
            if (node.child2.numKeys == 0) balance(node.child2);
            if (node.child3 != null && node.child3.numKeys == 0) balance(node.child3);
        }
    }
    private Node getMax(Node n) {
        while (n.child3 != null || n.child2 != null) {
            if (n.child3 != null) n = n.child3;
            else n = n.child2;
        }
        return n;
    }
    private void balance(Node node) {
        Node p = node.parent;
        if (p == null) return;
        if (p.child1 == node) {
            Node s = p.child2;
            if (s.numKeys == 2) {
                node.key1 = p.key1;
                node.val1 = p.val1;
                node.numKeys = 1;
                p.key1 = s.key1;
                p.val1 = s.val1;
                s.key1 = s.key2;
                s.val1 = s.val2;
                s.numKeys = 1;
            } else {
                node.key1 = p.key1;
                node.val1 = p.val1;
                node.numKeys = 1;
                p.key1 = p.key2;
                p.val1 = p.val2;
                p.numKeys = 1;
                p.child2 = p.child3;
                p.child3 = null;
            }
        } else if (p.child2 == node) {
            Node l = p.child1;
            Node r = p.child3;
            if (l.numKeys == 2) {
                node.key1 = p.key1;
                node.val1 = p.val1;
                node.numKeys = 1;
                p.key1 = l.key2;
                p.val1 = l.val2;
                l.numKeys = 1;
            } else if (r != null && r.numKeys == 2) {
                node.key1 = p.key2;
                node.val1 = p.val2;
                node.numKeys = 1;
                p.key2 = r.key1;
                p.val2 = r.val1;
                r.key1 = r.key2;
                r.val1 = r.val2;
                r.numKeys = 1;
            } else if (l.numKeys == 1) {
                l.key2 = p.key1;
                l.val2 = p.val1;
                l.numKeys = 2;
                p.key1 = p.key2;
                p.val1 = p.val2;
                p.numKeys = 1;
                p.child2 = p.child3;
                p.child3 = null;
            } else if (r != null && r.numKeys == 1) {
                node.key2 = p.key2;
                node.val2 = p.val2;
                node.numKeys = 2;
                p.numKeys = 1;
                p.child3 = null;
            }
        } else {
            Node s = p.child2;
            if (s.numKeys == 2) {
                node.key1 = p.key2;
                node.val1 = p.val2;
                node.numKeys = 1;
                p.key2 = s.key2;
                p.val2 = s.val2;
                s.numKeys = 1;
            } else {
                Node ls = p.child2;
                ls.key2 = p.key2;
                ls.val2 = p.val2;
                ls.numKeys = 2;
                p.numKeys = 1;
                p.child3 = null;
            }
        }
    }
    public void printTree() {
        printRec(root);
        System.out.println();
    }
    private void printRec(Node node) {
        if (node == null) return;
        printRec(node.child1);
        System.out.print("(" + node.key1 + "," + node.val1 + ") ");
        printRec(node.child2);
        if (node.numKeys == 2) {
            System.out.print("(" + node.key2 + "," + node.val2 + ") ");
            printRec(node.child3);
        }
    }
}
