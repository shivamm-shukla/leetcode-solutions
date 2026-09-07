class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        find(result, new ArrayList<>(), candidates, target, 0);
        return result;
    }

    private void find(List<List<Integer>> result, List<Integer> temp, int[] candidates, int target, int i){
        if(i == candidates.length){
            if(target == 0){
                result.add(new ArrayList<>(temp)); // ✅ FIX
            }
            return;
        }

        int candidate = candidates[i];

        // pick
        if(candidate <= target){
            temp.add(candidate);
            find(result, temp, candidates, target - candidate, i);
            temp.remove(temp.size() - 1); // backtrack
        }

        // not pick
        find(result, temp, candidates, target, i + 1);
    }
}