import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String cons_expr = sc.nextLine();
        String result = calc(cons_expr);
        System.out.println(result);
    }
    public static String calc(String cons_expr){

        String expr = cons_expr.replaceAll(" ","").toUpperCase();
        pattern_exception(expr);

        String str_result;
        String[] arrexpr;
        arrexpr = data_extract(expr);

        value_exception(arrexpr);

        str_result = math_operation(arrexpr);
        return str_result;
    }
    static String math_operation(String[] arrexpr){

        String str_num01="",str_num02="", str_result="";
        int int_num01,int_num02, int_result;

        String math_op = arrexpr[1];

        str_num01 = arrexpr[0];
        str_num02 = arrexpr[2];

        //System.out.println("arabic_or_roman previous!");
        if (arabic_or_roman(str_num01)){
            int_num01 = Integer.parseInt(str_num01);
            int_num02 = Integer.parseInt(str_num02);
            int_result = math_switch_ar(int_num01, int_num02, math_op);
            str_result = Integer.toString(int_result);
        }else{
            str_result = roman_operation(str_num01,str_num02, math_op);
        }
        return str_result;
    }
    static int math_switch_ar(int var01, int var02, String math_op){
        int int_result=0;
        switch(math_op) {
            case "+":
                int_result = var01 + var02;
                break;
            case "-":
                int_result = var01 - var02;
                break;
            case "*":
                int_result = var01 * var02;
                break;
            case "/":
                int_result = var01 / var02;
                break;
        }
        return int_result;
    }
    static int math_switch_ro(int var01, int var02, String math_op){
        int int_result=0;
        switch(math_op) {
            case "+":
                int_result = var01 + var02;
                break;
            case "-":
                if(var01 - var02 > 0){
                    int_result = var01 - var02;
                    break;
                }else{
                    throw new ArithmeticException("Roman calculation result can't be less than 1!");
                }
            case "*":
                int_result = var01 * var02;
                break;
            case "/":
                int_result = var01 / var02;
                break;
        }
        return int_result;
    }
    static String roman_operation(String str_num01, String str_num02, String math_op){

        int[] ar_arr = new int[]{1,2,3,4,5,6,7,8,9,10};
        String[] ro_arr = new String[]{"I","II","III","IV","V","VI","VII","VIII","IX","X"};

        int arr_index01=0,arr_index02=0,int_result;
        String buffer="",str_result;

        for (int i=0; i < ro_arr.length; i++){
            buffer = ro_arr[i];
            //System.out.println("[roman_operation] buffer: " + buffer);
            if(str_num01.equals(buffer)){
                arr_index01 = i;
            }
            if(str_num02.equals(buffer)){
                arr_index02 = i;
            }
        }

        int int_num01 = ar_arr[arr_index01];
        int int_num02 = ar_arr[arr_index02];

        int_result = math_switch_ro(int_num01, int_num02, math_op);
        str_result = to_roman(int_result);
        return str_result;
    }
    static String to_roman(int value){

        int[] ar_arr = new int[]{100,90,50,40,10,9,5,4,1};
        String[] ro_arr = new String[]{"C","XC","L","XL","X","IX","V","IV","I"};
        String result="",buffer="";

        for (int i = 0; i < ar_arr.length; i++){
            while(value >= ar_arr[i]){
                buffer = ro_arr[i];
                result = result.concat(buffer);
                value -= ar_arr[i];
            }
        }
        return result;
    }
    static void pattern_exception(String expr){
        String arabic_pattern = "^([0-9]{1,2})([/+*-])([0-9]{1,2})$"; // Arabic numbers only
        String roman_pattern = "^(X|IX|VIII|VII|VI|V|IV|III|II|I)([/+*-])(X|IX|VIII|VII|VI|V|IV|III|II|I)$"; // Roman numbers only

        boolean ar_result = Pattern.matches(arabic_pattern,expr);
        boolean ro_result = Pattern.matches(roman_pattern,expr);
        if(!ar_result && !ro_result){
            throw new ArithmeticException("[Pattern exception] Allowed only arabic numbers or only roman numbers and few operators (/,+,*,-).");
        }
    }
    static void value_exception(String[] arrexpr){

        String str_num01="",str_num02="";

        str_num01 = arrexpr[0];
        str_num02 = arrexpr[2];

        catch_arvar_1to10(str_num01);
        catch_arvar_1to10(str_num02);
    }
    static void catch_arvar_1to10(String str_num){
        // Its validating operand in range 1..10
        int int_num01;
        if(Pattern.matches("[0-9]{1,2}",str_num)){
            int_num01 = Integer.parseInt(str_num);
            if (11 > int_num01 && int_num01 > 0 ){
            }else{
                throw new ArithmeticException("[value_exception.catch_arvar_1to10] Operand should be in range 1..10");
            }
        }
    }
    static boolean arabic_or_roman(String str_num){
        boolean b;
        if(Pattern.matches("[0-9]{1,2}",str_num)){
            b = true;
        }else{
            b = false;
        }
        return b;
    }
    private static String[] data_extract(String str){
        //This method created to extracting data from string, and return String[] for 3 elements
        String[] arrexpr = str.split("");
        String str_num01="",str_num02="",math_op="",boofer="";
        String[] returnarr = new String[3];

        for (String s : arrexpr) {
            if (Pattern.matches("[/+*-]", s)) {
                math_op = s;
            } else if (math_op == "") {
                boofer = s;
                str_num01 = str_num01.concat(boofer);
            } else {
                boofer = s;
                str_num02 = str_num02.concat(boofer);
            }
        }
        returnarr[0] = str_num01;
        returnarr[1] = math_op;
        returnarr[2] = str_num02;
        return returnarr;
    }
}