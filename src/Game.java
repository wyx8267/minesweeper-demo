import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Game {
    private boolean gameOver;
    private Board board;
    private Scanner scanner;
    private int cellCountNeedToReveal;

    public Game(Board board) {
        this.board = board;
        scanner = new Scanner(System.in);
        cellCountNeedToReveal = 0;
    }

    public void start() {
        System.out.println("Minesweeper is starting!");
        // Todo: 游戏执行循环
        while (!gameOver) {
            // 显示当前棋盘状态
            board.printBoard();
            // 获取/处理用户输入
            processInput();
        }
    }

    private void processInput() {
        // 获取用户输入 - 提示部分
        System.out.println("请输入游戏指令：");
        System.out.println("指令f表示标记地雷，格式：f x y");
        System.out.println("例子：f 1 0 表示将坐标（1，0）标记成地雷");
        System.out.println("指令r表示揭示格子，格式：r x y");
        System.out.println("例子：r 1 0 表示将坐标（1，0）所在格子揭开");

        // 获取用户输入 - 捕获游戏指令
        String command = scanner.next().toLowerCase();
        int x = scanner.nextInt();
        int y = scanner.nextInt();

        // 获取用户输入 - 检查游戏指令
        if (!checkCommandInput(command, x, y)) {
            return;
        }

        // 处理用户输入
        switch (command) {
            case "r":
                // 执行揭示单元格操作
                revealWithDFS(x, y);
                // revealWithBFS(x, y);
                break;
            case "f":
                // 执行标记(取消标记)单元格操作
                flag(x, y);
                break;
            default:
                System.out.println("不正确的指令输入，请按照提示输入");
        }

    }

    private boolean checkCommandInput(String command, int x, int y) {
        if (!"r".equals(command) && !"f".equals(command)) {
            System.out.println("不正确的指令输入，请按照提示输入");
            return false;
        }
        if (x < 0 || x >= board.getHeight() || y < 0 || y >= board.getWidth()) {
            System.out.println("不合法的坐标，请检查后输入正确坐标");
            return false;
        }
        return true;
    }

    private void revealWithDFS(int x, int y) {
        Cell[][] cells = board.getCells();
        Cell curCell = cells[x][y];
        if (curCell.isMine()) {
            System.out.println("踩到了地雷，游戏结束");
            gameOver = true;
            return;
        }
        // 只揭示没有被揭示过的格子
        if (!curCell.isRevealed()) {
            curCell.setRevealed(true);
            cellCountNeedToReveal++;
            if(checkWin()) {
                gameOver = true;
                System.out.println("恭喜你，扫除所有地雷，赢得了游戏！！！");
            }
            // 如果当前单元格周围没有雷，自动揭示
            if (curCell.getAdjacentMines() == 0) {
                // 自动揭示算法
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int newX = x + dx;
                        int newY = y + dy;
                        if (checkBound(newX, newY) && !cells[newX][newY].isMine()) {
                            revealWithDFS(newX, newY);
                        }
                    }
                }
            }
        }
    }

    private boolean checkBound(int x, int y) {
        return x >= 0 && x < board.getHeight() && y >= 0 && y < board.getWidth();
    }

    private void revealWithBFS(int x, int y) {
        Cell[][] cells = board.getCells();
        Cell curCell = cells[x][y];
        if (curCell.isMine()) {
            System.out.println("踩到了地雷，游戏结束");
            gameOver = true;
            return;
        }
        // 只揭示没有被揭示过的格子
        if (!curCell.isRevealed()) {
            Queue<Cell> queue = new LinkedList<>();
            boolean[][] visited = new boolean[board.getHeight()][board.getWidth()];
            queue.offer(new Cell(x, y));
            visited[x][y] = true;

            while (!queue.isEmpty()) {
                Cell cell = queue.poll();
                if (!cell.isRevealed()) {
                    cells[cell.getX()][cell.getY()].setRevealed(true);
                    cellCountNeedToReveal++;
                    if(checkWin()) {
                        gameOver = true;
                        System.out.println("恭喜你，扫除所有地雷，赢得了游戏！！！");
                    }
                    // 如果当前单元格周围没有雷，自动揭示
                    if (cells[cell.getX()][cell.getY()].getAdjacentMines() == 0) {
                        // 自动揭示算法
                        for (int dx = -1; dx <= 1; dx++) {
                            for (int dy = -1; dy <= 1; dy++) {
                                int newX = cell.getX() + dx;
                                int newY = cell.getY() + dy;
                                if (checkBound(newX, newY) && !visited[newX][newY] && !cells[newX][newY].isMine()) {
                                    queue.offer(new Cell(newX, newY));
                                    visited[newX][newY] = true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void flag(int x, int y) {
        // Todo: 标记成地雷的格子不能超过地雷的数量
        Cell[][] cells = board.getCells();
        Cell curCell = cells[x][y];
        boolean curFlag = curCell.isFlagged();
        curCell.setFlagged(!curFlag);
    }

    private boolean checkWin() {
//        Cell[][] cells = board.getCells();
//        int cellCountNeedToReveal = 0;
//        for (int i = 0; i < board.getHeight(); i++) {
//            for (int j = 0; j < board.getWidth(); j++) {
//                if(!cells[i][j].isMine() && cells[i][j].isRevealed()){
//                    cellCountNeedToReveal++;
//                }
//            }
//        }
        return cellCountNeedToReveal == board.getHeight() * board.getWidth() - board.getMineNumber();
    }

    public static void main(String[] args) {
        Board board = new Board(8, 10, 10);
        Game game = new Game(board);
        game.start();
    }
}