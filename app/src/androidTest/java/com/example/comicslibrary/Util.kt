import java.math.BigInteger
import java.security.MessageDigest
import java.security.PrivateKey
import java.security.PublicKey
import java.sql.Timestamp

fun getHash(timestamp: String, privateKey: String, publicKey: String ):String{
    val hashStr = timestamp + privateKey + publicKey
    val md = MessageDigest.getInstance("MD5")


    return BigInteger(1, md.digest(hashStr.toByteArray()))
        .toString(16)
        .padStart(32, '0')
}