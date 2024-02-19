/**
 * 定义扫雷游戏单元格的属性和行为
 */
public class Cell {
    // 是否有雷
    private boolean mine;
    // 是否被揭示
    private boolean revealed;
    // 是否被标记为雷
    private boolean flagged;
    // 周围地雷个数
    private int adjacentMines;

    private int x;
    private int y;

    public Cell(){

    }

    public Cell(boolean mine, boolean revealed, boolean flagged, int adjacentMines) {
        this.mine = mine;
        this.revealed = revealed;
        this.flagged = flagged;
        this.adjacentMines = adjacentMines;
    }

    public Cell(boolean mine, boolean revealed, boolean flagged, int adjacentMines, int x, int y) {
        this.mine = mine;
        this.revealed = revealed;
        this.flagged = flagged;
        this.adjacentMines = adjacentMines;
        this.x = x;
        this.y = y;
    }

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }


    @Override
    public String toString() {
        if (isFlagged()) {
            // 1. 单元格被标记成地雷，显示F
            return "F";
        } else if (!isRevealed()) {
//            if(isMine()){
//                return "@";
//            }
            // 2. 没有揭示的时候，显示？
            return "?";
        } else if (isMine()) {
            // 3. 单元格被揭示后，是地雷，显示*提示用户踩到地雷
            return "*";
        } else if (getAdjacentMines() > 0) {
            // 4. 单元格被揭示后，不是地雷，显示周围雷数量的数字
            return String.valueOf(getAdjacentMines());
        } else {
            // 5. 单元格自动被揭示，显示空字符串
            return " ";
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
