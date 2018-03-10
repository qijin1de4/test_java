public class CbtcDigest {

        /** ハッシュ値計算に使用する100の位の素数表 */
        private final static int[] PRIMES = { 101, 103, 107, 109, 113, 127, 131,
                        137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197,
                        199, 211, 223, 227, 281, 283, 293, 307, 311, 313, 317, 331, 337,
                        347, 349, 353, 359, 567, 373, 379, 383, 389, 397, 401, 409, 419,
                        421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491,
                        499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587,
                        593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659,
                        661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751,
                        757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839,
                        853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937,
                        941, 947, 953, 967, 971, 977, 983, 991, 997 };

        /** 16進数で使用されるchar文字表 */
        private final static char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6',
                        '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

        /**
         * 商品ディレクトリ用パスを商品管理番号のハッシュ値を算出する。
         *
         * @param manageNumber
         *            商品管理番号
         * @return 商品ディレクトリ用パス
         */
        public static String digest(String manageNumber) {
                long hash = manageNumber.hashCode();
                int prime = getPrime(hash);
                char[] cArray = toCharArray(hash);
                long calcedVal = prime;
                for (int i = 0; i < cArray.length; i++) {
                        calcedVal += cArray[i] * (prime - i);
                }

                // 16進数変換で4桁行くかをチェック
                // 行かない場合、50をかける。(最低値が101なので50かけで4桁は超える)
                if (calcedVal < 4096) {
                        calcedVal *= 50;
                }

                StringBuilder buffer = new StringBuilder(Long.toHexString(calcedVal))
                                .reverse();
                return buffer.substring(0, 4);
        }

        /**
         * 10進数を16進数のchar配列にして返す。<br>
         * ロジック的にはjava.lang.Long#toUnsignedString()と同様。<br>
         * 最後にStringに変換する処理が無駄なので実装し直し。
         *
         * @param hex
         *            10進数のlong値
         * @return 16進数のchar配列
         */
        public static char[] toCharArray(long hex) {
                char[] buf = new char[64];
                int charPos = 64;
                int radix = 1 << 4;
                long mask = radix - 1;
                do {
                        buf[--charPos] = DIGITS[(int) (hex & mask)];
                        hex >>>= 4;
                } while (hex != 0);

                int len = 64 - charPos;
                char[] result = new char[len];
                System.arraycopy(buf, charPos, result, 0, len);
                return result;
        }

        /**
         * 商品管理番号よりハッシュ値計算の為の素数を算出する。
         *
         * @param manageNumber
         *            商品管理番号
         * @return ハッシュ値計算の為の素数
         */
        private static int getPrime(long hash) {
                // ハッシュ値が負数の場合、正数へ変換
                if (hash < 0) {
                        hash = ~hash + 1;
                }
                int index = (int) hash % PRIMES.length;
                return PRIMES[index];
        }

        public static void main(String[] args) {
            System.out.println(digest(args[0]));
        }

}
