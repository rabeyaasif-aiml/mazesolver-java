import java.util.*;

public class Mazesolver {

    // ========== ENUM ==========
    public enum Direction { LEFT, RIGHT, UP, DOWN }

    // ========== CELL ==========
    public static class Cell {
        int x, y;
        Cell(int x, int y) { this.x = x; this.y = y; }

        public void draw(double r, java.awt.Color c) {
            StdDraw.setPenColor(c);
            StdDraw.filledCircle(x + 0.5, y + 0.5, r);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Cell)) return false;
            Cell c = (Cell) o;
            return x == c.x && y == c.y;
        }

        @Override
        public int hashCode() { return Objects.hash(x, y); }
    }

    // ========== PATH ==========
    public static class Path implements Iterable<Cell> {
        LinkedList<Cell> path = new LinkedList<>();
        public void add(Cell c) { path.add(c); }
        public Iterator<Cell> iterator() { return path.iterator(); }
    }

    // ========== MAZE CLASS ==========
    public static class Maze {
        int size;
        boolean[][] visited;
        boolean[][] left, right, up, down;
        Cell start, end;
        Path visitedPath = new Path();
        Path solutionPath = null;

        public Maze(int size) {
            this.size = size;
            visited = new boolean[size][size];
            left  = new boolean[size][size];
            right = new boolean[size][size];
            up    = new boolean[size][size];
            down  = new boolean[size][size];

            // initially all walls present
            for (int i=0;i<size;i++) {
                for (int j=0;j<size;j++) {
                    left[i][j] = right[i][j] = up[i][j] = down[i][j] = true;
                }
            }
        }

        public void setStart(int x, int y) { start = new Cell(x,y); }
        public void setEnd(int x, int y) { end = new Cell(x,y); }

        public void removeWall(int x,int y, Direction dir) {
            switch(dir){
                case LEFT:
                    left[x][y]=false; right[x-1][y]=false; break;
                case RIGHT:
                    right[x][y]=false; left[x+1][y]=false; break;
                case UP:
                    up[x][y]=false; down[x][y+1]=false; break;
                case DOWN:
                    down[x][y]=false; up[x][y-1]=false; break;
            }
        }

        public boolean isOpen(int x, int y, Direction d){
            switch(d){
                case LEFT: return !left[x][y];
                case RIGHT: return !right[x][y];
                case UP: return !up[x][y];
                case DOWN: return !down[x][y];
            }
            return false;
        }

        public void visit(int x,int y){
            visited[x][y] = true;
            visitedPath.add(new Cell(x,y));
        }

        public boolean isVisited(int x,int y){ return visited[x][y]; }

        public void setSolution(Path p){ solutionPath = p; }

        // DRAWING
        public void draw() {
            StdDraw.clear();
            StdDraw.setXscale(0,size);
            StdDraw.setYscale(0,size);

            // draw maze walls
            StdDraw.setPenColor(StdDraw.BLACK);
            for(int x=0;x<size;x++){
                for(int y=0;y<size;y++){
                    if(left[x][y])  StdDraw.line(x, y, x, y+1);
                    if(right[x][y]) StdDraw.line(x+1, y, x+1, y+1);
                    if(up[x][y])    StdDraw.line(x, y+1, x+1, y+1);
                    if(down[x][y])  StdDraw.line(x, y, x+1, y);
                }
            }

            // draw visited (grey)
            for(Cell c: visitedPath.path){
                if(!c.equals(start) && !c.equals(end))
                    c.draw(0.35, java.awt.Color.LIGHT_GRAY);
            }

            // draw solution (blue)
            if(solutionPath != null){
                for(Cell c: solutionPath.path){
                    if(!c.equals(start) && !c.equals(end))
                        c.draw(0.35, java.awt.Color.BLUE);
                }
            }

            // draw start (green)
            if(start != null) start.draw(0.45, java.awt.Color.GREEN);

            // draw end (red)
            if(end != null) end.draw(0.45, java.awt.Color.RED);

            StdDraw.show();
        }
    }

    // ========== MAZE GENERATOR (DFS) ==========
    public static class MazeGenerator {
        Random rand = new Random();

        public Maze generate(int size){
            Maze m = new Maze(size);
            boolean[][] visited = new boolean[size][size];
            Stack<Cell> stack = new Stack<>();

            stack.push(new Cell(0,0));
            visited[0][0] = true;

            while(!stack.isEmpty()){
                Cell c = stack.peek();
                int x=c.x, y=c.y;

                ArrayList<Direction> dirs = new ArrayList<>();
                if(x>0 && !visited[x-1][y]) dirs.add(Direction.LEFT);
                if(x<size-1 && !visited[x+1][y]) dirs.add(Direction.RIGHT);
                if(y>0 && !visited[x][y-1]) dirs.add(Direction.DOWN);
                if(y<size-1 && !visited[x][y+1]) dirs.add(Direction.UP);

                if(dirs.isEmpty()) { stack.pop(); continue; }

                Direction d = dirs.get(rand.nextInt(dirs.size()));
                m.removeWall(x,y,d);

                switch(d){
                    case LEFT:  x--; break;
                    case RIGHT: x++; break;
                    case DOWN:  y--; break;
                    case UP:    y++; break;
                }

                visited[x][y] = true;
                stack.push(new Cell(x,y));
            }

            // start & end random
            m.setStart(0,0);
            m.setEnd(size-1,size-1);

            return m;
        }
    }

    // ========== BFS SOLVER ==========
    public static Path solve(Maze m){
        Queue<Cell> q = new LinkedList<>();
        Map<Cell,Cell> parent = new HashMap<>();

        q.add(m.start);
        m.visit(m.start.x, m.start.y);

        while(!q.isEmpty()){
            Cell c = q.remove();

            if(c.equals(m.end)) break;

            int x=c.x, y=c.y;

            if(x>0 && !m.isVisited(x-1,y) && m.isOpen(x,y,Direction.LEFT)) {
                m.visit(x-1,y);
                Cell n=new Cell(x-1,y);
                parent.put(n,c);
                q.add(n);
            }

            if(x<m.size-1 && !m.isVisited(x+1,y) && m.isOpen(x,y,Direction.RIGHT)) {
                m.visit(x+1,y);
                Cell n=new Cell(x+1,y);
                parent.put(n,c);
                q.add(n);
            }

            if(y>0 && !m.isVisited(x,y-1) && m.isOpen(x,y,Direction.DOWN)) {
                m.visit(x,y-1);
                Cell n=new Cell(x,y-1);
                parent.put(n,c);
                q.add(n);
            }

            if(y<m.size-1 && !m.isVisited(x,y+1) && m.isOpen(x,y,Direction.UP)) {
                m.visit(x,y+1);
                Cell n=new Cell(x,y+1);
                parent.put(n,c);
                q.add(n);
            }
        }

        // reconstruct path
        Path p = new Path();
        Cell curr = m.end;

        while(curr != null && !curr.equals(m.start)){
            p.path.addFirst(curr);
            curr = parent.get(curr);
        }
        p.path.addFirst(m.start);

        return p;
    }

    // ========== MAIN ==========
    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();

        int size = 20;

        MazeGenerator gen = new MazeGenerator();
        Maze maze = gen.generate(size);

        Path sol = solve(maze);
        maze.setSolution(sol);

        maze.draw();
    }
}
