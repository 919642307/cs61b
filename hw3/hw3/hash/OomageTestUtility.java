package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        ArrayList<Oomage>[] bucket = new ArrayList[M];
        for (int j = 0;j < M;j++) {
            bucket[j] = new ArrayList<>();
        }
        int N = 0;
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode()&0x7FFFFFFF)%M;
            bucket[bucketNum].add(o);
            N++;
        }

        boolean judgement = true;
        for (int i = 0;i < M;i++) {
            if (bucket[i].size()<(N/50)||bucket[i].size()>(N/2.5)) {
                judgement = false;
            }
        }
        return judgement;
    }
}
