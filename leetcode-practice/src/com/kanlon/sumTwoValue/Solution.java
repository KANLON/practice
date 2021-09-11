package com.kanlon.sumTwoValue;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {

    public static void main(String[] args) {
        ListNode l11 = new ListNode(2);
        ListNode l12 = new ListNode(4);
        ListNode l13 = new ListNode(9);

        l11.next=l12;
        l12.next=l13;

        ListNode l21 = new ListNode(5);
        ListNode l22 = new ListNode(6);
        ListNode l23 = new ListNode(4);
        ListNode l24 = new ListNode(9);
        l21.next=l22;
        l22.next=l23;
        l23.next=l24;

        Solution solution = new Solution();
        solution.addTwoNumbers(l11,l21);
    }


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 先将原两个  链表 反转，再从头节点开始相加，如果慢1，则进一位，相加的值作为sum的链表的结点之一，sum的链表相加时为从尾  向上指
        ListNode newL1 =l1;
        ListNode newL2 = l2;
        if(newL1==null || newL2==null){
            if(newL1==null && newL2 ==null){
                return null;
            }
            return newL1==null?newL2:newL1;
        }
        ListNode sumListNodeLast = new ListNode((newL1.val+newL2.val)%10);
        ListNode sumListNodeHead = sumListNodeLast;
        int addOne = (newL1.val+newL2.val)>=10?1:0;
        ListNode tempNode1 = newL1.next;
        ListNode tempNode2 = newL2.next;
        while(tempNode1!=null || tempNode2!=null){
            int val1 = tempNode1==null?0:tempNode1.val;
            int val2 = tempNode2==null?0:tempNode2.val;
            if(tempNode1!=null){
                tempNode1=tempNode1.next;
            }
            if(tempNode2!=null){
                tempNode2=tempNode2.next;
            }
            ListNode lastNode = new ListNode((val1+val2+addOne)%10);
            sumListNodeHead.next = lastNode;
            sumListNodeHead = lastNode;
            addOne = (val1+val2+addOne)>=10?1:0;
        }
        // 如果最后一位 还有进位，也添加多一个结点
        if(addOne==1) {
            ListNode lastNode = new ListNode((addOne)%10);
            sumListNodeHead.next = lastNode;
            sumListNodeHead = lastNode;
        }

        return sumListNodeLast;
    }


    // 反转链表
    public ListNode reverseNode(ListNode orginListNode){
        if(orginListNode==null || orginListNode.next==null){
            return orginListNode;
        }
        // 先获取当前结点的下一个结点保存，再获取当前结点，将当前结点的指向上一个结点，在遍历将当前结点设置为下一个结点,将下一个结点设置为当前结点。如果当前结点为头结点，则清空其指向
        ListNode orgintHeadNode = orginListNode;
        ListNode tempListNode = orginListNode.next;
        ListNode lsatNode = orginListNode;
        orginListNode.next=null;
        ListNode newHeadNode = tempListNode;
        while(tempListNode!=null){
            ListNode tempNextListNode = tempListNode.next;
            // 提前设置后头结点
            if(tempNextListNode==null){
                newHeadNode = tempListNode;
            }
            tempListNode.next=lsatNode;
            lsatNode = tempListNode;
            tempListNode = tempNextListNode;
        }
        return newHeadNode;
    }
}














