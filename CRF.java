import HelperClasses.Pair;
import HelperClasses.sha256;
import java.util.*;
import java.io.*;

public class CRF extends sha256 {

    // Stores the output size of the function Fn()
    public int outputsize;

    CRF(int size) {
        outputsize = size;
        assert outputsize <= 64;
    }

    // Outputs the mapped outputSize characters long string s' for an input string s
    public String Fn(String s) {
        String shasum = encrypt(s);
        return shasum.substring(0,outputsize);
    }

    /*==========================
    |- To be done by students -|
    ==========================*/


    private String getSaltString(int stringSize) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < stringSize) { 
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public Pair<String, String> FindCollDeterministic() {
        
        Set<String> set = new HashSet<String>();
        set.add("");
        for(int i = 1 ; i <= outputsize; i++){
            Set<String> setNew = new HashSet<String>();
            for(String curr: set){
                String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
                for(int j = 0; j < SALTCHARS.length();j++){
                    setNew.add(curr + SALTCHARS.charAt(j));
                }
            }
            set = setNew;
        }
        Hashtable<String,String> table = new Hashtable<>();

        for(String curr : set){
            String sig = Fn(curr);
            if(table.get(sig) != null){
                String other = table.get(sig);
                return new Pair(other,curr);
            }else{
                table.put(sig, curr);
            }
        }
        return null;
    }

    public void FindCollRandomized() {
        
        String attempt_filename = "FindCollRandomizedAttempts.txt";
        String outcome_filename = "FindCollRandomizedOutcome.txt";

        Hashtable<String,String> table = new Hashtable<>();
        int times = 1000;

        try{
            FileOutputStream os = new FileOutputStream(attempt_filename, false);
            PrintStream p = new PrintStream(os);

            FileOutputStream os2 = new FileOutputStream(outcome_filename, false);
            PrintStream p2 = new PrintStream(os2);

            while(times >= 0){
                String rand = getSaltString(outputsize+1);
                String sig = Fn(rand);            
                if(table.get(sig) != null){
                    String other = table.get(sig);
                    p2.print("FOUND" + "\n" + other + "\n" + rand);
                    return;
                }else{
                    table.put(sig, rand);
                }
                p.println(sig);
                times--;
            }
            p2.println("NOT FOUND");
        }catch(Exception e){
            System.out.println("File not Found");
        }
    }
}