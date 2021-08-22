package HelperClasses;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class DigitalSignature {

    protected KeyPair generate_keys() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");
            keyPairGen.initialize(2048);
            KeyPair pair = keyPairGen.generateKeyPair();

            return pair;
        }
        catch(Exception e) {
            return null;
        }
    }

    protected String sign_message(String message, String sk) {
        // Private key is the secret key sk
        try {
            Signature sign = Signature.getInstance("SHA256withDSA");
            PrivateKey privKey = Conversion.getPrivateKey(Conversion.hexToByte(sk));
            sign.initSign(privKey);

            sign.update(message.getBytes());
            byte[] signature = sign.sign();

            return Conversion.byteToHex(signature);
        }
        catch(Exception e) {
            return "";
        }
    }

    protected boolean verify_signature(String message, String pk, String signature) {
        try {
            Signature sign = Signature.getInstance("SHA256withDSA");
            PublicKey publKey = Conversion.getPublicKey(Conversion.hexToByte(pk));
            sign.initVerify(publKey);

            sign.update(message.getBytes());
            return sign.verify(Conversion.hexToByte(signature));
        }
        catch(Exception e) {
            return false;
        }
    }

}
