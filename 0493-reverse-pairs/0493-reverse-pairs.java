class Solution {
    public int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    private int mergeSort(int[] arr, int low, int high) {
        if (low >= high) return 0;

        int mid = low + (high - low) / 2;

        int cnt = 0;

        cnt += mergeSort(arr, low, mid);
        cnt += mergeSort(arr, mid + 1, high);

        cnt += countPairs(arr, low, mid, high);

        merge(arr, low, mid, high);

        return cnt;
    }

    private int countPairs(int[] arr, int low, int mid, int high) {
        int cnt = 0;

        int right = mid + 1;

        for (int i = low; i <= mid; i++) {

            while (right <= high &&
                   (long) arr[i] > 2L * arr[right]) {
                right++;
            }

            cnt += (right - (mid + 1));
        }

        return cnt;
    }

    private void merge(int[] arr, int low, int mid, int high) {

        int[] temp = new int[high - low + 1];

        int left = low;
        int right = mid + 1;
        int k = 0;

        while (left <= mid && right <= high) {

            if (arr[left] <= arr[right]) {
                temp[k++] = arr[left++];
            } else {
                temp[k++] = arr[right++];
            }
        }

        while (left <= mid) {
            temp[k++] = arr[left++];
        }

        while (right <= high) {
            temp[k++] = arr[right++];
        }

        for (int i = low; i <= high; i++) {
            arr[i] = temp[i - low];
        }
    }
}