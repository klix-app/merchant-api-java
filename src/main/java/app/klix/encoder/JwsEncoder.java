package app.klix.encoder;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import lombok.extern.slf4j.Slf4j;


import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Slf4j
public class JwsEncoder {

    public String encodeJwsBody(String payload, String merchantCertificateId, String privateKeyContent) {
        try {

            PrivateKey privateKey = createPrivateKeyObject(privateKeyContent);

            JWSSigner signer = new RSASSASigner(privateKey, true);
            JWSObject jwsObject = new JWSObject(
                    new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(merchantCertificateId).build(), new Payload(payload));
            jwsObject.sign(signer);
            return jwsObject.serialize();
        } catch (Exception ex) {
//            log.error(ex.getLocalizedMessage());
            return null;
        }
    }

    private PrivateKey createPrivateKeyObject(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {

        String temp = privateKey;
        String privKeyPEM = temp.replace("-----BEGIN PRIVATE KEY-----", "");
        privKeyPEM = privKeyPEM.replace("-----END PRIVATE KEY-----", "");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privKeyPEM.replaceAll("\\n", "").replaceAll("\\r", "")));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

}