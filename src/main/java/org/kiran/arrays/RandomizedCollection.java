public import java.util.*;

class RandomizedCollection {
    // I store all elements to support O(1) random access
    private final List<Integer> arr = new ArrayList<>();
    // I map each value to the set of indices where it appears in arr
    private final Map<Integer, Set<Integer>> pos = new HashMap<>();
    // I keep one Random instance for uniform index selection
    private final Random rng = new Random();

    // I insert val by appending and recording its new index
    public boolean insert(int val) {
        // I add index arr.size() to the index set for val
        pos.computeIfAbsent(val, k -> new HashSet<>()).add(arr.size());
        // I append val at the end so its index is known
        arr.add(val);
        // I return true only if this was the first copy
        return pos.get(val).size() == 1;
    }

    // I remove one occurrence of val if present
    public boolean remove(int val) {
        // I return false early if val does not exist
        Set<Integer> set = pos.get(val);
        if (set == null || set.isEmpty()) return false;

        // I take an arbitrary index of val to remove
        int idx = set.iterator().next();

        // I get the last index to enable O(1) removal
        int lastIdx = arr.size() - 1;
        // I read the last value to update its index set after swap
        int lastVal = arr.get(lastIdx);

        // I perform swap only if removing from not-the-last position
        if (idx != lastIdx) {
            // I move last value into idx position in the array
            arr.set(idx, lastVal);
            // I update lastVal's index set: remove old lastIdx, add new idx
            Set<Integer> lastSet = pos.get(lastVal);
            // I remove the obsolete index pointing to the old position
            lastSet.remove(lastIdx);
            // I record the new index after the swap
            lastSet.add(idx);
        }

        // I remove the last element from the array
        arr.remove(lastIdx);
        // I erase idx from val's index set because that occurrence is gone
        set.remove(idx);
        // I clean up the map when no more occurrences of val remain
        if (set.isEmpty()) pos.remove(val);

        // I return true to signal successful removal
        return true;
    }

    // I return a uniformly random element among all stored elements
    public int getRandom() {
        // I choose a random position over current array size
        int i = rng.nextInt(arr.size());
        // I return the value at that position
        return arr.get(i);
    }
}
