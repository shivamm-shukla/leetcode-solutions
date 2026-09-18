import java.util.*;

class Solution {

    public int minimumDifference(int[] nums) {

        int n = nums.length / 2;
        int totalSum = 0;

        for (int num : nums) {
            totalSum += num;
        }

        // Store subset sums grouped by count
        List<Integer>[] left = new ArrayList[n + 1];
        List<Integer>[] right = new ArrayList[n + 1];

        for (int i = 0; i <= n; i++) {
            left[i] = new ArrayList<>();
            right[i] = new ArrayList<>();
        }

        // Generate subset sums for both halves
        generateSubsets(nums, 0, n, 0, 0, left);
        generateSubsets(nums, n, 2 * n, 0, 0, right);

        // Sort right-side sums
        for (int i = 0; i <= n; i++) {
            Collections.sort(right[i]);
        }

        int minDiff = Integer.MAX_VALUE;

        // Select k elements from the left half
        for (int k = 0; k <= n; k++) {

            int remaining = n - k;

            for (int leftSum : left[k]) {

                int target = totalSum / 2 - leftSum;

                List<Integer> list = right[remaining];

                // Find the closest sum to target
                int index = Collections.binarySearch(
                    list, target
                );

                if (index < 0) {
                    index = -index - 1;
                }

                // Check the element at index
                if (index < list.size()) {

                    int sum = leftSum + list.get(index);

                    minDiff = Math.min(
                        minDiff,
                        Math.abs(totalSum - 2 * sum)
                    );
                }

                // Check the element before index
                if (index > 0) {

                    int sum = leftSum + list.get(index - 1);

                    minDiff = Math.min(
                        minDiff,
                        Math.abs(totalSum - 2 * sum)
                    );
                }
            }
        }

        return minDiff;
    }

    private void generateSubsets(
        int[] nums,
        int index,
        int end,
        int count,
        int sum,
        List<Integer>[] result
    ) {

        // Base case
        if (index == end) {
            result[count].add(sum);
            return;
        }

        // Do not take current element
        generateSubsets(
            nums,
            index + 1,
            end,
            count,
            sum,
            result
        );

        // Take current element
        generateSubsets(
            nums,
            index + 1,
            end,
            count + 1,
            sum + nums[index],
            result
        );
    }
}