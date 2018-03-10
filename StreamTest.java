import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamTest {

    public static void main(String[] args) {
        List<String> strs = Arrays.asList(new String[]{"aaa", "bbb", "ccc", "ddd", "eee", "fff"});
        List<String> resultStrList = null;
        resultStrList = strs.stream()
            .limit(3)
            .collect(Collectors.toList());
        resultStrList.stream()
            .forEach(t-> System.out.println(t));
    }
}

