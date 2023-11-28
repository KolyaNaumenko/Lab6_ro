import java.security.SecureRandom;
import java.util.Arrays;

class SimpleMultiplying {
    private static int[][] A;
    private static int[][] B;
    private static int[][] result;

    public static int[][] multiply(int[][] A, int[][] B) {
        SimpleMultiplying.A = A;
        SimpleMultiplying.B = B;

        result = new int[A.length][A.length];

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                result[i][j] = 0;
                for (int k = 0; k < A.length; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return result;
    }
}

class TapeMultiplying {
    private static int[][] A;
    private static int[][] B;
    private static int count;
    private static int[][] result;

    public static int[][] multiply(int[][] A, int[][] B, int count) {
        TapeMultiplying.A = A;
        TapeMultiplying.B = B;
        TapeMultiplying.count = count;

        result = new int[A.length][A.length];

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                result[i][j] = 0;
            }
        }

        Thread[] tapes = new Thread[count];
        for (int i = 0; i < tapes.length; i++) {
            tapes[i] = new Thread(new Tape(i));
        }

        for (Thread tape : tapes) {
            tape.start();
        }

        for (Thread tape : tapes) {
            try {
                tape.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private static class Tape implements Runnable {
        private int tapeIndex;

        public Tape(int index) {
            this.tapeIndex = index;
        }

        @Override
        public void run() {
            int pivot = (int) Math.ceil(A.length / (double) count);
            for (int row = tapeIndex * pivot; row < (tapeIndex + 1) * pivot && row < A.length; row++) {
                int cell = result[row][tapeIndex];
                int counter = 0;
                int index = row;
                while (counter < A.length) {
                    cell += A[row][index] * B[index][tapeIndex];
                    counter++;
                    index = (index + 1) % A.length;
                }
                result[row][tapeIndex] = cell;
            }
        }
    }
}

class FoxMultiplying {
    private static int[][] A;
    private static int[][] B;
    private static int count;
    private static int[][] result;

    public static int[][] multiply(int[][] A, int[][] B, int count) {
        FoxMultiplying.A = A;
        FoxMultiplying.B = B;
        FoxMultiplying.count = count;

        result = new int[A.length][A.length];

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                result[i][j] = 0;
            }
        }

        Thread[] tasks = new Thread[count];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new Thread(new Task(i));
        }

        for (Thread task : tasks) {
            task.start();
        }

        for (Thread task : tasks) {
            try {
                task.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private static class Task implements Runnable {
        private int taskIndex;

        public Task(int index) {
            this.taskIndex = index;
        }

        @Override
        public void run() {
            int pivot = (int) Math.ceil(A.length / (double) count);
            for (int row = taskIndex * pivot; row < (taskIndex + 1) * pivot && row < A.length; row++) {
                int cell = result[row][taskIndex];
                int counter = 0;
                int a_j = row;
                int b_i = row;
                while (counter < A.length) {
                    cell += A[row][a_j] * B[b_i][row];
                    b_i = (b_i + 1) % A.length;
                    a_j = (a_j + 1) % A.length;
                    counter++;
                }
                result[row][taskIndex] = cell;
            }
        }
    }
}

class CannonMultiplying {
    private static int[][] A;
    private static int[][] B;
    private static int count;
    private static int[][] result;

    public static int[][] multiply(int[][] A, int[][] B, int count) {
        CannonMultiplying.A = A;
        CannonMultiplying.B = B;
        CannonMultiplying.count = count;

        result = new int[A.length][A.length];

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                result[i][j] = 0;
            }
        }

        Thread[] tasks = new Thread[count];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new Thread(new Task(i));
        }

        for (Thread task : tasks) {
            task.start();
        }

        for (Thread task : tasks) {
            try {
                task.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private static class Task implements Runnable {
        private int taskIndex;

        public Task(int index) {
            this.taskIndex = index;
        }

        @Override
        public void run() {
            int pivot = (int) Math.ceil(A.length / (double) count);
            for (int row = taskIndex * pivot; row < (taskIndex + 1) * pivot && row < A.length; row++) {
                int cell = result[row][taskIndex];
                int counter = 0;
                int a_j = row;
                int b_i = row;
                while (counter < A.length) {
                    cell += A[row][a_j] * B[b_i][row];
                    a_j = (A.length + a_j - 1) % A.length;
                    b_i = (A.length + b_i - 1) % A.length;
                    counter++;
                }
                result[row][taskIndex] = cell;
            }
        }
    }
}

public class Main {
    private static final int[] SIZES = {10, 100, 500, 1000};

    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        long start;
        long end;

        System.out.println("SIZE\t\tSIMPLE\t\tTAPE(2)\t\tTAPE(4)\t\tFOX(2)\t\tFOX(4)\t\tCANNON(2)\t\tCANNON(4)\n");

        for (int k = 0; k < SIZES.length; k++) {
            int[][] A = new int[SIZES[k]][SIZES[k]];
            int[][] B = new int[SIZES[k]][SIZES[k]];

            for (int i = 0; i < SIZES[k]; i++) {
                for (int j = 0; j < SIZES[k]; j++) {
                    A[i][j] = random.nextInt(10);
                    B[i][j] = random.nextInt(10);
                }
            }

            long[] results = new long[7];
            start = System.currentTimeMillis();
            int[][] simple = SimpleMultiplying.multiply(A, B);
            end = System.currentTimeMillis();
            results[0] = end - start;
            start = System.currentTimeMillis();
            int[][] tape2 = TapeMultiplying.multiply(A, B, 2);
            end = System.currentTimeMillis();
            results[1] = end - start;
            start = System.currentTimeMillis();
            int[][] tape4 = TapeMultiplying.multiply(A, B, 4);
            end = System.currentTimeMillis();
            results[2] = end - start;
            start = System.currentTimeMillis();
            int[][] fox2 = FoxMultiplying.multiply(A, B, 2);
            end = System.currentTimeMillis();
            results[3] = end - start;
            start = System.currentTimeMillis();
            int[][] fox4 = FoxMultiplying.multiply(A, B, 4);
            end = System.currentTimeMillis();
            results[4] = end - start;
            start = System.currentTimeMillis();
            int[][] cannon2 = CannonMultiplying.multiply(A, B, 2);
            end = System.currentTimeMillis();
            results[5] = end - start;
            start = System.currentTimeMillis();
            int[][] cannon4 = CannonMultiplying.multiply(A, B, 4);
            end = System.currentTimeMillis();
            results[6] = end - start;

            System.out.println(Arrays.deepEquals(simple, tape2)
                    + " " + Arrays.deepEquals(simple, tape4)
                    + " " + Arrays.deepEquals(simple, fox2)
                    + " " + Arrays.deepEquals(simple, fox4)
                    + " " + Arrays.deepEquals(simple, cannon2)
                    + " " + Arrays.deepEquals(simple, cannon4));

            System.out.print(SIZES[k]);
            for (long r : results) {
                System.out.print("\t\t" + r + " ms");
            }
            System.out.println();
        }
    }
}