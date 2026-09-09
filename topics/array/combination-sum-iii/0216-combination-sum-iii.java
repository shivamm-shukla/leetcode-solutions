class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();

        recursion(1, k, n, new ArrayList<>(), res);

        return res;
    }

    private void recursion(int num,
                           int k,
                           int n,
                           List<Integer> temp,
                           List<List<Integer>> res) {

        if (k == 0) {
            if (n == 0) {
                res.add(new ArrayList<>(temp));
            }
            return;
        }

        if (num > 9) {
            return;
        }

        temp.add(num);

        recursion(
            num + 1,
            k - 1,
            n - num,
            temp,
            res
        );

        temp.remove(temp.size() - 1);


        recursion(
            num + 1,
            k,
            n,
            temp,
            res
        );
    }
}