class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {

        // int[] indegree = new int[numCourses];

        // for (int i = 0; i < prerequisites.length; i++) {
        //     indegree[prerequisites[i][0]]++;
        // }

        // Queue<Integer> q = new ArrayDeque<>();
        // int count = 0;

        // for (int i = 0; i < numCourses; i++) {
        //     if (indegree[i] == 0) {
        //         q.offer(i);
        //     }
        // }

        // while (!q.isEmpty()) {

        //     int node = q.poll();
        //     count++;

        //     for (int i = 0; i < prerequisites.length; i++) {

        //         if (prerequisites[i][1] == node) {

        //             int nextCourse = prerequisites[i][0];

        //             indegree[nextCourse]--;

        //             if (indegree[nextCourse] == 0) {
        //                 q.offer(nextCourse);
        //             }
        //         }
        //     }
        // }

        // return count == numCourses;

// This is working fine but to find next courses we have to traverse all the edges in prerequisites ...so time complexity will become : O(V x E)


// Optimized version (O(V + E)) by creating adjacency list

        int[] indegree = new int[numCourses];

        // prerequisite -> course
        List<List<Integer>> adj = new ArrayList<>();

        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }

        // Build graph
        for (int[] pre : prerequisites) {

            int course = pre[0];
            int prerequisite = pre[1];

            adj.get(prerequisite).add(course);

            indegree[course]++;
        }

        // Add all courses with 0 indegree
        Queue<Integer> q = new ArrayDeque<>();

        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }

        int count = 0;

        // Kahn's Algorithm
        while (!q.isEmpty()) {

            int node = q.poll();
            count++;

            for (int next : adj.get(node)) {

                indegree[next]--;

                if (indegree[next] == 0) {
                    q.offer(next);
                }
            }
        }

        return count == numCourses;

    }
}