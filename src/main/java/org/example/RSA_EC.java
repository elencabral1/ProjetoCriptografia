package org.example;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class RSA_EC {

    public static void main(String args[]) {
        String msg = "You are the champions!";
        String msgcifrada = null;
        String msgdecifrada = null;

        BigInteger p = new BigInteger("17");
        BigInteger q = new BigInteger("23");
        BigInteger n = p.multiply(q);
        BigInteger e = new BigInteger("3");

        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        BigInteger d = e.modInverse(m);

        System.out.println("p:" + p);
        System.out.println("q:" + q);
        System.out.println("n:" + n);
        System.out.println("e:" + e);
        System.out.println("d:" + d);

        byte[] msgBytes = msg.getBytes(StandardCharsets.US_ASCII);

        StringBuilder cifradaStringBuilder = new StringBuilder();
        for (byte b : msgBytes) {
            BigInteger msgBigInt = new BigInteger(new byte[] { b });
            BigInteger cifrada = msgBigInt.modPow(e, n);
            cifradaStringBuilder.append(cifrada).append(" ");
        }
        msgcifrada = cifradaStringBuilder.toString().trim();

        System.out.println("msg cifrada: " + msgcifrada);

        String[] cifradaParts = msgcifrada.split(" ");
        StringBuilder decifradaStringBuilder = new StringBuilder();
        for (String part : cifradaParts) {
            BigInteger cifradaBigInt = new BigInteger(part);
            BigInteger decifrada = cifradaBigInt.modPow(d, n);
            decifradaStringBuilder.append((char) decifrada.byteValueExact());
        }
        msgdecifrada = decifradaStringBuilder.toString();

        System.out.println("msg decifrada:" + msgdecifrada);
    }
}
