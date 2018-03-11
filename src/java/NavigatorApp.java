public class NavigatorApp {


    public static void main(String[] args) {

        char[] row00 = {'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '@'};
        char[] row01 = {'.', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'};
        char[] row02 = {'.', '.', '.', '#', '.', '.', '.', '#', '.', '.', '.', '.', '.', '#', '.', '.', '.', '#', '.', '.', '.', '.', '.', '#', '.', '.', '.', '#', '.', '.', '.'};
        char[] row03 = {'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'};
        char[] row04 = {'.', '.', '.', '#', '.', '.', '.', '#', '.', '.', '.', '.', '.', '#', '.', '#', '.', '#', '.', '.', '.', '.', '.', '#', '.', '#', '.', '#', '.', '.', '.'};
        char[] row05 = {'.', '.', '.', '#', '#', '.', '.', '#', '.', '.', '.', '.', '.', '#', '.', '.', '#', '#', '.', '.', '.', '.', '.', '#', '#', '.', '#', '#', '.', '.', '.'};
        char[] row06 = {'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'};
        char[] row07 = {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '.'};
        char[] row08 = {'.', '.', '.', '#', '.', '.', '.', '#', '.', '.', '.', '.', '.', '#', '.', '.', '.', '#', '.', '.', '.', '.', '.', '#', '.', '.', '.', '#', '.', '.', '.'};
        char[] row09 = {'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'};
        char[] row10 = {'.', '.', '.', '#', '.', '.', '.', '#', '.', '.', '.', '.', '.', '#', '.', '#', '.', '#', '.', '.', '.', '.', '.', '#', '.', '#', '.', '#', '.', '.', '.'};
        char[] row11 = {'X', '.', '.', '#', '#', '.', '.', '#', '.', '.', '.', '.', '.', '#', '.', '.', '#', '#', '.', '.', '.', '.', '.', '#', '#', '.', '#', '#', '.', '.', '.'};

        char[][] labyrinth = {row00, row01, row02, row03, row04, row05, row06, row07, row08, row09, row10, row11};

        Navigator mapping = new NavigatorImpl();
        mapping.searchRoute(labyrinth);
        for (int i = 0; i < 1000; i++)
            mapping.searchRoute(getRandomLabyrinth(100, 10000));

    }

    private static char[][] getRandomLabyrinth(int xMaxSize, int yMaxSize) {
        if (xMaxSize <= 0 || yMaxSize <= 1) {
           throw new IllegalArgumentException("Размер должен быть не менее 1 на 2");
        }
        char[][] randomLabyrinth = new char[(((int) (Math.random() * (yMaxSize - 1))) + 1)][(((int) (Math.random() * (xMaxSize - 2))) + 2)];
        System.out.println("Сгенерирован лабиринт размером " + randomLabyrinth.length + " на " + randomLabyrinth[0].length);
        for (int j = 0; j < randomLabyrinth.length; j++)
            for (int i = 0; i < randomLabyrinth[j].length; i++) {
                if ((int) (Math.random() * 10) >= 6)
                    randomLabyrinth[j][i] = '#';
                else
                    randomLabyrinth[j][i] = '.';
            }
        int y = (int) (Math.random() * randomLabyrinth.length);
        int x = (int) (Math.random() * randomLabyrinth[0].length);
        System.out.println("Случайные координаты начала: x = " + x + " y = " + y);
        randomLabyrinth[y][x] = '@';
        int yEnd = (int) (Math.random() * randomLabyrinth.length);
        int xEnd = (int) (Math.random() * randomLabyrinth[0].length);
        while (x == xEnd && y == yEnd) {
            yEnd = (int) (Math.random() * randomLabyrinth.length);
            xEnd = (int) (Math.random() * randomLabyrinth[0].length);
        }
        System.out.println("Случайные координаты конца: x = " + xEnd + " y = " + yEnd);
        randomLabyrinth[yEnd][xEnd] = 'X';

        return randomLabyrinth;

    }
}
