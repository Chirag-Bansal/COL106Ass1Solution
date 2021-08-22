import java.io.FileInputStream;
import java.util.Scanner;

import HelperClasses.Pair;

class DriverClass
{
    public static void main(String args[]) throws Exception
    {
        FileInputStream fs;
        Scanner sc;

        // CRF

        CRF CRF_obj = new CRF(3);
        Pair<String, String> p = CRF_obj.FindCollDeterministic();
        
        try {
            if(p==null || !CRF_obj.Fn(p.first).equals(CRF_obj.Fn(p.second)))
                throw new AssertionError();
            System.out.println("CRF ran successfully!");
        }
        catch (AssertionError e) {
            System.err.println("CRF failed!");
        }

        CRF_obj.FindCollRandomized();


        // Signature

        fs = new FileInputStream("TestCases/SampleInput_sign.txt");
        sc = new Scanner(fs);
        String message_sign = sc.nextLine();
        String sk_sign = sc.nextLine();
        sc.close();
        fs.close();

        String output_sign = Signature.Sign(message_sign, sk_sign);

        fs = new FileInputStream("TestCases/SampleOutput_sign.txt");
        sc = new Scanner(fs);
        String signature_sign = sc.nextLine();
        fs.close();
        sc.close();

        try {
            if(!output_sign.equals(signature_sign))
                throw new AssertionError();
            System.out.println("Signature ran successfully!");
        }
        catch (AssertionError e) {
            System.err.println("Signature failed!");
        }

        
        fs = new FileInputStream("TestCases/SampleInput_verify.txt");
        sc = new Scanner(fs);
        String message_verify = sc.nextLine();
        String vk_verify = sc.nextLine();
        String signature_verify = sc.nextLine();
        sc.close();
        fs.close();

        boolean output_verify = Signature.Verify(message_verify, vk_verify, signature_verify);

        fs = new FileInputStream("TestCases/SampleOutput_verify.txt");
        sc = new Scanner(fs);
        String result_verify = sc.nextLine();
        fs.close();
        sc.close();

        try {
            if(result_verify.equals("True") != output_verify)
                throw new AssertionError();
            System.out.println("Verification ran successfully!");
        }
        catch (AssertionError e) {
            System.err.println("Verification Failed!");
        }

        sc.close();
    }
}