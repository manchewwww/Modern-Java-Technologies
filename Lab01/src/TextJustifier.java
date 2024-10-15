import java.util.Arrays;

public class TextJustifier {
    public static String[] justifyText(String[] words, int maxWidth) {
        String[] newString = new String[]{};
        String toAppend = "";
        int index = 0;
        for (String str : words) {
            if((toAppend.length() + str.length()) < maxWidth) {
                if(toAppend.length() == 0) {
                    toAppend += str;
                }
                else {
                    toAppend += (" " + str);
                }
            }
            else {
                newString = Arrays.copyOf(newString, newString.length + 1);
                newString[newString.length - 1] = toAppend;
                toAppend = "";
                toAppend += str;
            }
        }
        newString = Arrays.copyOf(newString, newString.length + 1);
        newString[newString.length - 1] = toAppend;

        return newString;
    }

    public static void main(String[] args) {
        String[] str =  {"The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog."};
        String[] pr = justifyText(str, 11);
        for (int i = 0; i < pr.length; i++) {
            System.out.println(pr[i]);
        }

        String[] str1 = {"Science", "is", "what", "we", "understand", "well", "enough", "to", "explain", "to", "a", "computer."};
        String[] pr1 = justifyText(str1, 20);
        for (int i = 0; i < pr1.length; i++) {
            System.out.println(pr1[i]);
        }
    }
}
