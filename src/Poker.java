/**
 * COMP90041 Project 
 * Project Name: Poker.java
 * Student ID:653909 
 * Login ID: changjianm
 * Author: Changjian MA 
 * Date: 08/10/2015
 * Java program to characterise a poker hand 
 * according to the list of hands above correctly.
 */
import java.util.ArrayList;//ArrayList<>����Ǻܳ��õķ��ͼ���
import java.util.Arrays;//����Array��һЩ���߷������÷����������е�����Ҫ����һ������ʱ�����������ķ�����Arrays.toString(����)
import java.util.List;
  //����һ�´���˼·~~~~~~~~~~~~~~~~~~~~~~~��
/**
 * This is the Poker class with the main method. The functionality of the class
 * is to receive the input of the user and then check whether the input is valid
 * or not. If the input is valid, the program will output the description of
 * every player as well as the winner. If the input is invalid, the program will
 * print the error message. It uses Arrays, List and ArrayList to achieve a good
 * performance.
 */
public class Poker {
//==========================================================================================================
    /**
     * Create the constant of the number of the card, the description of the
     * player, simple version, error display and invalid card name. Each
     * constant of the parameter has different output.
     */
    
    //declare the instance variable(û��static����) and class variable(��static����).ͬʱdeclare two different kinds of enum.
    private static final int CardsNumber = 5;
    private static final String PlayerCondition = "Player 1: %s"; //��ʽ�������Ԥ��һ��������ַ��������������ĸ�ʽ�����
    private static final String SimpleVer = "NOT UNDERTAKEN";
    private static final String ErrorDisplay 
        = "Error: wrong number of arguments; must be a multiple of 5";
    private static final String CardnameInvalied = "Error: invalid card name '%s'";
    
    //static����һ�������̬���������ھ�̬�������������κζ���Ϳ��Խ��з��ʣ���˶��ھ�̬������˵����û��this�ģ�
    //��Ϊ�����������κζ��󣬼�Ȼ��û�ж��󣬾�̸����this�ˡ���������������ԣ��ھ�̬�����в��ܷ�����ķǾ�̬��Ա�����ͷǾ�̬��Ա������
    //��Ϊ�Ǿ�̬��Ա����/�������Ǳ�����������Ķ�����ܹ������á�
    
    /**
     * Create the constant of nine categories and the description of each hand
     * category, the instructions are in the project file.
     */
    public static final String StraightFlush = "%s-high straight flush";//�ٷֺ�S����ָ��ʽ�������final�ǳ��������Ǹ�����ĿҪ�����
    public static final String FourOfAKind = "Four %ss";
    public static final String FullHouse = "%ss full of %ss";
    public static final String Flush = "%s-high flush";
    public static final String Straight = "%s-high straight";
    public static final String ThreeOfAKind = "Three %ss";
    public static final String TwoPair = "%ss over %ss";
    public static final String OnePair = "Pair of %ss";
    public static final String HighCard = "%s-high";
    //������һ�´��룬����һ����������ŵ��������У�private��public����
    /**
     * Create the List of Ranks and the List of Suits as new ArrayList.
     */
    private static List<Rank> Ranks = new ArrayList<>(); //list��interface�������ķ�Χ��������һЩ��to be more flexible ,reduce code 
   // redundant, rank�Ǿ�̬���涨���ö�ٵ�����
    private static List<Suit> Suits = new ArrayList<>();
    private static Rank[] RanksOfArray;// ��object array��Ϊ��Ҫ����rank����Ҫ�õ�arrays.sort,��ͨ��arrays.sort����������
    /**
     * Create the enum to implement suit.There are four suits which are C, H, S,
     * D. C stands for CLUBS, H stands for HEARTS, S stands for SPADES and D
     * stands for DIAMONDS.
     */
    private enum Suit {
        C, H, S, D;
    }
    
    /**
     * Create the enum to implement rank.There are 13 possible ranks.
     */
    private enum Rank {

        // Enumerate all the possible Rank from Two to Ace.
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), 
            NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13), ACE(14);
        //Ϊ��Ҫ��ʼ������Ҫʹ�õ�hashset���������value��Ϊhashset��index������ÿһ��rank�ĳ��ִ�����it means its real value of the rank

        private int value;//instance variable ʵ������

        Rank(int value) {  //�������������ֵ ���洫���������Ǽ��� ���췽��constructor
            this.value = value;//��ֵ�������instance variable��
        }

        public int getValue() {//get ����
            return value;
        }

        public String naming() {
            return naming(value);
        }

        // Get 4 special ranks, use switch to naming them.
        // Except these 4 ranks, other ranks will return their own value.
        public static String naming(int value) {
            switch (value) {   //���������ֵ����󼸸��������ַ������£�
                case 14 :
                    return "Ace";
                case 13 :
                    return "King";
                case 12 :
                    return "Queen";
                case 11 :
                    return "Jack";
                default :
                    return String.valueOf(value);
            }
        }

    }
//===========================================================================================
    /**
     * Create the main method to verify the valied card name.
     */
    public static void main(String[] args) {//command line argument �����ֵ
        if (args == null || args.length == 0// �жϼ�⴫���������ݣ����ֱ��.length �Ļ��ͱ�����
                || args.length % CardsNumber != 0) {  //5������������0.�������0�Ļ�������5�ı������Ͳ�����ʾ������ʾ���򵥰����5
            System.out.println(ErrorDisplay);
            return; //ֹͣ����program
        }
        if (args.length > CardsNumber) {//�������5 �Ļ�������򵥰�ķ���ֵ
            System.out.println(SimpleVer);
            return;
        }
        for (String str : args) {  //һ��loop���ж�5���ַ��У���һ����rank���ڶ�����suit
            if (str.length() != 2) {//�ж������ÿһ�����Ƿ�ֻ��2���ַ�
                System.out.println(String.format(CardnameInvalied, str));
                return;//Ȼ����˳�����system.exit��0��
            }
            //�������2�������������£�
            String rank = str.substring(0, 1).toUpperCase();//�ֲ�������local variable��ͨ��substring�ָ��������ĸ����˼�ǽ�ȡ��һ����ĸ����ȡ�ַ���
            String suit = str.substring(1).toUpperCase();//�ӵڶ�����ʼ��ȡ�����������ֻ��1��ֵ�����Ծ��ǵڶ�����ĸ 
            //overloading ��ͬmethod name but different signature

            // Use switch code to verify the rank name. There are 13 ranks in
            // total.
            switch (rank) {  //�����rank
                case "2" :
                    Ranks.add(Rank.TWO);// convert data type from string to enum
                    break;
                case "3" :
                    Ranks.add(Rank.THREE);
                    break;
                case "4" :
                    Ranks.add(Rank.FOUR);
                    break;
                case "5" :
                    Ranks.add(Rank.FIVE);
                    break;
                case "6" :
                    Ranks.add(Rank.SIX);
                    break;
                case "7" :
                    Ranks.add(Rank.SEVEN);
                    break;
                case "8" :
                    Ranks.add(Rank.EIGHT);
                    break;
                case "9" :
                    Ranks.add(Rank.NINE);
                    break;
                case "T" :
                    Ranks.add(Rank.TEN);
                    break;
                case "J" :
                    Ranks.add(Rank.JACK);
                    break;
                case "Q" :
                    Ranks.add(Rank.QUEEN);
                    break;
                case "K" :
                    Ranks.add(Rank.KING);
                    break;
                case "A" :
                    Ranks.add(Rank.ACE);
                    break;
                default :
                    System.out.println(String.format(CardnameInvalied, str));
                    return;
            }

            // Use switch code to verify the suit name. There are 4 suits in
            // total.
            switch (suit) {
                case "C" :
                    Suits.add(Suit.C);//���������涨���enum class�����C
                    break;
                case "D" :
                    Suits.add(Suit.D);
                    break;
                case "H" :
                    Suits.add(Suit.H);
                    break;
                case "S" :
                    Suits.add(Suit.S);
                    break;
                default :
                    System.out.println(String.format(CardnameInvalied, str));
                    return;
            }
        }
//===============================================================================================================
        /**
         * Convert a list to an array to sort the ranks of array. It the same as
         * the order of enum.
         */
        RanksOfArray = Ranks.toArray(new Rank[CardsNumber]);//ranks ����list��ת����array�ͣ�ͬʱ����һ���µ�array���ŵ�ranksofarray��
        //require a new memory address
        Arrays.sort(RanksOfArray);//����rank �����򣬾�������Ķ��������

        // If the case belongs to any of these nine categories, then print the
        // corresponding description.
        String topRank = RanksOfArray[4].naming();//�ֲ�����local variable����0��4���������4�����ģ�����ӡ��actual name of each rank
       
        String secondRank = null;//û��ֵ��ҲҪ������һ����ֵ��
        String description = null;//Ϊ��Ҫ�洢�����е���һ��
        if (whetherFlush()) {
            description = Flush;//����Ҫprintһ���ַ���
        }
        if (whetherStriaght()) {//�������flush����ô�ж��ǲ���straight
            if (description == null) { //���֮ǰ�ǿգ��ǿ϶���straight
                description = Straight;//
            } else {//������ǿգ��Ǽ���flush ����straight
                description = StraightFlush;
            }
        }

        if (description == null) {  //����������ֶ�����
            String[] result = whetherAKind();//��סctrl��ת���÷���
            description = result[0]; //�����е�һ���������������е�����
            topRank = result[1];//����r1
            secondRank = result[2];//����r2
        }

        if (description == null) {
            description = HighCard;
            topRank = RanksOfArray[4].naming();//�������r��ֵ����ӡ����
        }

        String outPut = String.format(description, topRank,//���description���������R1��R2���μ���ĿҪ��
                secondRank == null ? "" : secondRank);
        System.out.println(String.format(PlayerCondition, outPut));
    }
//===================================================================================================================
    /**
     * To judge which category the five cards in hands belong to. Nine
     * categories are divided into three methods, whether flush or not, whether
     * straight or not and whether a kind or not.
     */
    private static boolean whetherFlush() {//static����static��������main�е���
        for (int i = 1; i < Suits.size(); i++) { //suit sizeָ��ɫ��������ֻ��5���ƣ�����ֻ��5����ɫ

            // To verify whether it is the same suit.
            if (!Suits.get(i - 1).equals(Suits.get(i))) {//��
                return false;//��һ����ǰһ����ȣ������һ������ô����false����˼�ǲ���flush
            }
        }
        return true;//�������flush 
    }

    /**
     * To verify whether it is a straight.
     */
    private static boolean whetherStriaght() {
        for (int i = 1; i < RanksOfArray.length; i++) {
            int diff = RanksOfArray[i - 1].getValue()//ǰһ����ȥ��һ���������-1����straight��������
                    - RanksOfArray[i].getValue();
            if (diff != -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Create a new method called whetherAKind to find the occurrence number of
     * rank in order to confirm which case it belongs to. There are four cases
     * below.
     */
    //�������
    private static String[] whetherAKind() {
        int[] hashSet = new int[Rank.ACE.getValue() + 1];//������ִ�����14������Ϊ���գ���value��Ϊindex
        //the value of each rank is treated as the index of the hashset to count the occcurence of each rank
        for (Rank rank : RanksOfArray) {//����������������
            hashSet[rank.getValue()]++;//value����index��ÿ����һ�������ö�Ӧ��index�ͼ�һ��������ִ���
        }
//�����Ǹ����ѱ������ĳ��ִ��������ж�����������description��
        String[] result = new String[3];//����result 0,1,2��3��
        int value = seekOccurence(4, hashSet);//��hashset���������ң���û�г���4�ε���
        if (value != -1) {
            result[0] = FourOfAKind;
            result[1] = Rank.naming(value);
            return result;
        }

        // If the occurrence number is 3, it needs further judgement to confirm
        // whether there are 2 card which are the same.
        value = seekOccurence(3, hashSet);
        if (value != -1) {//����ҵ�����3�ε�value����Ϊr1
            int value2 = seekOccurence(2, value, hashSet);//r2
            if (value2 != -1) {
                result[0] = FullHouse;
                result[1] = Rank.naming(value);
                result[2] = Rank.naming(value2);
                return result;
            }
            result[0] = ThreeOfAKind;
            result[1] = Rank.naming(value);
            return result;
        }

        value = seekOccurence(2, hashSet);
        if (value != -1) {
            int value2 = seekOccurence(2, value, hashSet);
            if (value2 != -1) {
                result[0] = TwoPair;
                if (value <= value2) {
                    result[1] = Rank.naming(value2);
                    result[2] = Rank.naming(value);
                } else {
                    result[1] = Rank.naming(value);
                    result[2] = Rank.naming(value2);

                }
                return result;
            }
            result[0] = OnePair;
            result[1] = Rank.naming(value);
            return result;
        }

        return result;
    }
//=====================================================================================
    //hash counting more Efficient than the normal for loop
    /**
     * Use hash idea to caculate the occurrence number of the same type.
     */
    private static int seekOccurence(int occurence, int[] hashSet) {//hashset��14 
        return seekOccurence(occurence, -1, hashSet);//seekOccurence������������overloading�Ĺ�ϵ��-1���ǲ���飬reduce the code redundant
    }

    private static int seekOccurence(int occurence, int unCheckPos,//special for indentifing two pair�����֮ǰ�鵽һ��pair����
            //�Ͳ��ټ��������ټ����һ��position�����Ƿ���pair
            int[] hashSet) {
        for (int i = 0; i < hashSet.length; i++) {
            if (hashSet[i] == occurence && i != unCheckPos) {
                return i;
            }
        }
        return -1;//��˼��û�ҵ��ж�γ��ִ�������
    }

}