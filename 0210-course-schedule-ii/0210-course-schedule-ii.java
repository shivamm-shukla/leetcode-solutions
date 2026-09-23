class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int n = numCourses;

// build graph and indegree

        List<List<Integer>> adj = new ArrayList<>();
        int[] indegree = new int[n];

        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] courses : prerequisites) {
            int course = courses[0];
            int prereq = courses[1];

            adj.get(prereq).add(course);
            indegree[course]++;
        }

// now kahn's algo

        Queue<Integer> q = new ArrayDeque<>();
        int[] res = new int[n];
        int count = 0;
        
        for (int i = 0; i < n; i++){
            if (indegree[i] == 0) q.offer(i);
        }

        while (!q.isEmpty()){
            int node = q.poll();
            res[count] = node;
            count++;

            for (int nei : adj.get(node)){
                indegree[nei]--;
                if (indegree[nei] == 0) q.offer(nei);
            }
        }

        if (count == n) return res;
        return new int[0];
    
    }
}