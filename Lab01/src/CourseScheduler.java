public class CourseScheduler {
    public static void swap(int[][] courses, int firstIndex, int secondIndex) {
        int[] temp = courses[firstIndex];
        courses[firstIndex] = courses[secondIndex];
        courses[secondIndex] = temp;
    }

    public static void sortArray(int[][] courses) {
        for (int i = 0; i < courses.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < courses.length; j++) {
                if(courses[minIndex][0] > courses[j][0]) {
                    minIndex = j;
                }
                else if(courses[minIndex][0] == courses[j][0] && courses[minIndex][1] > courses[j][1]) {
                    minIndex = j;
                }
            }
            if(minIndex != i) {
                swap(courses, i, minIndex);
            }


        }
    }

    public static int findMaxCourses(int[][] courses, int startIndex) {
        int counter = 1;
        int lastIndex = startIndex;
        for (int i = startIndex + 1; i < courses.length; i++) {
            if(courses[i][0] >= courses[lastIndex][1])  {
                counter++;
                lastIndex = i;
            }
        }
        return counter;
    }

    public static int maxNonOverlappingCourses(int[][] courses) {
        if(courses.length == 0) {
            return 0;
        }
        sortArray(courses);
        int counter = 0;
        for (int i = 0; i < courses.length; i++) {
            if(courses.length - i > counter) {
                counter = findMaxCourses(courses, i);
            }
        }
        return counter;
    }

    public static void main(String[] args) {
        System.out.println(maxNonOverlappingCourses(new int[][]{{9, 11}, {10, 12}, {11, 13}, {15, 16}}));
        System.out.println(maxNonOverlappingCourses(new int[][]{{19, 22}, {17, 19}, {9, 12}, {9, 11}, {15, 17}, {15, 17}}));
        System.out.println(maxNonOverlappingCourses(new int[][]{{19, 22}}));
        System.out.println(maxNonOverlappingCourses(new int[][]{{13, 15}, {13, 17}, {11, 17}}));
    }
}
