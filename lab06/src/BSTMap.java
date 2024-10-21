import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    private Node root;
    private int size;

    private class Node {
        K key;
        V value;
        Node left, right;
        
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = right = null;
        }
    }
    public BSTMap(K key, V value){
        root = new Node(key, value);
        size = 1;
    }

    public BSTMap() {
        root = null;
        size = 0;
    }
    @Override
    public void put(K key, V value) {
        putHelper(root, key, value);
    }
   // recursive version
    private void putHelper(Node curr, K key, V value) {
        if (curr == null) {
            root = new Node(key, value);
            size++;
        } else if (key.compareTo(curr.key) == 0) {
            curr.value = value;
        } else if (key.compareTo(curr.key) < 0) {
           if (curr.left == null) {
               curr.left = new Node(key, value);
               size++;
               return;
           }
           putHelper(curr.left, key, value);
        } else {
            if (curr.right == null) {
                curr.right = new Node(key, value);
                size++;
                return;
            }
            putHelper(curr.right, key, value);
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        Node ptr = root;
        while (ptr != null) {
           if (key.compareTo(ptr.key) == 0) {return ptr.value;}
           if (key.compareTo(ptr.key) < 0) { ptr = ptr.left;}
           if (key.compareTo(ptr.key) > 0) { ptr = ptr.right; }
        }
        return null;
    }


    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        return containsKeyHelper(root, key);
    }

    private boolean containsKeyHelper(Node curr,K key) {
        if (curr == null) { return false; }
        if (key.compareTo(curr.key) == 0) return true;
        else if (key.compareTo(curr.key) < 0) return containsKeyHelper(curr.left, key);
        else  return containsKeyHelper(curr.right, key);
    }
    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
      root = null;
      size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        keySetHelper(root, set);
        return set;
    }

    private void keySetHelper(Node cur, Set<K> s) {
        if (cur == null) return;
        s.add(cur.key);
        keySetHelper(cur.left, s);
        keySetHelper(cur.right, s);
    }
    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        return removeHelper(key, null, root);
    }

    /** recursive find key, if it's root just change it, but not connect by parent;
     * @param p parent of the current key
     **/
    private V removeHelper(K key, Node p, Node cur) {
        if (key.compareTo(cur.key) == 0) {
            V res = cur.value;
            if (p == null) {
                root = changeTree(root);
            } else {
                if (cur == p.left) {
                    p.left = changeTree(cur);
                }
                if (cur == p.right) {
                    p.right = changeTree(cur);
                }
            }
            size--;
            return res;
        } else if (key.compareTo(cur.key) < 0) {
            if (cur.left == null) return null;
            return removeHelper(key, cur, cur.left);
        } else {
            if (cur.right == null) return null;
            return removeHelper(key,cur, cur.right);
        }
    }

    /**
     * first, find the substitute node: left tree max or right tree min
     * second, replace it and  delete the node
     * @param cur
     * @return
     */
    private Node changeTree(Node cur) {
        Node res = null;
        if (numChild(cur) == 1) {
            res = cur.left != null ? cur.left : cur.right;
            cur.left = null;
            cur.right = null;
        } else if (numChild(cur) == 2){
            res = findSubsNode(cur, cur.right);    // remove from the tree, then get the node
            res.left = cur.left; res.right = cur.right;
            cur.left = null; cur.right = null;
        }
        return res;
    }

    // find right tree min node
    private Node findSubsNode(Node p, Node cur) {
        if (p.right == cur && cur.left == null && cur.right == null) {
            p.right = null;
            return cur;
        } else if (p.right == cur && cur.left == null) {
            p.right = cur.right;
            return cur;
        } else if (cur.left == null && cur.right != null) {
            p.left = cur.right;
            return cur;
        }else if (cur.left == null) {
            p.left = null;
            return cur;
        } else return findSubsNode(cur, cur.left);
    }


    private int numChild(Node n) {
        int count = 0;
        if (n.left != null) count++;
        if (n.right != null) count++;
        return count;
    }



    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {

       return new Map61BIterator();
    }

    private class Map61BIterator implements Iterator<K> {
        List<K> l = new ArrayList<>();
        public Map61BIterator() {
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public K next() {
            return null;
        }
    }
}
