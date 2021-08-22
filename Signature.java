import HelperClasses.DigitalSignature;
import HelperClasses.Conversion;
import java.security.KeyPair;

public class Signature extends DigitalSignature {

    public SignatureKeys keygen() {
        KeyPair keys = super.generate_keys();
        String sk = Conversion.byteToHex(keys.getPrivate().getEncoded());
        String vk = Conversion.byteToHex(keys.getPublic().getEncoded());
        return new SignatureKeys(sk, vk);
    }

    public String BoundedMsgSign(String message, String sk) {

        assert message.length() == 64;

        return super.sign_message(message, sk);
    }

    public boolean BoundedMsgVerify(String message, String pk, String signature) {

        assert message.length() == 64;

        return super.verify_signature(message, pk, signature);
    }

    /*==========================
    |- To be done by students -|
    ==========================*/

    public static String Sign(String m, String sk) {
        CRF obj = new CRF(64);
        Signature s = new Signature();
        String f = obj.Fn(m);
        String signature = s.BoundedMsgSign(f, sk);
        return signature;
    }

    public static boolean Verify(String m, String vk, String sig) {
        CRF obj = new CRF(64);
        String f = obj.Fn(m);
        Signature s = new Signature();
        return s.BoundedMsgVerify(f, vk, sig);
    }
}
