class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {

        int[] indegree = new int[numCourses];

        for (int i = 0; i < prerequisites.length; i++) {
            indegree[prerequisites[i][0]]++;
        }

        Queue<Integer> q = new ArrayDeque<>();
        int count = 0;

        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }

        while (!q.isEmpty()) {

            int node = q.poll();
            count++;

            for (int i = 0; i < prerequisites.length; i++) {

                if (prerequisites[i][1] == node) {

                    int nextCourse = prerequisites[i][0];

                    indegree[nextCourse]--;

                    if (indegree[nextCourse] == 0) {
                        q.offer(nextCourse);
                    }
                }
            }
        }

        return count == numCourses;
    }
}