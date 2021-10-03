package com.kanlon.longestPalindrome;

import java.util.ArrayList;
import java.util.List;

/**
 5. 最长回文子串
 给你一个字符串 s，找到 s 中最长的回文子串。



 示例 1：

 输入：s = "babad"
 输出："bab"
 解释："aba" 同样是符合题意的答案。
 示例 2：

 输入：s = "cbbd"
 输出："bb"
 示例 3：

 输入：s = "a"
 输出："a"
 示例 4：

 输入：s = "ac"
 输出："a"


 提示：

 1 <= s.length <= 1000
 s 仅由数字和英文字母（大写和/或小写）组成
 *
 * @author zhangcanlong
 * @date 2021/09/12
 */
class Solution {


    public static void main(String[] args) {
        String test1 = "babad";
        Solution solution =new Solution();
        System.out.println(solution.longestPalindrome(test1));
    }

    public String longestPalindrome(String s) {
        // 解法2，根据回文数的规则，从回文数一定是对称的，可以遍历 s ，在s的每个点，向两边同时迭代，如果两边不一致的，则跳到下一个点；

        if(s==null || s.length()<=1){
            return s ;
        }
        String maxStr = "";
        for(int i=0;i<s.length();++i){
            String longestPalindromeStr = "";
            // 以当前两个结点间作为中心向两边遍历
            for(int j=i;j<s.length();++j){
                int leftIndex = i-(j-i)-1;
                int rightIndex=j;
                if(leftIndex<0){
                    break;
                }
                if(s.charAt(leftIndex)==s.charAt(rightIndex)){
                    longestPalindromeStr= String.valueOf(s.charAt(leftIndex)) +longestPalindromeStr;
                    longestPalindromeStr= longestPalindromeStr+String.valueOf(s.charAt(rightIndex)) ;
                }else{
                    break;
                }
            }
            maxStr = maxStr.length()<longestPalindromeStr.length()?longestPalindromeStr:maxStr;

            // 以j 为作为中心，向两边遍历
            String longestPalindromeStr2 = String.valueOf(s.charAt(i));
            for(int j=i;j<s.length();++j){
                int leftIndex = i-(j-i)-1;
                int rightIndex=j+1;
                if(leftIndex<0 ||rightIndex>=s.length() ){
                    break;
                }
                if(s.charAt(leftIndex)==s.charAt(rightIndex)){
                    longestPalindromeStr2= String.valueOf(s.charAt(leftIndex)) +longestPalindromeStr2;
                    longestPalindromeStr2= longestPalindromeStr2+String.valueOf(s.charAt(rightIndex)) ;
                }else{
                    break;
                }
            }
            maxStr=maxStr.length()<longestPalindromeStr2.length()?longestPalindromeStr2:maxStr;
        }
        return maxStr;
    }


    // 暴力破解，n^3 次方
    public String solution1(String s){
        // 两个 for循环 暴力破解
        if(s==null || s.length()<=1){
            return s;
        }
        List<Character> maxStrList = new ArrayList<>();
        maxStrList.add(s.charAt(0));
        List<Character> tempList = new ArrayList<>();
        for(int i=0;i<s.length();++i){
            tempList = new ArrayList<>();
            tempList.add(s.charAt(i));
            for(int j=i+1;j<s.length();++j){
                tempList.add(s.charAt(j));
                // 判断是否是回文，是，并且长度大于原maxStrList则设置为maxStrList
                boolean isPalindromeStr = true;
                for(int z=0;z<(tempList.size()/2);++z){
                    if(!tempList.get(z).equals(tempList.get(tempList.size()-1-z)) ){
                        isPalindromeStr=false;
                        break;
                    }
                }
                if(isPalindromeStr){
                    maxStrList = tempList.size()>maxStrList.size()?new ArrayList<>(tempList):maxStrList;
                }
            }
        }
        String maxStr = "";
        for(int i=0;i<maxStrList.size();++i){
            maxStr +=maxStrList.get(i).toString();
        }
        return maxStr;

    }
    
}
