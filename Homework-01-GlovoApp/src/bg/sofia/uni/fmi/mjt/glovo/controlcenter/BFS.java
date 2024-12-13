package bg.sofia.uni.fmi.mjt.glovo.controlcenter;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {

    private final MapEntity[][] mapEntities;

    public BFS(MapEntity[][] mapEntities) {
        this.mapEntities = mapEntities;
    }

    public int searchPathFromStartToEnd(Location start, Location end) {
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        Queue<Location> queue = new LinkedList<Location>();
        boolean[][] visited = new boolean[mapEntities.length][mapEntities[0].length];

        visited[start.x()][start.y()] = true;
        queue.add(start);

        return loopForBFS(queue, visited, end, directions);
    }

    private int loopForBFS(Queue<Location> queue, boolean[][] visited, Location end,
                                  int[][] directions) {
        int direction = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Location location = queue.poll();

                if (location.equals(end)) {
                    return direction;
                }

                for (int j = 0; j < directions.length; j++) {
                    int newX = location.x() + directions[j][0];
                    int newY = location.y() + directions[j][1];

                    if (newX >= 0 && newX < mapEntities.length && newY >= 0 && newY < mapEntities[0].length &&
                        !visited[newX][newY] && isWalkable(mapEntities[newX][newY].type())) {
                        visited[newX][newY] = true;
                        queue.add(new Location(newX, newY));
                    }
                }
            }

            direction++;
        }

        return -1;
    }

    private boolean isWalkable(MapEntityType type) {
        return type == MapEntityType.RESTAURANT || type == MapEntityType.ROAD
            || type == MapEntityType.CLIENT || type == MapEntityType.DELIVERY_GUY_CAR
            || type == MapEntityType.DELIVERY_GUY_BIKE;
    }

}
