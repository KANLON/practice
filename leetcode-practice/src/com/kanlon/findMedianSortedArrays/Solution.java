package com.kanlon.findMedianSortedArrays;

/**
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums1 = [1,3], nums2 = [2]
 * 输出：2.00000
 * 解释：合并数组 = [1,2,3] ，中位数 2
 * 示例 2：
 *
 * 输入：nums1 = [1,2], nums2 = [3,4]
 * 输出：2.50000
 * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
 * 示例 3：
 *
 * 输入：nums1 = [0,0], nums2 = [0,0]
 * 输出：0.00000
 * 示例 4：
 *
 * 输入：nums1 = [], nums2 = [1]
 * 输出：1.00000
 * 示例 5：
 *
 * 输入：nums1 = [2], nums2 = []
 * 输出：2.00000
 *  
 *
 * 提示：
 *
 * nums1.length == m
 * nums2.length == n
 * 0 <= m <= 1000
 * 0 <= n <= 1000
 * 1 <= m + n <= 2000
 * -106 <= nums1[i], nums2[i] <= 106
 *  
 *
 * 进阶：你能设计一个时间复杂度为 O(log (m+n)) 的算法解决此问题吗？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/median-of-two-sorted-arrays
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author zhangcanlong
 * @date 2021/09/11
 */
class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums1 = {1,3};
        int[] nums2 = {2,7};
        System.out.println(solution.findMedianSortedArrays(nums1,nums2));
    }

    /**
     * 找到值排序的数组
     *
     * @param nums1 nums1
     * @param nums2 nums2
     * @return double
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 先整体排序，再取中位数
        // 1. 如果nums1 最小值 ，大于 nums2 最大值 则直接 nums2+num1
        // 2. 如果nums1 最大值，小于 num2 最小值 则直接 nums1+nums2
        // 3. 取 nums1 的第一位 ，与nums2的从后往前比较，找到等于或大于 nums第一位的元素，并记录下index，依次nums2 index 前面的元素和将这两个元素放到新的数组中.
        //  接着从第二位遍历 nums1，并与 nums2的index +1 的元素比较，如果nums1的比较小，则放nums1的元素，继续遍历nums1 index++；如果 nums2元素比较小，则放nums2 元素，并nums2 index++

        int[] allNums = new int[nums1.length+nums2.length];
        if(allNums.length==0){
            return 0;
        }
        if(allNums.length==1){
            return nums1.length==1?nums1[0]:nums2[0];
        }
        // 将较大的数组作为nums1  
        if(nums2.length>nums1.length){
                int[] tempNums = nums1;
                nums1=nums2;
                nums2=tempNums;
        }
        
        int nums1One = nums1[0];
        int nums2Index = 0;
        int nums1Index = 0;
        // 先找到的 与 nums1One 相等，或大于的元素
        for(int i=0;i<nums2.length;++i){
            if(nums2[i]>=nums1One){
                nums2Index=i;
                break;
            }
        }

        int allNumsCrrentIndex=0;
        if(nums2Index>=1 && nums2Index<=nums2.length-1){
            for(int i=0;i<nums2Index;++i){
                allNums[allNumsCrrentIndex++]=nums2[i];
            }
        }
        // 依次遍历nums1
        for(int i=0;i<nums1.length;i++){
            if(nums2Index>=0 && nums2Index<=nums2.length-1){
                if(nums1[i]<=nums2[nums2Index]){
                    allNums[allNumsCrrentIndex++]=nums1[i];
                }else{
                    allNums[allNumsCrrentIndex++]=nums2[nums2Index++];
                    // 这里遍历了数组1，但是数组1的元素还没遍历完
                    i--;
                }
            }else{
                allNums[allNumsCrrentIndex++]=nums1[i];
            }
        }

        // 将剩下的 nums2的元素放到返回数组中
        for(int i=nums2Index;i<nums2.length;++i){
            allNums[allNumsCrrentIndex++] = nums2[i];
        }

        if(allNums.length%2==0){
            return (allNums[allNums.length/2]+allNums[allNums.length/2-1])/2d;
        }else{
            return allNums[(allNums.length-1)/2];
        }
    }
}
