// Q.no.4.a.Solution
import java.util.LinkedList;
import java.util.Queue;

public class VirtualGameWorld {

    public static int minimumStepsToCollectKeys(String[] map) {
        int rows = map.length;
        int cols = map[0].length();

        int targetKeys = 0;
        int startX = 0, startY = 0;

        // Extract information from the grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char cell = map[i].charAt(j);
                if (cell == 'S') {
                    startX = i;
                    startY = j;
                } else if (cell == 'E') {
                    targetKeys |= (1 << ('f' - 'a')); // Set the bit for the exit door
                } else if (cell >= 'a' && cell <= 'f') {
                    targetKeys |= (1 << (cell - 'a')); // Set the bit for the key
                }
            }
        }

        // Perform BFS
        Queue<int[]> queue = new LinkedList<>();
        boolean[][][] visited = new boolean[rows][cols][1 << 6]; // 1 << 6 represents the keys bitmask
        queue.offer(new int[]{startX, startY, 0, 0}); // {x, y, keys, steps}

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int keys = current[2];
            int steps = current[3];

            if (keys == targetKeys) {
                return steps; // All keys collected, return the steps
            }

            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && map[newX].charAt(newY) != 'W') {
                    char cell = map[newX].charAt(newY);

                    if (cell == 'E' || cell == 'P' || (cell >= 'a' && cell <= 'f') || (cell >= 'A' && cell <= 'F' && (keys & (1 << (cell - 'A'))) != 0)) {
                        int newKeys = keys;
                        if (cell >= 'a' && cell <= 'f') {
                            newKeys |= (1 << (cell - 'a')); // Collect the key
                        }

                        if (!visited[newX][newY][newKeys]) {
                            visited[newX][newY][newKeys] = true;
                            queue.offer(new int[]{newX, newY, newKeys, steps + 1});
                        }
                    }
                }
            }
        }

        return -1; // All possible moves explored and keys are not collected
    }

    public static void main(String[] args) {
        String[] grid = {"SPaPP", "WWWPW", "bPAPB"};
        int result = minimumStepsToCollectKeys(grid);
        System.out.println("Minimum number of moves: " + result); // Output: 8
    }
}