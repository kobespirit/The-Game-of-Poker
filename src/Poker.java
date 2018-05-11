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
import java.util.ArrayList;//ArrayList<>这个是很常用的泛型集合
import java.util.Arrays;//导入Array的一些工具方法，该方法的作用有当你需要遍历一个数组时可以引用它的方法，Arrays.toString(数组)
import java.util.List;
  //梳理一下大体思路~~~~~~~~~~~~~~~~~~~~~~~？
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
    
    //declare the instance variable(没有static修饰) and class variable(有static修饰).同时declare two different kinds of enum.
    private static final int CardsNumber = 5;
    private static final String PlayerCondition = "Player 1: %s"; //格式化输出，预留一个输入的字符串，按照这样的格式输出它
    private static final String SimpleVer = "NOT UNDERTAKEN";
    private static final String ErrorDisplay 
        = "Error: wrong number of arguments; must be a multiple of 5";
    private static final String CardnameInvalied = "Error: invalid card name '%s'";
    
    //static方法一般称作静态方法，由于静态方法不依赖于任何对象就可以进行访问，因此对于静态方法来说，是没有this的，
    //因为它不依附于任何对象，既然都没有对象，就谈不上this了。并且由于这个特性，在静态方法中不能访问类的非静态成员变量和非静态成员方法，
    //因为非静态成员方法/变量都是必须依赖具体的对象才能够被调用。
    
    /**
     * Create the constant of nine categories and the description of each hand
     * category, the instructions are in the project file.
     */
    public static final String StraightFlush = "%s-high straight flush";//百分号S都是指格式化输出，final是常量，都是根据题目要求定义的
    public static final String FourOfAKind = "Four %ss";
    public static final String FullHouse = "%ss full of %ss";
    public static final String Flush = "%s-high flush";
    public static final String Straight = "%s-high straight";
    public static final String ThreeOfAKind = "Three %ss";
    public static final String TwoPair = "%ss over %ss";
    public static final String OnePair = "Pair of %ss";
    public static final String HighCard = "%s-high";
    //整合了一下代码，把另一个类的声明放到了主类中，private或public都行
    /**
     * Create the List of Ranks and the List of Suits as new ArrayList.
     */
    private static List<Rank> Ranks = new ArrayList<>(); //list是interface，声明的范围可以扩大一些，to be more flexible ,reduce code 
   // redundant, rank是静态下面定义的枚举的类型
    private static List<Suit> Suits = new ArrayList<>();
    private static Rank[] RanksOfArray;// 是object array，为了要排序rank，需要用到arrays.sort,并通过arrays.sort方法来排序
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
        //为了要初始化后面要使用的hashset，用这个的value作为hashset的index来计算每一个rank的出现次数，it means its real value of the rank

        private int value;//instance variable 实例变量

        Rank(int value) {  //上面括号里面的值 上面传进来几就是几， 构造方法constructor
            this.value = value;//赋值给上面的instance variable里
        }

        public int getValue() {//get 方法
            return value;
        }

        public String naming() {
            return naming(value);
        }

        // Get 4 special ranks, use switch to naming them.
        // Except these 4 ranks, other ranks will return their own value.
        public static String naming(int value) {
            switch (value) {   //上面输入的值，最后几个，调出字符串如下：
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
    public static void main(String[] args) {//command line argument 输入的值
        if (args == null || args.length == 0// 判断检测传进来的数据，如果直接.length 的话就报错了
                || args.length % CardsNumber != 0) {  //5的余数不等于0.如果等于0的话，就是5的倍数，就不会显示错误显示。简单版就是5
            System.out.println(ErrorDisplay);
            return; //停止整个program
        }
        if (args.length > CardsNumber) {//如果大于5 的话，输出简单版的返回值
            System.out.println(SimpleVer);
            return;
        }
        for (String str : args) {  //一个loop，判断5个字符中，第一个是rank，第二个是suit
            if (str.length() != 2) {//判断输入的每一个，是否只有2个字符
                System.out.println(String.format(CardnameInvalied, str));
                return;//然后就退出程序。system.exit（0）
            }
            //如果等于2，就往下走如下：
            String rank = str.substring(0, 1).toUpperCase();//局部变量，local variable，通过substring分割成两个字母。意思是截取第一个字母。截取字符串
            String suit = str.substring(1).toUpperCase();//从第二个开始截取，到最后，这里只有1个值，所以就是第二个字母 
            //overloading 相同method name but different signature

            // Use switch code to verify the rank name. There are 13 ranks in
            // total.
            switch (rank) {  //上面的rank
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
                    Suits.add(Suit.C);//就是最上面定义的enum class里面的C
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
        RanksOfArray = Ranks.toArray(new Rank[CardsNumber]);//ranks 就是list，转换成array型，同时生成一个新的array。放到ranksofarray。
        //require a new memory address
        Arrays.sort(RanksOfArray);//按照rank 来排序，就是上面的定义的升序

        // If the case belongs to any of these nine categories, then print the
        // corresponding description.
        String topRank = RanksOfArray[4].naming();//局部变量local variable，从0到4的五个数。4是最大的，并打印出actual name of each rank
       
        String secondRank = null;//没有值，也要先声明一个空值，
        String description = null;//为了要存储九种中的哪一种
        if (whetherFlush()) {
            description = Flush;//即将要print一个字符串
        }
        if (whetherStriaght()) {//如果不是flush，那么判断是不是straight
            if (description == null) { //如果之前是空，那肯定是straight
                description = Straight;//
            } else {//如果不是空，那即是flush 又是straight
                description = StraightFlush;
            }
        }

        if (description == null) {  //如果以上两种都不是
            String[] result = whetherAKind();//按住ctrl跳转到该方法
            description = result[0]; //数组中第一个代表五中类型中的类型
            topRank = result[1];//代表r1
            secondRank = result[2];//代表r2
        }

        if (description == null) {
            description = HighCard;
            topRank = RanksOfArray[4].naming();//输出最大的r的值最后打印出来
        }

        String outPut = String.format(description, topRank,//输出description，后面的是R1和R2，参见题目要求
                secondRank == null ? "" : secondRank);
        System.out.println(String.format(PlayerCondition, outPut));
    }
//===================================================================================================================
    /**
     * To judge which category the five cards in hands belong to. Nine
     * categories are divided into three methods, whether flush or not, whether
     * straight or not and whether a kind or not.
     */
    private static boolean whetherFlush() {//static调用static方法，在main中调用
        for (int i = 1; i < Suits.size(); i++) { //suit size指花色的数量，只有5张牌，所以只有5个花色

            // To verify whether it is the same suit.
            if (!Suits.get(i - 1).equals(Suits.get(i))) {//？
                return false;//后一个与前一个相比，如果不一样，那么返回false，意思是不是flush
            }
        }
        return true;//否则就是flush 
    }

    /**
     * To verify whether it is a straight.
     */
    private static boolean whetherStriaght() {
        for (int i = 1; i < RanksOfArray.length; i++) {
            int diff = RanksOfArray[i - 1].getValue()//前一个减去后一个，如果是-1就是straight，否则不是
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
    //如果不用
    private static String[] whetherAKind() {
        int[] hashSet = new int[Rank.ACE.getValue() + 1];//计算出现次数，14个数作为参照，拿value作为index
        //the value of each rank is treated as the index of the hashset to count the occcurence of each rank
        for (Rank rank : RanksOfArray) {//遍历输入的五个数据
            hashSet[rank.getValue()]++;//value就是index，每遇到一个数，该对应的index就加一，计算出现次数
        }
//下面是根据已遍历到的出现次数，来判断是属于哪种description：
        String[] result = new String[3];//上面result 0,1,2共3个
        int value = seekOccurence(4, hashSet);//在hashset的数组中找，有没有出现4次的牌
        if (value != -1) {
            result[0] = FourOfAKind;
            result[1] = Rank.naming(value);
            return result;
        }

        // If the occurrence number is 3, it needs further judgement to confirm
        // whether there are 2 card which are the same.
        value = seekOccurence(3, hashSet);
        if (value != -1) {//如果找到出现3次的value，此为r1
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
    private static int seekOccurence(int occurence, int[] hashSet) {//hashset是14 
        return seekOccurence(occurence, -1, hashSet);//seekOccurence的两个方法是overloading的关系，-1就是不检查，reduce the code redundant
    }

    private static int seekOccurence(int occurence, int unCheckPos,//special for indentifing two pair，如果之前查到一个pair，那
            //就不再检查这个，再检查另一个position，看是否是pair
            int[] hashSet) {
        for (int i = 0; i < hashSet.length; i++) {
            if (hashSet[i] == occurence && i != unCheckPos) {
                return i;
            }
        }
        return -1;//意思是没找到有多次出现次数的牌
    }

}