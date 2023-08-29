import java.util.concurrent.TimeUnit;
public class GOL {
    public static void main(String[] args) throws InterruptedException {

        String[] width = args[0].split("=");
        checkIfItIsNumber(width[1], "width");
        validRangeValues(Integer.parseInt(width[1]), "width");
        int w = Integer.parseInt(width[1]);


        String[] height = args[1].split("=");
        checkIfItIsNumber(height[1], "height");
        validRangeValues(Integer.parseInt(height[1]), "height");
        int h = Integer.parseInt(height[1]);

        String[] generations = args[2].split("=");
        checkIfItIsNumber(generations[1], "generations");
        int g = Integer.parseInt(generations[1]);

        String[] speed = args[3].split("=");
        checkIfItIsNumber(speed[1], "speed");
        validRangeValues(Integer.parseInt(speed[1]), "speed");
        int s = Integer.parseInt(speed[1]);

        String seed = args[4];
        String population = seed.substring(3, seed.length() - 1);

        validPopulation(population);
        verificationSeed(w,h,population);
        fillMatrix(w, h, population);

        int[][] generatedMatrix = fillMatrix(w, h, population);
        int generationsTocount = 1;
        game(w, h,s,g, generatedMatrix, generationsTocount);

    }

    public static void checkIfItIsNumber(String chain, String objectName) {

        if (chain.isEmpty()) {
            System.out.println(objectName + "= [No presente]");
        } else {
            try {
                int number = Integer.parseInt(chain);
                System.out.println(objectName + " = " + number);
            } catch (NumberFormatException e) {
                System.out.println(objectName + " = [Invalido]");
            }
        }
    }

    public static void validPopulation(String chain) {
        if (chain.isEmpty()) {
            System.out.println("population=[No  Presente]");
        } else {
            System.out.println("population = [" + chain + "]");
        }

    }

    public static void validRangeValues(int value, String objectName) {
        int letterACtion = 0;
        if (objectName == "width") {
            letterACtion = 1;
        } else if (objectName == "height") {
            letterACtion = 2;

        } else if (objectName == "speed") {
            letterACtion = 3;
        }

        switch (letterACtion) {
            case 1 -> {
                do {
                    if (value != 10 && value != 20 && value != 40 && value != 80) {
                        System.out.println("Valor fuera de rango, Valores permitidos para el Ancho \n"
                                + " 10, 20, 40 u 80");
                        System.exit(0);

                    }
                } while (value != 10 && value != 20 && value != 40 && value != 80);

            }
            case 2 -> {
                do {
                    if (value != 10 && value != 20 && value != 40) {
                        System.out.println("Valor fuera de rango, Valores permitidos para el Largo \n"
                                + " 10, 20 o 40 ");
                        System.exit(0);
                    }
                } while (value != 10 && value != 20 && value != 40);

            }

            case 3 -> {
                do {
                    if (value > 250 && value < 1000) {
                        System.out.println("Valor fuera de rango, Valores permitidos para la velocidad son  \n"
                                + " 250 , 1000 milisegundos ");
                        System.exit(0);
                    }
                } while (value != 250 && value != 1000);

            }
        }
    }

    public static void verificationSeed(int w, int h, String seed) {
        boolean running = true;
        int size = w * h;

        do {
            int count = 0;

            for (int i = 0; i < seed.length(); i++) {
                if (Character.isDigit(seed.charAt(i))) {
                    count++;
                }
            }

            if (seed.equalsIgnoreCase("rnd")) {
                running = false;

            } else if (count < size) {
                running = false;

            } else if (count == size) {
                running = false;
            }

        } while (running);

        System.out.println(seed);

    }
    public static int[][] fillMatrix(int w, int h, String population) {

        int[][] matrix = new int[h][w];

        if (population.equalsIgnoreCase("rnd")) {
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    matrix[i][j] = numberRandom(2);
                }
            }

            } else {
            String[] p = population.split("#");
            for (int i = 0; i < p.length; i++) {
                for (int j = 0; j < p[i].length(); j++) {
                    char c = p[i].charAt(j);
                    matrix[i][j] = c - '0';
                }
            }
        }

        return matrix;
    }

    public static int numberRandom(int limit){
    int number;

    number = (int) (Math.random() * limit);

    return number;
    }
    public static int countNeighbors(int row, int col, int [][] matrix) {
        int neighborns = 0;

        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                int newRow = row + i;
                int newColumn = col + j;

                if (newRow >= 0 && newRow < matrix.length && newColumn >= 0 && newColumn < matrix[0].length && !(i == 0 && j ==0)){
                    neighborns += matrix[newRow][newColumn];


                }
            }
        }
        return neighborns;
    }
    public static int[][] generateNextGeneration(int [][] matrix){
        int[][] newBoard = new int[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++){
                int neighbors = countNeighbors(i, j, matrix);

                if (matrix[i][j] == 1){
                    if (neighbors < 2 || neighbors > 3){
                        newBoard[i][j] = 0;

                    } else {
                        newBoard[i][j] = 1;

                    }

                } else {
                    if (neighbors == 3){
                        newBoard[i][j] = 1;
                    } else{
                        newBoard[i][j] = 0;
                    }
                }
            }
        }
        return newBoard;
    }

    public static void print(int w, int h,int [][] matrix){
        System.out.println();

        int [][] newMatrix = generateNextGeneration(matrix);

        printMatrix(w ,h , newMatrix);
    }
    public static void printMatrix(int w, int h,int [][] matrix){
        for (int i = 0; i < h; i++){
            System.out.println();
            for (int j = 0; j < w; j++){
                System.out.print(" " + matrix[i][j]);
            }
        }
    }
    public static void game(int w, int h, int s, int g, int[][] matrix, int generations) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(s);

        if (g == 0) {
            boolean running = true;

            while (running) {
                generations++;
                System.out.println("Número de generación: " + generations);
                System.out.println();

                print(w, h, matrix);

                matrix = generateNextGeneration(matrix);

                TimeUnit.MILLISECONDS.sleep(s);
                System.out.println();
            }
        } else {
            for (int i = 0; i < g - 1; i++) {
                System.out.print("Número de generación: " + generations);
                System.out.println();
                generations++;
                print(w, h, matrix);

                matrix = generateNextGeneration(matrix);

                TimeUnit.MILLISECONDS.sleep(s);
                System.out.println();
            }
        }
    }
    
}




