import static org.junit.Assert.fail;

public class UnionFind {
    private int[] p;
    private int n;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        p = new int[N];
        for (int i = 0; i < N; i++) {
            p[i] = -1;
        }
        n = N;
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        return Math.abs(p[find(v)]);
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        return p[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        int root1 = find(v1);
        int root2 = find(v2);
        int curV1, curV2;
        while (parent(v1) >= 0) {
            curV1 = v1;
            v1 = parent(v1);
            p[curV1] = root1;

        }
        while (parent(v2) >= 0) {
            curV2 = v2;
            v2 = parent(v2);
            p[curV2] = root2;
        }
        return root1 == root2;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (!(0 <= v && v < n)) {
            throw new IllegalArgumentException("out of range");
        }
        while (parent(v) >= 0) {
            v = parent(v);
        }
        return v;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        if (connected(v1, v2) || v1 == v2) return;
        int root1 = find(v1), root2 = find(v2);
        if (sizeOf(root1) > sizeOf(root2)) {
            p[root1] += p[root2];
            p[root2] = root1;
        } else {
            p[root2] += p[root1];
            p[root1] = root2;
        }
    }

}
