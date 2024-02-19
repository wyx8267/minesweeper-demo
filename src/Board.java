import java.util.Random;

/**
 * 定义扫雷游戏棋盘的属性与行为
 */
public class Board {
    private static final double FACTOR = 0.15;
    // 单元格组成的二维矩阵
    private Cell[][] cells;
    // 棋盘高度
    private final int height;
    // 棋盘宽度
    private final int width;

    // 地雷个数
    private int mineNumber;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.cells = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.cells[i][j] = new Cell();
            }
        }
        this.mineNumber = (int) Math.round(height * width * FACTOR);
        initMines();
    }

    public Board(int height, int width, int mineNumber) {
        // Todo: 地雷个数大于棋盘单元格数量异常情况处理
        if(mineNumber >= height * width){

        }
        this.height = height;
        this.width = width;
        this.cells = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.cells[i][j] = new Cell();
            }
        }
        this.mineNumber = mineNumber;
        initMines();
    }

    // 初始化完成后不再希望改动棋盘大小，所以只有getter
    public Cell[][] getCells() {
        return cells;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getMineNumber() {
        return mineNumber;
    }

    private void initMines(){
        // Step 1: 随机生成坐标，放置地雷
        // Step 2: 计算每个非地雷格子周围地雷数量

        // Step 1
        Random random = new Random();
        for (int i = 0; i < mineNumber; i++) {
            int x, y;
            // 确保不会在同一位置重复放雷，需要先检查是否有雷
            do{
                x = random.nextInt(height);
                y = random.nextInt(width);
            }while(cells[x][y].isMine());

            cells[x][y].setMine(true);
            // System.out.println("mine: " + x + " " + y);
        }

        // Step 2
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell curCell = cells[i][j];
                if(!curCell.isMine()){
                    int mineCount = 0;
                    // 坐标移动（8个方向）dx={-1,0,1} dy={-1,0,1}
                    for (int dx = -1; dx <= 1; dx++){
                        for (int dy = -1; dy <= 1; dy++){
                            int newX = i + dx;
                            int newY = j + dy;
                            if(checkMine(newX, newY)){
                                mineCount++;
                            }
                        }
                    }
                    curCell.setAdjacentMines(mineCount);
                }
            }
        }
    }

    private boolean checkMine(int newX, int newY) {
        return newX >= 0 && newY >= 0 && newX < height && newY < width && cells[newX][newY].isMine();
    }

    public void printBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // 显示当前单元格
                System.out.print(cells[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Board board = new Board(8, 10, 10);
        board.getCells()[0][0].setFlagged(true);
        board.getCells()[2][4].setRevealed(true);
        board.getCells()[3][7].setMine(true);
        board.getCells()[3][7].setRevealed(true);
        board.getCells()[5][5].setAdjacentMines(3);
        board.getCells()[5][5].setRevealed(true);
        board.printBoard();
    }
}
