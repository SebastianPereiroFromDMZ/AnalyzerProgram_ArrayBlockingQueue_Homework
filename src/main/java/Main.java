import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static BlockingQueue<String> stringsA = new ArrayBlockingQueue<>(100);
    private static BlockingQueue<String> stringsB = new ArrayBlockingQueue<>(100);
    private static BlockingQueue<String> stringsC = new ArrayBlockingQueue<>(100);
    private static  int QUANTITY = 10_000;


    public static void main(String[] args) throws InterruptedException {


        Thread generateTexts = new Thread(() -> {
            for (int i = 0; i < QUANTITY; i++) {
                String str = generateText("abc", 100_000);
                try {
                    stringsA.put(str);
                    stringsB.put(str);
                    stringsC.put(str);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });

        generateTexts.start();

        Thread countCharacterA = new Thread(() -> {
            int countCharA;
            int max = 0;
            for (int i = 0; i < QUANTITY; i++) {
                try {
                    String text = stringsA.take();
                    countCharA = countCharacterA(text);
                    if (countCharA > max){
                        max = countCharA;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Максимальное количество символа 'a' " + max );
        });

        countCharacterA.start();

        Thread countCharacterB = new Thread(() -> {
            int countCharB;
            int max = 0;
            for (int i = 0; i < QUANTITY; i++) {
                try {
                    String text = stringsB.take();
                    countCharB = countCharacterB(text);
                    if (countCharB > max){
                        max = countCharB;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Максимальное количество символа 'b' " + max );
        });

        countCharacterB.start();

        Thread countCharacterC = new Thread(() -> {
            int countCharC;
            int max = 0;
            for (int i = 0; i < QUANTITY; i++) {
                try {
                    String text = stringsC.take();
                    countCharC = countCharacterC(text);
                    if (countCharC > max){
                        max = countCharC;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
             System.out.println("Максимальное количество символа 'c' " + max );
        });

        countCharacterC.start();
    }


    public static int countCharacterA (String text){
        int count;
        int chr = 'a';
        char[] chars = text.toCharArray();
        Stream<Character> charStream = new String(chars)
                .chars()
                .filter(ch -> ch == chr)
                .mapToObj(i -> (char)i);

        count = ((int) charStream.count());
        return count;
    }


    public static int countCharacterB (String text){
        int count;
        int chr = 'b';
        char[] chars = text.toCharArray();
        Stream<Character> charStream = new String(chars)
                .chars()
                .filter(ch -> ch == chr)
                .mapToObj(i -> (char)i);

        count = ((int) charStream.count());
        return count;
    }


    public static int countCharacterC (String text){
        int count;
        int chr = 'c';
        char[] chars = text.toCharArray();
         Stream<Character> charStream = new String(chars)
                 .chars()
                 .filter(ch -> ch == chr)
                 .mapToObj(i -> (char)i);

        count = ((int) charStream.count());
        return count;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
