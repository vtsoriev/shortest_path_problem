import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NavigatorImpl implements Navigator {

    class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;

        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Point && (((Point) o).x == x) && (((Point) o).y == y);
        }

        @Override
        public int hashCode() {
            return x ^ y;
        }

        @Override
        public String toString() {
            return "x: " + Integer.valueOf(x).toString() + " y:" + Integer.valueOf(y).toString();
        }
    }

    private List<Point> queue = new ArrayList<>();
    private Point startPoint;
    private Point endPoint;

    /**
     * Для реализации метода можно использовать алгоритм Ли (волновой алгоритм).
     * Данный алгоритм как раз предназначен для поиска кратчайшего пути от стартовой ячейки к конечной ячейке,
     * если это возможно, и он проще, чем A*.
     * Работа алгоритма включает в себя три этапа:
     * 1) инициализацию,
     * 2) распространение волны
     * 3) и восстановление пути.
     */

    @Override
    public char[][] searchRoute(char[][] map) {
/*
 1) ИНИЦИАЛИЗАЦИЯ:
 */
        startPoint = null;
        endPoint = null;
        if (map.length == 0 || map[0].length == 0)
            return null;
        //Пробегаемся по переданному массиву с картой в поисках начальной и конечной точек:
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == '@') {
                    startPoint = new Point(x, y);
                    //System.out.println("Координаты начала пути - " + startPoint);
                }
                if (map[y][x] == 'X') {
                    endPoint = new Point(x, y);
                    //System.out.println("Координаты конца пути - " + endPoint);
                }
            }
        }
        if (endPoint == null || startPoint == null)
            throw new IllegalArgumentException("В передаваемой методу карте должны быть отмечены точки начала и конца пути!");
        //display(map);

        //Создаем двумерный массив int той же размерности, что и переданный двумерный массив char
        //(он будет служить нам дискретным рабочим полем):
        int[][] dwf = new int[map.length][map[0].length];
        // Заполняем дискретное рабочее поле максимальным значением - Integer.MAX_VALUE:
        for (int[] aDwf : dwf) Arrays.fill(aDwf, Integer.MAX_VALUE);
        // Значение n для стартовой точки равно 0, т.к. именно из нее будут считаться шаги до остальных точек:
        System.out.println(startPoint);
        dwf[startPoint.y][startPoint.x] = 0;
        //Добавляем стартовую точку в очередь:
        queue.add(startPoint);
        //Объявляем и инициализируем нулем переменную n:
        int n = 0;

        //Далее мы должны разметить в дискретном рабочем поле все клетки лабиринта, не являющиеся стенами,
        // и записать в них значение n, которое равно количеству шагов,
        // за которое можно попасть в данную клетку из клетки стартовой:

/*
 2) РАСПРОСТРАНЕНИЕ ВОЛНЫ:
 */
        while (!queue.isEmpty()) { // Цикл выполняется, пока есть объекты Point в очереди:
            Point p = queue.remove(0);

            if (p.equals(endPoint)) {
                System.out.println("Найден путь длины " + dwf[p.y][p.x] + ".");
            }
            n = dwf[p.y][p.x] + 1;
            /*
            В каждом выражении if проверяется одна из четырех возможных соседних клеток текущей клетки.
            Первое условие в каждом if проверяет границу лабиринта (массива) - если мы находимся, например, в углу,
            то клеток для перемещения будет не четыре, а две.
            Второе условие проверяет, не уперлись ли мы в стену (в клетке, совпадающей со стеной, остается Integer.MAX_VALUE).
            Наконец, третье условие служит для того, чтобы не присвоить значение клетке,
            которой уже ранее было присвоено значение, т.е. новое значение n присваивается только в том случае,
            если оно меньше текущего значения, сохраненного в клетке.
            Координаты клеток, удовлетворивших всем условиям, сохраняются в экземпляре Point и вставляются в очередь:
            при очередной итерации цикла while из очереди будет извлечена новая текущая точка и проверены все ее соседи.
            Т.о. будет размечено все дискретное рабочее поле, все его клетки, доступные из начальной (если конец пути доступен,
            но требует для пути большего либо равного количества шагов, чем значение константы Integer.MAX_VALUE,
            путь найден не будет)

            */

            if ((p.y + 1 < map.length) && map[p.y + 1][p.x] != '#' && dwf[p.y + 1][p.x] > n) {
                dwf[p.y + 1][p.x] = n;
                queue.add(new Point(p.x, p.y + 1));
            }

            if ((p.y - 1 >= 0) && (map[p.y - 1][p.x] != '#') && dwf[p.y - 1][p.x] > n) {
                dwf[p.y - 1][p.x] = n;
                queue.add(new Point(p.x, p.y - 1));
            }

            if ((p.x + 1 < map[p.y].length) && (map[p.y][p.x + 1] != '#') && dwf[p.y][p.x + 1] > n) {
                dwf[p.y][p.x + 1] = n;
                queue.add(new Point(p.x + 1, p.y));
            }

            if ((p.x - 1 >= 0) && (map[p.y][p.x - 1] != '#') && dwf[p.y][p.x - 1] > n) {
                dwf[p.y][p.x - 1] = n;
                queue.add(new Point(p.x - 1, p.y));
            }

        }
        //Если хочется посмотреть, как выглядит дискретное рабочее поле после заполнения:
        //display(dwf);
        /*
        Всем доступным ячейкам на данном этапе присвоено значение n, поэтому можно проверить,
        достижима ли конечная точка пути, и за сколько шагов в нее можно попасть.
        Если по координатам конца пути хранится изначально записанное во все ячейки значение Integer.MAX_VALUE,
        значит, пути из начала в конец не существует (либо оно занимает слишком много шагов,
        т.е. количество шагов n >=Integer.MAX_VALUE)
        */
        if (dwf[endPoint.y][endPoint.x] == Integer.MAX_VALUE) {
            System.out.println("Пути не существует!!! Выхода нет!!!");
            return null;
        }
/*
 3) ВОССТАНОВЛЕНИЕ ПУТИ:
 */
        /*
        Чтобы получить кратчайший путь (последовательность всех ячеек, образующих его),
        нужно двигаться от конечной точки к начальной, выбирая на каждом шаге ячейку со значением,
        меньшим значения в текущей ячейке дискретного рабочего поля.
        В данном случае экземпляры Point добавляются в список, названный path:
        */
        List<Point> path = new ArrayList<>();
        path.add(endPoint);
        int x = endPoint.x;
        int y = endPoint.y;
        n = dwf[y][x];
        while (n != 0) { // Пока не придем в начало пути
            //проверяем наличие соседних клеток и значения, записанные в них, которые д.б.меньше текущего значения n:
            if (y < dwf.length - 1 && dwf[y + 1][x] < n) {
                n = dwf[++y][x];
            } else if (y > 0 && dwf[y - 1][x] < n) {
                n = dwf[--y][x];
            } else if (x < dwf[y].length - 1 && dwf[y][x + 1] < n) {
                n = dwf[y][++x];
            } else if (x > 0 && dwf[y][x - 1] < n) {
                n = dwf[y][--x];
            }
            path.add(new Point(x, y));
        }

        //System.out.println("Путь найден!!!!!!! Его длина - " + (path.size() - 1));

        for (Point point : path) {
            if (!point.equals(startPoint) && !point.equals(endPoint))
                map[point.y][point.x] = '+';
        }

        display(map);
        return map;
    }


    private void display(char[][] charMatrix) {
        for (char[] i : charMatrix) {
            for (char j : i)
                System.out.print("| " + j);
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    private void display(int[][] intMatrix) {
        System.out.println();
        System.out.println("На данном этапе дискретное рабочее поле выглядит так:");
        for (int[] i : intMatrix) {
            for (int j : i) {
                if (j == Integer.MAX_VALUE)
                    System.out.print("|******");
                else {
                    StringBuilder blank = new StringBuilder();
                    while (String.valueOf(j).length() + blank.length() < 5)
                        blank.append(" ");
                    System.out.print("| " + blank + j);
                }
            }
            System.out.println();
        }
        System.out.println("(значение Integer.MAX_VALUE заменено на ******)\n");
        System.out.println();
    }
}
