class Solution {

    public List<String> letterCombinations(String digits) {

        List<String> res = new ArrayList<>();

        if (digits.length() == 0) {
            return res;
        }

        String[] map = new String[10];

        map[2] = "abc";
        map[3] = "def";
        map[4] = "ghi";
        map[5] = "jkl";
        map[6] = "mno";
        map[7] = "pqrs";
        map[8] = "tuv";
        map[9] = "wxyz";

        recursion(digits, 0, "", map, res);

        return res;
    }

    private void recursion(
        String digits,
        int index,
        String current,
        String[] map,
        List<String> res
    ) {

        
        if (index == digits.length()) {
            res.add(current);
            return;
        }

        String letters = map[digits.charAt(index) - '0'];

        for (char ch : letters.toCharArray()) {

            recursion(
                digits,
                index + 1,
                current + ch,
                map,
                res
            );
        }
    }
}