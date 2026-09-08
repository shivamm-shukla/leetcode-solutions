class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();

        Arrays.sort(candidates);

        recursion(res, new ArrayList<>(), candidates, 0, target);

        return res;
    }

    private void recursion(
        List<List<Integer>> res,
        List<Integer> temp,
        int[] candidates,
        int start,
        int target
    ) {
        if (target == 0) {
            res.add(new ArrayList<>(temp));
            return;
        }

        for (int i = start; i < candidates.length; i++) {

            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }

            if (candidates[i] > target) {
                break;
            }

            temp.add(candidates[i]);

            recursion(res, temp, candidates, i + 1, target - candidates[i]);

            temp.remove(temp.size() - 1);
        }
    }
}