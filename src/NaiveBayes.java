import java.io.*;
import java.util.*;


public class NaiveBayes {
    private static int numSpam;
    private static HashSet<String> all_vocab;
    private static Map<String, Integer> spam_vocab;
    private static int numHam;
    private static Map<String, Integer> ham_vocab;
    private static int rightCases;
    private static int allCases;
    //	HashMap<String, Integer> spam_vocab;
//	HashMap<String, Integer> all_vocab;


    NaiveBayes() {
        ham_vocab = new HashMap<String, Integer>();
        spam_vocab = new HashMap<String, Integer>();
        all_vocab = new HashSet<>();
        rightCases = 0;
        allCases = 0;

    }

//Updates hashmaps for ham
    public static void trainHam(InputStream in) {
        try {

            final Scanner s = new Scanner(in);
            //System.out.println("Run");  //debug
            HashSet<String> all = new HashSet<String>();
            while (s.hasNext()) {
                String me = s.next().toLowerCase();

                if (!me.contains("<"))
                    all.add(me);

                //System.out.println(me);

                if (!me.contains("<"))
                    all.add(me);

                //System.out.println(me);
                if (me.equals("</body>")) {
                    for (String i : all)
                        addham(i);
                    all = new HashSet<String>();
                    numHam++;
                }


            }


        } catch (Exception e) {

        }

        all_vocab.addAll(ham_vocab.keySet());
    }
    //Updates hashmaps for spam and also vocab
    public static void trainSpam(InputStream in) throws FileNotFoundException {

        try {

            final Scanner s = new Scanner(in);
            HashSet<String> all = new HashSet<String>();
            //System.out.println("Run");  //debug
            while (s.hasNext()) {
                String me = s.next().toLowerCase();

                if (!me.contains("<"))
                    all.add(me);

                if (me.equals("</body>")) {
                    for (String i : all)
                        addspam(i);
                    all = new HashSet<String>();
                    numSpam++;
                }


            }


        } catch (Exception e) {
            //System.out.println("Fucking bug");
        }
        all_vocab.addAll(spam_vocab.keySet());


    }

    public static void addham(String str) {
        int count = 1;
        if (ham_vocab.containsKey(str))
            count = ham_vocab.get(str) + 1;

        ham_vocab.put(str, count);

    }

    public static void addspam(String str) {
        int count = 1;
        if (spam_vocab.containsKey(str))
            count = spam_vocab.get(str) + 1;

        spam_vocab.put(str, count);

    }


    public static void test(InputStream str, String expected) {
        try {

            final Scanner s = new Scanner(str);
            long counter = 0;
            HashSet<String> all = new HashSet<>();
            while (s.hasNextLine()) {
                String me = s.nextLine().toLowerCase();

                if (me.contains("</body>")) {
                    counter++;
                    testMail(all, counter, expected);
                    all = new HashSet<>();
                } else if (me.contains("<") || me.isEmpty()) continue;

                String[] words = me.split(" ");
                all.addAll(Arrays.asList(words));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
// Updates hashmaps and also implements the calculations for our tests
    public static void testMail(HashSet<String> emailVocab, long i, String expected) {
        double tot = numSpam + numHam;
        double initSpam = numSpam / tot;
        double initHam = numHam / tot;
//        System.out.println(initSpam + " " + initHam);
        double probSpam = Math.log(initSpam);
        double probHam = Math.log(initHam);
        int features = 0;

        for (String me : all_vocab) {
            if (emailVocab.contains(me)) {
                features++;
                double tmp = (spam_vocab.get(me) + 1.0) / (numSpam + 2.0);
                //System.out.println(me + " sp:" + tmp);
                probSpam += Math.log(tmp);
                tmp = (ham_vocab.get(me) + 1.0) / (numHam + 2.0);
               // System.out.println(me + " hp:" + tmp);
                probHam += Math.log(tmp);
            } else {
                double tmp = (numSpam - spam_vocab.get(me) + 1.0) / (numSpam + 2.0);
                probSpam += Math.log(tmp);
                //System.out.println(me + " sp:" + tmp);
                tmp = (numHam - ham_vocab.get(me) + 1.0) / (numHam + 2.0);
                probHam += Math.log(tmp);
               // System.out.println(me + " hp:" + tmp);
            }
        }
        String ans = (probHam >= probSpam ? "ham" : "spam");
        probSpam = Math.round(probSpam * 1000.00) / 1000.00;
        probHam = Math.round(probHam * 1000.00) / 1000.00;
        if (ans.equals(expected))
            rightCases++;
        System.out.println("Test " + i + " " + features + "/" + all_vocab.size() + " features true " + probSpam + " " + probHam + " " + expected + " " + (ans.equals(expected) ? "right" : "wrong"));
        allCases++;
    }


    public static void main(String[] args) throws FileNotFoundException {
        NaiveBayes n = new NaiveBayes();

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter training spam filename: ");
        String trainSpam = scan.nextLine();
        InputStream inTrainSpam = NaiveBayes.class.getResourceAsStream(trainSpam);

        System.out.println("Enter training ham filename: ");
        String trainHam = scan.nextLine();
        InputStream inTrainHam = NaiveBayes.class.getResourceAsStream(trainHam);

        System.out.println("Enter test spam filename: ");
        String testSpam = scan.nextLine();
        InputStream inTestSpam = NaiveBayes.class.getResourceAsStream(testSpam);

        System.out.println("Enter test ham filename: ");
        String testHam = scan.nextLine();
        InputStream inTestHam = NaiveBayes.class.getResourceAsStream(testHam);


        trainSpam(inTrainSpam);
        trainHam(inTrainHam);

        for (String word : all_vocab) {
            if (!spam_vocab.containsKey(word)) {
                spam_vocab.put(word, 0);
            }
            if (!ham_vocab.containsKey(word)) {
                ham_vocab.put(word, 0);
            }
        }

//        System.out.println(spam_vocab);
//        System.out.println(ham_vocab);

        test(inTestSpam, "spam");
        test(inTestHam, "ham");


//        for (Map.Entry e : spam_vocab.entrySet()) {
//            System.out.println(e.getKey() + " " + e.getValue());
//        }
//
//        for (Map.Entry e : ham_vocab.entrySet()) {
//            System.out.println(e.getKey() + " " + e.getValue());
//        }


        System.out.println("Total: " + n.rightCases + "/" + n.allCases + " emails classified correctly.");
    }


}
