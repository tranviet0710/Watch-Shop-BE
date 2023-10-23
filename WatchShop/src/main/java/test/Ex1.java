package test;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ex1 {
    public static void main(String[] args) {
        String input = "abracadabra";
//        int[] countNumberOfExistTime = new int[256];
//        for(char c: input.toCharArray()){
//            countNumberOfExistTime[c] ++;
//        }
//        int max = 0;
//        char charMax = 0;
//        for(int i = 0; i<countNumberOfExistTime.length; i++){
//            if(countNumberOfExistTime[i] > max){
//                max = countNumberOfExistTime[i];
//                charMax = (char) i;
//            }
//        }
//        System.out.println(charMax);
        Map<Character, Long> map = input.chars().mapToObj(x->(char) x).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(map.toString());
        char charMax = 0;
        long max = 0;
        for(Map.Entry<Character, Long> entry: map.entrySet()){
            if(entry.getValue() > max){
                max = entry.getValue();
                charMax = entry.getKey();
            }
        }
        System.out.println(charMax);
    }
}
