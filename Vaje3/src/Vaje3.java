public class Vaje3 {
    public static void main(String[] args) {
        Znaki.izpisi16bit((short)0b1111100111111001);
        Znaki.izpisi16bit(new short[] {(short)0b1111100011101000, (short)0b1111100111111010, (short)0b1111010001001111});
        Znaki.izpisi16bit("PPŠd");
        Znaki.izpisi64bit(0b1111111101000001010000000111110001000000010000000100000111111111L);
        Znaki.izpisi64bit(new long[] {0b1111111101000001010000010100100001111000010010000100000011100000L, 0b0011000001010000000100000001000000010000000100000001000011111111L});
        Znaki.izpisi64bit("P2 je super");
        Znaki.izpisi64bit(4342219536296657468L);
        Znaki.izpisi64bit(1746410238858002085L);
        Znaki.izpisi64bit(-36525672788885761L);
        Znaki.izpisi64bit("Če programiram, še bolj uživam!");
    }
}