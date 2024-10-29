public class TextJustifier {
    public static int getLengthOfWords(String[] words, int start, int end) {
        int sum = 0;
        for (int i = start; i <= end; i++) {
            sum += words[i].length();
        }
        return sum;
    }

    public static StringBuilder formatSpaces(String[] words, int start, int end, int maxWidth) {
        StringBuilder result = new StringBuilder();
        int wordsCount = end - start + 1;

        if ((words.length - 1) == end) {
            for (int i = start; i <= end; i++) {
                result.append(words[i]);
                if (i != end) {
                    result.append(" ");
                }
            }

            int spaces = maxWidth - result.length();
            for (int i = 0; i < spaces; i++) {
                result.append(" ");
            }
        }
        else if (wordsCount == 1) {
            for (int i = start; i <= end; i++) {
                result.append(words[i]);
                if (i != end) {
                    result.append(" ");
                }
            }
            int spaces = (maxWidth - result.length());
            for (int i = 0; i < spaces; i++) {
                result.append(" ");
            }

            result.append("\n");
        }
        else {
            int lengthOfWords = getLengthOfWords(words, start, end);
            int totalSpaces = maxWidth - lengthOfWords;
            int spacesBetweenWords = totalSpaces / (wordsCount - 1);
            int extraSpaces = totalSpaces % (wordsCount - 1);

            for (int i = start; i <= end; i++) {
                result.append(words[i]);
                int counter = 0;
                if (i != end) {
                    while (counter < spacesBetweenWords) {
                        result.append(" ");
                        counter++;

                    }
                }

                if (extraSpaces > 0) {
                    result.append(" ");
                    extraSpaces--;
                }

            }
            result.append("\n");
        }
        return result;
    }


    public static String[] justifyText(String[] words, int maxWidth) {
        if (words.length == 0) {
            return words;
        }
        StringBuilder result = new StringBuilder();
        int sumOfLength = 0;
        int startIndex = 0;
        int countWords = 0;
        for (String str : words) {
            if ((sumOfLength + str.length() + (countWords - 1)) < maxWidth) {
                sumOfLength += str.length();
                countWords++;
            }
            else {
                result.append(formatSpaces(words, startIndex, startIndex + countWords - 1, maxWidth));
                sumOfLength = str.length();
                startIndex += countWords;
                countWords = 1;
            }
        }
        result.append(formatSpaces(words, startIndex, startIndex + countWords - 1, maxWidth));
        String[] newString = result.toString().split("\n");

        return newString;
    }

    public static void main(String[] args) {
        String[] str = {"The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog."};
        String[] res = justifyText(str, 11);
        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i]);
        }

        String[] str1 = {"Science", "is", "what", "we", "understand", "well", "enough", "to", "explain", "to", "a", "computer."};
        String[] res1 = justifyText(str1, 20);
        for (int i = 0; i < res1.length; i++) {
            System.out.print(res1[i]);
        }
    }
}
